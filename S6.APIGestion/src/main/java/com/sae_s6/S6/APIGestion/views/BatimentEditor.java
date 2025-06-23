package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.service.BatimentService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
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
 * Éditeur pour l'entité Batiment.
 * Permet de créer, modifier ou supprimer un bâtiment via une interface utilisateur.
 */
@Scope("prototype")
@SpringComponent
@UIScope
public class BatimentEditor extends VerticalLayout implements KeyNotifier {

    private final BatimentService batimentService;

    private Batiment batiment;

    /**
     * Champ de texte pour le libellé du bâtiment.
     */
    public TextField libelleBatiment = new TextField("Libellé du bâtiment");

    /**
     * Boutons pour les actions de sauvegarde, annulation et suppression.
     */
    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    public Button cancel = new Button("Annuler");
    public Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    /**
     * Binder pour lier les champs de l'interface utilisateur à l'entité Batiment.
     */
    Binder<Batiment> binder = new Binder<>(Batiment.class);
    private ChangeHandler changeHandler;

    /**
     * Constructeur de l'éditeur de bâtiment.
     *
     * @param batimentService Service pour gérer les opérations sur les bâtiments.
     */
    public BatimentEditor(BatimentService batimentService) {
        this.batimentService = batimentService;

        add(libelleBatiment, actions);

        // Configure la validation pour le champ libelleBatiment
        binder.forField(libelleBatiment)
              .asRequired("Le libellé du bâtiment est obligatoire")
              .withValidationStatusHandler(status -> {
                  libelleBatiment.setErrorMessage(status.getMessage().orElse(""));
                  libelleBatiment.setInvalid(status.isError());
              })
              .bind(Batiment::getLibelleBatiment, Batiment::setLibelleBatiment);

        setSpacing(true);

        // Configure les thèmes des boutons
        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        // Ajoute un écouteur pour la touche Entrée
        addKeyPressListener(Key.ENTER, e -> save());

        // Configure les actions des boutons
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    /**
     * Supprime le bâtiment actuellement édité.
     */
    void delete() {
        if (batiment != null && batiment.getId() != null) {
            batimentService.deleteBatimentById(batiment.getId());
            changeHandler.onChange();
        }
    }

    /**
     * Sauvegarde le bâtiment actuellement édité.
     */
    void save() {
        try {
            binder.writeBean(batiment); // Valide et écrit les données dans l'objet batiment
            batimentService.saveBatiment(batiment);
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
     * Prépare l'éditeur pour un bâtiment donné.
     *
     * @param b Le bâtiment à éditer.
     */
    public final void editBatiment(Batiment b) {
        if (b == null) {
            setVisible(false);
            return;
        }

        final boolean isNewBatiment = (b.getId() == null);

        if (isNewBatiment) {
            batiment = b;
            delete.setVisible(false);
        } else {
            try {
                Batiment freshBatiment = batimentService.getBatimentById(b.getId());
                batiment = (freshBatiment != null) ? freshBatiment : b;
                delete.setVisible(true);
            } catch (Exception e) {
                batiment = b;
                delete.setVisible(true);
            }
        }

        binder.setBean(batiment);
        setVisible(true);
        libelleBatiment.focus();
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