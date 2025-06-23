package com.sae_s6.S6.APIGestion.views;

import org.springframework.context.annotation.Scope;

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.service.DonneeService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
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

        // Configuration du binder
        binder.forField(libelleDonnee)
              .asRequired("Le libellé de la donnée est obligatoire")
              .withValidationStatusHandler(status -> {
                  libelleDonnee.setErrorMessage(status.getMessage().orElse(""));
                  libelleDonnee.setInvalid(status.isError());
              })
              .bind(Donnee::getLibelleDonnee, Donnee::setLibelleDonnee);

        binder.forField(unite)
              .asRequired("L'unité est obligatoire")
              .withValidationStatusHandler(status -> {
                  unite.setErrorMessage(status.getMessage().orElse(""));
                  unite.setInvalid(status.isError());
              })
              .bind(Donnee::getUnite, Donnee::setUnite);

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
        try {
            binder.writeBean(donnee); // Valide et écrit les données dans l'objet donnee
            if (donnee.getId() == null) {
                donneeService.saveDonnee(donnee);
            } else {
                donneeService.updateDonnee(donnee);
            }
            changeHandler.onChange();
        } catch (ValidationException e) {
            Notification.show("Veuillez corriger les erreurs avant de sauvegarder.", 3000, Notification.Position.MIDDLE);
        }
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
            donnee = donneeService.getDonneeById(a.getId());
        } else {
            donnee = a;
        }
        cancel.setVisible(persisted);

        binder.setBean(donnee);

        setVisible(true);

        libelleDonnee.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}