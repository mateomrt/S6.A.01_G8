package com.sae_s6.S6.APIGestion.view;

import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.views.MurEditor;
import com.sae_s6.S6.APIGestion.views.MurView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour la vue MurView.
 * Vérifie le bon fonctionnement de l'interface utilisateur liée aux murs.
 */
@SpringBootTest
public class MurViewTest {

    // Injection de la vue MurView
    @Autowired
    private MurView murView;

    // Logger pour afficher des informations pendant les tests
    Logger logger = Logger.getLogger(MurViewTest.class.getName());

    /**
     * Teste si la vue MurView est correctement chargée.
     */
    @Test
    void testMurViewLoaded() {
        assertNotNull(murView, "MurView should be loaded by Spring");
    }

    /**
     * Teste si l'éditeur s'affiche correctement lorsqu'un mur est sélectionné.
     */
    @Test
    void editorVisibleWhenMurSelected() {
        // Récupère la grille contenant les murs
        Grid<Mur> grid = murView.grid;

        // Récupère le premier mur de la grille
        Mur firstMur = getFirstItem(grid);
        assertNotNull(firstMur, "There should be at least one Mur in the grid");

        // Récupère l'éditeur
        MurEditor editor = murView.editor;

        // Vérifie que l'éditeur n'est pas visible au départ
        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        // Simule la sélection d'un mur dans la grille
        grid.asSingleSelect().setValue(firstMur);

        // Vérifie que l'éditeur devient visible
        assertTrue(editor.isVisible(), "Editor should be visible when a Mur is selected");

        // Vérifie que les informations du mur sélectionné sont affichées dans l'éditeur
        logger.info("Mur in editor: " + editor.libelleMur.getValue());
        assertEquals(firstMur.getLibelleMur(), editor.libelleMur.getValue());
    }

    /**
     * Teste l'ajout d'un nouveau mur via l'éditeur.
     */
    @Test
    void editorVisibleWhenAddNewButtonClicked() {
        // Récupère l'éditeur
        MurEditor editor = murView.editor;

        // Vérifie que l'éditeur n'est pas visible au départ
        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        // Simule un clic sur le bouton "Ajouter un nouveau mur"
        murView.getAddNewBtn().click();

        // Vérifie que l'éditeur devient visible
        assertTrue(editor.isVisible(), "Editor should be visible when Add New Button is clicked");
    }

    /**
     * Méthode utilitaire pour récupérer le premier élément de la grille.
     * @param grid Grille contenant les murs
     * @return Premier mur de la grille
     */
    private Mur getFirstItem(Grid<Mur> grid) {
        return ((ListDataProvider<Mur>) grid.getDataProvider()).getItems().iterator().next();
    }

    /**
     * Méthode utilitaire pour récupérer le dernier élément de la grille.
     * @param grid Grille contenant les murs
     * @return Dernier mur de la grille
     */
    private Mur getLastItem(Grid<Mur> grid) {
        Collection<Mur> murs = ((ListDataProvider<Mur>) grid.getDataProvider()).getItems();
        List<Mur> murList = new ArrayList<>(murs);
        return murList.get(murList.size() - 1);
    }
}