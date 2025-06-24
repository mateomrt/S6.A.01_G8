package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.CapteurService;
import com.sae_s6.S6.APIGestion.service.MurService;
import com.sae_s6.S6.APIGestion.service.SalleService;
import com.sae_s6.S6.APIGestion.service.TypeCapteurService;
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
 * Éditeur pour l'entité Capteur.
 * Permet de créer, modifier ou supprimer un capteur via une interface utilisateur.
 */
@Scope("prototype")
@SpringComponent
@UIScope
public class CapteurEditor extends VerticalLayout implements KeyNotifier {

    private final CapteurService capteurService;
    private final MurService murService;
    private final SalleService salleService;
    private final TypeCapteurService typeCapteurService;

    private Capteur capteur;

    /**
     * Champs de texte pour les propriétés du capteur.
     */
    public TextField libelleCapteur = new TextField("Libellé capteur");
    public TextField positionXCapteur = new TextField("Position X");
    public TextField positionYCapteur = new TextField("Position Y");

    /**
     * ComboBox pour sélectionner les relations associées au capteur.
     */
    public ComboBox<Mur> murComboBox = new ComboBox<>("Mur");
    public ComboBox<Salle> salleComboBox = new ComboBox<>("Salle");
    public ComboBox<TypeCapteur> typeCapteurComboBox = new ComboBox<>("Type capteur");

    /**
     * Boutons pour les actions de sauvegarde, annulation et suppression.
     */
    public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
    Button cancel = new Button("Annuler");
    Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    /**
     * Binder pour lier les champs de l'interface utilisateur à l'entité Capteur.
     */
    Binder<Capteur> binder = new Binder<>(Capteur.class);
    private ChangeHandler changeHandler;

    /**
     * Constructeur de l'éditeur de capteur.
     *
     * @param capteurService Service pour gérer les opérations sur les capteurs.
     * @param murService Service pour gérer les murs.
     * @param salleService Service pour gérer les salles.
     * @param typeCapteurService Service pour gérer les types de capteurs.
     */
    public CapteurEditor(CapteurService capteurService, MurService murService, SalleService salleService, TypeCapteurService typeCapteurService) {
        this.capteurService = capteurService;
        this.salleService = salleService;
        this.murService = murService;
        this.typeCapteurService = typeCapteurService;

        // Configuration des ComboBox
        murComboBox.setPlaceholder("Sélectionner un mur");
        murComboBox.setClearButtonVisible(true);
        murComboBox.setItemLabelGenerator(Mur::getDesc);

        salleComboBox.setPlaceholder("Sélectionner une salle");
        salleComboBox.setClearButtonVisible(true);
        salleComboBox.setItemLabelGenerator(Salle::getDesc);

        typeCapteurComboBox.setPlaceholder("Sélectionner un type de capteur");
        typeCapteurComboBox.setClearButtonVisible(true);
        typeCapteurComboBox.setItemLabelGenerator(TypeCapteur::getDesc);

        // Disposition horizontale pour les champs principaux
        HorizontalLayout fieldsRow1 = new HorizontalLayout(libelleCapteur, positionXCapteur, positionYCapteur);
        fieldsRow1.setWidthFull();
        fieldsRow1.setSpacing(true);

        // Disposition horizontale pour les ComboBox
        HorizontalLayout fieldsRow2 = new HorizontalLayout(murComboBox, salleComboBox, typeCapteurComboBox);
        fieldsRow2.setWidthFull();
        fieldsRow2.setSpacing(true);

        // Configuration de la largeur des champs pour une meilleure répartition
        libelleCapteur.setWidthFull();
        positionXCapteur.setWidthFull();
        positionYCapteur.setWidthFull();
        murComboBox.setWidthFull();
        salleComboBox.setWidthFull();
        typeCapteurComboBox.setWidthFull();

        add(fieldsRow1, fieldsRow2, actions);

        // Configuration du binder
        binder.forField(libelleCapteur)
              .asRequired("Le libellé du capteur est obligatoire")
              .withValidationStatusHandler(status -> {
                  libelleCapteur.setErrorMessage(status.getMessage().orElse(""));
                  libelleCapteur.setInvalid(status.isError());
              })
              .bind(Capteur::getLibelleCapteur, Capteur::setLibelleCapteur);

        binder.forField(positionXCapteur)
              .withNullRepresentation("")
              .withConverter(new StringToDoubleConverter("La position X doit être un nombre"))
              .withValidationStatusHandler(status -> {
                  positionXCapteur.setErrorMessage(status.getMessage().orElse(""));
                  positionXCapteur.setInvalid(status.isError());
              })
              .bind(Capteur::getPositionXCapteur, Capteur::setPositionXCapteur);

        binder.forField(positionYCapteur)
              .withNullRepresentation("")
              .withConverter(new StringToDoubleConverter("La position Y doit être un nombre"))
              .withValidationStatusHandler(status -> {
                  positionYCapteur.setErrorMessage(status.getMessage().orElse(""));
                  positionYCapteur.setInvalid(status.isError());
              })
              .bind(Capteur::getPositionYCapteur, Capteur::setPositionYCapteur);

        binder.forField(murComboBox)
              .asRequired("Mur est obligatoire")
              .withValidationStatusHandler(status -> {
                  murComboBox.setErrorMessage(status.getMessage().orElse(""));
                  murComboBox.setInvalid(status.isError());
              })
              .bind(Capteur::getMurNavigation, Capteur::setMurNavigation);

        binder.forField(salleComboBox)
              .asRequired("Salle est obligatoire")
              .withValidationStatusHandler(status -> {
                  salleComboBox.setErrorMessage(status.getMessage().orElse(""));
                  salleComboBox.setInvalid(status.isError());
              })
              .bind(Capteur::getSalleNavigation, Capteur::setSalleNavigation);

        binder.forField(typeCapteurComboBox)
              .asRequired("Type capteur est obligatoire")
              .withValidationStatusHandler(status -> {
                  typeCapteurComboBox.setErrorMessage(status.getMessage().orElse(""));
                  typeCapteurComboBox.setInvalid(status.isError());
              })
              .bind(Capteur::getTypeCapteurNavigation, Capteur::setTypeCapteurNavigation);

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

    /**
     * Supprime le capteur actuellement édité.
     */
    void delete() {
        capteurService.deleteCapteurById(capteur.getId());
        changeHandler.onChange();
    }

    /**
     * Sauvegarde le capteur actuellement édité.
     */
    public void save() {
        try {
            binder.writeBean(capteur); // Valide et écrit les données dans l'objet capteur
            capteurService.saveCapteur(capteur);
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
     * Prépare l'éditeur pour un capteur donné.
     *
     * @param a Le capteur à éditer.
     */
    public final void editCapteur(Capteur a) {
        if (a == null) {
            setVisible(false);
            return;
        }

        murComboBox.setItems(murService.getAllMurs());
        salleComboBox.setItems(salleService.getAllSalles());
        typeCapteurComboBox.setItems(typeCapteurService.getAllTypeCapteurs());

        final boolean persisted = a.getId() != null;

        if (persisted) {
            capteur = capteurService.getCapteurById(a.getId());
        } else {
            capteur = a;
        }

        binder.setBean(capteur);
        setVisible(true);
        libelleCapteur.focus();
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