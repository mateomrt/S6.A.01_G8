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


@SpringBootTest
public class BatimentViewTest {

    @Autowired
    private BatimentView batimentView;

    Logger logger = Logger.getLogger(BatimentViewTest.class.getName());

    @Test
    void testBatimentViewLoaded() {
       
        assert batimentView != null;
    }

    @Test
    void editorAfficheQuandBatimentSelectionne() {
        Grid<Batiment> grid = batimentView.grid;
        Batiment firstBatiment = getFirstItem(grid);
        logger.info("First Batiment: " + firstBatiment);

        BatimentEditor editor = batimentView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");
        grid.asSingleSelect().setValue(firstBatiment);
        assertTrue(editor.isVisible(), "Editor should be visible when a Batiment is selected");

        logger.info("Batiment in editor: " + editor.libelleBatiment.getValue());
        assertEquals(firstBatiment.getLibelleBatiment(), editor.libelleBatiment.getValue());
    }

    @Test
    void editorAfficheQuandNewBatimentClicked() {
        BatimentEditor editor = batimentView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        batimentView.getAddNewBtn().click();

        assertTrue(editor.isVisible(), "Editor should be visible after clicking add new button");

        assertEquals("", editor.libelleBatiment.getValue(), "New Batiment should have empty libelle");

        // Simuler la saisie
        editor.libelleBatiment.setValue("Nouveau Batiment Test");
        editor.save.click();

        // Vérifier que le nouveau Batiment est bien dans la grille
        Batiment lastBatiment = getLastItem(batimentView.grid);
        logger.info("New Batiment should be last in grid: " + lastBatiment);
        assertEquals("Nouveau Batiment Test", lastBatiment.getLibelleBatiment());
    }

    private Batiment getFirstItem(Grid<Batiment> grid) {
        return ((ListDataProvider<Batiment>) grid.getDataProvider()).getItems().iterator().next();
    }

    private Batiment getLastItem(Grid<Batiment> grid) {
        Collection<Batiment> batiments = ((ListDataProvider<Batiment>) grid.getDataProvider()).getItems();
        List<Batiment> batimentList = new ArrayList<>(batiments);
        return batimentList.get(batimentList.size() - 1);
    }

}