package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.service.TypeEquipementService;
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

@SpringComponent
@UIScope
public class TypeEquipementEditor extends VerticalLayout implements KeyNotifier {

    private final TypeEquipementService typeEquipementService;

    private TypeEquipement typeEquipement;

    public TextField libelleTypeEquipement = new TextField("Libellé type équipement");

    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    public Button cancel = new Button("Annuler");
    public Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<TypeEquipement> binder = new Binder<>(TypeEquipement.class);
    private ChangeHandler changeHandler;

    public TypeEquipementEditor(TypeEquipementService typeEquipementService) {
        this.typeEquipementService = typeEquipementService;

        add(libelleTypeEquipement, actions);

        // Configuration du binder
        binder.forField(libelleTypeEquipement)
              .asRequired("Le libellé du type équipement est obligatoire")
              .withValidationStatusHandler(status -> {
                  libelleTypeEquipement.setErrorMessage(status.getMessage().orElse(""));
                  libelleTypeEquipement.setInvalid(status.isError());
              })
              .bind(TypeEquipement::getLibelleTypeEquipement, TypeEquipement::setLibelleTypeEquipement);

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
        typeEquipementService.deleteTypeEquipementById(typeEquipement.getId());
        changeHandler.onChange();
    }

    void save() {
        try {
            binder.writeBean(typeEquipement); // Valide et écrit les données dans l'objet typeEquipement
            if (typeEquipement.getId() == null) {
                typeEquipementService.saveTypeEquipement(typeEquipement);
            } else {
                typeEquipementService.updateTypeEquipement(typeEquipement);
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

    public final void editTypeEquipement(TypeEquipement t) {
        if (t == null) {
            setVisible(false);
            return;
        }

        final boolean isNewTypeEquipement = (t.getId() == null);

        if (isNewTypeEquipement) {
            typeEquipement = t;
            delete.setVisible(false); // Pas de bouton supprimer pour un nouveau type d'équipement
        } else {
            typeEquipement = typeEquipementService.getTypeEquipementById(t.getId());
            delete.setVisible(true); // Afficher le bouton supprimer pour un type d'équipement existant
        }

        binder.setBean(typeEquipement);
        setVisible(true);
        libelleTypeEquipement.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}