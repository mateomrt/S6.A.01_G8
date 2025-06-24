package com.sae_s6.S6.APIGestion.service;

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.repository.DonneeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour le service DonneeService.
 * Utilise Mockito pour simuler le repository DonneeRepo.
 */
public class DonneeServiceTest {

    // Mock du repository DonneeRepo
    @Mock
    private DonneeRepo donneeRepo;

    // Injection du service avec le mock
    @InjectMocks
    private DonneeService donneeService;

    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la récupération de toutes les données (cas succès).
     */
    @Test
    public void testGetAllDonnees() {
        // Arrange : préparation des données fictives
        Donnee donnee1 = new Donnee();
        Donnee donnee2 = new Donnee();
        when(donneeRepo.findAll()).thenReturn(Arrays.asList(donnee1, donnee2));

        // Act : appel du service
        List<Donnee> result = donneeService.getAllDonnees();

        // Assert : vérification des résultats
        assertEquals(2, result.size());
        verify(donneeRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'une donnée par son identifiant (cas trouvé).
     */
    @Test
    public void testGetDonneeById_Found() {
        // Arrange : préparation des données fictives
        Donnee donnee = new Donnee();
        donnee.setId(1);
        when(donneeRepo.findById(1)).thenReturn(Optional.of(donnee));

        // Act : appel du service
        Donnee result = donneeService.getDonneeById(1);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    /**
     * Teste la récupération d'une donnée par son identifiant (cas non trouvé).
     */
    @Test
    public void testGetDonneeById_NotFound() {
        // Arrange : simulation d'un résultat vide
        when(donneeRepo.findById(1)).thenReturn(Optional.empty());

        // Act : appel du service
        Donnee result = donneeService.getDonneeById(1);

        // Assert : vérification des résultats
        assertNull(result);
    }

    /**
     * Teste la sauvegarde d'une donnée.
     */
    @Test
    public void testSaveDonnee() {
        // Arrange : préparation des données fictives
        Donnee donnee = new Donnee();
        donnee.setId(1);
        when(donneeRepo.save(donnee)).thenReturn(donnee);

        // Act : appel du service
        Donnee result = donneeService.saveDonnee(donnee);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(donneeRepo, times(1)).save(donnee);
    }

    /**
     * Teste la mise à jour d'une donnée.
     */
    @Test
    public void testUpdateDonnee() {
        // Arrange : préparation des données fictives
        Donnee donnee = new Donnee();
        donnee.setId(1);
        when(donneeRepo.save(donnee)).thenReturn(donnee);

        // Act : appel du service
        Donnee result = donneeService.updateDonnee(donnee);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(donneeRepo, times(1)).save(donnee);
    }

    /**
     * Teste la suppression d'une donnée par son identifiant.
     */
    @Test
    public void testDeleteDonneeById() {
        // Arrange : préparation de l'identifiant
        Integer id = 1;

        // Act : appel du service
        donneeService.deleteDonneeById(id);

        // Assert : vérification des résultats
        verify(donneeRepo, times(1)).deleteById(id);
    }
}