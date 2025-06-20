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
import org.springframework.util.StringUtils;

@Route(value = "typeEquipement")
@PageTitle("Les Types d'équipement")
@Menu(title = "Les Types d'équipement", order = 1, icon = "vaadin:clipboard-check")
public class TypeEquipementView extends VerticalLayout {

    private final TypeEquipementService typeEquipementService;

    public final Grid<TypeEquipement> grid;
    public final TextField filter;
    private final Button addNewBtn;
    public final TypeEquipementEditor editor;

    public Button getAddNewBtn() {
        return addNewBtn;
    }

    public TypeEquipementView(TypeEquipementService typeEquipementService, TypeEquipementEditor editor) {
        this.typeEquipementService = typeEquipementService;
        this.editor = editor;
        this.grid = new Grid<>(TypeEquipement.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Ajouter un type d'équipement", VaadinIcon.PLUS.create());

        // Build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "libelleTypeEquipement");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        filter.setPlaceholder("Filtrer par nom");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listTypeEquipement(e.getValue()));

        // Connect selected TypeEquipement to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editTypeEquipement(e.getValue());
        });

        // Instantiate and edit new TypeEquipement when the new button is clicked
        addNewBtn.addClickListener(e -> editor.editTypeEquipement(new TypeEquipement()));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listTypeEquipement(filter.getValue());
        });

        // Initialize listing
        listTypeEquipement(null);
    }

    void listTypeEquipement(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(typeEquipementService.getByLibelleTypeEquipementContainingIgnoreCase(filterText));
        } else {
            grid.setItems(typeEquipementService.getAllTypeEquipements());
        }
    }
}