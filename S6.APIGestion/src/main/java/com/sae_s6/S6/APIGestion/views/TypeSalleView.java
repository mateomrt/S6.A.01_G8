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
import org.springframework.util.StringUtils;

@Route (value="typeSalle") 
@PageTitle("Les Types de salle")
@Menu(title = "Les types de salle", order = 1, icon = "vaadin:clipboard-check")

public class TypeSalleView extends VerticalLayout {

	//private final AuteurRepo repo;
	private final TypeSalleService typeSalleService;

	final Grid<TypeSalle> grid;

	final TextField filter;

	private final Button addNewBtn;

	//public AuteurView(AuteurRepo repo, AuteurEditor editor) {
	public TypeSalleView(TypeSalleService typeSalleService, TypeSalleEditor editor) {
		//this.repo = repo;
		this.typeSalleService = typeSalleService;
		//this.editor = editor;
		this.grid = new Grid<>(TypeSalle.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Ajouter un type de salle", VaadinIcon.PLUS.create());

		// build layout
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

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editTypeSalle(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editTypeSalle(new TypeSalle()));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listTypeSalle(filter.getValue());
		});

		// Initialize listing
		listTypeSalle(null);
	}

	// tag::listTypeSalle[]
	void listTypeSalle(String filterText) {
		if (StringUtils.hasText(filterText)) {
			grid.setItems(typeSalleService.getByLibelleTypeSalleContainingIgnoreCase(filterText));
		} else {
			grid.setItems(typeSalleService.getAllTypeSalles());
		}
	}
	// end::listCustomers[]

}
