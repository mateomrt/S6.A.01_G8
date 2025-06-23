package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.service.MurService;
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
 * Vue pour la gestion des murs.
 * Fournit une interface utilisateur pour afficher, filtrer, ajouter, modifier et supprimer des murs.
 */
@Component
@Scope("prototype")
@Route(value = "mur")
@PageTitle("Les Murs")
@Menu(title = "Les Murs", order = 2, icon = "vaadin:stop")
public class MurView extends VerticalLayout {

    /**
     * Service pour gérer les opérations sur les murs.
     */
    private final MurService murService;

    /**
     * Grille pour afficher la liste des murs.
     */
    public final Grid<Mur> grid;

    /**
     * Champ de texte pour filtrer les murs par libellé.
     */
    public final TextField filter;

    /**
     * Bouton pour ajouter un nouveau mur.
     */
    private final Button addNewBtn;

    /**
     * Éditeur pour gérer les opérations sur les murs.
     */
    public final MurEditor editor;

    /**
     * Getter pour le bouton d'ajout de mur.
     *
     * @return Le bouton d'ajout de mur.
     */
    public Button getAddNewBtn() {
        return addNewBtn;
    }

    /**
     * Constructeur de la vue des murs.
     *
     * @param murService Service pour gérer les opérations sur les murs.
     * @param editor Éditeur pour gérer les murs.
     */
    public MurView(MurService murService, MurEditor editor) {
        this.murService = murService;
        this.editor = editor;
        this.grid = new Grid<>(Mur.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Ajouter un mur", VaadinIcon.PLUS.create());

        // Construction de la mise en page
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        // Configuration de la grille
        grid.setHeight("300px");
        grid.setColumns("id", "libelleMur", "hauteur", "longueur", "orientation");

        grid.addColumn(mur -> {
            Salle salle = mur.getSalleNavigation();
            return salle != null ? salle.getDesc() : "";
        }).setHeader("Salle").setKey("SalleDescription");

        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        // Configuration du champ de filtre
        filter.setPlaceholder("Filtrer par nom");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listMurs(e.getValue()));

        // Connecte la sélection dans la grille à l'éditeur
        grid.asSingleSelect().addValueChangeListener(e -> editor.editMur(e.getValue()));

        // Configure le bouton pour ajouter un nouveau mur
        addNewBtn.addClickListener(e -> editor.editMur(new Mur()));

        // Configure le gestionnaire de changement pour l'éditeur
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listMurs(filter.getValue());
        });

        // Initialise la liste des murs
        listMurs(null);
    }

    /**
     * Liste les murs en fonction du texte de filtre.
     *
     * @param filterText Texte de filtre pour rechercher les murs par libellé.
     */
    void listMurs(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(murService.getByLibelleMurContainingIgnoreCase(filterText));
        } else {
            grid.setItems(murService.getAllMurs());
        }
    }
}