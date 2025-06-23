package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.TypeCapteurDonneeService;
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
 * Vue pour la gestion des associations entre types de capteurs et données.
 * Fournit une interface utilisateur pour afficher, filtrer, ajouter, modifier et supprimer ces associations.
 */
@Component
@Scope("prototype")
@Route(value = "typeCapteurDonnee")
@PageTitle("Les types capteur donnée")
@Menu(title = "Les types capteur donnée", order = 5, icon = "vaadin:clipboard-check")
public class TypeCapteurDonneeView extends VerticalLayout {

    /**
     * Service pour gérer les opérations sur les associations entre types de capteurs et données.
     */
    private final TypeCapteurDonneeService typeCapteurDonneeService;

    /**
     * Grille pour afficher la liste des associations.
     */
    public final Grid<TypeCapteurDonnee> grid;

    /**
     * Champ de texte pour filtrer les associations par libellé de donnée ou type de capteur.
     */
    public final TextField filter;

    /**
     * Bouton pour ajouter une nouvelle association.
     */
    private final Button addNewBtn;

    /**
     * Éditeur pour gérer les opérations sur les associations.
     */
    public final TypeCapteurDonneeEditor editor;

    /**
     * Getter pour le bouton d'ajout d'association.
     *
     * @return Le bouton d'ajout d'association.
     */
    public Button getAddNewBtn() {
        return addNewBtn;
    }

    /**
     * Constructeur de la vue des associations.
     *
     * @param typeCapteurDonneeService Service pour gérer les associations entre types de capteurs et données.
     * @param editor Éditeur pour gérer les associations.
     */
    public TypeCapteurDonneeView(TypeCapteurDonneeService typeCapteurDonneeService, TypeCapteurDonneeEditor editor) {
        this.typeCapteurDonneeService = typeCapteurDonneeService;
        this.editor = editor;
        this.grid = new Grid<>(TypeCapteurDonnee.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Ajouter un type capteur donnée", VaadinIcon.PLUS.create());

        // Construction de la mise en page
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        // Configuration de la grille
        grid.setHeight("300px");

        // Désactiver l'affichage automatique des colonnes
        grid.setColumns(); // Supprime toutes les colonnes par défaut

        // Ajout des colonnes personnalisées pour afficher les libellés
        grid.addColumn(typeCapteurDonnee -> {
            Donnee donnee = typeCapteurDonnee.getDonneeNavigation();
            return donnee != null ? donnee.getLibelleDonnee() : "";
        }).setHeader("Libellé Donnée");

        grid.addColumn(typeCapteurDonnee -> {
            TypeCapteur typeCapteur = typeCapteurDonnee.getTypeCapteurNavigation();
            return typeCapteur != null ? typeCapteur.getLibelleTypeCapteur() : "";
        }).setHeader("Libellé Type Capteur");

        // Configuration du champ de filtre
        filter.setPlaceholder("Filtrer par donnée ou type capteur");

        // Logique associée aux composants

        // Remplace la liste par du contenu filtré lorsque l'utilisateur modifie le filtre
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listTypeCapteurDonnees(e.getValue()));

        // Connecte la sélection dans la grille à l'éditeur ou masque l'éditeur si aucune sélection
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editTypeCapteurDonnee(e.getValue());
        });

        // Instancie et édite une nouvelle association lorsque le bouton "Ajouter" est cliqué
        addNewBtn.addClickListener(e -> editor.editTypeCapteurDonnee(new TypeCapteurDonnee()));

        // Écoute les changements effectués par l'éditeur et rafraîchit les données depuis le backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listTypeCapteurDonnees(filter.getValue());
        });

        // Initialise la liste des associations
        listTypeCapteurDonnees(null);
    }

    /**
     * Liste les associations en fonction du texte de filtre.
     *
     * @param filterText Texte de filtre pour rechercher les associations par libellé de donnée ou type de capteur.
     */
    void listTypeCapteurDonnees(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(typeCapteurDonneeService.getAllTypeCapteurDonnee().stream()
                    .filter(typeCapteurDonnee -> {
                        Donnee donnee = typeCapteurDonnee.getDonneeNavigation();
                        TypeCapteur typeCapteur = typeCapteurDonnee.getTypeCapteurNavigation();
                        return (donnee != null && donnee.getLibelleDonnee().toLowerCase().contains(filterText.toLowerCase())) ||
                               (typeCapteur != null && typeCapteur.getLibelleTypeCapteur().toLowerCase().contains(filterText.toLowerCase()));
                    })
                    .toList()); // Convertit le Stream en List
        } else {
            grid.setItems(typeCapteurDonneeService.getAllTypeCapteurDonnee());
        }
    }
}