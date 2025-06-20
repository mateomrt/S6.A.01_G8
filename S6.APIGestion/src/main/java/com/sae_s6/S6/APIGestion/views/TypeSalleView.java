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

@Component
@Scope("prototype")
@Route(value = "typeSalle")
@PageTitle("Les Types de salle")
@Menu(title = "Les Types de salles", order = 1, icon = "vaadin:clipboard-check")
public class TypeSalleView extends VerticalLayout {

    private final TypeSalleService typeSalleService;

    public final Grid<TypeSalle> grid;
    public final TextField filter;
    private final Button addNewBtn;
    public final TypeSalleEditor editor;

    public Button getAddNewBtn() {
        return addNewBtn;
    }

    public TypeSalleView(TypeSalleService typeSalleService, TypeSalleEditor editor) {
        this.typeSalleService = typeSalleService;
        this.editor = editor;
        this.grid = new Grid<>(TypeSalle.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Ajouter un type de salle", VaadinIcon.PLUS.create());

        // Build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "libelleTypeSalle");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        filter.setPlaceholder("Filtrer par nom");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listTypeSalle(e.getValue()));

        // Connect selected TypeSalle to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editTypeSalle(e.getValue());
        });

        // Instantiate and edit new TypeSalle when the new button is clicked
        addNewBtn.addClickListener(e -> editor.editTypeSalle(new TypeSalle()));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listTypeSalle(filter.getValue());
        });

        // Initialize listing
        listTypeSalle(null);
    }

    void listTypeSalle(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(typeSalleService.getByLibelleTypeSalleContainingIgnoreCase(filterText));
        } else {
            grid.setItems(typeSalleService.getAllTypeSalles());
        }
    }
}