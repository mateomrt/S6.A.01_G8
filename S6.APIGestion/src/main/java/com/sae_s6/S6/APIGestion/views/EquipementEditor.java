package com.sae_s6.S6.APIGestion.views;

import org.springframework.context.annotation.Scope;

import com.sae_s6.S6.APIGestion.entity.Equipement;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.service.EquipementService;
import com.sae_s6.S6.APIGestion.service.MurService;
import com.sae_s6.S6.APIGestion.service.SalleService;
import com.sae_s6.S6.APIGestion.service.TypeEquipementService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@Scope("prototype")
@SpringComponent
@UIScope
public class EquipementEditor extends VerticalLayout implements KeyNotifier {

	private final EquipementService equipementService;
	private final MurService murService;
	private final SalleService salleService;
	private final TypeEquipementService typeEquipementService;

	private Equipement equipement;

	/* Fields to edit properties in Equipement entity */
	public TextField libelleEquipement = new TextField("Libellé équipement");
	TextField hauteur = new TextField("Hauteur");
	TextField largeur = new TextField("Largeur");
	TextField position_x = new TextField("Position X");
	TextField position_y = new TextField("Position Y");
    
	ComboBox<Mur> MurComboBox = new ComboBox<>("Mur");
	ComboBox<Salle> SalleComboBox = new ComboBox<>("Salle");
	ComboBox<TypeEquipement> TypeEquipementComboBox = new ComboBox<>("Type équipement");

	/* Action buttons */
	Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
	Button cancel = new Button("Annuler");
	Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<Equipement> binder = new Binder<>(Equipement.class);
	private ChangeHandler changeHandler;

	public EquipementEditor(EquipementService equipementService, MurService murService, SalleService salleService, TypeEquipementService typeEquipementService) {
		this.equipementService = equipementService;
		this.murService = murService;
		this.salleService = salleService;
		this.typeEquipementService = typeEquipementService;

		// Configuration des ComboBox
		MurComboBox.setPlaceholder("Sélectionner un mur");
		MurComboBox.setClearButtonVisible(true);
		MurComboBox.setItemLabelGenerator(Mur::getDesc);

		SalleComboBox.setPlaceholder("Sélectionner une salle");
		SalleComboBox.setClearButtonVisible(true);
		SalleComboBox.setItemLabelGenerator(Salle::getDesc);

		TypeEquipementComboBox.setPlaceholder("Sélectionner un type d'équipement");
		TypeEquipementComboBox.setClearButtonVisible(true);
		TypeEquipementComboBox.setItemLabelGenerator(TypeEquipement::getDesc);

		// Organisation des champs en lignes horizontales
		// Ligne 1 : Libellé, Mur, Salle
		HorizontalLayout fieldsRow1 = new HorizontalLayout(libelleEquipement, MurComboBox, SalleComboBox);
		fieldsRow1.setWidthFull();
		fieldsRow1.setSpacing(true);

		// Ligne 2 : Type équipement, Hauteur, Largeur
		HorizontalLayout fieldsRow2 = new HorizontalLayout(TypeEquipementComboBox, hauteur, largeur);
		fieldsRow2.setWidthFull();
		fieldsRow2.setSpacing(true);

		// Ligne 3 : Position X, Position Y (avec un champ vide pour l'alignement)
		HorizontalLayout fieldsRow3 = new HorizontalLayout(position_x, position_y);
		fieldsRow3.setSpacing(true);
		fieldsRow3.setWidthFull();

		// Configuration de la largeur des champs pour une répartition équitable
		// Ligne 1 - 3 champs
		libelleEquipement.setWidthFull();
		MurComboBox.setWidthFull();
		SalleComboBox.setWidthFull();

		// Ligne 2 - 3 champs
		TypeEquipementComboBox.setWidthFull();
		hauteur.setWidthFull();
		largeur.setWidthFull();

		// Ligne 3 - 2 champs (chaque champ prend 33.33% pour s'aligner avec les lignes du dessus)
		position_x.setWidth("calc(33.33% - 5px)");
		position_y.setWidth("calc(33.33% - 5px)");

		add(fieldsRow1, fieldsRow2, fieldsRow3, actions);

		// Configuration du binder
		binder.bindInstanceFields(this);
		
		binder.forField(MurComboBox)
            .asRequired("Mur est obligatoire")
            .bind(Equipement::getMurNavigation, Equipement::setMurNavigation);
			
		binder.forField(SalleComboBox)
            .asRequired("Salle est obligatoire")
            .bind(Equipement::getSalleNavigation, Equipement::setSalleNavigation);
			
		binder.forField(TypeEquipementComboBox)
            .asRequired("Type équipement est obligatoire")
            .bind(Equipement::getTypeEquipementNavigation, Equipement::setTypeEquipementNavigation);

		binder.forField(hauteur)
			.withNullRepresentation("") 
			.withConverter(new StringToDoubleConverter("La hauteur doit être un nombre"))
			.bind(Equipement::getHauteur, Equipement::setHauteur);
		
		binder.forField(largeur)
			.withNullRepresentation("") 
			.withConverter(new StringToDoubleConverter("La largeur doit être un nombre"))
			.bind(Equipement::getLargeur, Equipement::setLargeur);
			
		binder.forField(position_x)
			.withNullRepresentation("") 
			.withConverter(new StringToDoubleConverter("La position X doit être un nombre"))
			.bind(Equipement::getPosition_x, Equipement::setPosition_x);
			
		binder.forField(position_y)
			.withNullRepresentation("") 
			.withConverter(new StringToDoubleConverter("La position Y doit être un nombre"))
			.bind(Equipement::getPosition_y, Equipement::setPosition_y);

		// Configuration et style des composants
		setSpacing(true);
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

		addKeyPressListener(Key.ENTER, e -> save());

		// Configuration des actions des boutons
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		cancel.addClickListener(e -> cancel());

		// L'éditeur est caché par défaut
		setVisible(false);
	}

