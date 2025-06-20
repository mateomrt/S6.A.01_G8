package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class TypeCapteurDonneeEditor extends VerticalLayout implements KeyNotifier {

    private final TypeCapteurDonneeService typeCapteurDonneeService;
    private final DonneeService donneeService;
    private final TypeCapteurService typeCapteurService;

    private TypeCapteurDonnee typeCapteurDonnee;

    // Champs pour les propriétés de navigation
    public ComboBox<Donnee> donneeComboBox = new ComboBox<>("Donnée");
    public ComboBox<TypeCapteur> typeCapteurComboBox = new ComboBox<>("Type Capteur");

    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    public Button cancel = new Button("Annuler");
    public Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<TypeCapteurDonnee> binder = new Binder<>(TypeCapteurDonnee.class);
    private ChangeHandler changeHandler;

    public TypeCapteurDonneeEditor(TypeCapteurDonneeService typeCapteurDonneeService, DonneeService donneeService, TypeCapteurService typeCapteurService) {
        this.typeCapteurDonneeService = typeCapteurDonneeService;
        this.donneeService = donneeService;
        this.typeCapteurService = typeCapteurService;

        // Configure ComboBox for Donnee
        donneeComboBox.setItems(donneeService.getAllDonnees());
        donneeComboBox.setItemLabelGenerator(Donnee::getLibelleDonnee);
        donneeComboBox.setPlaceholder("Sélectionner une donnée");

        // Configure ComboBox for TypeCapteur
        typeCapteurComboBox.setItems(typeCapteurService.getAllTypeCapteurs());
        typeCapteurComboBox.setItemLabelGenerator(TypeCapteur::getLibelleTypeCapteur);
        typeCapteurComboBox.setPlaceholder("Sélectionner un type capteur");

        add(donneeComboBox, typeCapteurComboBox, actions);

        // Liaison des champs
        binder.forField(donneeComboBox)
              .bind(TypeCapteurDonnee::getDonneeNavigation, TypeCapteurDonnee::setDonneeNavigation);

        binder.forField(typeCapteurComboBox)
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

    void delete() {
        typeCapteurDonneeService.deleteTypeCapteurDonneeById(typeCapteurDonnee.getId());
        changeHandler.onChange();
    }

    void save() {
        typeCapteurDonneeService.saveTypeCapteurDonnee(typeCapteurDonnee);
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

    public final void editTypeCapteurDonnee(TypeCapteurDonnee t) {
        if (t == null) {
            setVisible(false);
            return;
        }

        final boolean isNewTypeCapteurDonnee = (t.getId() == null);

        if (isNewTypeCapteurDonnee) {
            typeCapteurDonnee = t;
            delete.setVisible(false); // Pas de bouton supprimer pour un nouveau type capteur donnée
        } else {
            typeCapteurDonnee = typeCapteurDonneeService.getTypeCapteurDonneeById(t.getId());
            delete.setVisible(true); // Afficher le bouton supprimer pour un type capteur donnée existant
        }

        binder.setBean(typeCapteurDonnee);
        setVisible(true);
        donneeComboBox.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}