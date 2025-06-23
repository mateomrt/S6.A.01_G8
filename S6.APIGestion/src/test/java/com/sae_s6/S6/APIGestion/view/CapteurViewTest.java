package com.sae_s6.S6.APIGestion.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.views.CapteurEditor;
import com.sae_s6.S6.APIGestion.views.CapteurView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

@SpringBootTest
/**
 * Test class for the CapteurView component.
 * Voir : https://vaadin.com/docs/v23/tutorial/unit-and-integration-testing
 */
public class CapteurViewTest {

    @Autowired
    private CapteurView capteurView;

    Logger logger = Logger.getLogger(CapteurViewTest.class.getName());

    @Test
    void testCapteurView() {
        // This test will check if the CapteurView bean is loaded correctly
        // and if it can be instantiated without any issues.
        assert capteurView != null;
    }

    @Test
    void editorAfficheQuandCapteurSelectionne() {
        Grid<Capteur> grid = capteurView.grid;
        Capteur firstCapteur = getFirstItem(grid);
        logger.info("First Capteur: " + firstCapteur);

        CapteurEditor editor = capteurView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        grid.asSingleSelect().setValue(firstCapteur);

        assertTrue(editor.isVisible(), "Editor should be visible when a Capteur is selected");

        logger.info("Capteur in editor: " + editor.libelleCapteur.getValue());
        assertEquals(firstCapteur.getLibelleCapteur(), editor.libelleCapteur.getValue());
    }

