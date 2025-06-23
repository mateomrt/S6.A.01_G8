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
import org.springframework.util.StringUtils;

@Route (value="typeCapteur") 
@PageTitle("Les types de capteur")
@Menu(title = "Les types de capteur", order = 4, icon = "vaadin:info")

public class TypeCapteurView extends VerticalLayout {

	//private final AuteurRepo repo;
	private final TypeCapteurService typeCapteurService;

	final Grid<TypeCapteur> grid;

	final TextField filter;

	private final Button addNewBtn;

	//public AuteurView(AuteurRepo repo, AuteurEditor editor) {
	public TypeCapteurView(TypeCapteurService typeCapteurService, TypeCapteurEditor editor) {
		//this.repo = repo;
		this.typeCapteurService = typeCapteurService;
		//this.editor = editor;
		this.grid = new Grid<>(TypeCapteur.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Ajouter un type de capteur", VaadinIcon.PLUS.create());

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("id", "libelleTypeCapteur", "modeTypeCapteur");
		
		
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filtrer par nom");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listTypesCapteur(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editTypeCapteur(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editTypeCapteur(new TypeCapteur()));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listTypesCapteur(filter.getValue());
		});

		// Initialize listing
		listTypesCapteur(null);
	}

	// tag::listSalles[]
	void listTypesCapteur(String filterText) {
		if (StringUtils.hasText(filterText)) {
			grid.setItems(typeCapteurService.getByLibelleTypeCapteurContainingIgnoreCase(filterText));
		} else {
			grid.setItems(typeCapteurService.getAllTypeCapteurs());
		}
	}
	// end::listCustomers[]

}
