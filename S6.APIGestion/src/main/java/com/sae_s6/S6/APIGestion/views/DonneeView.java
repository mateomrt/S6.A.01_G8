package com.sae_s6.S6.APIGestion.views;



import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.service.DonneeService;
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

@Route (value="donnee") 
@PageTitle("Les Données")
@Menu(title = "Les Données", order = 4, icon = "vaadin:clipboard-check")

public class DonneeView extends VerticalLayout {

	//private final AuteurRepo repo;
	private final DonneeService donneeService;

	final Grid<Donnee> grid;

	final TextField filter;

	private final Button addNewBtn;

	//public AuteurView(AuteurRepo repo, AuteurEditor editor) {
	public DonneeView(DonneeService donneeService, DonneeEditor editor) {
		//this.repo = repo;
		this.donneeService = donneeService;
		//this.editor = editor;
		this.grid = new Grid<>(Donnee.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Ajouter une donnée", VaadinIcon.PLUS.create());

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("id", "libelleDonnee", "unite");
		
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filtrer par nom");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listDonnees(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editDonnee(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editDonnee(new Donnee()));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listDonnees(filter.getValue());
		});

		// Initialize listing
		listDonnees(null);
	}

	// tag::listSalles[]
	void listDonnees(String filterText) {
		if (StringUtils.hasText(filterText)) {
			grid.setItems(donneeService.getByLibelleDonneeContainingIgnoreCase(filterText));
		} else {
			grid.setItems(donneeService.getAllDonnees());
		}
	}
	// end::listCustomers[]

}
