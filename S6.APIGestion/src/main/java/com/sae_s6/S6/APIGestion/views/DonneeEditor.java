package com.sae_s6.S6.APIGestion.views;

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
import org.springframework.context.annotation.Scope;

/**
 * Éditeur pour l'entité Donnee.
 * Permet de créer, modifier ou supprimer une donnée via une interface utilisateur.
 */
@Scope("prototype")
@SpringComponent
@UIScope
public class DonneeEditor extends VerticalLayout implements KeyNotifier {

    private final DonneeService donneeService;

    /**
     * La donnée actuellement éditée.
     */
    private Donnee donnee;

    /**
     * Champs de texte pour les propriétés de la donnée.
     */
    public TextField libelleDonnee = new TextField("Libellé donnée");
    TextField unite = new TextField("Unité");

    /**
     * Boutons pour les actions de sauvegarde, annulation et suppression.
     */
    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    Button cancel = new Button("Annuler");
    Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    /**
     * Binder pour lier les champs de l'interface utilisateur à l'entité Donnee.
     */
    Binder<Donnee> binder = new Binder<>(Donnee.class);
    private ChangeHandler changeHandler;

    /**
     * Constructeur de l'éditeur de donnée.
     *
     * @param donneeService Service pour gérer les opérations sur les données.
     */
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

        // Configure et style les composants
        setSpacing(true);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    /**
     * Supprime la donnée actuellement éditée.
     */
    void delete() {
        donneeService.deleteDonneeById(donnee.getId());
        changeHandler.onChange();
    }

    /**
     * Sauvegarde la donnée actuellement éditée.
     */
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

    /**
     * Annule l'édition en cours.
     */
    void cancel() {
        setVisible(false);
        if (changeHandler != null) {
            changeHandler.onChange();
        }
    }

    /**
     * Interface pour gérer les changements après une action.
     */
    public interface ChangeHandler {
        void onChange();
    }

    /**
     * Prépare l'éditeur pour une donnée donnée.
     *
     * @param a La donnée à éditer.
     */
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

    /**
     * Définit le gestionnaire de changement.
     *
     * @param h Le gestionnaire de changement.
     */
    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}