package com.sae_s6.S6.APIGestion.view;

import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.views.TypeSalleEditor;
import com.sae_s6.S6.APIGestion.views.TypeSalleView;
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
public class TypeSalleViewTest {

    @Autowired
    private TypeSalleView typeSalleView;

    Logger logger = Logger.getLogger(TypeSalleViewTest.class.getName());

    @Test
    void testTypeSalleViewLoaded() {
        assertNotNull(typeSalleView, "TypeSalleView should be loaded by Spring");
    }

    @Test
    void editorVisibleWhenTypeSalleSelected() {
        Grid<TypeSalle> grid = typeSalleView.grid;
        TypeSalle firstTypeSalle = getFirstItem(grid);
        assertNotNull(firstTypeSalle, "There should be at least one TypeSalle in the grid");

        TypeSalleEditor editor = typeSalleView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        grid.asSingleSelect().setValue(firstTypeSalle);

        assertTrue(editor.isVisible(), "Editor should be visible when a TypeSalle is selected");

        logger.info("TypeSalle in editor: " + editor.libelleTypeSalle.getValue());
        assertEquals(firstTypeSalle.getLibelleTypeSalle(), editor.libelleTypeSalle.getValue());
    }

    @Test
    void editorVisibleWhenAddNewButtonClicked() {
        TypeSalleEditor editor = typeSalleView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        typeSalleView.getAddNewBtn().click();

        assertTrue(editor.isVisible(), "Editor should be visible after clicking add new button");

        assertEquals("", editor.libelleTypeSalle.getValue(), "New TypeSalle should have empty libelle");

        // Simuler la saisie d'un nouveau libellé
        editor.libelleTypeSalle.setValue("Nouveau TypeSalle Test");
        editor.save.click();

        // Vérifier que le nouveau TypeSalle est présent dans la grille
        TypeSalle lastTypeSalle = getLastItem(typeSalleView.grid);
        logger.info("New TypeSalle should be last in grid: " + lastTypeSalle);
        assertEquals("Nouveau TypeSalle Test", lastTypeSalle.getLibelleTypeSalle());
    }

    private TypeSalle getFirstItem(Grid<TypeSalle> grid) {
        return ((ListDataProvider<TypeSalle>) grid.getDataProvider()).getItems().iterator().next();
    }

    private TypeSalle getLastItem(Grid<TypeSalle> grid) {
        Collection<TypeSalle> typeSalles = ((ListDataProvider<TypeSalle>) grid.getDataProvider()).getItems();
        List<TypeSalle> typeSalleList = new ArrayList<>(typeSalles);
        return typeSalleList.get(typeSalleList.size() - 1);
    }
}