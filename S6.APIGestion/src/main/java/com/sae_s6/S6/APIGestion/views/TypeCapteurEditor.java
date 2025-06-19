package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.TypeCapteurService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
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
public class TypeCapteurEditor extends VerticalLayout implements KeyNotifier {

	private final TypeCapteurService typeCapteurService;

	/**
	 * The currently edited auteur
	 */
	private TypeCapteur typeCapteur;

	/* Fields to edit properties in Auteur entity */
	TextField libelleTypeCapteur = new TextField("Libellé type capteur");
	TextField modeTypeCapteur = new TextField("Mode du type capteur");
    

	HorizontalLayout fields = new HorizontalLayout(libelleTypeCapteur, modeTypeCapteur);

	/* Action buttons */
	Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
	Button cancel = new Button("Annuler");
	Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<TypeCapteur> binder = new Binder<>(TypeCapteur.class);
	private ChangeHandler changeHandler;

	public TypeCapteurEditor(TypeCapteurService typeCapteurService) {
		this.typeCapteurService = typeCapteurService;

		add(libelleTypeCapteur, modeTypeCapteur, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);

		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

		addKeyPressListener(Key.ENTER, e -> save());

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		cancel.addClickListener(e -> editTypeCapteur(typeCapteur));
		setVisible(false);
	}

	void delete() {
		typeCapteurService.deleteTypeCapteurById(typeCapteur.getId());
		changeHandler.onChange();
	}

	void save() {
        if (typeCapteur.getId() == null) {
            // If the livre is new, we save it
            typeCapteurService.saveTypeCapteur(typeCapteur);
        } else {
            // If the livre already exists, we update it
            typeCapteurService.updateTypeCapteur(typeCapteur);
        }
        changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editTypeCapteur(TypeCapteur a) {
		if (a == null) {
			setVisible(false);
			return;
		}


		final boolean persisted = a.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			// In a more complex app, you might want to load
			// the entity/DTO with lazy loaded relations for editing
			typeCapteur = typeCapteurService.getTypeCapteurById(a.getId());
		}
		else {
			typeCapteur = a;
		}
		cancel.setVisible(persisted);

		// Bind auteur properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(typeCapteur);

		setVisible(true);

		// Focus first name initially
		libelleTypeCapteur.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		changeHandler = h;
	}

}
