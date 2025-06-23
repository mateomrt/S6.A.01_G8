package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.Equipement;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.service.EquipementService;
import com.sae_s6.S6.APIGestion.service.MurService;
import com.sae_s6.S6.APIGestion.service.SalleService;
import com.sae_s6.S6.APIGestion.service.TypeEquipementService;
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
import org.springframework.context.annotation.Scope;

/**
 * Éditeur pour l'entité Equipement.
 * Permet de créer, modifier ou supprimer un équipement via une interface utilisateur.
 */
@Scope("prototype")
@SpringComponent
@UIScope
public class EquipementEditor extends VerticalLayout implements KeyNotifier {

    private final EquipementService equipementService;
    private final MurService murService;
    private final SalleService salleService;
    private final TypeEquipementService typeEquipementService;

    /**
     * L'équipement actuellement édité.
     */
    private Equipement equipement;

    /**
     * Champs de texte pour les propriétés de l'équipement.
     */
    public TextField libelleEquipement = new TextField("Libellé équipement");
    TextField hauteur = new TextField("Hauteur");
    TextField largeur = new TextField("Largeur");
    TextField position_x = new TextField("Position X");
    TextField position_y = new TextField("Position Y");

    /**
     * ComboBox pour sélectionner les relations associées à l'équipement.
     */
    ComboBox<Mur> MurComboBox = new ComboBox<>("Mur");
    ComboBox<Salle> SalleComboBox = new ComboBox<>("Salle");
    ComboBox<TypeEquipement> TypeEquipementComboBox = new ComboBox<>("Type équipement");

    /**
     * Boutons pour les actions de sauvegarde, annulation et suppression.
     */
    Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    Button cancel = new Button("Annuler");
    Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    /**
     * Binder pour lier les champs de l'interface utilisateur à l'entité Equipement.
     */
    Binder<Equipement> binder = new Binder<>(Equipement.class);
    private ChangeHandler changeHandler;

