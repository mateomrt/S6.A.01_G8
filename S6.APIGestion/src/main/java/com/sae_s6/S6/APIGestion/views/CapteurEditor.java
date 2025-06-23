package com.sae_s6.S6.APIGestion.views;

import org.springframework.context.annotation.Scope;

import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.CapteurService;
import com.sae_s6.S6.APIGestion.service.MurService;
import com.sae_s6.S6.APIGestion.service.SalleService;
import com.sae_s6.S6.APIGestion.service.TypeCapteurService;
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
public class CapteurEditor extends VerticalLayout implements KeyNotifier {

	private final CapteurService capteurService;
	private final MurService murService;
	private final SalleService salleService;
	private final TypeCapteurService typeCapteurService;

	private Capteur capteur;

	/* Fields to edit properties in Capteur entity */
	public TextField libelleCapteur = new TextField("Libellé capteur");
	public TextField positionXCapteur = new TextField("Position X");
	public TextField positionYCapteur = new TextField("Position Y");
    
	public ComboBox<Mur> murComboBox = new ComboBox<>("Mur");
	public ComboBox<Salle> salleComboBox = new ComboBox<>("Salle");
	public ComboBox<TypeCapteur> typeCapteurComboBox = new ComboBox<>("Type capteur");

	/* Action buttons */
	public Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
	Button cancel = new Button("Annuler");
	Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<Capteur> binder = new Binder<>(Capteur.class);
	private ChangeHandler changeHandler;

	public CapteurEditor(CapteurService capteurService, MurService murService, SalleService salleService, TypeCapteurService typeCapteurService) {
		this.capteurService = capteurService;
		this.salleService = salleService;
		this.murService = murService;
		this.typeCapteurService = typeCapteurService;

		// Configuration des ComboBox
		murComboBox.setPlaceholder("Sélectionner un mur");
		murComboBox.setClearButtonVisible(true);
		murComboBox.setItemLabelGenerator(Mur::getDesc);

		salleComboBox.setPlaceholder("Sélectionner une salle");
		salleComboBox.setClearButtonVisible(true);
		salleComboBox.setItemLabelGenerator(Salle::getDesc);

		typeCapteurComboBox.setPlaceholder("Sélectionner un type de capteur");
		typeCapteurComboBox.setClearButtonVisible(true);
		typeCapteurComboBox.setItemLabelGenerator(TypeCapteur::getDesc);

		// Disposition horizontale pour les champs principaux
		HorizontalLayout fieldsRow1 = new HorizontalLayout(libelleCapteur, positionXCapteur, positionYCapteur);
		fieldsRow1.setWidthFull();
		fieldsRow1.setSpacing(true);
		
		// Disposition horizontale pour les ComboBox
		HorizontalLayout fieldsRow2 = new HorizontalLayout(murComboBox, salleComboBox, typeCapteurComboBox);
		fieldsRow2.setWidthFull();
		fieldsRow2.setSpacing(true);

		// Configuration de la largeur des champs pour une meilleure répartition
		libelleCapteur.setWidthFull();
		positionXCapteur.setWidthFull();
		positionYCapteur.setWidthFull();
		murComboBox.setWidthFull();
		salleComboBox.setWidthFull();
		typeCapteurComboBox.setWidthFull();

		add(fieldsRow1, fieldsRow2, actions);

		// Configuration du binder
		binder.bindInstanceFields(this);
		
		binder.forField(murComboBox)
            .asRequired("Mur est obligatoire")
            .bind(Capteur::getMurNavigation, Capteur::setMurNavigation);
			
		binder.forField(salleComboBox)
            .asRequired("Salle est obligatoire")
            .bind(Capteur::getSalleNavigation, Capteur::setSalleNavigation);
		
		binder.forField(typeCapteurComboBox)
            .asRequired("Type capteur est obligatoire")
            .bind(Capteur::getTypeCapteurNavigation, Capteur::setTypeCapteurNavigation);

		binder.forField(positionXCapteur)
			.withNullRepresentation("") 
			.withConverter(new StringToDoubleConverter("La position X doit être un nombre"))
			.bind(Capteur::getPositionXCapteur, Capteur::setPositionXCapteur);

		binder.forField(positionYCapteur)
			.withNullRepresentation("") 
			.withConverter(new StringToDoubleConverter("La position Y doit être un nombre"))
			.bind(Capteur::getPositionYCapteur, Capteur::setPositionYCapteur);

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
		capteurService.deleteCapteurById(capteur.getId());
		changeHandler.onChange();
	}

	void save() {
		// Validation avant sauvegarde
		if (!binder.validate().isOk()) {
			return;
		}

		if (capteur.getId() == null) {
			capteurService.saveCapteur(capteur);
		} else {
			capteurService.updateCapteur(capteur);
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
		libelleCapteur.clear();
		positionXCapteur.clear();
		positionYCapteur.clear();
		murComboBox.clear();
		salleComboBox.clear();
		typeCapteurComboBox.clear();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editCapteur(Capteur a) {
		if (a == null) {
			setVisible(false);
			clearForm();
			return;
		}

		// Chargement des données pour les ComboBox
		murComboBox.setItems(murService.getAllMurs());
		salleComboBox.setItems(salleService.getAllSalles());
		typeCapteurComboBox.setItems(typeCapteurService.getAllTypeCapteurs());

		final boolean persisted = a.getId() != null;
		
		if (persisted) {
			capteur = capteurService.getCapteurById(a.getId());
		} else {
			capteur = a;
		}

		// Configuration de la visibilité des boutons
		cancel.setVisible(true); // Le bouton annuler est toujours visible
		delete.setVisible(persisted); // Le bouton supprimer n'est visible que pour les capteurs existants

		// Binding des données
		binder.setBean(capteur);

		setVisible(true);
		libelleCapteur.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}
}