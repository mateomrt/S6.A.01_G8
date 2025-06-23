package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.Equipement;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.service.EquipementService;
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
 * Vue pour la gestion des équipements.
 * Fournit une interface utilisateur pour afficher, filtrer, ajouter, modifier et supprimer des équipements.
 */
@Component
@Scope("prototype")
@Route(value = "equipement")
@PageTitle("Les Equipements")
@Menu(title = "Les equipements", order = 2, icon = "vaadin:tools")
public class EquipementView extends VerticalLayout {

    /**
     * Service pour gérer les opérations sur les équipements.
     */
    private final EquipementService equipementService;

    /**
     * Grille pour afficher la liste des équipements.
     */
    public final Grid<Equipement> grid;

    /**
     * Champ de texte pour filtrer les équipements par libellé.
     */
    public final TextField filter;

    /**
     * Bouton pour ajouter un nouvel équipement.
     */
    private final Button addNewBtn;

    /**
     * Éditeur pour gérer les opérations sur les équipements.
     */
    public final EquipementEditor editor;

    /**
     * Constructeur de la vue des équipements.
     *
     * @param equipementService Service pour gérer les opérations sur les équipements.
     * @param editor Éditeur pour gérer les équipements.
     */
    public EquipementView(EquipementService equipementService, EquipementEditor editor) {
        this.equipementService = equipementService;
        this.grid = new Grid<>(Equipement.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Ajouter un équipement", VaadinIcon.PLUS.create());
        this.editor = editor;

        // Construction de la mise en page
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        // Configuration de la grille
        grid.setHeight("300px");
        grid.setColumns("id", "libelleEquipement", "hauteur", "largeur", "position_x", "position_y");

        grid.addColumn(equipement -> {
            Mur mur = equipement.getMurNavigation();
            return mur != null ? mur.getDesc() : "";
        }).setHeader("Mur").setKey("MurDescription");

        grid.addColumn(equipement -> {
            Salle salle = equipement.getSalleNavigation();
            return salle != null ? salle.getDesc() : "";
        }).setHeader("Salle").setKey("SalleDescription");

        grid.addColumn(equipement -> {
            TypeEquipement typeEquipement = equipement.getTypeEquipementNavigation();
            return typeEquipement != null ? typeEquipement.getDesc() : "";
        }).setHeader("Type équipement").setKey("typeEquipementDescription");

        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        // Configuration du champ de filtre
        filter.setPlaceholder("Filtrer par nom");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listEquipements(e.getValue()));

        // Connecte la sélection dans la grille à l'éditeur
        grid.asSingleSelect().addValueChangeListener(e -> editor.editEquipement(e.getValue()));

        // Configure le bouton pour ajouter un nouvel équipement
        addNewBtn.addClickListener(e -> editor.editEquipement(new Equipement()));

        // Configure le gestionnaire de changement pour l'éditeur
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listEquipements(filter.getValue());
        });

        // Initialise la liste des équipements
        listEquipements(null);
    }

    /**
     * Liste les équipements en fonction du texte de filtre.
     *
     * @param filterText Texte de filtre pour rechercher les équipements par libellé.
     */
    void listEquipements(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(equipementService.getByLibelleEquipementContainingIgnoreCase(filterText));
        } else {
            grid.setItems(equipementService.getAllEquipements());
        }
    }

    /**
     * Getter pour le bouton d'ajout d'équipement.
     *
     * @return Le bouton d'ajout d'équipement.
     */
    public Button getAddNewBtn() {
        return addNewBtn;
    }
}