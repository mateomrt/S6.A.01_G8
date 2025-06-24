package com.sae_s6.S6.APIGestion.view;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.views.BatimentEditor;
import com.sae_s6.S6.APIGestion.views.BatimentView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

/**
 * Classe de test unitaire pour la vue BatimentView.
 * Vérifie le bon fonctionnement de l'interface utilisateur liée aux bâtiments.
 */
@SpringBootTest
public class BatimentViewTest {

    // Injection de la vue BatimentView
    @Autowired
    private BatimentView batimentView;

    // Logger pour afficher des informations pendant les tests
    Logger logger = Logger.getLogger(BatimentViewTest.class.getName());

    /**
     * Teste si la vue BatimentView est correctement chargée.
     */
    @Test
    void testBatimentViewLoaded() {
        assert batimentView != null;
    }

    /**
     * Teste si l'éditeur s'affiche correctement lorsqu'un bâtiment est sélectionné.
     */
    @Test
    void editorAfficheQuandBatimentSelectionne() {
        // Récupère la grille de bâtiments
        Grid<Batiment> grid = batimentView.grid;

        // Récupère le premier bâtiment de la grille
        Batiment firstBatiment = getFirstItem(grid);
        logger.info("First Batiment: " + firstBatiment);

        // Récupère l'éditeur
        BatimentEditor editor = batimentView.editor;

        // Vérifie que l'éditeur n'est pas visible au départ
        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        // Simule la sélection d'un bâtiment dans la grille
        grid.asSingleSelect().setValue(firstBatiment);

        // Vérifie que l'éditeur devient visible
        assertTrue(editor.isVisible(), "Editor should be visible when a Batiment is selected");

        // Vérifie que les informations du bâtiment sélectionné sont affichées dans l'éditeur
        logger.info("Batiment in editor: " + editor.libelleBatiment.getValue());
        assertEquals(firstBatiment.getLibelleBatiment(), editor.libelleBatiment.getValue());
    }

    /**
     * Teste si l'éditeur s'affiche correctement lorsqu'on clique sur le bouton "Ajouter un nouveau bâtiment".
     */
    @Test
    void editorAfficheQuandNewBatimentClicked() {
        // Récupère l'éditeur
        BatimentEditor editor = batimentView.editor;

        // Vérifie que l'éditeur n'est pas visible au départ
        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        // Simule un clic sur le bouton "Ajouter un nouveau bâtiment"
        batimentView.getAddNewBtn().click();

        // Vérifie que l'éditeur devient visible
        assertTrue(editor.isVisible(), "Editor should be visible after clicking add new button");

        // Vérifie que les champs de l'éditeur sont vides pour un nouveau bâtiment
        assertEquals("", editor.libelleBatiment.getValue(), "New Batiment should have empty libelle");

        // Simule la saisie d'un nouveau bâtiment
        editor.libelleBatiment.setValue("Nouveau Batiment Test");
        editor.save.click();

        // Vérifie que le nouveau bâtiment est ajouté à la grille
        Batiment lastBatiment = getLastItem(batimentView.grid);
        logger.info("New Batiment should be last in grid: " + lastBatiment);
        assertEquals("Nouveau Batiment Test", lastBatiment.getLibelleBatiment());
    }

    /**
     * Récupère le premier élément de la grille.
     * @param grid Grille contenant les bâtiments
     * @return Premier bâtiment de la grille
     */
    private Batiment getFirstItem(Grid<Batiment> grid) {
        return ((ListDataProvider<Batiment>) grid.getDataProvider()).getItems().iterator().next();
    }

    /**
     * Récupère le dernier élément de la grille.
     * @param grid Grille contenant les bâtiments
     * @return Dernier bâtiment de la grille
     */
    private Batiment getLastItem(Grid<Batiment> grid) {
        Collection<Batiment> batiments = ((ListDataProvider<Batiment>) grid.getDataProvider()).getItems();
        List<Batiment> batimentList = new ArrayList<>(batiments);
        return batimentList.get(batimentList.size() - 1);
    }
}