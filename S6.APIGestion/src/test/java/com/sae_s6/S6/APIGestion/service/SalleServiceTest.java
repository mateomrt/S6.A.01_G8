package com.sae_s6.S6.APIGestion.service;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.repository.BatimentRepo;
import com.sae_s6.S6.APIGestion.repository.SalleRepo;
import com.sae_s6.S6.APIGestion.repository.TypeSalleRepo;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

/**
 * Classe de test unitaire pour le service SalleService.
 * Utilise Mockito pour simuler les repositories.
 */
public class SalleServiceTest {

    // Mocks des repositories nécessaires
    @Mock
    private SalleRepo salleRepo;

    @Mock
    private BatimentRepo batimentRepo;

    @Mock
    private TypeSalleRepo typeSalleRepo;

    // Injection du service avec les mocks
    @InjectMocks
    private SalleService salleService;

    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la récupération de toutes les salles.
     */
    @Test
    public void testGetAllSalles() {
        // Arrange : préparation des données fictives
        Salle salle1 = new Salle();
        Salle salle2 = new Salle();
        when(salleRepo.findAll()).thenReturn(Arrays.asList(salle1, salle2));

        // Act : appel du service
        List<Salle> salles = salleService.getAllSalles();

        // Assert : vérification des résultats
        assertEquals(2, salles.size());
        verify(salleRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'une salle par son identifiant (cas trouvé).
     */
    @Test
    public void testGetSalleById_Found() {
        // Arrange : préparation des données fictives
        Salle salle = new Salle();
        salle.setId(1);
        when(salleRepo.findById(1)).thenReturn(Optional.of(salle));

        // Act : appel du service
        Salle result = salleService.getSalleById(1);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    /**
     * Teste la récupération d'une salle par son identifiant (cas non trouvé).
     */
    @Test
    public void testGetSalleById_NotFound() {
        // Arrange : simulation d'un résultat vide
        when(salleRepo.findById(1)).thenReturn(Optional.empty());

        // Act : appel du service
        Salle result = salleService.getSalleById(1);

        // Assert : vérification des résultats
        assertNull(result);
    }

    /**
     * Teste la sauvegarde d'une salle.
     */
    @Test
    public void testSaveSalle() {
        // Arrange : création des objets nécessaires
        Salle salle = new Salle();
        salle.setId(1);

        Batiment batiment = new Batiment();
        batiment.setId(10);

        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(20);

        salle.setBatimentNavigation(batiment);
        salle.setTypeSalleNavigation(typeSalle);

        Salle savedSalle = new Salle();
        savedSalle.setId(100); // ID généré automatiquement

        // Simulation des dépendances
        when(batimentRepo.findById(10)).thenReturn(Optional.of(batiment));
        when(typeSalleRepo.findById(20)).thenReturn(Optional.of(typeSalle));
        when(salleRepo.save(any(Salle.class))).thenReturn(savedSalle);

        // Act : appel du service
        Salle result = salleService.saveSalle(salle);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(100, result.getId()); // Vérifie que l'ID généré est correct
        verify(batimentRepo).findById(10);
        verify(typeSalleRepo).findById(20);
        verify(salleRepo).save(salle);
    }

    /**
     * Teste la mise à jour d'une salle.
     */
    @Test
    public void testUpdateSalle() {
        // Arrange : préparation des données fictives
        Salle salle = new Salle();
        salle.setId(1);
        when(salleRepo.save(salle)).thenReturn(salle);

        // Act : appel du service
        Salle result = salleService.updateSalle(salle);

        // Assert : vérification des résultats
        assertEquals(1, result.getId());
    }

    /**
     * Teste la suppression d'une salle par son identifiant.
     */
    @Test
    public void testDeleteSalleById() {
        // Act : appel du service
        salleService.deleteSalleById(1);

        // Assert : vérification des résultats
        verify(salleRepo, times(1)).deleteById(1);
    }
}