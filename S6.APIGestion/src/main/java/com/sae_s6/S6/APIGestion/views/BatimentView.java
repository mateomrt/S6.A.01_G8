package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.service.BatimentService;
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
 * Vue pour la gestion des bâtiments.
 * Fournit une interface utilisateur pour afficher, filtrer, ajouter, modifier et supprimer des bâtiments.
 */
@Component
@Scope("prototype")
@Route(value = "batiment")
@PageTitle("Les Batiments")
@Menu(title = "Les Batiments", order = 4, icon = "vaadin:building")
public class BatimentView extends VerticalLayout {

    private final BatimentService batimentService;

    /**
     * Grille pour afficher la liste des bâtiments.
     */
    public final Grid<Batiment> grid;

    /**
     * Champ de texte pour filtrer les bâtiments par libellé.
     */
    public final TextField filter;

    /**
     * Bouton pour ajouter un nouveau bâtiment.
     */
    private final Button addNewBtn;

    /**
     * Éditeur pour gérer les opérations sur les bâtiments.
     */
    public final BatimentEditor editor;

    /**
     * Constructeur de la vue des bâtiments.
     *
     * @param batimentService Service pour gérer les opérations sur les bâtiments.
     * @param editor Éditeur pour gérer les bâtiments.
     */
    public BatimentView(BatimentService batimentService, BatimentEditor editor) {
        this.batimentService = batimentService;
        this.editor = editor;
        this.grid = new Grid<>(Batiment.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Ajouter un batiment", VaadinIcon.PLUS.create());

        // Construction de la mise en page
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        // Configuration de la grille
        grid.setHeight("300px");
        grid.setColumns("id", "libelleBatiment");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        // Configuration du champ de filtre
        filter.setPlaceholder("Filtrer par nom");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listBatiments(e.getValue()));

        // Connecte la sélection dans la grille à l'éditeur
        grid.asSingleSelect().addValueChangeListener(e -> editor.editBatiment(e.getValue()));

        // Configure le bouton pour ajouter un nouveau bâtiment
        addNewBtn.addClickListener(e -> editor.editBatiment(new Batiment()));

        // Configure le gestionnaire de changement pour l'éditeur
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listBatiments(filter.getValue());
        });

        // Initialise la liste des bâtiments
        listBatiments(null);
    }

    /**
     * Liste les bâtiments en fonction du texte de filtre.
     *
     * @param filterText Texte de filtre pour rechercher les bâtiments par libellé.
     */
    void listBatiments(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(batimentService.getByLibelleBatimentContainingIgnoreCase(filterText));
        } else {
            grid.setItems(batimentService.getAllBatiments());
        }
    }

    /**
     * Getter pour le bouton d'ajout de bâtiment.
     *
     * @return Le bouton d'ajout de bâtiment.
     */
    public Button getAddNewBtn() {
        return addNewBtn;
    }
}