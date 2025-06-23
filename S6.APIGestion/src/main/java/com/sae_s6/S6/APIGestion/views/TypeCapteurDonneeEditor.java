package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonneeEmbedId;
import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.TypeCapteurDonneeService;
import com.sae_s6.S6.APIGestion.service.DonneeService;
import com.sae_s6.S6.APIGestion.service.TypeCapteurService;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.extern.slf4j.Slf4j;

/**
 * Éditeur pour l'entité TypeCapteurDonnee.
 * Permet de créer, modifier ou supprimer une association entre un type de capteur et une donnée via une interface utilisateur.
 */
@SpringComponent
@UIScope
@Slf4j
public class TypeCapteurDonneeEditor extends VerticalLayout implements KeyNotifier {

    private final TypeCapteurDonneeService typeCapteurDonneeService;
    private final DonneeService donneeService;
    private final TypeCapteurService typeCapteurService;

    /**
     * L'association actuellement éditée.
     */
    private TypeCapteurDonnee typeCapteurDonnee;

    /**
     * ID d'origine de l'association (utilisé pour les modifications).
     */
    private TypeCapteurDonneeEmbedId originalId = null;

    /**
     * ComboBox pour sélectionner une donnée.
     */
    public ComboBox<Donnee> donneeComboBox = new ComboBox<>("Donnée");

    /**
     * ComboBox pour sélectionner un type de capteur.
     */
    public ComboBox<TypeCapteur> typeCapteurComboBox = new ComboBox<>("Type Capteur");

    /**
     * Boutons pour les actions de sauvegarde, annulation et suppression.
     */
    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    public Button cancel = new Button("Annuler");
    public Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    /**
     * Binder pour lier les champs de l'interface utilisateur à l'entité TypeCapteurDonnee.
     */
    Binder<TypeCapteurDonnee> binder = new Binder<>(TypeCapteurDonnee.class);

    /**
     * Gestionnaire de changement pour notifier les modifications.
     */
    private ChangeHandler changeHandler;

    /**
     * Constructeur de l'éditeur.
     *
     * @param typeCapteurDonneeService Service pour gérer les associations entre types de capteurs et données.
     * @param donneeService Service pour gérer les données.
     * @param typeCapteurService Service pour gérer les types de capteurs.
     */
    public TypeCapteurDonneeEditor(TypeCapteurDonneeService typeCapteurDonneeService, DonneeService donneeService, TypeCapteurService typeCapteurService) {
        this.typeCapteurDonneeService = typeCapteurDonneeService;
        this.donneeService = donneeService;
        this.typeCapteurService = typeCapteurService;

        // Configuration des ComboBox
        donneeComboBox.setItems(donneeService.getAllDonnees());
        donneeComboBox.setItemLabelGenerator(Donnee::getLibelleDonnee);
        donneeComboBox.setPlaceholder("Sélectionner une donnée");

        typeCapteurComboBox.setItems(typeCapteurService.getAllTypeCapteurs());
        typeCapteurComboBox.setItemLabelGenerator(TypeCapteur::getLibelleTypeCapteur);
        typeCapteurComboBox.setPlaceholder("Sélectionner un type capteur");

        add(donneeComboBox, typeCapteurComboBox, actions);

        // Configuration du binder
        binder.forField(donneeComboBox)
              .asRequired("La donnée est obligatoire")
              .withValidationStatusHandler(status -> {
                  donneeComboBox.setErrorMessage(status.getMessage().orElse(""));
                  donneeComboBox.setInvalid(status.isError());
              })
              .bind(TypeCapteurDonnee::getDonneeNavigation, TypeCapteurDonnee::setDonneeNavigation);

        binder.forField(typeCapteurComboBox)
              .asRequired("Le type capteur est obligatoire")
              .withValidationStatusHandler(status -> {
                  typeCapteurComboBox.setErrorMessage(status.getMessage().orElse(""));
                  typeCapteurComboBox.setInvalid(status.isError());
              })
              .bind(TypeCapteurDonnee::getTypeCapteurNavigation, TypeCapteurDonnee::setTypeCapteurNavigation);

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
     * Supprime l'association actuellement éditée.
     */
    void delete() {
        if (typeCapteurDonnee != null && typeCapteurDonnee.getId() != null) {
            typeCapteurDonneeService.deleteTypeCapteurDonneeById(typeCapteurDonnee.getId());
            changeHandler.onChange();
        }
    }

    /**
     * Sauvegarde l'association actuellement éditée.
     */
    void save() {
        try {
            binder.writeBean(typeCapteurDonnee); // Valide et écrit les données dans l'objet typeCapteurDonnee

            TypeCapteurDonneeEmbedId newId = new TypeCapteurDonneeEmbedId(
                donneeComboBox.getValue().getId(),
                typeCapteurComboBox.getValue().getId()
            );

            typeCapteurDonnee.setId(newId);

            if (originalId != null) {
                // Cas modification : delete + save
                typeCapteurDonneeService.updateTypeCapteurDonnee(originalId, typeCapteurDonnee);
            } else {
                // Cas création
                typeCapteurDonneeService.saveTypeCapteurDonnee(typeCapteurDonnee);
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
     * Prépare l'éditeur pour une association donnée.
     *
     * @param t L'association à éditer.
     */
    public final void editTypeCapteurDonnee(TypeCapteurDonnee t) {
        if (t == null) {
            setVisible(false);
            return;
        }

        boolean isExisting = (t.getId() != null);

        if (isExisting) {
            typeCapteurDonnee = typeCapteurDonneeService.getTypeCapteurDonneeById(t.getId());
            if (typeCapteurDonnee == null) {
                log.warn("Aucune association trouvée pour l'id: {}", t.getId());
                setVisible(false);
                return;
            }
            originalId = t.getId(); // Mémorise l'ID d'origine
            delete.setVisible(true);
        } else {
            typeCapteurDonnee = new TypeCapteurDonnee();
            originalId = null;
            delete.setVisible(false);
        }

        binder.setBean(typeCapteurDonnee);
        donneeComboBox.setValue(typeCapteurDonnee.getDonneeNavigation());
        typeCapteurComboBox.setValue(typeCapteurDonnee.getTypeCapteurNavigation());

        setVisible(true);
        donneeComboBox.focus();
    }

    /**
     * Définit le gestionnaire de changement.
     *
     * @param h Le gestionnaire de changement.
     */
    public void setChangeHandler(ChangeHandler h) {
        this.changeHandler = h;
    }
}