package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.CapteurService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Vue pour la gestion des capteurs.
 * Fournit une interface utilisateur pour afficher, filtrer, ajouter, modifier et supprimer des capteurs.
 */
@Component
@Scope("prototype")
@Route(value = "capteur")
@PageTitle("Les Capteurs")
@Menu(title = "Les Capteurs", order = 3, icon = "vaadin:line-bar-chart")
public class CapteurView extends VerticalLayout {

    /**
     * Service pour gérer les opérations sur les capteurs.
     */
    public final CapteurService capteurService;

    /**
     * Grille pour afficher la liste des capteurs.
     */
    public final Grid<Capteur> grid;

    /**
     * Champ de texte pour filtrer les capteurs par libellé.
     */
    public final TextField filter;

    /**
     * Bouton pour ajouter un nouveau capteur.
     */
    public final Button addNewBtn;

    /**
     * Éditeur pour gérer les opérations sur les capteurs.
     */
    public final CapteurEditor editor;

    /**
     * Constructeur de la vue des capteurs.
     *
     * @param capteurService Service pour gérer les opérations sur les capteurs.
     * @param editor Éditeur pour gérer les capteurs.
     */
    public CapteurView(CapteurService capteurService, CapteurEditor editor) {
        this.capteurService = capteurService;
        this.grid = new Grid<>(Capteur.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Ajouter un capteur", VaadinIcon.PLUS.create());
        this.editor = editor;

        // Construction de la mise en page
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        // Configuration de la grille
        grid.setHeight("300px");
        grid.setColumns("id", "libelleCapteur", "positionXCapteur", "positionYCapteur");

        grid.addColumn(capteur -> {
            Mur mur = capteur.getMurNavigation();
            return mur != null ? mur.getDesc() : "";
        }).setHeader("Mur").setKey("MurDescription");

        grid.addColumn(capteur -> {
            Salle salle = capteur.getSalleNavigation();
            return salle != null ? salle.getDesc() : "";
        }).setHeader("Salle").setKey("SalleDescription");

        grid.addColumn(capteur -> {
            TypeCapteur typeCapteur = capteur.getTypeCapteurNavigation();
            return typeCapteur != null ? typeCapteur.getDesc() : "";
        }).setHeader("Type capteur").setKey("typeCapteurDescription");

        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        // Configuration du champ de filtre
        filter.setPlaceholder("Filtrer par nom");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listCapteurs(e.getValue()));

        // Connecte la sélection dans la grille à l'éditeur
        grid.asSingleSelect().addValueChangeListener(e -> editor.editCapteur(e.getValue()));

        // Configure le bouton pour ajouter un nouveau capteur
        addNewBtn.addClickListener(e -> editor.editCapteur(new Capteur()));

        // Configure le gestionnaire de changement pour l'éditeur
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listCapteurs(filter.getValue());
        });

        // Initialise la liste des capteurs
        listCapteurs(null);
    }

    /**
     * Liste les capteurs en fonction du texte de filtre.
     *
     * @param filterText Texte de filtre pour rechercher les capteurs par libellé.
     */
    void listCapteurs(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(capteurService.getByLibelleCapteurContainingIgnoreCase(filterText));
        } else {
            grid.setItems(capteurService.getAllCapteurs());
        }
    }

    /**
     * Getter pour le bouton d'ajout de capteur.
     *
     * @return Le bouton d'ajout de capteur.
     */
    public Button getAddNewBtn() {
        return addNewBtn;
    }
}