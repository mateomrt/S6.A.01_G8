package com.sae_s6.S6.APIGestion.view;

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.views.DonneeEditor;
import com.sae_s6.S6.APIGestion.views.DonneeView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour la vue DonneeView.
 * Vérifie le bon fonctionnement de l'interface utilisateur liée aux données.
 */
@SpringBootTest
public class DonneeViewTest {

    // Injection de la vue DonneeView
    @Autowired
    private DonneeView donneeView;

    // Logger pour afficher des informations pendant les tests
    Logger logger = Logger.getLogger(DonneeViewTest.class.getName());

    /**
     * Initialise l'environnement de test avant chaque test.
     */
    @BeforeEach
    void setupUIContext() {
        UI.setCurrent(new UI());
    }

    /**
     * Teste si la vue DonneeView est correctement chargée.
     */
    @Test
    void testDonneeViewLoaded() {
        assertNotNull(donneeView, "DonneeView should be loaded by Spring");
    }

    /**
     * Teste si l'éditeur s'affiche correctement lorsqu'une donnée est sélectionnée.
     */
    @Test
    void editorVisibleWhenDonneeSelected() {
        Grid<Donnee> grid = donneeView.grid;
        Donnee firstDonnee = getFirstItem(grid);
        assertNotNull(firstDonnee, "There should be at least one Donnee in the grid");

        DonneeEditor editor = donneeView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        grid.asSingleSelect().setValue(firstDonnee);

        assertTrue(editor.isVisible(), "Editor should be visible when a Donnee is selected");

        logger.info("Donnee in editor: " + editor.libelleDonnee.getValue());
        assertEquals(firstDonnee.getLibelleDonnee(), editor.libelleDonnee.getValue());
    }

    /**
     * Teste l'ajout d'une nouvelle donnée via l'éditeur.
     */
    @Test
    void editorVisibleWhenAddNewButtonClicked() {
        DonneeEditor editor = donneeView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        donneeView.getAddNewBtn().click();

        assertTrue(editor.isVisible(), "Editor should be visible after clicking Add New Button");

        assertEquals("", editor.libelleDonnee.getValue(), "New Donnee should have empty libelle");
    
        editor.libelleDonnee.setValue("Humidité relative");
        editor.save.click();

        // Vérifie que la nouvelle Donnee est bien dans la grille
        Donnee lastDonnee = getLastItem(donneeView.grid);
        logger.info("New Donnee should be last in grid: " + lastDonnee);
        assertEquals("Humidité relative", lastDonnee.getLibelleDonnee());
    }

    /**
     * Méthode utilitaire pour récupérer le premier élément de la grille.
     * @param grid Grille contenant les données
     * @return Première donnée de la grille
     */
    private Donnee getFirstItem(Grid<Donnee> grid) {
        return ((ListDataProvider<Donnee>) grid.getDataProvider()).getItems().iterator().next();
    }

    /**
     * Méthode utilitaire pour récupérer le dernier élément de la grille.
     * @param grid Grille contenant les données
     * @return Dernière donnée de la grille
     */
    private Donnee getLastItem(Grid<Donnee> grid) {
        Collection<Donnee> donnees = ((ListDataProvider<Donnee>) grid.getDataProvider()).getItems();
        List<Donnee> donneeList = new ArrayList<>(donnees);
        return donneeList.get(donneeList.size() - 1);
    }
}