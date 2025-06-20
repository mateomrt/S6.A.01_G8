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

    @Mock
    private TypeEquipementRepo typeEquipementRepo;

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
        TypeEquipement eq1 = new TypeEquipement();
        TypeEquipement eq2 = new TypeEquipement();
        when(typeEquipementRepo.findAll()).thenReturn(Arrays.asList(eq1, eq2));

        List<TypeEquipement> result = typeEquipementService.getAllTypeEquipements();

        assertEquals(2, result.size());
        verify(typeEquipementRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'un type d'équipement par son identifiant (cas trouvé).
     */
    @Test
    public void testGetTypeEquipementById_Found() {
        TypeEquipement eq = new TypeEquipement();
        eq.setId(1);
        when(typeEquipementRepo.findById(1)).thenReturn(Optional.of(eq));

        TypeEquipement result = typeEquipementService.getTypeEquipementById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    /**
     * Teste la récupération d'un type d'équipement par son identifiant (cas non trouvé).
     */
    @Test
    public void testGetTypeEquipementById_NotFound() {
        when(typeEquipementRepo.findById(1)).thenReturn(Optional.empty());

        TypeEquipement result = typeEquipementService.getTypeEquipementById(1);

        assertNull(result);
    }

    /**
     * Teste la sauvegarde d'un type d'équipement.
     */
    @Test
    public void testSaveTypeEquipement() {
        TypeEquipement eq = new TypeEquipement();
        eq.setId(1);
        when(typeEquipementRepo.save(eq)).thenReturn(eq);

        TypeEquipement result = typeEquipementService.saveTypeEquipement(eq);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(typeEquipementRepo, times(1)).save(eq);
    }

    /**
     * Teste la mise à jour d'un type d'équipement.
     */
    @Test
    public void testUpdateTypeEquipement() {
        TypeEquipement eq = new TypeEquipement();
        eq.setId(1);
        when(typeEquipementRepo.save(eq)).thenReturn(eq);

        TypeEquipement result = typeEquipementService.updateTypeEquipement(eq);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(typeEquipementRepo, times(1)).save(eq);
    }

    /**
     * Teste la suppression d'un type d'équipement par son identifiant.
     */
    @Test
    public void testDeleteTypeEquipementById() {
        Integer id = 1;
        typeEquipementService.deleteTypeEquipementById(id);
        verify(typeEquipementRepo, times(1)).deleteById(id);
    }
}