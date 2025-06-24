package com.sae_s6.S6.APIGestion.service;

import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.repository.TypeEquipementRepo;
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
 * Classe de test unitaire pour le service TypeEquipementService.
 * Utilise Mockito pour simuler le repository TypeEquipementRepo.
 */
public class TypeEquipementServiceTest {

    // Mock du repository TypeEquipementRepo
    @Mock
    private TypeEquipementRepo typeEquipementRepo;

    // Injection du service avec le mock
    @InjectMocks
    private TypeEquipementService typeEquipementService;

    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la récupération de tous les types d'équipement.
     */
    @Test
    public void testGetAllTypeEquipements() {
        // Arrange : préparation des données fictives
        TypeEquipement eq1 = new TypeEquipement();
        TypeEquipement eq2 = new TypeEquipement();
        when(typeEquipementRepo.findAll()).thenReturn(Arrays.asList(eq1, eq2));

        // Act : appel du service
        List<TypeEquipement> result = typeEquipementService.getAllTypeEquipements();

        // Assert : vérification des résultats
        assertEquals(2, result.size());
        verify(typeEquipementRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'un type d'équipement par son identifiant (cas trouvé).
     */
    @Test
    public void testGetTypeEquipementById_Found() {
        // Arrange : préparation des données fictives
        TypeEquipement eq = new TypeEquipement();
        eq.setId(1);
        when(typeEquipementRepo.findById(1)).thenReturn(Optional.of(eq));

        // Act : appel du service
        TypeEquipement result = typeEquipementService.getTypeEquipementById(1);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    /**
     * Teste la récupération d'un type d'équipement par son identifiant (cas non trouvé).
     */
    @Test
    public void testGetTypeEquipementById_NotFound() {
        // Arrange : simulation d'un résultat vide
        when(typeEquipementRepo.findById(1)).thenReturn(Optional.empty());

        // Act : appel du service
        TypeEquipement result = typeEquipementService.getTypeEquipementById(1);

        // Assert : vérification des résultats
        assertNull(result);
    }

    /**
     * Teste la sauvegarde d'un type d'équipement.
     */
    @Test
    public void testSaveTypeEquipement() {
        // Arrange : préparation des données fictives
        TypeEquipement eq = new TypeEquipement();
        eq.setId(1);
        when(typeEquipementRepo.save(eq)).thenReturn(eq);

        // Act : appel du service
        TypeEquipement result = typeEquipementService.saveTypeEquipement(eq);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(typeEquipementRepo, times(1)).save(eq);
    }

    /**
     * Teste la mise à jour d'un type d'équipement.
     */
    @Test
    public void testUpdateTypeEquipement() {
        // Arrange : préparation des données fictives
        TypeEquipement eq = new TypeEquipement();
        eq.setId(1);
        when(typeEquipementRepo.save(eq)).thenReturn(eq);

        // Act : appel du service
        TypeEquipement result = typeEquipementService.updateTypeEquipement(eq);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(typeEquipementRepo, times(1)).save(eq);
    }

    /**
     * Teste la suppression d'un type d'équipement par son identifiant.
     */
    @Test
    public void testDeleteTypeEquipementById() {
        // Arrange : préparation de l'identifiant
        Integer id = 1;

        // Act : appel du service
        typeEquipementService.deleteTypeEquipementById(id);

        // Assert : vérification des résultats
        verify(typeEquipementRepo, times(1)).deleteById(id);
    }
}