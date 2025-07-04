package com.sae_s6.S6.APIGestion.views;

import org.springframework.context.annotation.Scope;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.service.BatimentService;
import com.sae_s6.S6.APIGestion.service.SalleService;
import com.sae_s6.S6.APIGestion.service.TypeSalleService;
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

/**
 * Éditeur pour l'entité Salle.
 * Permet de créer, modifier ou supprimer une salle via une interface utilisateur.
 */
@Scope("prototype")
@SpringComponent
@UIScope
public class SalleEditor extends VerticalLayout implements KeyNotifier {

    private final SalleService salleService;
    private final BatimentService batimentService;
    private final TypeSalleService typeSalleService;

    /**
     * La salle actuellement éditée.
     */
    private Salle salle;

    /**
     * Champs de texte pour les propriétés de la salle.
     */
    public TextField libelleSalle = new TextField("Libellé salle");
    public TextField superficie = new TextField("Superficie");

    /**
     * ComboBox pour sélectionner les relations associées à la salle.
     */
    public ComboBox<Batiment> batimentComboBox = new ComboBox<>("Batiment");
    public ComboBox<TypeSalle> typeSalleComboBox = new ComboBox<>("Type salle");

    /**
     * Boutons pour les actions de sauvegarde, annulation et suppression.
     */
    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    public Button cancel = new Button("Annuler");
    public Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    /**
     * Binder pour lier les champs de l'interface utilisateur à l'entité Salle.
     */
    Binder<Salle> binder = new Binder<>(Salle.class);
    private ChangeHandler changeHandler;

    /**
     * Constructeur de l'éditeur de salle.
     *
     * @param salleService Service pour gérer les opérations sur les salles.
     * @param batimentService Service pour gérer les bâtiments.
     * @param typeSalleService Service pour gérer les types de salles.
     */
    public SalleEditor(SalleService salleService, BatimentService batimentService, TypeSalleService typeSalleService) {
        this.salleService = salleService;
        this.batimentService = batimentService;
        this.typeSalleService = typeSalleService;

        // Configuration des ComboBox
        batimentComboBox.setPlaceholder("Sélectionner un batiment");
        batimentComboBox.setClearButtonVisible(true);
        batimentComboBox.setItemLabelGenerator(Batiment::getDesc);

        typeSalleComboBox.setPlaceholder("Sélectionner un type salle");
        typeSalleComboBox.setClearButtonVisible(true);
        typeSalleComboBox.setItemLabelGenerator(TypeSalle::getDesc);

        // Organisation des champs en lignes horizontales
        HorizontalLayout fieldsRow1 = new HorizontalLayout(libelleSalle, superficie, batimentComboBox);
        fieldsRow1.setWidthFull();
        fieldsRow1.setSpacing(true);

        HorizontalLayout fieldsRow2 = new HorizontalLayout(typeSalleComboBox);
        fieldsRow2.setSpacing(true);
        fieldsRow2.setWidthFull();

        libelleSalle.setWidthFull();
        superficie.setWidthFull();
        batimentComboBox.setWidthFull();
        typeSalleComboBox.setWidth("calc(33.33% - 10px)");

        add(fieldsRow1, fieldsRow2, actions);

        // Configuration du binder
        binder.bindInstanceFields(this);

        binder.forField(batimentComboBox)
              .asRequired("Batiment est obligatoire")
              .withValidationStatusHandler(status -> {
                  batimentComboBox.setErrorMessage(status.getMessage().orElse(""));
                  batimentComboBox.setInvalid(status.isError());
              })
              .bind(Salle::getBatimentNavigation, Salle::setBatimentNavigation);

        binder.forField(typeSalleComboBox)
              .asRequired("Type salle est obligatoire")
              .withValidationStatusHandler(status -> {
                  typeSalleComboBox.setErrorMessage(status.getMessage().orElse(""));
                  typeSalleComboBox.setInvalid(status.isError());
              })
              .bind(Salle::getTypeSalleNavigation, Salle::setTypeSalleNavigation);

        binder.forField(superficie)
              .withNullRepresentation("")
              .withConverter(new StringToDoubleConverter("La superficie doit être un nombre"))
              .withValidationStatusHandler(status -> {
                  superficie.setErrorMessage(status.getMessage().orElse(""));
                  superficie.setInvalid(status.isError());
              })
              .bind(Salle::getSuperficie, Salle::setSuperficie);

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
     * Supprime la salle actuellement éditée.
     */
    void delete() {
        salleService.deleteSalleById(salle.getId());
        changeHandler.onChange();
    }

    /**
     * Sauvegarde la salle actuellement éditée.
     */
    void save() {
        try {
            binder.writeBean(salle); // Valide et écrit les données dans l'objet salle
            salleService.saveSalle(salle);
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
     * Prépare l'éditeur pour une salle donnée.
     *
     * @param s La salle à éditer.
     */
    public final void editSalle(Salle s) {
        if (s == null) {
            setVisible(false);
            return;
        }

        batimentComboBox.setItems(batimentService.getAllBatiments());
        typeSalleComboBox.setItems(typeSalleService.getAllTypeSalles());

        final boolean isNewSalle = (s.getId() == null);

        if (isNewSalle) {
            salle = s;
            delete.setVisible(false);
        } else {
            salle = salleService.getSalleById(s.getId());
            delete.setVisible(true);
        }

        binder.setBean(salle);
        setVisible(true);
        libelleSalle.focus();
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