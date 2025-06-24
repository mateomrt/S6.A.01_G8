package com.sae_s6.S6.APIGestion.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.views.TypeCapteurDonneeEditor;
import com.sae_s6.S6.APIGestion.views.TypeCapteurDonneeView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

/**
 * Classe de test unitaire pour la vue TypeCapteurDonneeView.
 * Vérifie le bon fonctionnement de l'interface utilisateur liée aux associations entre capteurs et données.
 */
@SpringBootTest
public class TypeCapteurDonneeViewTest {

    // Injection de la vue TypeCapteurDonneeView
    @Autowired
    private TypeCapteurDonneeView typeCapteurDonneeView;

    // Logger pour afficher des informations pendant les tests
    Logger logger = Logger.getLogger(TypeCapteurDonneeViewTest.class.getName());

    /**
     * Initialise l'environnement de test avant chaque test.
     */
    @BeforeEach
    void setUp() {
        UI ui = new UI();
        UI.setCurrent(ui);
    }

    /**
     * Teste si la vue TypeCapteurDonneeView est correctement chargée.
     */
    @Test
    void testTypeCapteurDonneeViewLoaded() {
        assertNotNull(typeCapteurDonneeView, "TypeCapteurDonneeView should be loaded by Spring");
    }

    /**
     * Teste si l'éditeur s'affiche correctement lorsqu'une association TypeCapteurDonnee est sélectionnée.
     */
    @Test
    void editorAfficheQuandTypeCapteurDonneeSelectionne() {
        // Récupère la grille contenant les associations
        Grid<TypeCapteurDonnee> grid = typeCapteurDonneeView.grid;

        // Récupère la première association de la grille
        TypeCapteurDonnee firstTypeCapteurDonnee = getFirstItem(grid);
        assertNotNull(firstTypeCapteurDonnee, "There should be at least one TypeCapteurDonnee in the grid");

        // Récupère l'éditeur
        TypeCapteurDonneeEditor editor = typeCapteurDonneeView.editor;

        // Vérifie que l'éditeur n'est pas visible au départ
        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        // Simule la sélection d'une association dans la grille
        grid.asSingleSelect().setValue(firstTypeCapteurDonnee);

        // Vérifie que l'éditeur devient visible
        assertTrue(editor.isVisible(), "Editor should be visible when a TypeCapteurDonnee is selected");

        // Vérifie que les informations de l'association sélectionnée sont affichées dans l'éditeur
        logger.info("TypeCapteurDonnee in editor: " + editor.donneeComboBox.getValue() + editor.typeCapteurComboBox.getValue());
        assertEquals(firstTypeCapteurDonnee.getDonneeNavigation().getLibelleDonnee(), editor.donneeComboBox.getValue().getLibelleDonnee());
        assertEquals(firstTypeCapteurDonnee.getTypeCapteurNavigation().getLibelleTypeCapteur(), editor.typeCapteurComboBox.getValue().getLibelleTypeCapteur());
    }

    /**
     * Teste l'ajout d'une nouvelle association TypeCapteurDonnee via l'éditeur.
     */
    @Test
    void editorAfficheQuandNewTypeCapteurDonneeClicked() {
        // Récupère l'éditeur
        TypeCapteurDonneeEditor editor = typeCapteurDonneeView.editor;

        // Vérifie que l'éditeur n'est pas visible au départ
        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        // Simule un clic sur le bouton "Ajouter une nouvelle association"
        typeCapteurDonneeView.getAddNewBtn().click();

        // Vérifie que l'éditeur devient visible
        assertTrue(editor.isVisible(), "Editor should be visible after clicking add new button");

        // Vérifie que les champs sont vides ou initialisés
        assertNull(editor.donneeComboBox.getValue(), "New TypeCapteurDonnee should have no Donnee selected");

        // Récupère la liste des Donnee disponibles dans le ComboBox
        List<Donnee> donnees = new ArrayList<>(editor.donneeComboBox
            .getDataProvider()
            .fetch(new com.vaadin.flow.data.provider.Query<>())
            .toList());

        assertFalse(donnees.isEmpty(), "Aucune Donnee disponible dans le ComboBox");

        // Sélectionne une Donnee pour le test
        Donnee donneeTest = donnees.get(1);

        // Simule la saisie
        editor.donneeComboBox.setValue(donneeTest);
        editor.save.click();

        // Vérifie que la nouvelle association est bien dans la grille
        TypeCapteurDonnee lastTypeCapteurDonnee = getLastItem(typeCapteurDonneeView.grid);
        logger.info("New TypeCapteurDonnee should be last in grid: " + lastTypeCapteurDonnee);
        assertEquals(donneeTest.getLibelleDonnee(), lastTypeCapteurDonnee.getDonneeNavigation().getLibelleDonnee());
    }

    /**
     * Méthode utilitaire pour récupérer le premier élément de la grille.
     * @param grid Grille contenant les associations TypeCapteurDonnee
     * @return Première association de la grille
     */
    private TypeCapteurDonnee getFirstItem(Grid<TypeCapteurDonnee> grid) {
        return ((ListDataProvider<TypeCapteurDonnee>) grid.getDataProvider()).getItems().iterator().next();
    }

    /**
     * Méthode utilitaire pour récupérer le dernier élément de la grille.
     * @param grid Grille contenant les associations TypeCapteurDonnee
     * @return Dernière association de la grille
     */
    private TypeCapteurDonnee getLastItem(Grid<TypeCapteurDonnee> grid) {
        Collection<TypeCapteurDonnee> typeCapteurDonnees = ((ListDataProvider<TypeCapteurDonnee>) grid.getDataProvider()).getItems();
        List<TypeCapteurDonnee> typeCapteurDonneeList = new ArrayList<>(typeCapteurDonnees);
        return typeCapteurDonneeList.get(typeCapteurDonneeList.size() - 1);
    }
}