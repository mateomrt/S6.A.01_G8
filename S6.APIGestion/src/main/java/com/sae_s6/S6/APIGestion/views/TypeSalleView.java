package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.service.TypeSalleService;
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
 * Vue pour la gestion des types de salles.
 * Fournit une interface utilisateur pour afficher, filtrer, ajouter, modifier et supprimer les types de salles.
 */
@Component
@Scope("prototype")
@Route(value = "typeSalle")
@PageTitle("Les Types de salle")
@Menu(title = "Les Types de salles", order = 1, icon = "vaadin:cubes")
public class TypeSalleView extends VerticalLayout {

    /**
     * Service pour gérer les opérations sur les types de salles.
     */
    private final TypeSalleService typeSalleService;

    /**
     * Grille pour afficher la liste des types de salles.
     */
    public final Grid<TypeSalle> grid;

    /**
     * Champ de texte pour filtrer les types de salles par libellé.
     */
    public final TextField filter;

    /**
     * Bouton pour ajouter un nouveau type de salle.
     */
    private final Button addNewBtn;

    /**
     * Éditeur pour gérer les opérations sur les types de salles.
     */
    public final TypeSalleEditor editor;

    /**
     * Getter pour le bouton d'ajout de type de salle.
     *
     * @return Le bouton d'ajout de type de salle.
     */
    public Button getAddNewBtn() {
        return addNewBtn;
    }

    /**
     * Constructeur de la vue des types de salles.
     *
     * @param typeSalleService Service pour gérer les types de salles.
     * @param editor Éditeur pour gérer les types de salles.
     */
    public TypeSalleView(TypeSalleService typeSalleService, TypeSalleEditor editor) {
        this.typeSalleService = typeSalleService;
        this.editor = editor;
        this.grid = new Grid<>(TypeSalle.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Ajouter un type de salle", VaadinIcon.PLUS.create());

        // Construction de la mise en page
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        // Configuration de la grille
        grid.setHeight("300px");
        grid.setColumns("id", "libelleTypeSalle");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        // Configuration du champ de filtre
        filter.setPlaceholder("Filtrer par nom");

        // Logique associée aux composants

        // Remplace la liste par du contenu filtré lorsque l'utilisateur modifie le filtre
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listTypeSalle(e.getValue()));

        // Connecte la sélection dans la grille à l'éditeur ou masque l'éditeur si aucune sélection
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editTypeSalle(e.getValue());
        });

        // Instancie et édite un nouveau type de salle lorsque le bouton "Ajouter" est cliqué
        addNewBtn.addClickListener(e -> editor.editTypeSalle(new TypeSalle()));

        // Écoute les changements effectués par l'éditeur et rafraîchit les données depuis le backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listTypeSalle(filter.getValue());
        });

        // Initialise la liste des types de salles
        listTypeSalle(null);
    }

    /**
     * Liste les types de salles en fonction du texte de filtre.
     *
     * @param filterText Texte de filtre pour rechercher les types de salles par libellé.
     */
    void listTypeSalle(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(typeSalleService.getByLibelleTypeSalleContainingIgnoreCase(filterText));
        } else {
            grid.setItems(typeSalleService.getAllTypeSalles());
        }
    }
}