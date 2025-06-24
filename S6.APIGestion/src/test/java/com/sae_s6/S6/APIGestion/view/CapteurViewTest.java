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
import java.lang.reflect.Type;

/**
 * Classe de test unitaire pour la vue CapteurView.
 * Vérifie le bon fonctionnement de l'interface utilisateur liée aux capteurs.
 */
@SpringBootTest
public class CapteurViewTest {

    /**
     * Initialise l'environnement de test avant chaque test.
     */
    @BeforeEach
    void setUp() {
        UI ui = new UI();
        UI.setCurrent(ui);
    }

    // Injection de la vue CapteurView
    @Autowired
    private CapteurView capteurView;

    // Logger pour afficher des informations pendant les tests
    Logger logger = Logger.getLogger(CapteurViewTest.class.getName());

    /**
     * Teste si la vue CapteurView est correctement chargée.
     */
    @Test
    void testCapteurView() {
        assert capteurView != null;
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

        // Simule un clic sur le bouton "Ajouter un nouveau capteur"
        capteurView.getAddNewBtn().click();

        // Vérifie que l'éditeur devient visible
        assertTrue(editor.isVisible(), "Editor should be visible after clicking Add New Button");

        // Vérifie que les champs sont vides ou initialisés
        assertEquals("", editor.libelleCapteur.getValue());
        assertEquals("", editor.positionXCapteur.getValue());
        assertEquals("", editor.positionYCapteur.getValue());
        assertNull(editor.murComboBox.getValue());
        assertNull(editor.salleComboBox.getValue());
        assertNull(editor.typeCapteurComboBox.getValue());

        // Sauvegarde le nombre de capteurs avant ajout
        Collection<Capteur> capteursAvant = ((ListDataProvider<Capteur>) capteurView.grid.getDataProvider()).getItems();
        int nombreCapteursAvant = capteursAvant.size();

        // Récupère les valeurs pour les ComboBox
        List<Mur> murs = new ArrayList<>(editor.murComboBox.getDataProvider().fetch(new Query<>()).toList());
        List<TypeCapteur> typeCapteurs = new ArrayList<>(editor.typeCapteurComboBox.getDataProvider().fetch(new Query<>()).toList());
        List<Salle> salles = new ArrayList<>(editor.salleComboBox.getDataProvider().fetch(new Query<>()).toList());

        assertFalse(murs.isEmpty(), "Aucun mur disponible pour le test");
        assertFalse(typeCapteurs.isEmpty(), "Aucun type de capteur disponible pour le test");
        assertFalse(salles.isEmpty(), "Aucune salle disponible pour le test");

        // Sélectionne les premiers éléments valides
        Mur murTest = murs.get(0);
        TypeCapteur typeCapteurTest = typeCapteurs.get(0);
        Salle salleTest = salles.get(0);

        // Remplit le formulaire
        editor.libelleCapteur.setValue("Nouveau Capteur Test");
        editor.murComboBox.setValue(murTest);
        editor.positionXCapteur.setValue(String.valueOf(10));
        editor.positionYCapteur.setValue(String.valueOf(20));
        editor.salleComboBox.setValue(salleTest);
        editor.typeCapteurComboBox.setValue(typeCapteurTest);

        // Clic sur le bouton save
        editor.save.click();

        // Attendre que le grid soit mis à jour
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Vérifie que le capteur a bien été ajouté
        Collection<Capteur> capteursApres = ((ListDataProvider<Capteur>) capteurView.grid.getDataProvider()).getItems();
        int nombreCapteursApres = capteursApres.size();

        assertEquals(nombreCapteursAvant + 1, nombreCapteursApres, "Le capteur n'a pas été ajouté");

        Capteur lastCapteur = getLastItem(capteurView.grid);
        assertEquals("Nouveau Capteur Test", lastCapteur.getLibelleCapteur());
        assertEquals(10.0, lastCapteur.getPositionXCapteur());
        assertEquals(20.0, lastCapteur.getPositionYCapteur());
        assertEquals(murTest, lastCapteur.getMurNavigation());
        assertEquals(salleTest, lastCapteur.getSalleNavigation());
        assertEquals(typeCapteurTest, lastCapteur.getTypeCapteurNavigation());
    }

    /**
     * Méthode utilitaire pour récupérer le dernier élément de la grille.
     * @param grid Grille contenant les capteurs
     * @return Dernier capteur de la grille
     */
    private Capteur getLastItem(Grid<Capteur> grid) {
        Collection<Capteur> capteurs = ((ListDataProvider<Capteur>) grid.getDataProvider()).getItems();
        return new ArrayList<>(capteurs).get(capteurs.size() - 1);
    }

    /**
     * Méthode utilitaire pour récupérer le premier élément de la grille.
     * @param grid Grille contenant les capteurs
     * @return Premier capteur de la grille
     */
    private Capteur getFirstItem(Grid<Capteur> grid) {
        return ((ListDataProvider<Capteur>) grid.getDataProvider()).getItems().iterator().next();
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

            assertNotNull(((com.vaadin.flow.component.textfield.TextField) filterField.get(capteurView)).getValue());
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
        assertNotNull(grid.getColumnByKey("MurDescription"));
        assertNotNull(grid.getColumnByKey("SalleDescription"));
        assertNotNull(grid.getColumnByKey("typeCapteurDescription"));
    }

    /**
     * Teste la largeur des colonnes de la grille.
     */
    @Test
    public void testGridColumnWidths() {
        Grid<Capteur> grid = capteurView.grid;

        assertEquals("50px", grid.getColumnByKey("id").getWidth());
        assertEquals(0, grid.getColumnByKey("id").getFlexGrow());
    }

    /**
     * Teste le placeholder du filtre.
     */
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

    /**
     * Teste le texte du bouton "Ajouter un capteur".
     */
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

    /**
     * Récupère l'éditeur depuis la vue.
     * @return Instance de CapteurEditor
     */
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