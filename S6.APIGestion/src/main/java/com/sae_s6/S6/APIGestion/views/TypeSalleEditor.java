package com.sae_s6.S6.APIGestion.views;


import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.service.TypeSalleService;
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
public class TypeSalleEditor extends VerticalLayout implements KeyNotifier {

	private final TypeSalleService typeSalleService;

	/**
	 * The currently edited auteur
	 */
	private TypeSalle typeSalle;

	/* Fields to edit properties in Auteur entity */
	TextField libelleTypeSalle = new TextField("Libellé type salle");
    
	

	HorizontalLayout fields = new HorizontalLayout(libelleTypeSalle);

	/* Action buttons */
	Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
	Button cancel = new Button("Annuler");
	Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<TypeSalle> binder = new Binder<>(TypeSalle.class);
	private ChangeHandler changeHandler;

	public TypeSalleEditor(TypeSalleService typeSalleService) {
		this.typeSalleService = typeSalleService;

		

		add(libelleTypeSalle, actions);

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
		cancel.addClickListener(e -> editTypeSalle(typeSalle));
		setVisible(false);
	}

	void delete() {
		typeSalleService.deleteTypeSalleById(typeSalle.getId());
		changeHandler.onChange();
	}

	void save() {
        if (typeSalle.getId() == null) {
            // If the livre is new, we save it
            typeSalleService.saveTypeSalle(typeSalle);
        } else {
            // If the livre already exists, we update it
            typeSalleService.updateTypeSalle(typeSalle);
        }
        changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editTypeSalle(TypeSalle a) {
		if (a == null) {
			setVisible(false);
			return;
		}

		final boolean persisted = a.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			// In a more complex app, you might want to load
			// the entity/DTO with lazy loaded relations for editing
			typeSalle = typeSalleService.getTypeSalleById(a.getId());
		}
		else {
			typeSalle = a;
		}
		cancel.setVisible(persisted);

		// Bind auteur properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(typeSalle);

		setVisible(true);

		// Focus first name initially
		libelleTypeSalle.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		changeHandler = h;
	}

}
