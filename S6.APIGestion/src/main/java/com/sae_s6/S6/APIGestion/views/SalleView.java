package com.sae_s6.S6.APIGestion.views;


import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.service.SalleService;

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

@Route (value="salle") 
@PageTitle("Les Salles")
@Menu(title = "Les Salle", order = 0, icon = "vaadin:clipboard-check")

public class SalleView extends VerticalLayout {

	//private final AuteurRepo repo;
	private final SalleService salleService;

	final Grid<Salle> grid;

	final TextField filter;

	private final Button addNewBtn;

	//public AuteurView(AuteurRepo repo, AuteurEditor editor) {
	public SalleView(SalleService salleService, SalleEditor editor) {
		//this.repo = repo;
		this.salleService = salleService;
		//this.editor = editor;
		this.grid = new Grid<>(Salle.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Ajouter un salle", VaadinIcon.PLUS.create());

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("id", "batiment_id", "typesalle_id", "libelle_salle", "superficie");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filtrer par nom");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listAuteurs(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editSalle(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editSalle(new Salle()));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listAuteurs(filter.getValue());
		});

		// Initialize listing
		listAuteurs(null);
	}

	// tag::listAuteurs[]
	void listAuteurs(String filterText) {
		if (StringUtils.hasText(filterText)) {
			grid.setItems(salleService.getSallesByLibelleSalleLike(filterText));
		} else {
			grid.setItems(salleService.getAllSalles());
		}
	}
	// end::listCustomers[]

}