	void delete() {
		equipementService.deleteEquipementById(equipement.getId());
		changeHandler.onChange();
	}

	void save() {
		// Validation avant sauvegarde
		if (!binder.validate().isOk()) {
			return;
		}

		if (equipement.getId() == null) {
			equipementService.saveEquipement(equipement);
		} else {
			equipementService.updateEquipement(equipement);
		}
		changeHandler.onChange();
	}

	void cancel() {
		// Dans tous les cas, on ferme l'éditeur et on nettoie le formulaire
		setVisible(false);
		clearForm();
		// Notifier le changement pour actualiser la vue principale
		if (changeHandler != null) {
			changeHandler.onChange();
		}
	}

	private void clearForm() {
		binder.setBean(null);
		libelleEquipement.clear();
		hauteur.clear();
		largeur.clear();
		position_x.clear();
		position_y.clear();
		MurComboBox.clear();
		SalleComboBox.clear();
		TypeEquipementComboBox.clear();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editEquipement(Equipement a) {
		if (a == null) {
			setVisible(false);
			clearForm();
			return;
		}

		// Chargement des données pour les ComboBox
		MurComboBox.setItems(murService.getAllMurs());
		SalleComboBox.setItems(salleService.getAllSalles());
		TypeEquipementComboBox.setItems(typeEquipementService.getAllTypeEquipements());

		final boolean persisted = a.getId() != null;
		
		if (persisted) {
			equipement = equipementService.getEquipementById(a.getId());
		} else {
			equipement = a;
		}

		// Configuration de la visibilité des boutons
		cancel.setVisible(true); // Le bouton annuler est toujours visible
		delete.setVisible(persisted); // Le bouton supprimer n'est visible que pour les équipements existants

		// Binding des données
		binder.setBean(equipement);

		setVisible(true);
		libelleEquipement.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}
}