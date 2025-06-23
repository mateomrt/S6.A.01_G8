package com.sae_s6.S6.APIGestion.views;

import org.springframework.context.annotation.Scope;

import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.service.TypeSalleService;
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

/**
 * Éditeur pour l'entité TypeSalle.
 * Permet de créer, modifier ou supprimer un type de salle via une interface utilisateur.
 */
@Scope("prototype")
@SpringComponent
@UIScope
public class TypeSalleEditor extends VerticalLayout implements KeyNotifier {

    /**
     * Service pour gérer les opérations sur les types de salles.
     */
    private final TypeSalleService typeSalleService;

    /**
     * Le type de salle actuellement édité.
     */
    private TypeSalle typeSalle;

    /**
     * Champ de texte pour le libellé du type de salle.
     */
    public TextField libelleTypeSalle = new TextField("Libellé type salle");

    /**
     * Boutons pour les actions de sauvegarde, annulation et suppression.
     */
    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    public Button cancel = new Button("Annuler");
    public Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    /**
     * Binder pour lier les champs de l'interface utilisateur à l'entité TypeSalle.
     */
    Binder<TypeSalle> binder = new Binder<>(TypeSalle.class);

    /**
     * Gestionnaire de changement pour notifier les modifications.
     */
    private ChangeHandler changeHandler;

    /**
     * Constructeur de l'éditeur de type de salle.
     *
     * @param typeSalleService Service pour gérer les opérations sur les types de salles.
     */
    public TypeSalleEditor(TypeSalleService typeSalleService) {
        this.typeSalleService = typeSalleService;

        // Ajout des composants à la vue
        add(libelleTypeSalle, actions);

        // Configuration du binder
        binder.forField(libelleTypeSalle)
              .asRequired("Le libellé du type salle est obligatoire")
              .withValidationStatusHandler(status -> {
                  libelleTypeSalle.setErrorMessage(status.getMessage().orElse(""));
                  libelleTypeSalle.setInvalid(status.isError());
              })
              .bind(TypeSalle::getLibelleTypeSalle, TypeSalle::setLibelleTypeSalle);

        // Configuration des composants
        setSpacing(true);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        // Configuration des actions des boutons
        addKeyPressListener(Key.ENTER, e -> save());
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());

        // L'éditeur est caché par défaut
        setVisible(false);
    }

    /**
     * Supprime le type de salle actuellement édité.
     */
    void delete() {
        typeSalleService.deleteTypeSalleById(typeSalle.getId());
        changeHandler.onChange();
    }

    /**
     * Sauvegarde le type de salle actuellement édité.
     */
    void save() {
        try {
            binder.writeBean(typeSalle); // Valide et écrit les données dans l'objet typeSalle
            if (typeSalle.getId() == null) {
                typeSalleService.saveTypeSalle(typeSalle);
            } else {
                typeSalleService.updateTypeSalle(typeSalle);
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
     * Prépare l'éditeur pour un type de salle donné.
     *
     * @param t Le type de salle à éditer.
     */
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

    /**
     * Définit le gestionnaire de changement.
     *
     * @param h Le gestionnaire de changement.
     */
    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}