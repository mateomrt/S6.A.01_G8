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

    @Mock
    private SalleRepo salleRepo;

    @Mock
    private BatimentRepo batimentRepo;

    @Mock
    private TypeSalleRepo typeSalleRepo;

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
        Salle salle1 = new Salle();
        Salle salle2 = new Salle();
        when(salleRepo.findAll()).thenReturn(Arrays.asList(salle1, salle2));

        List<Salle> salles = salleService.getAllSalles();

        assertEquals(2, salles.size());
        verify(salleRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'une salle par son identifiant (cas trouvé).
     */
    @Test
    public void testGetSalleById_Found() {
        Salle salle = new Salle();
        salle.setId(1);
        when(salleRepo.findById(1)).thenReturn(Optional.of(salle));

        Salle result = salleService.getSalleById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    /**
     * Teste la récupération d'une salle par son identifiant (cas non trouvé).
     */
    @Test
    public void testGetSalleById_NotFound() {
        when(salleRepo.findById(1)).thenReturn(Optional.empty());

        Salle result = salleService.getSalleById(1);

        assertNull(result);
    }

    /**
     * Teste la sauvegarde d'une salle.
     */
    @Test
    public void testSaveSalle() {
        // Création des objets nécessaires
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

        // Appel de la méthode à tester
        Salle result = salleService.saveSalle(salle);

        // Vérifications
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
        Salle salle = new Salle();
        salle.setId(1);
        when(salleRepo.save(salle)).thenReturn(salle);

        Salle result = salleService.updateSalle(salle);

        assertEquals(1, result.getId());
    }

    /**
     * Teste la suppression d'une salle par son identifiant.
     */
    @Test
    public void testDeleteSalleById() {
        salleService.deleteSalleById(1);
        verify(salleRepo, times(1)).deleteById(1);
    }
}