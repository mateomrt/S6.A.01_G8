package com.sae_s6.S6.APIGestion.views;

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

@SpringComponent
@UIScope
public class DonneeEditor extends VerticalLayout implements KeyNotifier {

    private final DonneeService donneeService;

    private Donnee donnee;

    public TextField libelleDonnee = new TextField("Libellé donnée");
    public TextField unite = new TextField("Unité");

    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    public Button cancel = new Button("Annuler");
    public Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Donnee> binder = new Binder<>(Donnee.class);
    private ChangeHandler changeHandler;

    public DonneeEditor(DonneeService donneeService) {
        this.donneeService = donneeService;

        add(libelleDonnee, unite, actions);

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
        donneeService.deleteDonneeById(donnee.getId());
        changeHandler.onChange();
    }

    void save() {
        donneeService.saveDonnee(donnee);
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

    public final void editDonnee(Donnee d) {
        if (d == null) {
            setVisible(false);
            return;
        }

        final boolean isNewDonnee = (d.getId() == null);

        if (isNewDonnee) {
            donnee = d;
            delete.setVisible(false); // Pas de bouton supprimer pour une nouvelle donnée
        } else {
            donnee = donneeService.getDonneeById(d.getId());
            delete.setVisible(true); // Afficher le bouton supprimer pour une donnée existante
        }

        binder.setBean(donnee);
        setVisible(true);
        libelleDonnee.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}