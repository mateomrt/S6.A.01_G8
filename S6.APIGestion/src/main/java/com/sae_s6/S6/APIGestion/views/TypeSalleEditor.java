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

@SpringComponent
@UIScope
public class TypeSalleEditor extends VerticalLayout implements KeyNotifier {

    private final TypeSalleService typeSalleService;

    private TypeSalle typeSalle;

    public TextField libelleTypeSalle = new TextField("Libellé type salle");

    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    public Button cancel = new Button("Annuler");
    public Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<TypeSalle> binder = new Binder<>(TypeSalle.class);
    private ChangeHandler changeHandler;

    public TypeSalleEditor(TypeSalleService typeSalleService) {
        this.typeSalleService = typeSalleService;

        add(libelleTypeSalle, actions);

        binder.bindInstanceFields(this);

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
        typeSalleService.deleteTypeSalleById(typeSalle.getId());
        changeHandler.onChange();
    }

    void save() {
        typeSalleService.saveTypeSalle(typeSalle);
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

    public final void editTypeSalle(TypeSalle t) {
        if (t == null) {
            setVisible(false);
            return;
        }

        final boolean isNewTypeSalle = (t.getId() == null);

        if (isNewTypeSalle) {
            typeSalle = t;
            delete.setVisible(false); // Pas de bouton supprimer pour un nouveau type de salle
        } else {
            typeSalle = typeSalleService.getTypeSalleById(t.getId());
            delete.setVisible(true); // Afficher le bouton supprimer pour un type de salle existant
        }

        binder.setBean(typeSalle);
        setVisible(true);
        libelleTypeSalle.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}