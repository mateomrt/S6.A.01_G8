package com.sae_s6.S6.APIGestion.view;

import com.sae_s6.S6.APIGestion.entity.Equipement;
import com.sae_s6.S6.APIGestion.views.EquipementEditor;
import com.sae_s6.S6.APIGestion.views.EquipementView;
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
public class EquipementViewTest {

    @Autowired
    private EquipementView equipementView;

    Logger logger = Logger.getLogger(EquipementViewTest.class.getName());

    @Test
    void testEquipementViewLoaded() {
        assertNotNull(equipementView, "EquipementView should be loaded by Spring");
    }

    @Test
    void editorVisibleWhenEquipementSelected() {
        Grid<Equipement> grid = equipementView.grid;
        Equipement firstEquipement = getFirstItem(grid);
        assertNotNull(firstEquipement, "There should be at least one Equipement in the grid");

        EquipementEditor editor = (EquipementEditor) grid.getParent().get().getChildren()
                .filter(component -> component instanceof EquipementEditor)
                .findFirst()
                .orElse(null);

        assertNotNull(editor, "EquipementEditor should be present");
        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        grid.asSingleSelect().setValue(firstEquipement);

        assertTrue(editor.isVisible(), "Editor should be visible when an Equipement is selected");

        logger.info("Equipement in editor: " + editor.libelleEquipement.getValue());
        assertEquals(firstEquipement.getLibelleEquipement(), editor.libelleEquipement.getValue());
    }

    @Test
    void editorVisibleWhenAddNewButtonClicked() {
        EquipementEditor editor = (EquipementEditor) equipementView.getChildren()
                .filter(component -> component instanceof EquipementEditor)
                .findFirst()
                .orElse(null);
    
        assertNotNull(editor, "EquipementEditor should be present");
        assertFalse(editor.isVisible(), "Editor should not be visible initially");
    
        equipementView.getAddNewBtn().click();
    
        assertTrue(editor.isVisible(), "Editor should be visible when Add New Button is clicked");
    }
    

    private Equipement getFirstItem(Grid<Equipement> grid) {
        return ((ListDataProvider<Equipement>) grid.getDataProvider()).getItems().iterator().next();
    }

    private Equipement getLastItem(Grid<Equipement> grid) {
        Collection<Equipement> equipements = ((ListDataProvider<Equipement>) grid.getDataProvider()).getItems();
        List<Equipement> equipementList = new ArrayList<>(equipements);
        return equipementList.get(equipementList.size() - 1);
    }
}