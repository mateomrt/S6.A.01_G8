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

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.views.TypeCapteurDonneeEditor;
import com.sae_s6.S6.APIGestion.views.TypeCapteurDonneeView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

@SpringBootTest
public class TypeCapteurDonneeViewTest {
    @BeforeEach
    void setUp() {
    UI ui = new UI();
    UI.setCurrent(ui);
}

    @Autowired
    private TypeCapteurDonneeView typeCapteurDonneeView;

    Logger logger = Logger.getLogger(TypeCapteurDonneeViewTest.class.getName());

    @Test
    void testTypeCapteurDonneeViewLoaded() {
       
        assert typeCapteurDonneeView != null;
    }

    @Test
    void editorAfficheQuandTypeCapteurDonneeSelectionne() {
        Grid<TypeCapteurDonnee> grid = typeCapteurDonneeView.grid;
        TypeCapteurDonnee firstTypeCapteurDonnee = getFirstItem(grid);
        logger.info("First TypeCapteurDonnee: " + firstTypeCapteurDonnee);

        TypeCapteurDonneeEditor editor = typeCapteurDonneeView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");
        grid.asSingleSelect().setValue(firstTypeCapteurDonnee);
        assertTrue(editor.isVisible(), "Editor should be visible when a TypeCapteurDonnee is selected");

        logger.info("TypeCapteurDonnee in editor: " + editor.donneeComboBox.getValue() + editor.typeCapteurComboBox.getValue());
        assertEquals(firstTypeCapteurDonnee.getDonneeNavigation().getLibelleDonnee(), editor.donneeComboBox.getValue().getLibelleDonnee());
        assertEquals(firstTypeCapteurDonnee.getTypeCapteurNavigation().getLibelleTypeCapteur(), editor.typeCapteurComboBox.getValue().getLibelleTypeCapteur());
    }

    @Test
    void editorAfficheQuandNewTypeCapteurDonneeClicked() {
        TypeCapteurDonneeEditor editor = typeCapteurDonneeView.editor;

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        typeCapteurDonneeView.getAddNewBtn().click();

        assertTrue(editor.isVisible(), "Editor should be visible after clicking add new button");

        assertNull(editor.donneeComboBox.getValue(), "New TypeCapteurDonnee should have no Donnee selected");

        // Récupérer la liste des Donnee disponibles dans le ComboBox
        List<Donnee> donnees = new ArrayList<>(editor.donneeComboBox
            .getDataProvider()
            .fetch(new com.vaadin.flow.data.provider.Query<>())
            .toList());

        assertFalse(donnees.isEmpty(), "Aucune Donnee disponible dans le ComboBox");

        Donnee donneeTest = donnees.get(1); 

        // Simuler la saisie
        editor.donneeComboBox.setValue(donneeTest);
        editor.save.click();

        // Vérifier que le nouveau TypeCapteurDonnee est bien dans la grille
        TypeCapteurDonnee lastTypeCapteurDonnee = getLastItem(typeCapteurDonneeView.grid);
        logger.info("New TypeCapteurDonnee should be last in grid: " + lastTypeCapteurDonnee);
        assertEquals(donneeTest.getLibelleDonnee(), lastTypeCapteurDonnee.getDonneeNavigation().getLibelleDonnee());
    }

    private TypeCapteurDonnee getFirstItem(Grid<TypeCapteurDonnee> grid) {
        return ((ListDataProvider<TypeCapteurDonnee>) grid.getDataProvider()).getItems().iterator().next();
    }

    private TypeCapteurDonnee getLastItem(Grid<TypeCapteurDonnee> grid) {
        Collection<TypeCapteurDonnee> typeCapteurDonnees = ((ListDataProvider<TypeCapteurDonnee>) grid.getDataProvider()).getItems();
        List<TypeCapteurDonnee> typeCapteurDonneeList = new ArrayList<>(typeCapteurDonnees);
        return typeCapteurDonneeList.get(typeCapteurDonneeList.size() - 1);
    }

}
