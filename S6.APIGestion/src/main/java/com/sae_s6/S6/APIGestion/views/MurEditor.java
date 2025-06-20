package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.service.MurService;
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
 * Un simple éditeur de murs.
 * Basé sur l'exemple BatimentEditor.
 */
@SpringComponent
@UIScope
public class MurEditor extends VerticalLayout implements KeyNotifier {

    private final MurService murService;

    /**
     * Le mur actuellement édité
     */
    private Mur mur;

    /* Champs publics pour les tests */
    public TextField nom = new TextField("Nom du mur");

    /* Boutons d'action */
    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    public Button cancel = new Button("Annuler");
    public Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Mur> binder = new Binder<>(Mur.class);
    private ChangeHandler changeHandler;

    @Autowired
    public MurEditor(MurService murService) {
        this.murService = murService;

        add(nom, actions);

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
        if (mur != null && mur.getId() != null) {
            murService.deleteMurById(mur.getId());
            changeHandler.onChange();
        }
    }

    void save() {
        if (mur != null) {
            murService.saveMur(mur);
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

    public final void editMur(Mur m) {
        if (m == null) {
            setVisible(false);
            return;
        }

        final boolean isNewMur = (m.getId() == null);

        if (isNewMur) {
            // Nouveau mur
            mur = m;
            delete.setVisible(false); // Pas de bouton supprimer pour un nouveau mur
        } else {
            // Mur existant - récupération depuis la base de données
            try {
                Mur freshMur = murService.getMurById(m.getId());
                mur = (freshMur != null) ? freshMur : m;
                delete.setVisible(true); // Afficher le bouton supprimer pour un mur existant
            } catch (Exception e) {
                // En cas d'erreur, utiliser l'objet fourni
                mur = m;
                delete.setVisible(true);
            }
        }

        // Bind mur properties to similarly named fields
        binder.setBean(mur);

        setVisible(true);

        // Focus first component
        nom.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when save, delete or cancel is clicked
        changeHandler = h;
    }
}