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

@SpringBootTest
public class TypeCapteurViewTest {

    @Autowired
    private TypeCapteurView typeCapteurView;

    Logger logger = Logger.getLogger(TypeCapteurViewTest.class.getName());

    @Test
    void testTypeCapteurViewLoaded() {
        assert typeCapteurView != null;
    }

    @Test
    public void editorShownWhenTypeCapteurSelected() {
        Grid<TypeCapteur> grid = typeCapteurView.grid;
        TypeCapteur firstTypeCapteur = getFirstItem(grid);
        logger.info("First TypeCapteur: " + firstTypeCapteur);

        TypeCapteurEditor editor = (TypeCapteurEditor) typeCapteurView.getComponentAt(2); 

        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        grid.asSingleSelect().setValue(firstTypeCapteur);
        assertTrue(editor.isVisible(), "Editor should be visible when a TypeCapteur is selected");

       
        assertEquals(firstTypeCapteur.getLibelleTypeCapteur(), editor.libelleTypeCapteur.getValue());
        assertEquals(firstTypeCapteur.getModeTypeCapteur(), editor.modeTypeCapteur.getValue());
    }

    @Test
    public void editorShownWhenAddNewClicked() {
        TypeCapteurEditor editor = (TypeCapteurEditor) typeCapteurView.getComponentAt(2);
        assertFalse(editor.isVisible());

        Button addNewBtn = typeCapteurView.addNewBtn;
        addNewBtn.click(); 

        assertTrue(editor.isVisible());

        // Vérifier que les champs sont vides ou initialisés
        assertEquals("", editor.libelleTypeCapteur.getValue());
        assertEquals("", editor.modeTypeCapteur.getValue());

        // Simuler la saisie de données dans l'éditeur
        editor.libelleTypeCapteur.setValue("Capteur Test");
        editor.modeTypeCapteur.setValue("Mode Test");

        editor.save.click(); 

        TypeCapteur lastTypeCapteur = getLastItem(typeCapteurView.grid);
        logger.info("Dernier TypeCapteur dans la grille : " + lastTypeCapteur);

        assertEquals("Capteur Test", lastTypeCapteur.getLibelleTypeCapteur());
        assertEquals("Mode Test", lastTypeCapteur.getModeTypeCapteur());
    }

    
    private TypeCapteur getFirstItem(Grid<TypeCapteur> grid) {
        return ((ListDataProvider<TypeCapteur>) grid.getDataProvider()).getItems().iterator().next();
    }

    private TypeCapteur getLastItem(Grid<TypeCapteur> grid) {
        Collection<TypeCapteur> items = ((ListDataProvider<TypeCapteur>) grid.getDataProvider()).getItems();
        List<TypeCapteur> list = new ArrayList<>(items);
        return list.get(list.size() - 1);
    }
}
