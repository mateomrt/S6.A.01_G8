package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.service.BatimentService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Un simple éditeur de bâtiments.
 * Basé sur l'exemple AuteurEditor du professeur.
 */
@SpringComponent
@UIScope
public class BatimentEditor extends VerticalLayout implements KeyNotifier {

    private final BatimentService batimentService;

    /**
     * Le bâtiment actuellement édité
     */
    private Batiment batiment;

    /* Champs publics pour les tests (comme dans l'exemple du prof) */
    public TextField libelleBatiment = new TextField("Libellé du bâtiment");

    /* Boutons d'action */
    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    public Button cancel = new Button("Annuler");
    public Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Batiment> binder = new Binder<>(Batiment.class);
    private ChangeHandler changeHandler;

    @Autowired
    public BatimentEditor(BatimentService batimentService) {
        this.batimentService = batimentService;

        add(libelleBatiment, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    void delete() {
        if (batiment != null && batiment.getId() != null) {
            batimentService.deleteBatimentById(batiment.getId());
            changeHandler.onChange();
        }
    }

    void save() {
        if (batiment != null) {
            batimentService.saveBatiment(batiment);
            changeHandler.onChange();
        }
    }

    void cancel() {
        // Cache l'éditeur et désélectionne dans la grille
        setVisible(false);
        if (changeHandler != null) {
            changeHandler.onChange();
        }
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editBatiment(Batiment b) {
        if (b == null) {
            setVisible(false);
            return;
        }
        
        final boolean isNewBatiment = (b.getId() == null);
        
        if (isNewBatiment) {
            // Nouveau bâtiment
            batiment = b;
            delete.setVisible(false); // Pas de bouton supprimer pour un nouveau bâtiment
        } else {
            // Bâtiment existant - récupération depuis la base de données
            try {
                Batiment freshBatiment = batimentService.getBatimentById(b.getId());
                batiment = (freshBatiment != null) ? freshBatiment : b;
                delete.setVisible(true); // Afficher le bouton supprimer pour un bâtiment existant
            } catch (Exception e) {
                // En cas d'erreur, utiliser l'objet fourni
                batiment = b;
                delete.setVisible(true);
            }
        }

        // Bind batiment properties to similarly named fields
        binder.setBean(batiment);

        setVisible(true);

        // Focus first component
        libelleBatiment.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when save, delete or cancel is clicked
        changeHandler = h;
    }
}