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

public class TypeSalleServiceTest {

    @Mock
    private TypeSalleRepo typeSalleRepo;

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
        TypeSalle typeSalle1 = new TypeSalle();
        TypeSalle typeSalle2 = new TypeSalle();
        when(typeSalleRepo.findAll()).thenReturn(Arrays.asList(typeSalle1, typeSalle2));

        List<TypeSalle> result = typeSalleService.getAllTypeSalles();

        assertEquals(2, result.size());
        verify(typeSalleRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'un type de salle par son identifiant (cas trouvé).
     */
    @Test
    public void testGetTypeSalleById_Found() {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(1);
        when(typeSalleRepo.findById(1)).thenReturn(Optional.of(typeSalle));

        TypeSalle result = typeSalleService.getTypeSalleById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    /**
     * Teste la récupération d'un type de salle par son identifiant (cas non trouvé).
     */
    @Test
    public void testGetTypeSalleById_NotFound() {
        when(typeSalleRepo.findById(1)).thenReturn(Optional.empty());

        TypeSalle result = typeSalleService.getTypeSalleById(1);

        assertNull(result);
    }

    /**
     * Teste la sauvegarde d'un type de salle.
     */
    @Test
    public void testSaveTypeSalle() {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(1);
        when(typeSalleRepo.save(typeSalle)).thenReturn(typeSalle);

        TypeSalle result = typeSalleService.saveTypeSalle(typeSalle);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(typeSalleRepo, times(1)).save(typeSalle);
    }

    /**
     * Teste la mise à jour d'un type de salle.
     */
    @Test
    public void testUpdateTypeSalle() {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(1);
        when(typeSalleRepo.save(typeSalle)).thenReturn(typeSalle);

        TypeSalle result = typeSalleService.updateTypeSalle(typeSalle);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(typeSalleRepo, times(1)).save(typeSalle);
    }

    /**
     * Teste la suppression d'un type de salle par son identifiant.
     */
    @Test
    public void testDeleteTypeSalleById() {
        Integer id = 1;
        typeSalleService.deleteTypeSalleById(id);
        verify(typeSalleRepo, times(1)).deleteById(id);
    }
}