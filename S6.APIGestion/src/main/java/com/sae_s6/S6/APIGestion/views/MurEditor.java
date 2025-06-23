package com.sae_s6.S6.APIGestion.views;

import org.springframework.context.annotation.Scope;

import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.Mur.Orientation;
import com.sae_s6.S6.APIGestion.service.MurService;
import com.sae_s6.S6.APIGestion.service.SalleService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@Scope("prototype")
@SpringComponent
@UIScope
public class MurEditor extends VerticalLayout implements KeyNotifier {

    private final MurService murService;
    private final SalleService salleService;

    private Mur mur;

    public TextField libelleMur = new TextField("Libellé du mur");
    public TextField hauteur = new TextField("Hauteur");
    public TextField longueur = new TextField("Longueur");
    public ComboBox<Orientation> orientationComboBox = new ComboBox<>("Orientation");
    public ComboBox<Salle> salleComboBox = new ComboBox<>("Salle");

    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    public Button cancel = new Button("Annuler");
    public Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Mur> binder = new Binder<>(Mur.class);
    private ChangeHandler changeHandler;

    public MurEditor(MurService murService, SalleService salleService) {
        this.murService = murService;
        this.salleService = salleService;

        salleComboBox.setPlaceholder("Sélectionner une salle");
        salleComboBox.setClearButtonVisible(true);
        salleComboBox.setItemLabelGenerator(Salle::getDesc);

        orientationComboBox.setItems(Orientation.values());
        orientationComboBox.setItemLabelGenerator(Orientation::name);

        HorizontalLayout fieldsRow1 = new HorizontalLayout(libelleMur, salleComboBox, hauteur);
        fieldsRow1.setSpacing(true);
        fieldsRow1.setWidthFull();

        HorizontalLayout fieldsRow2 = new HorizontalLayout(longueur, orientationComboBox);
        fieldsRow2.setSpacing(true);
        fieldsRow2.setWidthFull();

        libelleMur.setWidthFull();
        hauteur.setWidthFull();
        salleComboBox.setWidthFull();

        longueur.setWidth("calc(33.33% - 10px)");
        orientationComboBox.setWidth("calc(33.33% - 10px)");

        add(fieldsRow1, fieldsRow2, actions);

        // Configuration du binder
        binder.forField(libelleMur)
              .asRequired("Le libellé du mur est obligatoire")
              .withValidationStatusHandler(status -> {
                  libelleMur.setErrorMessage(status.getMessage().orElse(""));
                  libelleMur.setInvalid(status.isError());
              })
              .bind(Mur::getLibelleMur, Mur::setLibelleMur);

        binder.forField(salleComboBox)
              .asRequired("Salle est obligatoire")
              .withValidationStatusHandler(status -> {
                  salleComboBox.setErrorMessage(status.getMessage().orElse(""));
                  salleComboBox.setInvalid(status.isError());
              })
              .bind(Mur::getSalleNavigation, Mur::setSalleNavigation);

        binder.forField(orientationComboBox)
              .asRequired("Orientation est obligatoire")
              .withValidationStatusHandler(status -> {
                  orientationComboBox.setErrorMessage(status.getMessage().orElse(""));
                  orientationComboBox.setInvalid(status.isError());
              })
              .bind(Mur::getOrientation, Mur::setOrientation);

        binder.forField(hauteur)
              .withNullRepresentation("")
              .withConverter(new StringToDoubleConverter("La hauteur doit être un nombre"))
              .withValidationStatusHandler(status -> {
                  hauteur.setErrorMessage(status.getMessage().orElse(""));
                  hauteur.setInvalid(status.isError());
              })
              .bind(Mur::getHauteur, Mur::setHauteur);

        binder.forField(longueur)
              .withNullRepresentation("")
              .withConverter(new StringToDoubleConverter("La longueur doit être un nombre"))
              .withValidationStatusHandler(status -> {
                  longueur.setErrorMessage(status.getMessage().orElse(""));
                  longueur.setInvalid(status.isError());
              })
              .bind(Mur::getLongueur, Mur::setLongueur);

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
        murService.deleteMurById(mur.getId());
        changeHandler.onChange();
    }

    void save() {
        try {
            binder.writeBean(mur); // Valide et écrit les données dans l'objet mur
            murService.saveMur(mur);
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

    public final void editMur(Mur m) {
        if (m == null) {
            setVisible(false);
            return;
        }

        salleComboBox.setItems(salleService.getAllSalles());
        orientationComboBox.setItems(Orientation.values());

        final boolean isNewMur = (m.getId() == null);

        if (isNewMur) {
            mur = m;
            delete.setVisible(false);
        } else {
            mur = murService.getMurById(m.getId());
            delete.setVisible(true);
        }

        binder.setBean(mur);
        setVisible(true);
        libelleMur.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}