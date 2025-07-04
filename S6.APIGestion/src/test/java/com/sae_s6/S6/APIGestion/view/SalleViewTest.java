package com.sae_s6.S6.APIGestion.view;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.views.SalleEditor;
import com.sae_s6.S6.APIGestion.views.SalleView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour la vue SalleView.
 * Vérifie le bon fonctionnement de l'interface utilisateur liée aux salles.
 */
@SpringBootTest
public class SalleViewTest {

    // Injection de la vue SalleView
    @Autowired
    private SalleView salleView;

    // Logger pour afficher des informations pendant les tests
    Logger logger = Logger.getLogger(SalleViewTest.class.getName());

    /**
     * Teste si la vue SalleView est correctement chargée.
     */
    @Test
    void testSalleViewLoaded() {
        assertNotNull(salleView, "SalleView should be loaded by Spring");
    }

    /**
     * Teste si l'éditeur s'affiche correctement lorsqu'une salle est sélectionnée.
     */
    @Test
    void editorVisibleWhenSalleSelected() {
        // Récupère la grille contenant les salles
        Grid<Salle> grid = salleView.grid;

        // Récupère la première salle de la grille
        Salle firstSalle = getFirstItem(grid);
        assertNotNull(firstSalle, "There should be at least one Salle in the grid");

        // Récupère l'éditeur
        SalleEditor editor = salleView.editor;

        // Vérifie que l'éditeur n'est pas visible au départ
        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        // Simule la sélection d'une salle dans la grille
        grid.asSingleSelect().setValue(firstSalle);

        // Vérifie que l'éditeur devient visible
        assertTrue(editor.isVisible(), "Editor should be visible when a Salle is selected");

        // Vérifie que les informations de la salle sélectionnée sont affichées dans l'éditeur
        logger.info("Salle in editor: " + editor.libelleSalle.getValue());
        assertEquals(firstSalle.getLibelleSalle(), editor.libelleSalle.getValue());
    }

    /**
     * Teste l'ajout d'une nouvelle salle via l'éditeur.
     */
    @Test
    void editorVisibleWhenAddNewButtonClicked() {
        // Récupère l'éditeur
        SalleEditor editor = salleView.editor;

        // Vérifie que l'éditeur n'est pas visible au départ
        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        // Simule un clic sur le bouton "Ajouter une nouvelle salle"
        salleView.getAddNewBtn().click();

        // Vérifie que l'éditeur devient visible
        assertTrue(editor.isVisible(), "Editor should be visible when Add New Button is clicked");

        // Sauvegarde le nombre de salles avant ajout
        Collection<Salle> items = ((ListDataProvider<Salle>) salleView.grid.getDataProvider()).getItems();
        int nbSalles = items.size();

        // Vérifie que les dépendances nécessaires sont disponibles
        List<TypeSalle> typeSalles = new ArrayList<>(editor.typeSalleComboBox.getDataProvider().fetch(new Query<>()).toList());
        List<Batiment> batiments = new ArrayList<>(editor.batimentComboBox.getDataProvider().fetch(new Query<>()).toList());

        assertFalse(typeSalles.isEmpty(), "Aucun type de salle disponible pour le test");
        assertFalse(batiments.isEmpty(), "Aucun bâtiment disponible pour le test");

        // Sélectionne les premiers éléments valides
        TypeSalle typeSalleTest = typeSalles.get(0);
        Batiment batimentTest = batiments.get(0);

        // Remplit le formulaire
        editor.libelleSalle.setValue("Salle Test");
        editor.typeSalleComboBox.setValue(typeSalleTest);
        editor.batimentComboBox.setValue(batimentTest);

        // Simule un clic sur le bouton "Save"
        editor.save.click();

        // Vérifie que la nouvelle salle a été ajoutée
        Collection<Salle> updatedItems = ((ListDataProvider<Salle>) salleView.grid.getDataProvider()).getItems();
        assertEquals(nbSalles + 1, updatedItems.size(), "Le nombre de salles devrait augmenter de 1 après l'ajout");

        Salle lastSalle = getLastItem(salleView.grid);
        assertEquals("Salle Test", lastSalle.getLibelleSalle(), "Le libellé de la salle ajoutée devrait correspondre");
        assertEquals(typeSalleTest, lastSalle.getTypeSalleNavigation(), "Le type de salle ajouté devrait correspondre");
        assertEquals(batimentTest, lastSalle.getBatimentNavigation(), "Le bâtiment de la salle ajoutée devrait correspondre");
    }

    /**
     * Méthode utilitaire pour récupérer le premier élément de la grille.
     * @param grid Grille contenant les salles
     * @return Première salle de la grille
     */
    private Salle getFirstItem(Grid<Salle> grid) {
        return ((ListDataProvider<Salle>) grid.getDataProvider()).getItems().iterator().next();
    }

    /**
     * Méthode utilitaire pour récupérer le dernier élément de la grille.
     * @param grid Grille contenant les salles
     * @return Dernière salle de la grille
     */
    private Salle getLastItem(Grid<Salle> grid) {
        Collection<Salle> salles = ((ListDataProvider<Salle>) grid.getDataProvider()).getItems();
        List<Salle> salleList = new ArrayList<>(salles);
        return salleList.get(salleList.size() - 1);
    }
}