// package com.sae_s6.S6.APIGestion.views;


// import com.sae_s6.S6.APIGestion.entity.Batiment;
// import com.sae_s6.S6.APIGestion.entity.Salle;
// import com.sae_s6.S6.APIGestion.entity.TypeSalle;
// import com.sae_s6.S6.APIGestion.service.BatimentService;
// import com.sae_s6.S6.APIGestion.service.SalleService;
// import com.sae_s6.S6.APIGestion.service.TypeSalleService;
// import com.vaadin.flow.component.Key;
// import com.vaadin.flow.component.KeyNotifier;
// import com.vaadin.flow.component.button.Button;
// import com.vaadin.flow.component.button.ButtonVariant;
// import com.vaadin.flow.component.combobox.ComboBox;
// import com.vaadin.flow.component.icon.VaadinIcon;
// import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
// import com.vaadin.flow.component.orderedlayout.VerticalLayout;
// import com.vaadin.flow.component.textfield.TextField;
// import com.vaadin.flow.data.binder.Binder;
// import com.vaadin.flow.data.converter.StringToDoubleConverter;
// import com.vaadin.flow.spring.annotation.SpringComponent;
// import com.vaadin.flow.spring.annotation.UIScope;

// /**
//  * A simple example to introduce building forms. As your real application is probably much
//  * more complicated than this example, you could re-use this form in multiple places. This
//  * example component is only used in MainView.
//  * <p>
//  * In a real world application you'll most likely using a common super class for all your
//  * forms - less code, better UX.
//  */
// @SpringComponent
// @UIScope
// public class SalleEditor extends VerticalLayout implements KeyNotifier {

// 	private final SalleService salleService;
// 	private final BatimentService batimentService;
// 	private final TypeSalleService typeSalleService;

// 	/**
// 	 * The currently edited auteur
// 	 */
// 	private Salle salle;

// 	/* Fields to edit properties in Auteur entity */
// 	TextField libelleSalle = new TextField("Libellé salle");
// 	TextField superficie = new TextField("Superficie");
    
// 	ComboBox<Batiment> batimentComboBox = new ComboBox<>("Batiment");
// 	ComboBox<TypeSalle> typeSalleComboBox = new ComboBox<>("Type salle");
	

// 	HorizontalLayout fields = new HorizontalLayout(libelleSalle, superficie);

// 	/* Action buttons */
// 	Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
// 	Button cancel = new Button("Annuler");
// 	Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
// 	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

// 	Binder<Salle> binder = new Binder<>(Salle.class);
// 	private ChangeHandler changeHandler;

// 	public SalleEditor(SalleService salleService, BatimentService batimentService, TypeSalleService typeSalleService) {
// 		this.salleService = salleService;
// 		this.batimentService = batimentService;
// 		this.typeSalleService = typeSalleService;

// 		batimentComboBox.setPlaceholder("Sélectionner un batiment");
// 		batimentComboBox.setClearButtonVisible(true);
// 		// do it after :
// 		//auteurComboBox.setItems(auteurService.getAllAuteurs());
// 		batimentComboBox.setItemLabelGenerator(Batiment::getDesc);

// 		typeSalleComboBox.setPlaceholder("Sélectionner un type salle");
// 		typeSalleComboBox.setClearButtonVisible(true);
// 		// do it after :
// 		//auteurComboBox.setItems(auteurService.getAllAuteurs());
// 		typeSalleComboBox.setItemLabelGenerator(TypeSalle::getDesc);

// 		add(libelleSalle, superficie, batimentComboBox, typeSalleComboBox, actions);

// 		// bind using naming convention
// 		binder.bindInstanceFields(this);
// 		binder.forField(batimentComboBox)
//             .asRequired("Batiment est obligatoire")
//             .bind(Salle::getBatimentNavigation, Salle::setBatimentNavigation);
			
// 		binder.forField(typeSalleComboBox)
//             .asRequired("Auteur est obligatoire")
//             .bind(Salle::getTypeSalleNavigation, Salle::setTypeSalleNavigation);

// 		binder.forField(superficie)
// 			.withNullRepresentation("") 
// 			.withConverter(new StringToDoubleConverter("La superficie doit être un nombre"))
// 			.bind(Salle::getSuperficie, Salle::setSuperficie);

// 		// Configure and style components
// 		setSpacing(true);

// 		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
// 		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

// 		addKeyPressListener(Key.ENTER, e -> save());

// 		// wire action buttons to save, delete and reset
// 		save.addClickListener(e -> save());
// 		delete.addClickListener(e -> delete());
// 		cancel.addClickListener(e -> editSalle(salle));
// 		setVisible(false);
// 	}

// 	void delete() {
// 		salleService.deleteSalleById(salle.getId());
// 		changeHandler.onChange();
// 	}

// 	void save() {
//         if (salle.getId() == null) {
//             // If the livre is new, we save it
//             salleService.saveSalle(salle);
//         } else {
//             // If the livre already exists, we update it
//             salleService.updateSalle(salle);
//         }
//         changeHandler.onChange();
// 	}

// 	public interface ChangeHandler {
// 		void onChange();
// 	}

// 	public final void editSalle(Salle a) {
// 		if (a == null) {
// 			setVisible(false);
// 			return;
// 		}

// 		batimentComboBox.setItems(batimentService.getAllBatiments());
// 		typeSalleComboBox.setItems(typeSalleService.getAllTypeSalles());

// 		final boolean persisted = a.getId() != null;
// 		if (persisted) {
// 			// Find fresh entity for editing
// 			// In a more complex app, you might want to load
// 			// the entity/DTO with lazy loaded relations for editing
// 			salle = salleService.getSalleById(a.getId());
// 		}
// 		else {
// 			salle = a;
// 		}
// 		cancel.setVisible(persisted);

// 		// Bind auteur properties to similarly named fields
// 		// Could also use annotation or "manual binding" or programmatically
// 		// moving values from fields to entities before saving
// 		binder.setBean(salle);

// 		setVisible(true);

// 		// Focus first name initially
// 		libelleSalle.focus();
// 	}

// 	public void setChangeHandler(ChangeHandler h) {
// 		// ChangeHandler is notified when either save or delete
// 		// is clicked
// 		changeHandler = h;
// 	}

// }
