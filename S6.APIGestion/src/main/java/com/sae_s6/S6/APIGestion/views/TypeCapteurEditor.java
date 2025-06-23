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

@Scope("prototype")
@SpringComponent
@UIScope
public class TypeCapteurEditor extends VerticalLayout implements KeyNotifier {

    private final TypeCapteurService typeCapteurService;

    private TypeCapteur typeCapteur;

    /* Fields to edit properties in TypeCapteur entity */
    public TextField libelleTypeCapteur = new TextField("Libellé type capteur");
    public TextField modeTypeCapteur = new TextField("Mode du type capteur");

    /* Action buttons */
    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    public Button cancel = new Button("Annuler");
    public Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    public HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    public Binder<TypeCapteur> binder = new Binder<>(TypeCapteur.class);
    private ChangeHandler changeHandler;

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

    void delete() {
        typeCapteurService.deleteTypeCapteurById(typeCapteur.getId());
        changeHandler.onChange();
    }

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

    void cancel() {
        setVisible(false);
        clearForm();
        if (changeHandler != null) {
            changeHandler.onChange();
        }
    }

    private void clearForm() {
        binder.setBean(null);
        libelleTypeCapteur.clear();
        modeTypeCapteur.clear();
    }

    public interface ChangeHandler {
        void onChange();
    }

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

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}