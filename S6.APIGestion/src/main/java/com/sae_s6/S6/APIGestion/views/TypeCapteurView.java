package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.TypeCapteurService;
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
 * Vue pour la gestion des types de capteurs.
 * Fournit une interface utilisateur pour afficher, filtrer, ajouter, modifier et supprimer les types de capteurs.
 */
@Component
@Scope("prototype")
@Route(value = "typeCapteur")
@PageTitle("Les types de capteur")
@Menu(title = "Les types de capteur", order = 4, icon = "vaadin:info")
public class TypeCapteurView extends VerticalLayout {

    /**
     * Service pour gérer les opérations sur les types de capteurs.
     */
    private final TypeCapteurService typeCapteurService;

    /**
     * Grille pour afficher la liste des types de capteurs.
     */
    public final Grid<TypeCapteur> grid;

    /**
     * Champ de texte pour filtrer les types de capteurs par libellé.
     */
    public final TextField filter;

    /**
     * Bouton pour ajouter un nouveau type de capteur.
     */
    public final Button addNewBtn;

    /**
     * Éditeur pour gérer les opérations sur les types de capteurs.
     */
    public final TypeCapteurEditor editor;

    /**
     * Constructeur de la vue des types de capteurs.
     *
     * @param typeCapteurService Service pour gérer les types de capteurs.
     * @param editor Éditeur pour gérer les types de capteurs.
     */
    public TypeCapteurView(TypeCapteurService typeCapteurService, TypeCapteurEditor editor) {
        this.typeCapteurService = typeCapteurService;
        this.editor = editor;
        this.grid = new Grid<>(TypeCapteur.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Ajouter un type de capteur", VaadinIcon.PLUS.create());

        // Construction de la mise en page
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        // Configuration de la grille
        grid.setHeight("300px");
        grid.setColumns("id", "libelleTypeCapteur", "modeTypeCapteur");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        // Configuration du champ de filtre
        filter.setPlaceholder("Filtrer par nom");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listTypesCapteur(e.getValue()));

        // Connecte la sélection dans la grille à l'éditeur
        grid.asSingleSelect().addValueChangeListener(e -> editor.editTypeCapteur(e.getValue()));

        // Configure le bouton pour ajouter un nouveau type de capteur
        addNewBtn.addClickListener(e -> editor.editTypeCapteur(new TypeCapteur()));

        // Configure le gestionnaire de changement pour l'éditeur
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listTypesCapteur(filter.getValue());
        });

        // Initialise la liste des types de capteurs
        listTypesCapteur(null);
    }

    /**
     * Liste les types de capteurs en fonction du texte de filtre.
     *
     * @param filterText Texte de filtre pour rechercher les types de capteurs par libellé.
     */
    void listTypesCapteur(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(typeCapteurService.getByLibelleTypeCapteurContainingIgnoreCase(filterText));
        } else {
            grid.setItems(typeCapteurService.getAllTypeCapteurs());
        }
    }
}