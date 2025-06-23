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

@SpringBootTest
public class MurViewTest {

    @Autowired
    private MurView murView;

    Logger logger = Logger.getLogger(MurViewTest.class.getName());

    @Test
    void testMurViewLoaded() {
        assertNotNull(murView, "MurView should be loaded by Spring");
    }

    @Test
    void editorVisibleWhenMurSelected() {
        Grid<Mur> grid = murView.grid;
        Mur firstMur = getFirstItem(grid);
        assertNotNull(firstMur, "There should be at least one Mur in the grid");

        MurEditor editor = murView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        grid.asSingleSelect().setValue(firstMur);

        assertTrue(editor.isVisible(), "Editor should be visible when a Mur is selected");

        logger.info("Mur in editor: " + editor.libelleMur.getValue());
        assertEquals(firstMur.getLibelleMur(), editor.libelleMur.getValue());
    }

    @Test
    void editorVisibleWhenAddNewButtonClicked() {
        MurEditor editor = murView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        murView.getAddNewBtn().click();

        assertTrue(editor.isVisible(), "Editor should be visible when Add New Button is clicked");
    }

    private Mur getFirstItem(Grid<Mur> grid) {
        return ((ListDataProvider<Mur>) grid.getDataProvider()).getItems().iterator().next();
    }

    private Mur getLastItem(Grid<Mur> grid) {
        Collection<Mur> murs = ((ListDataProvider<Mur>) grid.getDataProvider()).getItems();
        List<Mur> murList = new ArrayList<>(murs);
        return murList.get(murList.size() - 1);
    }
}