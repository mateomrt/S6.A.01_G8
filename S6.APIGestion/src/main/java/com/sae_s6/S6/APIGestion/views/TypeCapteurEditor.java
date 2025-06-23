package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.TypeCapteurService;
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
 * Éditeur pour l'entité TypeCapteur.
 * Permet de créer, modifier ou supprimer un type de capteur via une interface utilisateur.
 */
@Scope("prototype")
@SpringComponent
@UIScope
public class TypeCapteurEditor extends VerticalLayout implements KeyNotifier {

    private final TypeCapteurService typeCapteurService;

    /**
     * Le type de capteur actuellement édité.
     */
    private TypeCapteur typeCapteur;

    /**
     * Champs de texte pour les propriétés du type de capteur.
     */
    public TextField libelleTypeCapteur = new TextField("Libellé type capteur");
    public TextField modeTypeCapteur = new TextField("Mode du type capteur");

    /**
     * Boutons pour les actions de sauvegarde, annulation et suppression.
     */
    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    public Button cancel = new Button("Annuler");
    public Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    public HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    /**
     * Binder pour lier les champs de l'interface utilisateur à l'entité TypeCapteur.
     */
    public Binder<TypeCapteur> binder = new Binder<>(TypeCapteur.class);

    /**
     * Gestionnaire de changement pour notifier les modifications.
     */
    private ChangeHandler changeHandler;

    /**
     * Constructeur de l'éditeur de type de capteur.
     *
     * @param typeCapteurService Service pour gérer les opérations sur les types de capteurs.
     */
    public TypeCapteurEditor(TypeCapteurService typeCapteurService) {
        this.typeCapteurService = typeCapteurService;

        // Organisation des champs en ligne horizontale
        HorizontalLayout fieldsRow = new HorizontalLayout(libelleTypeCapteur, modeTypeCapteur);
        fieldsRow.setWidthFull();
        fieldsRow.setSpacing(true);

        // Configuration de la largeur des champs pour une répartition équitable
        libelleTypeCapteur.setWidthFull();
        modeTypeCapteur.setWidthFull();

        add(fieldsRow, actions);

        // Configuration du binder
        binder.forField(libelleTypeCapteur)
              .asRequired("Le libellé du type capteur est obligatoire")
              .withValidationStatusHandler(status -> {
                  libelleTypeCapteur.setErrorMessage(status.getMessage().orElse(""));
                  libelleTypeCapteur.setInvalid(status.isError());
              })
              .bind(TypeCapteur::getLibelleTypeCapteur, TypeCapteur::setLibelleTypeCapteur);

        binder.forField(modeTypeCapteur)
              .asRequired("Le mode du type capteur est obligatoire")
              .withValidationStatusHandler(status -> {
                  modeTypeCapteur.setErrorMessage(status.getMessage().orElse(""));
                  modeTypeCapteur.setInvalid(status.isError());
              })
              .bind(TypeCapteur::getModeTypeCapteur, TypeCapteur::setModeTypeCapteur);

        // Configuration et style des composants
        setSpacing(true);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addKeyPressListener(Key.ENTER, e -> save());

        // Configuration des actions des boutons
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());

        // L'éditeur est caché par défaut
        setVisible(false);
    }

    /**
     * Supprime le type de capteur actuellement édité.
     */
    void delete() {
        typeCapteurService.deleteTypeCapteurById(typeCapteur.getId());
        changeHandler.onChange();
    }

    /**
     * Sauvegarde le type de capteur actuellement édité.
     */
    void save() {
        try {
            binder.writeBean(typeCapteur); // Valide et écrit les données dans l'objet typeCapteur
            if (typeCapteur.getId() == null) {
                typeCapteurService.saveTypeCapteur(typeCapteur);
            } else {
                typeCapteurService.updateTypeCapteur(typeCapteur);
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
        clearForm();
        if (changeHandler != null) {
            changeHandler.onChange();
        }
    }

    /**
     * Efface les champs du formulaire.
     */
    private void clearForm() {
        binder.setBean(null);
        libelleTypeCapteur.clear();
        modeTypeCapteur.clear();
    }

    /**
     * Interface pour gérer les changements après une action.
     */
    public interface ChangeHandler {
        void onChange();
    }

    /**
     * Prépare l'éditeur pour un type de capteur donné.
     *
     * @param a Le type de capteur à éditer.
     */
    public final void editTypeCapteur(TypeCapteur a) {
        if (a == null) {
            setVisible(false);
            clearForm();
            return;
        }

        final boolean persisted = a.getId() != null;

        if (persisted) {
            typeCapteur = typeCapteurService.getTypeCapteurById(a.getId());
        } else {
            typeCapteur = a;
        }

        cancel.setVisible(true);
        delete.setVisible(persisted);

        binder.setBean(typeCapteur);

        setVisible(true);
        libelleTypeCapteur.focus();
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