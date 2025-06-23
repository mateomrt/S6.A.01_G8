package com.sae_s6.S6.APIGestion.views;

import org.springframework.context.annotation.Scope;

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.service.DonneeService;
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

@Scope("prototype")
@SpringComponent
@UIScope
public class DonneeEditor extends VerticalLayout implements KeyNotifier {

    private final DonneeService donneeService;

	/**
	 * The currently edited donnee
	 */
	private Donnee donnee;

	/* Fields to edit properties in Donnee entity */
	public TextField libelleDonnee = new TextField("Libellé donnée");
	TextField unite = new TextField("Unité");

	/* Action buttons */
	public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
	Button cancel = new Button("Annuler");
	Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Donnee> binder = new Binder<>(Donnee.class);
    private ChangeHandler changeHandler;

    public DonneeEditor(DonneeService donneeService) {
        this.donneeService = donneeService;

		// Organisation des champs en ligne horizontale
		HorizontalLayout fieldsRow = new HorizontalLayout(libelleDonnee, unite);
		fieldsRow.setWidthFull();
		fieldsRow.setSpacing(true);

		// Configuration de la largeur des champs pour une répartition équitable
		libelleDonnee.setWidthFull();
		unite.setWidthFull();

		add(fieldsRow, actions);
		
		binder.bindInstanceFields(this);
		
		// Configure and style components
		setSpacing(true);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    void delete() {
        donneeService.deleteDonneeById(donnee.getId());
        changeHandler.onChange();
    }

	void save() {
        if (donnee.getId() == null) {
            // If the donnee is new, we save it
            donneeService.saveDonnee(donnee);
        } else {
            // If the donnee already exists, we update it
            donneeService.updateDonnee(donnee);
        }
        changeHandler.onChange();
    }

    void cancel() {
        setVisible(false);
        if (changeHandler != null) {
            changeHandler.onChange();
        }
    }

    public interface ChangeHandler {
        void onChange();
    }

	public final void editDonnee(Donnee a) {
		if (a == null) {
			setVisible(false);
			return;
		}

		final boolean persisted = a.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			// In a more complex app, you might want to load
			// the entity/DTO with lazy loaded relations for editing
			donnee = donneeService.getDonneeById(a.getId());
		}
		else {
			donnee = a;
		}
		cancel.setVisible(persisted);

		// Bind donnee properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(donnee);

		setVisible(true);

		// Focus first name initially
		libelleDonnee.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		changeHandler = h;
	}
}