package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.service.DonneeService;
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
 * Vue pour la gestion des données.
 * Fournit une interface utilisateur pour afficher, filtrer, ajouter, modifier et supprimer des données.
 */
@Component
@Scope("prototype")
@Route(value = "donnee")
@PageTitle("Les Données")
@Menu(title = "Les Données", order = 4, icon = "vaadin:pie-bar-chart")
public class DonneeView extends VerticalLayout {

    /**
     * Service pour gérer les opérations sur les données.
     */
    private final DonneeService donneeService;

    /**
     * Grille pour afficher la liste des données.
     */
    public final Grid<Donnee> grid;

    /**
     * Champ de texte pour filtrer les données par libellé.
     */
    public final TextField filter;

    /**
     * Bouton pour ajouter une nouvelle donnée.
     */
    private final Button addNewBtn;

    /**
     * Éditeur pour gérer les opérations sur les données.
     */
    public final DonneeEditor editor;

    /**
     * Constructeur de la vue des données.
     *
     * @param donneeService Service pour gérer les opérations sur les données.
     * @param editor Éditeur pour gérer les données.
     */
    public DonneeView(DonneeService donneeService, DonneeEditor editor) {
        this.donneeService = donneeService;
        this.editor = editor;
        this.grid = new Grid<>(Donnee.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Ajouter une donnée", VaadinIcon.PLUS.create());

        // Construction de la mise en page
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        // Configuration de la grille
        grid.setHeight("300px");
        grid.setColumns("id", "libelleDonnee", "unite");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        // Configuration du champ de filtre
        filter.setPlaceholder("Filtrer par nom");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listDonnees(e.getValue()));

        // Connecte la sélection dans la grille à l'éditeur
        grid.asSingleSelect().addValueChangeListener(e -> editor.editDonnee(e.getValue()));

        // Configure le bouton pour ajouter une nouvelle donnée
        addNewBtn.addClickListener(e -> editor.editDonnee(new Donnee()));

        // Configure le gestionnaire de changement pour l'éditeur
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listDonnees(filter.getValue());
        });

        // Initialise la liste des données
        listDonnees(null);
    }

    /**
     * Liste les données en fonction du texte de filtre.
     *
     * @param filterText Texte de filtre pour rechercher les données par libellé.
     */
    void listDonnees(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(donneeService.getByLibelleDonneeContainingIgnoreCase(filterText));
        } else {
            grid.setItems(donneeService.getAllDonnees());
        }
    }

    /**
     * Getter pour le bouton d'ajout de donnée.
     *
     * @return Le bouton d'ajout de donnée.
     */
    public Button getAddNewBtn() {
        return addNewBtn;
    }
}