    /**
     * Constructeur de l'éditeur d'équipement.
     *
     * @param equipementService Service pour gérer les opérations sur les équipements.
     * @param murService Service pour gérer les murs.
     * @param salleService Service pour gérer les salles.
     * @param typeEquipementService Service pour gérer les types d'équipements.
     */
    public EquipementEditor(EquipementService equipementService, MurService murService, SalleService salleService, TypeEquipementService typeEquipementService) {
        this.equipementService = equipementService;
        this.murService = murService;
        this.salleService = salleService;
        this.typeEquipementService = typeEquipementService;

        // Configuration des ComboBox
        MurComboBox.setPlaceholder("Sélectionner un mur");
        MurComboBox.setClearButtonVisible(true);
        MurComboBox.setItemLabelGenerator(Mur::getDesc);

        SalleComboBox.setPlaceholder("Sélectionner une salle");
        SalleComboBox.setClearButtonVisible(true);
        SalleComboBox.setItemLabelGenerator(Salle::getDesc);

        TypeEquipementComboBox.setPlaceholder("Sélectionner un type d'équipement");
        TypeEquipementComboBox.setClearButtonVisible(true);
        TypeEquipementComboBox.setItemLabelGenerator(TypeEquipement::getDesc);

        // Organisation des champs en lignes horizontales
        HorizontalLayout fieldsRow1 = new HorizontalLayout(libelleEquipement, MurComboBox, SalleComboBox);
        fieldsRow1.setWidthFull();
        fieldsRow1.setSpacing(true);

        HorizontalLayout fieldsRow2 = new HorizontalLayout(TypeEquipementComboBox, hauteur, largeur);
        fieldsRow2.setWidthFull();
        fieldsRow2.setSpacing(true);

        HorizontalLayout fieldsRow3 = new HorizontalLayout(position_x, position_y);
        fieldsRow3.setSpacing(true);
        fieldsRow3.setWidthFull();

        libelleEquipement.setWidthFull();
        MurComboBox.setWidthFull();
        SalleComboBox.setWidthFull();
        TypeEquipementComboBox.setWidthFull();
        hauteur.setWidthFull();
        largeur.setWidthFull();
        position_x.setWidth("calc(33.33% - 5px)");
        position_y.setWidth("calc(33.33% - 5px)");

        add(fieldsRow1, fieldsRow2, fieldsRow3, actions);

        // Configuration du binder
        binder.forField(libelleEquipement)
              .asRequired("Le libellé de l'équipement est obligatoire")
              .withValidationStatusHandler(status -> {
                  libelleEquipement.setErrorMessage(status.getMessage().orElse(""));
                  libelleEquipement.setInvalid(status.isError());
              })
              .bind(Equipement::getLibelleEquipement, Equipement::setLibelleEquipement);

        binder.forField(hauteur)
              .withNullRepresentation("")
              .withConverter(new StringToDoubleConverter("La hauteur doit être un nombre"))
              .withValidationStatusHandler(status -> {
                  hauteur.setErrorMessage(status.getMessage().orElse(""));
                  hauteur.setInvalid(status.isError());
              })
              .bind(Equipement::getHauteur, Equipement::setHauteur);

        binder.forField(largeur)
              .withNullRepresentation("")
              .withConverter(new StringToDoubleConverter("La largeur doit être un nombre"))
              .withValidationStatusHandler(status -> {
                  largeur.setErrorMessage(status.getMessage().orElse(""));
                  largeur.setInvalid(status.isError());
              })
              .bind(Equipement::getLargeur, Equipement::setLargeur);

        binder.forField(position_x)
              .withNullRepresentation("")
              .withConverter(new StringToDoubleConverter("La position X doit être un nombre"))
              .withValidationStatusHandler(status -> {
                  position_x.setErrorMessage(status.getMessage().orElse(""));
                  position_x.setInvalid(status.isError());
              })
              .bind(Equipement::getPosition_x, Equipement::setPosition_x);

        binder.forField(position_y)
              .withNullRepresentation("")
              .withConverter(new StringToDoubleConverter("La position Y doit être un nombre"))
              .withValidationStatusHandler(status -> {
                  position_y.setErrorMessage(status.getMessage().orElse(""));
                  position_y.setInvalid(status.isError());
              })
              .bind(Equipement::getPosition_y, Equipement::setPosition_y);

        binder.forField(MurComboBox)
              .asRequired("Mur est obligatoire")
              .withValidationStatusHandler(status -> {
                  MurComboBox.setErrorMessage(status.getMessage().orElse(""));
                  MurComboBox.setInvalid(status.isError());
              })
              .bind(Equipement::getMurNavigation, Equipement::setMurNavigation);

        binder.forField(SalleComboBox)
              .asRequired("Salle est obligatoire")
              .withValidationStatusHandler(status -> {
                  SalleComboBox.setErrorMessage(status.getMessage().orElse(""));
                  SalleComboBox.setInvalid(status.isError());
              })
              .bind(Equipement::getSalleNavigation, Equipement::setSalleNavigation);

        binder.forField(TypeEquipementComboBox)
              .asRequired("Type équipement est obligatoire")
              .withValidationStatusHandler(status -> {
                  TypeEquipementComboBox.setErrorMessage(status.getMessage().orElse(""));
                  TypeEquipementComboBox.setInvalid(status.isError());
              })
              .bind(Equipement::getTypeEquipementNavigation, Equipement::setTypeEquipementNavigation);

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
     * Supprime l'équipement actuellement édité.
     */
    void delete() {
        equipementService.deleteEquipementById(equipement.getId());
        changeHandler.onChange();
    }

    /**
     * Sauvegarde l'équipement actuellement édité.
     */
    void save() {
        try {
            binder.writeBean(equipement); // Valide et écrit les données dans l'objet equipement
            equipementService.saveEquipement(equipement);
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
     * Prépare l'éditeur pour un équipement donné.
     *
     * @param a L'équipement à éditer.
     */
    public final void editEquipement(Equipement a) {
        if (a == null) {
            setVisible(false);
            return;
        }

        MurComboBox.setItems(murService.getAllMurs());
        SalleComboBox.setItems(salleService.getAllSalles());
        TypeEquipementComboBox.setItems(typeEquipementService.getAllTypeEquipements());

        final boolean persisted = a.getId() != null;

        if (persisted) {
            equipement = equipementService.getEquipementById(a.getId());
        } else {
            equipement = a;
        }

        binder.setBean(equipement);
        setVisible(true);
        libelleEquipement.focus();
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