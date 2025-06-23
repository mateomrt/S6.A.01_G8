package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.service.TypeEquipementService;
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
 * Vue pour la gestion des types d'équipements.
 * Fournit une interface utilisateur pour afficher, filtrer, ajouter, modifier et supprimer les types d'équipements.
 */
@Component
@Scope("prototype")
@Route(value = "typeEquipement")
@PageTitle("Les Types d'équipement")
@Menu(title = "Les Types d'équipement", order = 1, icon = "vaadin:wrench")
public class TypeEquipementView extends VerticalLayout {

    /**
     * Service pour gérer les opérations sur les types d'équipements.
     */
    private final TypeEquipementService typeEquipementService;

    /**
     * Grille pour afficher la liste des types d'équipements.
     */
    public final Grid<TypeEquipement> grid;

    /**
     * Champ de texte pour filtrer les types d'équipements par libellé.
     */
    public final TextField filter;

    /**
     * Bouton pour ajouter un nouveau type d'équipement.
     */
    private final Button addNewBtn;

    /**
     * Éditeur pour gérer les opérations sur les types d'équipements.
     */
    public final TypeEquipementEditor editor;

    /**
     * Getter pour le bouton d'ajout de type d'équipement.
     *
     * @return Le bouton d'ajout de type d'équipement.
     */
    public Button getAddNewBtn() {
        return addNewBtn;
    }

    /**
     * Constructeur de la vue des types d'équipements.
     *
     * @param typeEquipementService Service pour gérer les types d'équipements.
     * @param editor Éditeur pour gérer les types d'équipements.
     */
    public TypeEquipementView(TypeEquipementService typeEquipementService, TypeEquipementEditor editor) {
        this.typeEquipementService = typeEquipementService;
        this.editor = editor;
        this.grid = new Grid<>(TypeEquipement.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Ajouter un type d'équipement", VaadinIcon.PLUS.create());

        // Construction de la mise en page
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        // Configuration de la grille
        grid.setHeight("300px");
        grid.setColumns("id", "libelleTypeEquipement");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        // Configuration du champ de filtre
        filter.setPlaceholder("Filtrer par nom");

        // Logique associée aux composants

        // Remplace la liste par du contenu filtré lorsque l'utilisateur modifie le filtre
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listTypeEquipement(e.getValue()));

        // Connecte la sélection dans la grille à l'éditeur ou masque l'éditeur si aucune sélection
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editTypeEquipement(e.getValue());
        });

        // Instancie et édite un nouveau type d'équipement lorsque le bouton "Ajouter" est cliqué
        addNewBtn.addClickListener(e -> editor.editTypeEquipement(new TypeEquipement()));

        // Écoute les changements effectués par l'éditeur et rafraîchit les données depuis le backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listTypeEquipement(filter.getValue());
        });

        // Initialise la liste des types d'équipements
        listTypeEquipement(null);
    }

    /**
     * Liste les types d'équipements en fonction du texte de filtre.
     *
     * @param filterText Texte de filtre pour rechercher les types d'équipements par libellé.
     */
    void listTypeEquipement(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(typeEquipementService.getByLibelleTypeEquipementContainingIgnoreCase(filterText));
        } else {
            grid.setItems(typeEquipementService.getAllTypeEquipements());
        }
    }
}