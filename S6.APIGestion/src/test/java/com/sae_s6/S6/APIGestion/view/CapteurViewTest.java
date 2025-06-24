package com.sae_s6.S6.APIGestion.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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
import com.vaadin.flow.data.provider.Query;

import java.lang.reflect.Field;

/**
 * Classe de test unitaire pour la vue CapteurView.
 * Vérifie le bon fonctionnement de l'interface utilisateur liée aux capteurs.
 */
@SpringBootTest
public class CapteurViewTest {

    @Autowired
    private CapteurView capteurView;

    // Logger pour afficher des informations pendant les tests
    Logger logger = Logger.getLogger(CapteurViewTest.class.getName());

    /**
     * Initialise l'environnement de test avant chaque test.
     */
    @BeforeEach
    void setUp() {
        UI ui = new UI();
        UI.setCurrent(ui);
    }

    /**
     * Teste si la vue CapteurView est correctement chargée.
     */
    @Test
    void testCapteurViewLoaded() {
        assertNotNull(capteurView, "La vue CapteurView n'a pas été chargée correctement.");
    }

    /**
     * Teste si l'éditeur s'affiche correctement lorsqu'un capteur est sélectionné.
     */
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

    /**
     * Teste l'ajout d'un nouveau capteur via l'éditeur.
     */
    @Test
    void editorAfficheQuandNewCapteurClicked() {
        CapteurEditor editor = capteurView.editor;

        // Vérifie l'état initial
        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        // Simule un clic sur le bouton "Ajouter un capteur"
        capteurView.getAddNewBtn().click();

        // Vérifie que l'éditeur est visible et que les champs sont vides
        assertTrue(editor.isVisible(), "Editor should be visible after clicking the button");
        assertEquals("", editor.libelleCapteur.getValue());
        assertEquals("", editor.positionXCapteur.getValue());
        assertEquals("", editor.positionYCapteur.getValue());
        assertNull(editor.murComboBox.getValue());
        assertNull(editor.salleComboBox.getValue());
        assertNull(editor.typeCapteurComboBox.getValue());

        // Sauvegarde le nombre de capteurs avant ajout
        Collection<Capteur> capteursAvant = ((ListDataProvider<Capteur>) capteurView.grid.getDataProvider()).getItems();
        int nombreCapteursAvant = capteursAvant.size();

        // Remplit le formulaire
        editor.libelleCapteur.setValue("Nouveau Capteur Test");
        editor.positionXCapteur.setValue("10");
        editor.positionYCapteur.setValue("20");

        // Simule un clic sur le bouton "Save"
        editor.save.click();

        // Vérifie que le capteur a été ajouté
        Collection<Capteur> capteursApres = ((ListDataProvider<Capteur>) capteurView.grid.getDataProvider()).getItems();
        int nombreCapteursApres = capteursApres.size();
        assertEquals(nombreCapteursAvant + 1, nombreCapteursApres, "Le capteur n'a pas été ajouté.");
    }

    /**
     * Teste la fonctionnalité de filtre.
     */
    @Test
    public void testFilterFunctionality() {
        try {
            Field filterField = CapteurView.class.getDeclaredField("filter");
            filterField.setAccessible(true);
            ((com.vaadin.flow.component.textfield.TextField) filterField.get(capteurView)).setValue("test");

            assertEquals("test", ((com.vaadin.flow.component.textfield.TextField) filterField.get(capteurView)).getValue());
        } catch (Exception e) {
            fail("Erreur lors du test du filtre: " + e.getMessage());
        }
    }

    /**
     * Teste les colonnes de la grille.
     */
    @Test
    public void testGridColumns() {
        Grid<Capteur> grid = capteurView.grid;

        assertNotNull(grid.getColumnByKey("id"));
        assertNotNull(grid.getColumnByKey("libelleCapteur"));
        assertNotNull(grid.getColumnByKey("positionXCapteur"));
        assertNotNull(grid.getColumnByKey("positionYCapteur"));
    }

    /**
     * Méthode utilitaire pour récupérer le premier élément de la grille.
     * @param grid Grille contenant les capteurs
     * @return Premier capteur de la grille
     */
    private Capteur getFirstItem(Grid<Capteur> grid) {
        return ((ListDataProvider<Capteur>) grid.getDataProvider()).getItems().iterator().next();
    }
}