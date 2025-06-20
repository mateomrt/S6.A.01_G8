package com.sae_s6.S6.APIGestion.service;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Mur.Orientation;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.repository.BatimentRepo;
import com.sae_s6.S6.APIGestion.repository.MurRepo;
import com.sae_s6.S6.APIGestion.repository.SalleRepo;
import com.sae_s6.S6.APIGestion.repository.TypeSalleRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour le service MurService.
 * Utilise Mockito pour simuler les repositories.
 */
public class MurServiceTest {

    @Mock
    private MurRepo murRepo;

    @Mock
    private SalleRepo salleRepo;

    @Mock
    private BatimentRepo batimentRepo;

    @Mock
    private TypeSalleRepo typeSalleRepo;

    @InjectMocks
    private MurService murService;

    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la récupération de tous les murs.
     */
    @Test
    public void testGetAllMurs() {
        Mur mur1 = new Mur();
        Mur mur2 = new Mur();
        when(murRepo.findAll()).thenReturn(Arrays.asList(mur1, mur2));

        List<Mur> result = murService.getAllMurs();

        assertEquals(2, result.size());
        verify(murRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'un mur par son identifiant (cas trouvé).
     */
    @Test
    public void testGetMurById_Found() {
        Mur mur = new Mur();
        mur.setId(1);
        when(murRepo.findById(1)).thenReturn(Optional.of(mur));

        Mur result = murService.getMurById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    /**
     * Teste la récupération d'un mur par son identifiant (cas non trouvé).
     */
    @Test
    public void testGetMurById_NotFound() {
        when(murRepo.findById(1)).thenReturn(Optional.empty());

        Mur result = murService.getMurById(1);

        assertNull(result);
    }

    /**
     * Teste la sauvegarde d'un mur.
     */
    @Test
    public void testSaveMur() {
        // Création des objets nécessaires
        Batiment batiment = new Batiment();
        batiment.setId(10);
    
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(20);
    
        Salle salle = new Salle();
        salle.setId(5);
        salle.setBatimentNavigation(batiment);
        salle.setTypeSalleNavigation(typeSalle);
    
        Mur mur = new Mur();
        mur.setSalleNavigation(salle);
    
        Mur savedMur = new Mur();
        savedMur.setId(100); // ID généré automatiquement
    
        // Simulation des dépendances
        when(salleRepo.findById(5)).thenReturn(Optional.of(salle));
        when(murRepo.save(any(Mur.class))).thenReturn(savedMur);
    
        // Appel de la méthode à tester
        Mur result = murService.saveMur(mur);
    
        // Vérifications
        assertNotNull(result);
        assertEquals(100, result.getId()); // Vérifie que l'ID généré est correct
        verify(salleRepo).findById(5);
        verify(murRepo).save(mur);
    }

    /**
     * Teste la mise à jour d'un mur.
     */
    @Test
    public void testUpdateMur() {
        Mur existingMur = new Mur();
        existingMur.setId(1);
        existingMur.setLibelleMur("Ancien titre");

        Mur updatedMur = new Mur();
        updatedMur.setId(1);
        updatedMur.setLibelleMur("Nouveau titre");

        when(murRepo.save(updatedMur)).thenReturn(updatedMur);

        Mur result = murService.updateMur(updatedMur);

        assertNotNull(result);
        assertEquals("Nouveau titre", result.getLibelleMur());
        verify(murRepo, times(1)).save(updatedMur);
    }

    /**
     * Teste la suppression d'un mur par son identifiant.
     */
    @Test
    public void testDeleteMurById() {
        Integer id = 1;
        murService.deleteMurById(id);
        verify(murRepo, times(1)).deleteById(id);
    }
}