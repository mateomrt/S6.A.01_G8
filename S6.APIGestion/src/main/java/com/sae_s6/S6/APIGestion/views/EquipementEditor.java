// package com.sae_s6.S6.APIGestion.views;

// import com.sae_s6.S6.APIGestion.service.*;
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
// public class EquipementEditor extends VerticalLayout implements KeyNotifier {

//     private final service.TypeEquipementService typeEquipementService;

// 	private final EquipementService equipementService;
// 	private final MurService murService;
// 	private final SalleService salleService;
// 	private final TypeEquipement typeEquipement;

// 	/**
// 	 * The currently edited auteur
// 	 */
// 	private Equipement equipement;

// 	/* Fields to edit properties in Auteur entity */
// 	TextField libelleEquipement = new TextField("Libellé equipement");
// 	TextField hauteur = new TextField("Hauteur");
// 	TextField largeur = new TextField("Largeur");
// 	TextField position_x = new TextField("Position X");
// 	TextField position_y = new TextField("Position Y");
    
// 	ComboBox<Mur> MurComboBox = new ComboBox<>("Mur");
// 	ComboBox<Salle> SalleComboBox = new ComboBox<>("Salle");
// 	ComboBox<TypeEquipement> TypeEquipementComboBox = new ComboBox<>("Type equipement");
	

// 	HorizontalLayout fields = new HorizontalLayout(libelleEquipement, hauteur, largeur, position_x, position_y);

// 	/* Action buttons */
// 	Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
// 	Button cancel = new Button("Annuler");
// 	Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
// 	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

// 	Binder<Equipement> binder = new Binder<>(Equipement.class);
// 	private ChangeHandler changeHandler;

// 	public EquipementEditor(EquipementService equipementService, MurService murService, SalleService salleService, TypeEquipement typeEquipement, service.TypeEquipementService typeEquipementService) {
// 		this.equipementService = equipementService;
// 		this.murService = murService;
// 		this.salleService = salleService;
// 		this.typeEquipement = typeEquipement;

// 		MurComboBox.setPlaceholder("Sélectionner un mur");
// 		MurComboBox.setClearButtonVisible(true);
// 		MurComboBox.setItemLabelGenerator(Mur::getDesc);

// 		SalleComboBox.setPlaceholder("Sélectionner une salle");
// 		SalleComboBox.setClearButtonVisible(true);
// 		SalleComboBox.setItemLabelGenerator(Salle::getDesc);

// 		TypeEquipementComboBox.setPlaceholder("Sélectionner un type d'équipement");
// 		TypeEquipementComboBox.setClearButtonVisible(true);
// 		TypeEquipementComboBox.setItemLabelGenerator(TypeEquipement::getDesc);

// 		add(libelleEquipement, MurComboBox, SalleComboBox, TypeEquipementComboBox, hauteur, largeur, position_x, position_y, actions);

// 		// bind using naming convention
// 		binder.bindInstanceFields(this);
// 		binder.forField(MurComboBox)
//             .asRequired("Mur est obligatoire")
//             .bind(Equipement::getMurNavigation, Equipement::setMurNavigation);
			
// 		binder.forField(SalleComboBox)
//             .asRequired("Salle est obligatoire")
//             .bind(Equipement::getSalleNavigation, Equipement::setSalleNavigation);
			
// 		binder.forField(TypeEquipementComboBox)
//             .asRequired("Type equipement est obligatoire")
//             .bind(Equipement::getTypeEquipementNavigation, Equipement::setTypeEquipementNavigation);

// 		binder.forField(hauteur)
// 			.withNullRepresentation("") 
// 			.withConverter(new StringToDoubleConverter("La hauter doit être un nombre"))
// 			.bind(Equipement::getHauteur, Equipement::setHauteur);
		
// 		binder.forField(largeur)
// 			.withNullRepresentation("") 
// 			.withConverter(new StringToDoubleConverter("La largeur doit être un nombre"))
// 			.bind(Equipement::getLargeur, Equipement::setLargeur);
			
// 		binder.forField(position_x)
// 			.withNullRepresentation("") 
// 			.withConverter(new StringToDoubleConverter("La position X doit être un nombre"))
// 			.bind(Equipement::getPosition_x, Equipement::setPosition_x);
			
// 		binder.forField(position_y)
// 			.withNullRepresentation("") 
// 			.withConverter(new StringToDoubleConverter("La position Y doit être un nombre"))
// 			.bind(Equipement::getPosition_y, Equipement::setPosition_y);
		

// 		// Configure and style components
// 		setSpacing(true);

// 		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
// 		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

// 		addKeyPressListener(Key.ENTER, e -> save());

// 		// wire action buttons to save, delete and reset
// 		save.addClickListener(e -> save());
// 		delete.addClickListener(e -> delete());
// 		cancel.addClickListener(e -> editEquipement(equipement));
// 		setVisible(false);
// 		this.typeEquipementService = typeEquipementService;
// 	}

// 	void delete() {
// 		equipementService.deleteEquipementById(equipement.getId());
// 		changeHandler.onChange();
// 	}

// 	void save() {
//         if (equipement.getId() == null) {
//             // If the livre is new, we save it
//             equipementService.saveEquipement(equipement);
//         } else {
//             // If the livre already exists, we update it
//             equipementService.updateEquipement(equipement);
//         }
//         changeHandler.onChange();
// 	}

// 	public interface ChangeHandler {
// 		void onChange();
// 	}

// 	public final void editEquipement(Equipement a) {
// 		if (a == null) {
// 			setVisible(false);
// 			return;
// 		}

// 		MurComboBox.setItems(murService.getAllMurs());
// 		SalleComboBox.setItems(salleService.getAllSalles());
// 		TypeEquipementComboBox.setItems(typeEquipementService.getAllTypeEquipements());

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
