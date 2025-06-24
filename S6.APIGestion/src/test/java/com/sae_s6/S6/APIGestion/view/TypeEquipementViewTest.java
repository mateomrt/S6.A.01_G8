package com.sae_s6.S6.APIGestion.view;

import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.views.TypeEquipementEditor;
import com.sae_s6.S6.APIGestion.views.TypeEquipementView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

@SpringBootTest
public class TypeEquipementViewTest {
    @Autowired
    private TypeEquipementView typeEquipementView;

    private Grid<TypeEquipement> grid;
    private TypeEquipementEditor editor;

    @BeforeEach
    void setUp() {
        grid = typeEquipementView.grid;
        editor = typeEquipementView.editor;
    }

    @Test
    void typeEquipementViewLoaded() {
        assertNotNull(typeEquipementView, "La vue TypeEquipementView doit être injectée");
    }

    @Test
    void editorInitialementCache() {
        assertFalse(editor.isVisible(), "L'éditeur doit être caché au démarrage");
    }

    @Test
    void clicAjouterAfficheEditeur() {
        typeEquipementView.getAddNewBtn().click();
        assertTrue(editor.isVisible(), "L'éditeur doit s'afficher après clic sur 'Ajouter'");
    }

    @Test
    void selectionDansGrilleAfficheEditeur() {
        TypeEquipement typeEquipement = getFirstTypeEquipement();
        grid.asSingleSelect().setValue(typeEquipement);
        assertTrue(editor.isVisible(), "L'éditeur doit être visible après sélection");
        assertEquals(typeEquipement.getLibelleTypeEquipement(), editor.libelleTypeEquipement.getValue());
    }

    @Test
    void colonnesDeGrilleCorrectes() {
        assertNotNull(grid.getColumnByKey("id"));
        assertNotNull(grid.getColumnByKey("libelleTypeEquipement"));
    }

    @Test
    void champFiltrePresent() throws Exception {
        var filterField = TypeEquipementView.class.getDeclaredField("filter");
        filterField.setAccessible(true);
        var textField = (com.vaadin.flow.component.textfield.TextField) filterField.get(typeEquipementView);
        assertEquals("Filtrer par nom", textField.getPlaceholder());
    }

    private TypeEquipement getFirstTypeEquipement() {
        Collection<TypeEquipement> typeEquipements = ((ListDataProvider<TypeEquipement>) grid.getDataProvider()).getItems();
        return typeEquipements.iterator().next();
    }

}
