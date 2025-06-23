package com.sae_s6.S6.APIGestion.views;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sae_s6.S6.APIGestion.entity.Equipement;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.service.EquipementService;
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
@Component
@Scope("prototype")
@Route(value = "equipement") 
@PageTitle("Les Equipements")
@Menu(title = "Les equipements", order = 2, icon = "vaadin:tools")

public class EquipementView extends VerticalLayout {

	//private final AuteurRepo repo;
	private final EquipementService equipementService;

	final Grid<Equipement> grid;

	final TextField filter;

	private final Button addNewBtn;

	//public AuteurView(AuteurRepo repo, AuteurEditor editor) {
	public EquipementView(EquipementService equipementService, EquipementEditor editor) {
		this.equipementService = equipementService;
		this.grid = new Grid<>(Equipement.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Ajouter un équipement", VaadinIcon.PLUS.create());

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("id", "libelleEquipement", "hauteur", "largeur", "position_x", "position_y" );
		
		grid.addColumn(equipement -> {
            Mur mur = equipement.getMurNavigation();
            return mur != null ? mur.getDesc() : "";
        }).setHeader("Mur").setKey("MurDescription");

		grid.addColumn(equipement -> {
            Salle salle = equipement.getSalleNavigation();
            return salle != null ? salle.getDesc() : "";
        }).setHeader("Salle").setKey("SalleDescription");

        grid.addColumn(equipement -> {
            TypeEquipement typeEquipement = equipement.getTypeEquipementNavigation();
            return typeEquipement != null ? typeEquipement.getDesc() : "";
        }).setHeader("Type équipement").setKey("typeEquipementDescription");
		
		
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filtrer par nom");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listEquipements(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editEquipement(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editEquipement(new Equipement()));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listEquipements(filter.getValue());
		});

		// Initialize listing
		listEquipements(null);
	}

	// tag::listSalles[]
	void listEquipements(String filterText) {
		if (StringUtils.hasText(filterText)) {
			grid.setItems(equipementService.getByLibelleEquipementContainingIgnoreCase(filterText));
		} else {
			grid.setItems(equipementService.getAllEquipements());
		}
	}
	// end::listCustomers[]
}