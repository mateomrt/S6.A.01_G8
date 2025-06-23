package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.CapteurService;
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
@Route (value="capteur") 
@PageTitle("Les Capteurs")
@Menu(title = "Les Capteurs", order = 3, icon = "vaadin:line-bar-chart")

public class CapteurView extends VerticalLayout {

	//private final AuteurRepo repo;
	private final CapteurService capteurService;

	final Grid<Capteur> grid;

	final TextField filter;

	private final Button addNewBtn;

	//public AuteurView(AuteurRepo repo, AuteurEditor editor) {
	public CapteurView(CapteurService capteurService, CapteurEditor editor) {
		//this.repo = repo;
		this.capteurService = capteurService;
		//this.editor = editor;
		this.grid = new Grid<>(Capteur.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Ajouter un capteur", VaadinIcon.PLUS.create());

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("id", "libelleCapteur", "positionXCapteur", "positionYCapteur");
		
		grid.addColumn(capteur -> {
            Mur mur = capteur.getMurNavigation();
            return mur != null ? mur.getDesc() : "";
        }).setHeader("Mur").setKey("MurDescription");

		grid.addColumn(capteur -> {
            Salle salle = capteur.getSalleNavigation();
            return salle != null ? salle.getDesc() : "";
        }).setHeader("Salle").setKey("SalleDescription");

		grid.addColumn(capteur -> {
            TypeCapteur typeCapteur = capteur.getTypeCapteurNavigation();
            return typeCapteur != null ? typeCapteur.getDesc() : "";
        }).setHeader("Type capteur").setKey("typeCapteurDescription");
		
		
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filtrer par nom");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCapteurs(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCapteur(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCapteur(new Capteur()));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCapteurs(filter.getValue());
		});

		// Initialize listing
		listCapteurs(null);
	}

	// tag::listSalles[]
	void listCapteurs(String filterText) {
		if (StringUtils.hasText(filterText)) {
			grid.setItems(capteurService.getByLibelleCapteurContainingIgnoreCase(filterText));
		} else {
			grid.setItems(capteurService.getAllCapteurs());
		}
	}
	// end::listCustomers[]

}
