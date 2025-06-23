package com.sae_s6.S6.APIGestion.view;

import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.views.CapteurEditor;
import com.sae_s6.S6.APIGestion.views.CapteurView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

@SpringBootTest
public class CapteurViewTest {

    @Autowired
    private CapteurView capteurView;

    private Grid<Capteur> grid;
    private CapteurEditor editor;

    @BeforeEach
    void setUp() {
        grid = capteurView.grid;
        editor = capteurView.editor;
    }

    @Test
    void capteurViewLoaded() {
        assertNotNull(capteurView, "La vue CapteurView doit être injectée");
    }

    @Test
    void editorInitialementCache() {
        assertFalse(editor.isVisible(), "L'éditeur doit être caché au démarrage");
    }

    @Test
    void clicAjouterAfficheEditeur() {
        capteurView.getAddNewBtn().click();
        assertTrue(editor.isVisible(), "L'éditeur doit s'afficher après clic sur 'Ajouter'");
    }

    @Test
    void selectionDansGrilleAfficheEditeur() {
        Capteur capteur = getFirstCapteur();
        grid.asSingleSelect().setValue(capteur);
        assertTrue(editor.isVisible(), "L'éditeur doit être visible après sélection");
        assertEquals(capteur.getLibelleCapteur(), editor.libelleCapteur.getValue());
    }

    @Test
    void colonnesDeGrilleCorrectes() {
        assertNotNull(grid.getColumnByKey("id"));
        assertNotNull(grid.getColumnByKey("libelleCapteur"));
        assertNotNull(grid.getColumnByKey("positionXCapteur"));
        assertNotNull(grid.getColumnByKey("positionYCapteur"));
        assertNotNull(grid.getColumnByKey("MurDescription"));
        assertNotNull(grid.getColumnByKey("SalleDescription"));
        assertNotNull(grid.getColumnByKey("typeCapteurDescription"));
    }

    @Test
    void champFiltrePresent() throws Exception {
        var filterField = CapteurView.class.getDeclaredField("filter");
        filterField.setAccessible(true);
        var textField = (com.vaadin.flow.component.textfield.TextField) filterField.get(capteurView);
        assertEquals("Filtrer par nom", textField.getPlaceholder());
    }

    private Capteur getFirstCapteur() {
        Collection<Capteur> capteurs = ((ListDataProvider<Capteur>) grid.getDataProvider()).getItems();
        return capteurs.iterator().next();
    }
}