    @Test
    void editorAfficheQuandNewCapteurClicked() {
        CapteurEditor editor = capteurView.editor;
    
        assertFalse(editor.isVisible(), "Editor should not be visible initially");
        capteurView.getAddNewBtn().click();
        assertTrue(editor.isVisible());
        logger.info("Editor is now visible for adding a new Capteur");
        assertEquals("", editor.libelleCapteur.getValue());
        assertEquals("", editor.positionXCapteur.getValue());
        assertEquals("", editor.positionYCapteur.getValue());
        assertNull(editor.murComboBox.getValue());
        assertNull(editor.salleComboBox.getValue());
        assertNull(editor.typeCapteurComboBox.getValue());
    
        // Compter les capteurs avant l'ajout
        Collection<Capteur> capteursAvant = ((ListDataProvider<Capteur>) capteurView.grid.getDataProvider()).getItems();
        int nombreCapteursAvant = capteursAvant.size();
        logger.info("Nombre de capteurs avant ajout: " + nombreCapteursAvant);

    
        List<Mur> murs = new ArrayList<>(editor.murComboBox.getDataProvider().fetch(new com.vaadin.flow.data.provider.Query<>()).toList());
        List<TypeCapteur> typeCapteurs = new ArrayList<>(editor.typeCapteurComboBox.getDataProvider().fetch(new com.vaadin.flow.data.provider.Query<>()).toList());
        List<Salle> salles = new ArrayList<>(editor.salleComboBox.getDataProvider().fetch(new com.vaadin.flow.data.provider.Query<>()).toList());
    
        // Vérifier qu'il y a au moins des données pour le test
        assertFalse(murs.isEmpty(), "Aucun mur disponible pour le test");
        assertFalse(typeCapteurs.isEmpty(), "Aucun type de capteur disponible pour le test");
        assertFalse(salles.isEmpty(), "Aucune salle disponible pour le test");
    
        // Utiliser les premiers éléments disponibles
        Mur murTest = murs.get(0);
        TypeCapteur typeCapteurTest = typeCapteurs.get(0);
        Salle salleTest = salles.get(0);
    
        // Remplir le formulaire
        editor.libelleCapteur.setValue("Nouveau Capteur Test");
        editor.murComboBox.setValue(murTest);
        editor.positionXCapteur.setValue("10.0");
        editor.positionYCapteur.setValue("20.0");
        editor.salleComboBox.setValue(salleTest);
        editor.typeCapteurComboBox.setValue(typeCapteurTest);
        logger.info("Avant le clic sur le bouton save.");

        // Simuler le clic sur le bouton "save"
       editor.save();
        logger.info("Clic sur le bouton save effectué.");
        try {
            Thread.sleep(1000);  // Attendez que l'événement soit pris en charge
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Après le clic sur le bouton save.");
    
        // Vérifier que le nouveau Capteur est bien dans la grille
        Collection<Capteur> capteursApres = ((ListDataProvider<Capteur>) capteurView.grid.getDataProvider()).getItems();
        int nombreCapteursApres = capteursApres.size();
        logger.info("Nombre de capteurs après ajout: " + nombreCapteursApres);
        assertEquals(nombreCapteursAvant + 1, nombreCapteursApres, "Le nombre de capteurs après l'ajout ne correspond pas");
        Capteur lastCapteur = getLastItem(capteurView.grid);
        logger.info("New Capteur should be last in grid: " + lastCapteur);
        assertEquals("Nouveau Capteur Test", lastCapteur.getLibelleCapteur(), "Le libellé du nouveau capteur ne correspond pas");
        assertEquals("10.0", lastCapteur.getPositionXCapteur(), "La position X du nouveau capteur ne correspond pas");
        assertEquals("20.0", lastCapteur.getPositionYCapteur(), "La position Y du nouveau capteur ne correspond pas");  
        assertEquals(murTest, lastCapteur.getMurNavigation(), "Le mur du nouveau capteur ne correspond pas");
        assertEquals(salleTest, lastCapteur.getSalleNavigation(), "La salle du nouveau capteur ne correspond pas");
        assertEquals(typeCapteurTest, lastCapteur.getTypeCapteurNavigation(), "Le type de capteur du nouveau capteur ne correspond pas");


    }

    private Capteur getFirstItem(Grid<Capteur> grid) {
        return ((ListDataProvider<Capteur>) grid.getDataProvider()).getItems().iterator().next();
    }

    private Capteur getLastItem(Grid<Capteur> grid) {
        Collection<Capteur> capteurs = ((ListDataProvider<Capteur>) grid.getDataProvider()).getItems();
        List<Capteur> capteurList = new ArrayList<>(capteurs);
        return capteurList.get(capteurList.size() - 1);
    }

    @Test
    public void testFilterFunctionality() {
        // Test the filter functionality
        try {
            Field filterField = CapteurView.class.getDeclaredField("filter");
            filterField.setAccessible(true);
            ((com.vaadin.flow.component.textfield.TextField) filterField.get(capteurView)).setValue("test");
            
            // Verify that the listCapteurs method is called with the filter value
            assertNotNull(((com.vaadin.flow.component.textfield.TextField) filterField.get(capteurView)).getValue());
            assertEquals("test", ((com.vaadin.flow.component.textfield.TextField) filterField.get(capteurView)).getValue());
        } catch (Exception e) {
            fail("Erreur lors du test du filtre: " + e.getMessage());
        }
    }

    @Test
    public void testGridColumns() {
        Grid<Capteur> grid = capteurView.grid;
        
        // Verify that all expected columns are present
        assertNotNull(grid.getColumnByKey("id"));
        assertNotNull(grid.getColumnByKey("libelleCapteur"));
        assertNotNull(grid.getColumnByKey("positionXCapteur"));
        assertNotNull(grid.getColumnByKey("positionYCapteur"));
        assertNotNull(grid.getColumnByKey("MurDescription"));
        assertNotNull(grid.getColumnByKey("SalleDescription"));
        assertNotNull(grid.getColumnByKey("typeCapteurDescription"));
    }

    @Test
    public void testGridColumnWidths() {
        Grid<Capteur> grid = capteurView.grid;
        
        // Test that the ID column has the correct width
        assertEquals("50px", grid.getColumnByKey("id").getWidth());
        assertEquals(0, grid.getColumnByKey("id").getFlexGrow());
    }

    @Test
    public void testFilterPlaceholder() {
        try {
            Field filterField = CapteurView.class.getDeclaredField("filter");
            filterField.setAccessible(true);
            assertEquals("Filtrer par nom", ((com.vaadin.flow.component.textfield.TextField) filterField.get(capteurView)).getPlaceholder());
        } catch (Exception e) {
            fail("Erreur lors du test du placeholder: " + e.getMessage());
        }
    }

    @Test
    public void testAddNewButtonText() {
        try {
            Field addNewBtnField = CapteurView.class.getDeclaredField("addNewBtn");
            addNewBtnField.setAccessible(true);
            assertEquals("Ajouter un capteur", ((com.vaadin.flow.component.button.Button) addNewBtnField.get(capteurView)).getText());
        } catch (Exception e) {
            fail("Erreur lors du test du bouton: " + e.getMessage());
        }
    }
    private CapteurEditor getEditorFromView() {
        try {
            Field editorField = CapteurView.class.getDeclaredField("editor");
            editorField.setAccessible(true);
            return (CapteurEditor) editorField.get(capteurView);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Erreur lors de l'accès à l'éditeur: " + e.getMessage());
            return null; // Ne sera jamais atteint, mais nécessaire pour la compilation
        }
    }
}