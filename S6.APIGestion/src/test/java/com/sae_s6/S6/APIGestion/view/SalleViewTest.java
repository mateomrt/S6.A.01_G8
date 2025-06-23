package com.sae_s6.S6.APIGestion.view;

import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.views.SalleEditor;
import com.sae_s6.S6.APIGestion.views.SalleView;
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
public class SalleViewTest {

    @Autowired
    private SalleView salleView;

    Logger logger = Logger.getLogger(SalleViewTest.class.getName());

    @Test
    void testSalleViewLoaded() {
        assertNotNull(salleView, "SalleView should be loaded by Spring");
    }

    @Test
    void editorVisibleWhenSalleSelected() {
        Grid<Salle> grid = salleView.grid;
        Salle firstSalle = getFirstItem(grid);
        assertNotNull(firstSalle, "There should be at least one Salle in the grid");

        SalleEditor editor = salleView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        grid.asSingleSelect().setValue(firstSalle);

        assertTrue(editor.isVisible(), "Editor should be visible when a Salle is selected");

        logger.info("Salle in editor: " + editor.libelleSalle.getValue());
        assertEquals(firstSalle.getLibelleSalle(), editor.libelleSalle.getValue());
    }

    @Test
    void editorVisibleWhenAddNewButtonClicked() {
    }

    private Salle getFirstItem(Grid<Salle> grid) {
        return ((ListDataProvider<Salle>) grid.getDataProvider()).getItems().iterator().next();
    }

    private Salle getLastItem(Grid<Salle> grid) {
        Collection<Salle> salles = ((ListDataProvider<Salle>) grid.getDataProvider()).getItems();
        List<Salle> salleList = new ArrayList<>(salles);
        return salleList.get(salleList.size() - 1);
    }
}