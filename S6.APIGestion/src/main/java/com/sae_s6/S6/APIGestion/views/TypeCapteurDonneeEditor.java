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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.extern.slf4j.Slf4j;

@SpringComponent
@UIScope
@Slf4j
public class TypeCapteurDonneeEditor extends VerticalLayout implements KeyNotifier {

    private final TypeCapteurDonneeService typeCapteurDonneeService;
    private final DonneeService donneeService;
    private final TypeCapteurService typeCapteurService;

    private TypeCapteurDonnee typeCapteurDonnee;
    private TypeCapteurDonneeEmbedId originalId = null;

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

        donneeComboBox.setItems(donneeService.getAllDonnees());
        donneeComboBox.setItemLabelGenerator(Donnee::getLibelleDonnee);
        donneeComboBox.setPlaceholder("Sélectionner une donnée");

        typeCapteurComboBox.setItems(typeCapteurService.getAllTypeCapteurs());
        typeCapteurComboBox.setItemLabelGenerator(TypeCapteur::getLibelleTypeCapteur);
        typeCapteurComboBox.setPlaceholder("Sélectionner un type capteur");

        add(donneeComboBox, typeCapteurComboBox, actions);

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
        if (typeCapteurDonnee != null && typeCapteurDonnee.getId() != null) {
            typeCapteurDonneeService.deleteTypeCapteurDonneeById(typeCapteurDonnee.getId());
            changeHandler.onChange();
        }
    }

    void save() {
        if (typeCapteurDonnee == null) {
            log.error("Impossible de sauvegarder : typeCapteurDonnee est null");
            return;
        }

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

    public void setChangeHandler(ChangeHandler h) {
        this.changeHandler = h;
    }
}
