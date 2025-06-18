// package com.sae_s6.S6.APIGestion.views;


// import com.sae_s6.S6.APIGestion.entity.Batiment;
// import com.sae_s6.S6.APIGestion.entity.Mur;
// import com.sae_s6.S6.APIGestion.entity.TypeSalle;
// import com.sae_s6.S6.APIGestion.service.MurService;
// import com.vaadin.flow.component.button.Button;
// import com.vaadin.flow.component.grid.Grid;
// import com.vaadin.flow.component.icon.VaadinIcon;
// import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
// import com.vaadin.flow.component.orderedlayout.VerticalLayout;
// import com.vaadin.flow.component.textfield.TextField;
// import com.vaadin.flow.data.value.ValueChangeMode;
// import com.vaadin.flow.router.Menu;
// import com.vaadin.flow.router.PageTitle;
// import com.vaadin.flow.router.Route;
// import org.springframework.util.StringUtils;

// @Route (value="mur") 
// @PageTitle("Les Murs")
// @Menu(title = "Les Murs", order = 0, icon = "vaadin:clipboard-check")

// public class MurView extends VerticalLayout {

// 	//private final AuteurRepo repo;
// 	private final MurService murService;

// 	final Grid<Mur> grid;

// 	final TextField filter;

// 	private final Button addNewBtn;

// 	//public AuteurView(AuteurRepo repo, AuteurEditor editor) {
// 	public MurView(MurService murService, MurEditor editor) {
// 		//this.repo = repo;
// 		this.murService = murService;
// 		//this.editor = editor;
// 		this.grid = new Grid<>(Mur.class);
// 		this.filter = new TextField();
// 		this.addNewBtn = new Button("Ajouter un mur", VaadinIcon.PLUS.create());

// 		// build layout
// 		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
// 		add(actions, grid, editor);

// 		grid.setHeight("300px");
// 		grid.setColumns("id", "libelleSalle", "superficie");
		
// 		grid.addColumn(salle -> {
//             Batiment batiment = salle.getBatimentNavigation();
//             return batiment != null ? batiment.getDesc() : "";
//         }).setHeader("Batiment").setKey("BatimentDescription");

// 		grid.addColumn(salle -> {
//             TypeSalle typeSalle = salle.getTypeSalleNavigation();
//             return typeSalle != null ? typeSalle.getDesc() : "";
//         }).setHeader("Type salle").setKey("typeSalleDescription");
		
		
// 		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

// 		filter.setPlaceholder("Filtrer par nom");

// 		// Hook logic to components

// 		// Replace listing with filtered content when user changes filter
// 		filter.setValueChangeMode(ValueChangeMode.LAZY);
// 		filter.addValueChangeListener(e -> listSalles(e.getValue()));

// 		// Connect selected Customer to editor or hide if none is selected
// 		grid.asSingleSelect().addValueChangeListener(e -> {
// 			editor.editMur(e.getValue());
// 		});

// 		// Instantiate and edit new Customer the new button is clicked
// 		addNewBtn.addClickListener(e -> editor.editMur(new Mur()));

// 		// Listen changes made by the editor, refresh data from backend
// 		editor.setChangeHandler(() -> {
// 			editor.setVisible(false);
// 			listSalles(filter.getValue());
// 		});

// 		// Initialize listing
// 		listSalles(null);
// 	}

// 	// tag::listSalles[]
// 	void listSalles(String filterText) {
// 		if (StringUtils.hasText(filterText)) {
// 			grid.setItems(murService.getByLibelleMurContainingIgnoreCase(filterText));
// 		} else {
// 			grid.setItems(murService.getAllMurs());
// 		}
// 	}
// 	// end::listCustomers[]

// }
