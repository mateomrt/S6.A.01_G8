package com.sae_s6.S6.APIGestion.views;


import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.CapteurService;
import com.sae_s6.S6.APIGestion.service.MurService;
import com.sae_s6.S6.APIGestion.service.SalleService;
import com.sae_s6.S6.APIGestion.service.TypeCapteurService;
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
public class CapteurEditor extends VerticalLayout implements KeyNotifier {

	private final CapteurService capteurService;
	private final MurService murService;
	private final SalleService salleService;
	private final TypeCapteurService typeCapteurService;

	/**
	 * The currently edited auteur
	 */
	private Capteur capteur;

	/* Fields to edit properties in Auteur entity */
	TextField libelleCapteur = new TextField("Libellé capteur");
	TextField positionXCapteur = new TextField("Position X");
	TextField positionYCapteur = new TextField("Position Y");
    
	ComboBox<Mur> murComboBox = new ComboBox<>("Mur");
	ComboBox<Salle> salleComboBox = new ComboBox<>("Salle");
	ComboBox<TypeCapteur> typeCapteurComboBox = new ComboBox<>("Type capteur");
	

	HorizontalLayout fields = new HorizontalLayout(libelleCapteur, positionXCapteur, positionYCapteur);

	/* Action buttons */
	Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
	Button cancel = new Button("Annuler");
	Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<Capteur> binder = new Binder<>(Capteur.class);
	private ChangeHandler changeHandler;

	public CapteurEditor(CapteurService capteurService, MurService murService, SalleService salleService,  TypeCapteurService typeCapteurService) {
		this.capteurService = capteurService;
		this.salleService = salleService;
		this.murService = murService;
		this.typeCapteurService = typeCapteurService;

		murComboBox.setPlaceholder("Sélectionner un mur");
		murComboBox.setClearButtonVisible(true);
		murComboBox.setItemLabelGenerator(Mur::getDesc);


		salleComboBox.setPlaceholder("Sélectionner une salle");
		salleComboBox.setClearButtonVisible(true);
		salleComboBox.setItemLabelGenerator(Salle::getDesc);

		typeCapteurComboBox.setPlaceholder("Sélectionner un type de capteur");
		typeCapteurComboBox.setClearButtonVisible(true);
		typeCapteurComboBox.setItemLabelGenerator(TypeCapteur::getDesc);

		add(libelleCapteur, murComboBox,  salleComboBox, typeCapteurComboBox, positionXCapteur, positionYCapteur, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);
		binder.forField(murComboBox)
            .asRequired("Mur est obligatoire")
            .bind(Capteur::getMurNavigation, Capteur::setMurNavigation);
			
		binder.forField(salleComboBox)
            .asRequired("Salle est obligatoire")
            .bind(Capteur::getSalleNavigation, Capteur::setSalleNavigation);
		
		binder.forField(typeCapteurComboBox)
            .asRequired("Type capteur est obligatoire")
            .bind(Capteur::getTypeCapteurNavigation, Capteur::setTypeCapteurNavigation);

		binder.forField(positionXCapteur)
			.withNullRepresentation("") 
			.withConverter(new StringToDoubleConverter("La superficie doit être un nombre"))
			.bind(Capteur::getPositionXCapteur, Capteur::setPositionXCapteur);

		binder.forField(positionYCapteur)
			.withNullRepresentation("") 
			.withConverter(new StringToDoubleConverter("La superficie doit être un nombre"))
			.bind(Capteur::getPositionYCapteur, Capteur::setPositionYCapteur);

		// Configure and style components
		setSpacing(true);

		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

		addKeyPressListener(Key.ENTER, e -> save());

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		cancel.addClickListener(e -> editCapteur(capteur));
		setVisible(false);
	}

	void delete() {
		capteurService.deleteCapteurById(capteur.getId());
		changeHandler.onChange();
	}

	void save() {
        if (capteur.getId() == null) {
            // If the livre is new, we save it
            capteurService.saveCapteur(capteur);
        } else {
            // If the livre already exists, we update it
            capteurService.updateCapteur(capteur);
        }
        changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editCapteur(Capteur a) {
		if (a == null) {
			setVisible(false);
			return;
		}

		murComboBox.setItems(murService.getAllMurs());
		salleComboBox.setItems(salleService.getAllSalles());
		typeCapteurComboBox.setItems(typeCapteurService.getAllTypeCapteurs());

		final boolean persisted = a.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			// In a more complex app, you might want to load
			// the entity/DTO with lazy loaded relations for editing
			capteur = capteurService.getCapteurById(a.getId());
		}
		else {
			capteur = a;
		}
		cancel.setVisible(persisted);

		// Bind auteur properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(capteur);

		setVisible(true);

		// Focus first name initially
		libelleCapteur.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		changeHandler = h;
	}

}
