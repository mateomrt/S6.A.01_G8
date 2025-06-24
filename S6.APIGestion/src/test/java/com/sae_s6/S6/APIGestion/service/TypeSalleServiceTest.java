package com.sae_s6.S6.APIGestion.service;

import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.repository.TypeSalleRepo;
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
 * Classe de test unitaire pour le service TypeSalleService.
 * Utilise Mockito pour simuler le repository TypeSalleRepo.
 */
public class TypeSalleServiceTest {

    // Mock du repository TypeSalleRepo
    @Mock
    private TypeSalleRepo typeSalleRepo;

    // Injection du service avec le mock
    @InjectMocks
    private TypeSalleService typeSalleService;

    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la récupération de tous les types de salle.
     */
    @Test
    public void testGetAllTypeSalles() {
        // Arrange : préparation des données fictives
        TypeSalle typeSalle1 = new TypeSalle();
        TypeSalle typeSalle2 = new TypeSalle();
        when(typeSalleRepo.findAll()).thenReturn(Arrays.asList(typeSalle1, typeSalle2));

        // Act : appel du service
        List<TypeSalle> result = typeSalleService.getAllTypeSalles();

        // Assert : vérification des résultats
        assertEquals(2, result.size());
        verify(typeSalleRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'un type de salle par son identifiant (cas trouvé).
     */
    @Test
    public void testGetTypeSalleById_Found() {
        // Arrange : préparation des données fictives
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(1);
        when(typeSalleRepo.findById(1)).thenReturn(Optional.of(typeSalle));

        // Act : appel du service
        TypeSalle result = typeSalleService.getTypeSalleById(1);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    /**
     * Teste la récupération d'un type de salle par son identifiant (cas non trouvé).
     */
    @Test
    public void testGetTypeSalleById_NotFound() {
        // Arrange : simulation d'un résultat vide
        when(typeSalleRepo.findById(1)).thenReturn(Optional.empty());

        // Act : appel du service
        TypeSalle result = typeSalleService.getTypeSalleById(1);

        // Assert : vérification des résultats
        assertNull(result);
    }

    /**
     * Teste la sauvegarde d'un type de salle.
     */
    @Test
    public void testSaveTypeSalle() {
        // Arrange : préparation des données fictives
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(1);
        when(typeSalleRepo.save(typeSalle)).thenReturn(typeSalle);

        // Act : appel du service
        TypeSalle result = typeSalleService.saveTypeSalle(typeSalle);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(typeSalleRepo, times(1)).save(typeSalle);
    }

    /**
     * Teste la mise à jour d'un type de salle.
     */
    @Test
    public void testUpdateTypeSalle() {
        // Arrange : préparation des données fictives
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(1);
        when(typeSalleRepo.save(typeSalle)).thenReturn(typeSalle);

        // Act : appel du service
        TypeSalle result = typeSalleService.updateTypeSalle(typeSalle);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(typeSalleRepo, times(1)).save(typeSalle);
    }

    /**
     * Teste la suppression d'un type de salle par son identifiant.
     */
    @Test
    public void testDeleteTypeSalleById() {
        // Arrange : préparation de l'identifiant
        Integer id = 1;

        // Act : appel du service
        typeSalleService.deleteTypeSalleById(id);

        // Assert : vérification des résultats
        verify(typeSalleRepo, times(1)).deleteById(id);
    }
}