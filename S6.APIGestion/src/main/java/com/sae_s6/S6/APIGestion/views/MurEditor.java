package com.sae_s6.S6.APIGestion.views;

import java.util.Locale;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.service.BatimentService;
import com.sae_s6.S6.APIGestion.service.MurService;
import com.sae_s6.S6.APIGestion.service.SalleService;
import com.sae_s6.S6.APIGestion.service.TypeSalleService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 * A simple example to introduce building forms. As your real application is probably much
 * more complicated than this example, you could re-use this form in multiple places. This
 * example component is only used in MainView.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX.
 */
@SpringComponent
@UIScope
public class MurEditor extends VerticalLayout implements KeyNotifier {

	private final MurService murService;
	private final SalleService salleService;

	/**
	 * The currently edited auteur
	 */
	private Mur mur;

	/* Fields to edit properties in Auteur entity */
	TextField libelleMur = new TextField("Libellé mur");
	TextField hauteur = new TextField("Hauteur");
	TextField longueur = new TextField("longueur");
    
	ComboBox<Salle> SalleComboBox = new ComboBox<>("Salle");
	

	HorizontalLayout fields = new HorizontalLayout(libelleSalle, superficie);

	/* Action buttons */
	Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
	Button cancel = new Button("Annuler");
	Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<Mur> binder = new Binder<>(Mur.class);
	private ChangeHandler changeHandler;

	public MurEditor(MurService murService, SalleService salleService) {
		this.murService = murService;
		this.salleService = salleService;

		SalleComboBox.setPlaceholder("Sélectionner une salle");
		SalleComboBox.setClearButtonVisible(true);
		// do it after :
		//auteurComboBox.setItems(auteurService.getAllAuteurs());
		SalleComboBox.setItemLabelGenerator(Salle::getDesc);


		add(libelleSalle, superficie, SalleComboBox, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);
		binder.forField(SalleComboBox)
            .asRequired("Salle est obligatoire")
            .bind(Mur::getSalleNavigation, Mur::setSalleNavigation);
			
		
		
		// Configure and style components
		setSpacing(true);

		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

		addKeyPressListener(Key.ENTER, e -> save());

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		cancel.addClickListener(e -> editMur(mur));
		setVisible(false);
	}

	void delete() {
		murService.deleteMur(mur.getId());
		changeHandler.onChange();
	}

	void save() {
        if (mur.getId() == null) {
            // If the livre is new, we save it
            murService.saveMur(mur);
        } else {
            // If the livre already exists, we update it
            murService.updateMur(mur);
        }
        changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editMur(Mur a) {
		if (a == null) {
			setVisible(false);
			return;
		}

		SalleComboBox.setItems(salleService.getAllSalles());

		final boolean persisted = a.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			// In a more complex app, you might want to load
			// the entity/DTO with lazy loaded relations for editing
			mur = murService.getMurById(a.getId());
		}
		else {
			mur = a;
		}
		cancel.setVisible(persisted);

		// Bind auteur properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(mur);

		setVisible(true);

		// Focus first name initially
		libelleSalle.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		changeHandler = h;
	}

}
