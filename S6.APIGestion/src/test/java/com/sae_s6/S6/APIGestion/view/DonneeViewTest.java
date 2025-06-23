package com.sae_s6.S6.APIGestion.view;

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.views.DonneeEditor;
import com.sae_s6.S6.APIGestion.views.DonneeView;
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

@SpringBootTest
public class DonneeViewTest {

    @Autowired
    private DonneeView donneeView;

    Logger logger = Logger.getLogger(DonneeViewTest.class.getName());

    @Test
    void testDonneeViewLoaded() {
        assertNotNull(donneeView, "DonneeView should be loaded by Spring");
    }

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

    @Test
    void editorVisibleWhenAddNewButtonClicked() {
        DonneeEditor editor = donneeView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        donneeView.getAddNewBtn().click();

        assertTrue(editor.isVisible(), "Editor should be visible after clicking Add New Button");

        assertEquals("", editor.libelleDonnee.getValue(), "New Donnee should have empty libelle");
    
        editor.libelleDonnee.setValue("Nouvelle Donnee Test");
        editor.save.click();

        // Vérifier que la nouvelle Donnee est bien dans la grille
        Donnee lastDonnee = getLastItem(donneeView.grid);
        logger.info("New Donnee should be last in grid: " + lastDonnee);
        assertEquals("Nouvelle Donnee Test", lastDonnee.getLibelleDonnee());
    }

    private Donnee getFirstItem(Grid<Donnee> grid) {
        return ((ListDataProvider<Donnee>) grid.getDataProvider()).getItems().iterator().next();
    }

    private Donnee getLastItem(Grid<Donnee> grid) {
        Collection<Donnee> donnees = ((ListDataProvider<Donnee>) grid.getDataProvider()).getItems();
        List<Donnee> donneeList = new ArrayList<>(donnees);
        return donneeList.get(donneeList.size() - 1);
    }
}