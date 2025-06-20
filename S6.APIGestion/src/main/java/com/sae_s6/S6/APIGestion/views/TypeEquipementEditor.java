package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.service.TypeEquipementService;
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

@SpringComponent
@UIScope
public class TypeEquipementEditor extends VerticalLayout implements KeyNotifier {
    	private final TypeEquipementService typeEquipementService;

	/**
	 * The currently edited TypeEquipement
	 */
	private TypeEquipement typeEquipement;

	/* Fields to edit properties in TypeEquipement entity */
	TextField libelleTypeEquipement = new TextField("Libellé type équipement");
    
	

	HorizontalLayout fields = new HorizontalLayout(libelleTypeEquipement);

	/* Action buttons */
	Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
	Button cancel = new Button("Annuler");
	Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<TypeEquipement> binder = new Binder<>(TypeEquipement.class);
	private ChangeHandler changeHandler;

	public TypeEquipementEditor(TypeEquipementService typeEquipementService) {
		this.typeEquipementService = typeEquipementService;

		

		add(libelleTypeEquipement, actions);

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
		cancel.addClickListener(e -> editTypeEquipement(typeEquipement));
		setVisible(false);
	}

	void delete() {
		typeEquipementService.deleteTypeEquipementById(typeEquipement.getId());
		changeHandler.onChange();
	}

	void save() {
        if (typeEquipement.getId() == null) {
            // If the typeEquipement is new, we save it
            typeEquipementService.saveTypeEquipement(typeEquipement);
        } else {
            // If the typeEquipement already exists, we update it
            typeEquipementService.updateTypeEquipement(typeEquipement);
        }
        changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editTypeEquipement(TypeEquipement a) {
		if (a == null) {
			setVisible(false);
			return;
		}

		final boolean persisted = a.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			// In a more complex app, you might want to load
			// the entity/DTO with lazy loaded relations for editing
			typeEquipement = typeEquipementService.getTypeEquipementById(a.getId());
		}
		else {
			typeEquipement = a;
		}
		cancel.setVisible(persisted);

		// Bind auteur properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(typeEquipement);

		setVisible(true);

		// Focus first name initially
		libelleTypeEquipement.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		changeHandler = h;
	}

}
