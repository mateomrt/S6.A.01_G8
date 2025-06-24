package com.sae_s6.S6.APIGestion.view;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.views.TypeEquipementEditor;
import com.sae_s6.S6.APIGestion.views.TypeEquipementView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;


@SpringBootTest
public class TypeEquipementViewTest {
    @Autowired
    private TypeEquipementView typeEquipementView;

    Logger logger = Logger.getLogger(TypeEquipementViewTest.class.getName());

    @Test
    void testTypeEquipementViewLoaded() {
       
        assert typeEquipementView != null;
    }

    @Test
    void editorAfficheQuandTypeEquipementSelectionne() {
        Grid<TypeEquipement> grid = typeEquipementView.grid;
        TypeEquipement firstTypeEquipement = getFirstItem(grid);
        logger.info("First TypeEquipement: " + firstTypeEquipement);

        TypeEquipementEditor editor = typeEquipementView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");
        grid.asSingleSelect().setValue(firstTypeEquipement);
        assertTrue(editor.isVisible(), "Editor should be visible when a TypeEquipement is selected");

        logger.info("TypeEquipement in editor: " + editor.libelleTypeEquipement.getValue());
        assertEquals(firstTypeEquipement.getLibelleTypeEquipement(), editor.libelleTypeEquipement.getValue());
    }

    @Test
    void editorAfficheQuandNewTypeEquipementClicked() {
        TypeEquipementEditor editor = typeEquipementView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        typeEquipementView.getAddNewBtn().click();

        assertTrue(editor.isVisible(), "Editor should be visible after clicking add new button");

        assertEquals("", editor.libelleTypeEquipement.getValue(), "New TypeEquipement should have empty libelle");

        // Simuler la saisie
        editor.libelleTypeEquipement.setValue("Nouveau TypeEquipement Test");
        editor.save.click();

        // Vérifier que le nouveau TypeEquipement est bien dans la grille
        TypeEquipement lastTypeEquipement = getLastItem(typeEquipementView.grid);
        logger.info("New TypeEquipement should be last in grid: " + lastTypeEquipement);
        assertEquals("Nouveau TypeEquipement Test", lastTypeEquipement.getLibelleTypeEquipement());
    }

    private TypeEquipement getFirstItem(Grid<TypeEquipement> grid) {
        return ((ListDataProvider<TypeEquipement>) grid.getDataProvider()).getItems().iterator().next();
    }

    private TypeEquipement getLastItem(Grid<TypeEquipement> grid) {
        Collection<TypeEquipement> typeEquipements = ((ListDataProvider<TypeEquipement>) grid.getDataProvider()).getItems();
        List<TypeEquipement> typeEquipementList = new ArrayList<>(typeEquipements);
        return typeEquipementList.get(typeEquipementList.size() - 1);
    }

}
