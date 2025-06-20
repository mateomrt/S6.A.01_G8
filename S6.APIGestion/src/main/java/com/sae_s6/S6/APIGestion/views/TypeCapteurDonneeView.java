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
import org.springframework.util.StringUtils;

@Route(value = "typeCapteurDonnee")
@PageTitle("Les types capteur donnée")
@Menu(title = "Les types capteur donnée", order = 5, icon = "vaadin:clipboard-check")
public class TypeCapteurDonneeView extends VerticalLayout {

    private final TypeCapteurDonneeService typeCapteurDonneeService;

    public final Grid<TypeCapteurDonnee> grid;
    public final TextField filter;
    private final Button addNewBtn;
    public final TypeCapteurDonneeEditor editor;

    public Button getAddNewBtn() {
        return addNewBtn;
    }

    public TypeCapteurDonneeView(TypeCapteurDonneeService typeCapteurDonneeService, TypeCapteurDonneeEditor editor) {
        this.typeCapteurDonneeService = typeCapteurDonneeService;
        this.editor = editor;
        this.grid = new Grid<>(TypeCapteurDonnee.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Ajouter un type capteur donnée", VaadinIcon.PLUS.create());

        // Build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

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

        filter.setPlaceholder("Filtrer par donnée ou type capteur");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listTypeCapteurDonnees(e.getValue()));

        // Connect selected TypeCapteurDonnee to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editTypeCapteurDonnee(e.getValue());
        });

        // Instantiate and edit new TypeCapteurDonnee when the new button is clicked
        addNewBtn.addClickListener(e -> editor.editTypeCapteurDonnee(new TypeCapteurDonnee()));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listTypeCapteurDonnees(filter.getValue());
        });

        // Initialize listing
        listTypeCapteurDonnees(null);
    }

    void listTypeCapteurDonnees(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(typeCapteurDonneeService.getAllTypeCapteurDonnee().stream()
                    .filter(typeCapteurDonnee -> {
                        Donnee donnee = typeCapteurDonnee.getDonneeNavigation();
                        TypeCapteur typeCapteur = typeCapteurDonnee.getTypeCapteurNavigation();
                        return (donnee != null && donnee.getLibelleDonnee().toLowerCase().contains(filterText.toLowerCase())) ||
                               (typeCapteur != null && typeCapteur.getLibelleTypeCapteur().toLowerCase().contains(filterText.toLowerCase()));
                    })
                    .toList()); // Convert Stream to List
        } else {
            grid.setItems(typeCapteurDonneeService.getAllTypeCapteurDonnee());
        }
    }
}