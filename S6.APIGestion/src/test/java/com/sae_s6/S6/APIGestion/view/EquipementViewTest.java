package com.sae_s6.S6.APIGestion.view;

import com.sae_s6.S6.APIGestion.entity.Equipement;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.views.EquipementEditor;
import com.sae_s6.S6.APIGestion.views.EquipementView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour la vue EquipementView.
 * Vérifie le bon fonctionnement de l'interface utilisateur liée aux équipements.
 */
@SpringBootTest
public class EquipementViewTest {

    // Injection de la vue EquipementView
    @Autowired
    private EquipementView equipementView;

    // Logger pour afficher des informations pendant les tests
    Logger logger = Logger.getLogger(EquipementViewTest.class.getName());

    /**
     * Teste si la vue EquipementView est correctement chargée.
     */
    @Test
    void testEquipementViewLoaded() {
        assertNotNull(equipementView, "EquipementView should be loaded by Spring");
    }

    /**
     * Teste si l'éditeur s'affiche correctement lorsqu'un équipement est sélectionné.
     */
    @Test
    void editorVisibleWhenEquipementSelected() {
        Grid<Equipement> grid = equipementView.grid;
        Equipement firstEquipement = getFirstItem(grid);
        assertNotNull(firstEquipement, "There should be at least one Equipement in the grid");

        EquipementEditor editor = (EquipementEditor) grid.getParent().get().getChildren()
                .filter(component -> component instanceof EquipementEditor)
                .findFirst()
                .orElse(null);

        assertNotNull(editor, "EquipementEditor should be present");
        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        grid.asSingleSelect().setValue(firstEquipement);

        assertTrue(editor.isVisible(), "Editor should be visible when an Equipement is selected");

        logger.info("Equipement in editor: " + editor.libelleEquipement.getValue());
        assertEquals(firstEquipement.getLibelleEquipement(), editor.libelleEquipement.getValue());
    }

    /**
     * Teste l'ajout d'un nouvel équipement via l'éditeur.
     */
    @Test
    void editorVisibleWhenAddNewButtonClicked() {
        EquipementEditor editor = (EquipementEditor) equipementView.getChildren()
                .filter(component -> component instanceof EquipementEditor)
                .findFirst()
                .orElse(null);

        assertNotNull(editor, "EquipementEditor should be present");
        assertFalse(editor.isVisible(), "Editor should not be visible initially");

        equipementView.getAddNewBtn().click();

        assertTrue(editor.isVisible(), "Editor should be visible when Add New Button is clicked");

        Collection<Equipement> items = ((ListDataProvider<Equipement>) equipementView.grid.getDataProvider()).getItems();
        int nbEquipments = items.size();

        List<Mur> murs = new ArrayList<>(editor.MurComboBox.getDataProvider().fetch(new Query<>()).toList());
        List<TypeEquipement> typeEquipments = new ArrayList<>(editor.TypeEquipementComboBox.getDataProvider().fetch(new Query<>()).toList());
        List<Salle> salles = new ArrayList<>(editor.SalleComboBox.getDataProvider().fetch(new Query<>()).toList());

        assertFalse(murs.isEmpty(), "Aucun mur disponible pour le test");
        assertFalse(typeEquipments.isEmpty(), "Aucun type d'équipement disponible pour le test");
        assertFalse(salles.isEmpty(), "Aucune salle disponible pour le test");

        Mur murTest = murs.get(0);
        TypeEquipement typeEquipementTest = typeEquipments.get(0);
        Salle salleTest = salles.get(0);

        editor.libelleEquipement.setValue("Test Equipement");
        editor.position_x.setValue("10");
        editor.position_y.setValue("20");
        editor.hauteur.setValue("5");
        editor.largeur.setValue("3");
        editor.MurComboBox.setValue(murTest);
        editor.SalleComboBox.setValue(salleTest);
        editor.TypeEquipementComboBox.setValue(typeEquipementTest);

        editor.save.click();

        Collection<Equipement> updatedItems = ((ListDataProvider<Equipement>) equipementView.grid.getDataProvider()).getItems();
        assertEquals(nbEquipments + 1, updatedItems.size(), "Le nombre d'équipements devrait augmenter de 1 après l'ajout");

        Equipement lastEquipement = getLastItem(equipementView.grid);
        assertEquals("Test Equipement", lastEquipement.getLibelleEquipement(), "Le libellé de l'équipement ajouté devrait correspondre");
        assertEquals(10.0, lastEquipement.getPosition_x(), "La position X de l'équipement ajouté devrait être 10.0");
        assertEquals(20.0, lastEquipement.getPosition_y(), "La position Y de l'équipement ajouté devrait être 20.0");
        assertEquals(5.0, lastEquipement.getHauteur(), "La hauteur de l'équipement ajouté devrait être 5.0");
        assertEquals(3.0, lastEquipement.getLargeur(), "La largeur de l'équipement ajouté devrait être 3.0");
        assertEquals(murTest, lastEquipement.getMurNavigation(), "Le mur de l'équipement ajouté devrait correspondre");
        assertEquals(salleTest, lastEquipement.getSalleNavigation(), "La salle de l'équipement ajouté devrait correspondre");
        assertEquals(typeEquipementTest, lastEquipement.getTypeEquipementNavigation(), "Le type d'équipement de l'équipement ajouté devrait correspondre");
    }

    /**
     * Méthode utilitaire pour récupérer le premier élément de la grille.
     * @param grid Grille contenant les équipements
     * @return Premier équipement de la grille
     */
    private Equipement getFirstItem(Grid<Equipement> grid) {
        return ((ListDataProvider<Equipement>) grid.getDataProvider()).getItems().iterator().next();
    }

    /**
     * Méthode utilitaire pour récupérer le dernier élément de la grille.
     * @param grid Grille contenant les équipements
     * @return Dernier équipement de la grille
     */
    private Equipement getLastItem(Grid<Equipement> grid) {
        Collection<Equipement> equipements = ((ListDataProvider<Equipement>) grid.getDataProvider()).getItems();
        List<Equipement> equipementList = new ArrayList<>(equipements);
        return equipementList.get(equipementList.size() - 1);
    }
}