package com.sae_s6.S6.APIGestion.views;


import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.service.BatimentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Menu;
// // import com.vaadin.flow.router.PageTitle;
// // import com.vaadin.flow.router.Route;
// // import org.springframework.util.StringUtils;

// // @Route (value="batiment") 
// // @PageTitle("Les Batiments")
// // @Menu(title = "Les Batiments", order = 4, icon = "vaadin:clipboard-check")

// // public class BatimentView extends VerticalLayout {

// // 	//private final AuteurRepo repo;
// // 	private final BatimentService batimentService;

// // 	final Grid<Batiment> grid;

// // 	final TextField filter;

// // 	private final Button addNewBtn;

// // 	//public AuteurView(AuteurRepo repo, AuteurEditor editor) {
// // 	public BatimentView(BatimentService batimentService, BatimentEditor editor) {
// // 		//this.repo = repo;
// // 		this.batimentService = batimentService;
// // 		//this.editor = editor;
// // 		this.grid = new Grid<>(Batiment.class);
// // 		this.filter = new TextField();
// // 		this.addNewBtn = new Button("Ajouter un batiment", VaadinIcon.PLUS.create());

// // 		// build layout
// // 		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
// // 		add(actions, grid, editor);

// 		grid.setHeight("300px");
// 		grid.setColumns("id", "libelleBatiment");
		
		
// // 		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

// // 		filter.setPlaceholder("Filtrer par nom");

// // 		// Hook logic to components

// 		// Replace listing with filtered content when user changes filter
// 		filter.setValueChangeMode(ValueChangeMode.LAZY);
// 		filter.addValueChangeListener(e -> listBatiments(e.getValue()));

// 		// Connect selected Customer to editor or hide if none is selected
// 		grid.asSingleSelect().addValueChangeListener(e -> {
// 			editor.editBatiment(e.getValue());
// 		});

// 		// Instantiate and edit new Customer the new button is clicked
// 		addNewBtn.addClickListener(e -> editor.editBatiment(new Batiment()));

// 		// Listen changes made by the editor, refresh data from backend
// 		editor.setChangeHandler(() -> {
// 			editor.setVisible(false);
// 			listBatiments(filter.getValue());
// 		});

// 		// Initialize listing
// 		listBatiments(null);
// 	}

// 	// tag::listSalles[]
// 	void listBatiments(String filterText) {
// 		if (StringUtils.hasText(filterText)) {
// 			grid.setItems(batimentService.getByLibelleBatimentContainingIgnoreCase(filterText));
// 		} else {
// 			grid.setItems(batimentService.getAllBatiments());
// 		}
// 	}
// 	// end::listCustomers[]

// // }
