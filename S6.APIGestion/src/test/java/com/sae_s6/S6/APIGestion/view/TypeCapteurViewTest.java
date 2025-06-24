package com.sae_s6.S6.APIGestion.view;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.views.TypeCapteurEditor;
import com.sae_s6.S6.APIGestion.views.TypeCapteurView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

/**
 * Classe de test unitaire pour la vue TypeCapteurView.
 * Vérifie le bon fonctionnement de l'interface utilisateur liée aux types de capteurs.
 */
@SpringBootTest
public class TypeCapteurViewTest {

    // Injection de la vue TypeCapteurView
    @Autowired
    private TypeCapteurView typeCapteurView;

    // Logger pour afficher des informations pendant les tests
    Logger logger = Logger.getLogger(TypeCapteurViewTest.class.getName());

    /**
     * Teste si la vue TypeCapteurView est correctement chargée.
     */
    @Test
    void testTypeCapteurViewLoaded() {
        assertNotNull(typeCapteurView, "TypeCapteurView should be loaded by Spring");
    }

    /**
     * Teste si l'éditeur s'affiche correctement lorsqu'un type de capteur est sélectionné.
     */
    @Test
    public void editorShownWhenTypeCapteurSelected() {
        Grid<TypeCapteur> grid = typeCapteurView.grid;
        TypeCapteur firstTypeCapteur = getFirstItem(grid);
        assertNotNull(firstTypeCapteur, "There should be at least one TypeCapteur in the grid");

        TypeCapteurEditor editor = typeCapteurView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        grid.asSingleSelect().setValue(firstTypeCapteur);

        assertTrue(editor.isVisible(), "Editor should be visible when a TypeCapteur is selected");

        logger.info("TypeCapteur in editor: " + editor.libelleTypeCapteur.getValue());
        assertEquals(firstTypeCapteur.getLibelleTypeCapteur(), editor.libelleTypeCapteur.getValue());
        assertEquals(firstTypeCapteur.getModeTypeCapteur(), editor.modeTypeCapteur.getValue());
    }

    /**
     * Teste l'ajout d'un nouveau type de capteur via l'éditeur.
     */
    @Test
    public void editorShownWhenAddNewClicked() {
        TypeCapteurEditor editor = typeCapteurView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        Button addNewBtn = typeCapteurView.addNewBtn;
        addNewBtn.click();

        assertTrue(editor.isVisible(), "Editor should be visible when Add New Button is clicked");

        // Vérifie que les champs sont vides ou initialisés
        assertEquals("", editor.libelleTypeCapteur.getValue(), "New TypeCapteur should have empty libelle");
        assertEquals("", editor.modeTypeCapteur.getValue(), "New TypeCapteur should have empty mode");

        // Simule la saisie de données dans l'éditeur
        editor.libelleTypeCapteur.setValue("Capteur Test");
        editor.modeTypeCapteur.setValue("Mode Test");

        editor.save.click();

        TypeCapteur lastTypeCapteur = getLastItem(typeCapteurView.grid);
        logger.info("Dernier TypeCapteur dans la grille : " + lastTypeCapteur);

        assertEquals("Capteur Test", lastTypeCapteur.getLibelleTypeCapteur(), "Le libellé du type de capteur ajouté devrait correspondre");
        assertEquals("Mode Test", lastTypeCapteur.getModeTypeCapteur(), "Le mode du type de capteur ajouté devrait correspondre");
    }

    /**
     * Méthode utilitaire pour récupérer le premier élément de la grille.
     * @param grid Grille contenant les types de capteurs
     * @return Premier type de capteur de la grille
     */
    private TypeCapteur getFirstItem(Grid<TypeCapteur> grid) {
        return ((ListDataProvider<TypeCapteur>) grid.getDataProvider()).getItems().iterator().next();
    }

    /**
     * Méthode utilitaire pour récupérer le dernier élément de la grille.
     * @param grid Grille contenant les types de capteurs
     * @return Dernier type de capteur de la grille
     */
    private TypeCapteur getLastItem(Grid<TypeCapteur> grid) {
        Collection<TypeCapteur> items = ((ListDataProvider<TypeCapteur>) grid.getDataProvider()).getItems();
        List<TypeCapteur> list = new ArrayList<>(items);
        return list.get(list.size() - 1);
    }
}