package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.service.SalleService;
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
 * Vue pour la gestion des salles.
 * Fournit une interface utilisateur pour afficher, filtrer, ajouter, modifier et supprimer des salles.
 */
@Component
@Scope("prototype")
@Route(value = "salle")
@PageTitle("Les Salles")
@Menu(title = "Les Salles", order = 0, icon = "vaadin:cube")
public class SalleView extends VerticalLayout {

    /**
     * Service pour gérer les opérations sur les salles.
     */
    private final SalleService salleService;

    /**
     * Grille pour afficher la liste des salles.
     */
    public final Grid<Salle> grid;

    /**
     * Champ de texte pour filtrer les salles par libellé.
     */
    public final TextField filter;

    /**
     * Bouton pour ajouter une nouvelle salle.
     */
    private final Button addNewBtn;

    /**
     * Éditeur pour gérer les opérations sur les salles.
     */
    public final SalleEditor editor;

    /**
     * Getter pour le bouton d'ajout de salle.
     *
     * @return Le bouton d'ajout de salle.
     */
    public Button getAddNewBtn() {
        return addNewBtn;
    }

    /**
     * Constructeur de la vue des salles.
     *
     * @param salleService Service pour gérer les opérations sur les salles.
     * @param editor Éditeur pour gérer les salles.
     */
    public SalleView(SalleService salleService, SalleEditor editor) {
        this.salleService = salleService;
        this.editor = editor;
        this.grid = new Grid<>(Salle.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Ajouter une salle", VaadinIcon.PLUS.create());

        // Construction de la mise en page
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        // Configuration de la grille
        grid.setHeight("300px");
        grid.setColumns("id", "libelleSalle", "superficie");

        grid.addColumn(salle -> {
            Batiment batiment = salle.getBatimentNavigation();
            return batiment != null ? batiment.getDesc() : "";
        }).setHeader("Batiment").setKey("BatimentDescription");

        grid.addColumn(salle -> {
            TypeSalle typeSalle = salle.getTypeSalleNavigation();
            return typeSalle != null ? typeSalle.getDesc() : "";
        }).setHeader("Type salle").setKey("typeSalleDescription");

        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        // Configuration du champ de filtre
        filter.setPlaceholder("Filtrer par nom");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listSalles(e.getValue()));

        // Connecte la sélection dans la grille à l'éditeur
        grid.asSingleSelect().addValueChangeListener(e -> editor.editSalle(e.getValue()));

        // Configure le bouton pour ajouter une nouvelle salle
        addNewBtn.addClickListener(e -> editor.editSalle(new Salle()));

        // Configure le gestionnaire de changement pour l'éditeur
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listSalles(filter.getValue());
        });

        // Initialise la liste des salles
        listSalles(null);
    }

    /**
     * Liste les salles en fonction du texte de filtre.
     *
     * @param filterText Texte de filtre pour rechercher les salles par libellé.
     */
    void listSalles(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(salleService.getByLibelleSalleContainingIgnoreCase(filterText));
        } else {
            grid.setItems(salleService.getAllSalles());
        }
    }
}