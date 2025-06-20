package com.sae_s6.S6.APIGestion.views;

import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.TypeCapteurService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class TypeCapteurEditor extends VerticalLayout implements KeyNotifier {

	private final TypeCapteurService typeCapteurService;

	private TypeCapteur typeCapteur;
	private TypeCapteur originalTypeCapteur;

	/* Fields to edit properties in TypeCapteur entity */
	TextField libelleTypeCapteur = new TextField("Libellé type capteur");
	TextField modeTypeCapteur = new TextField("Mode du type capteur");

	/* Action buttons */
	Button save = new Button("Sauvegarder", VaadinIcon.CHECK.create());
	Button cancel = new Button("Annuler");
	Button delete = new Button("Supprimer", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<TypeCapteur> binder = new Binder<>(TypeCapteur.class);
	private ChangeHandler changeHandler;

	public TypeCapteurEditor(TypeCapteurService typeCapteurService) {
		this.typeCapteurService = typeCapteurService;

		// Organisation des champs en ligne horizontale
		HorizontalLayout fieldsRow = new HorizontalLayout(libelleTypeCapteur, modeTypeCapteur);
		fieldsRow.setWidthFull();
		fieldsRow.setSpacing(true);

		// Configuration de la largeur des champs pour une répartition équitable
		libelleTypeCapteur.setWidthFull();
		modeTypeCapteur.setWidthFull();

		add(fieldsRow, actions);

		// Configuration du binder
		binder.bindInstanceFields(this);

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
		typeCapteurService.deleteTypeCapteurById(typeCapteur.getId());
		changeHandler.onChange();
	}

	void save() {
		// Validation avant sauvegarde
		if (!binder.validate().isOk()) {
			return;
		}

		if (typeCapteur.getId() == null) {
			typeCapteurService.saveTypeCapteur(typeCapteur);
		} else {
			typeCapteurService.updateTypeCapteur(typeCapteur);
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
		libelleTypeCapteur.clear();
		modeTypeCapteur.clear();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editTypeCapteur(TypeCapteur a) {
		if (a == null) {
			setVisible(false);
			clearForm();
			return;
		}

		final boolean persisted = a.getId() != null;
		
		if (persisted) {
			// TypeCapteur existant : on charge depuis la BD
			originalTypeCapteur = typeCapteurService.getTypeCapteurById(a.getId());
			typeCapteur = typeCapteurService.getTypeCapteurById(a.getId());
		} else {
			// Nouveau TypeCapteur
			originalTypeCapteur = null;
			typeCapteur = a;
		}

		// Configuration de la visibilité des boutons
		cancel.setVisible(true); // Le bouton annuler est toujours visible
		delete.setVisible(persisted); // Le bouton supprimer n'est visible que pour les types existants

		// Binding des données
		binder.setBean(typeCapteur);

		setVisible(true);
		libelleTypeCapteur.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}
}