package com.sae_s6.S6.APIGestion.service;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.repository.BatimentRepo;
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
 * Classe de test unitaire pour le service BatimentService.
 * Utilise Mockito pour simuler le repository BatimentRepo.
 */
public class BatimentServiceTest {

    @Mock
    private BatimentRepo batimentRepo;

    @InjectMocks
    private BatimentService batimentService;

    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la récupération de tous les bâtiments (cas succès).
     */
    @Test
    void testGetAllBatiments() {
        Batiment b1 = new Batiment();
        Batiment b2 = new Batiment();
        when(batimentRepo.findAll()).thenReturn(Arrays.asList(b1, b2));

        List<Batiment> result = batimentService.getAllBatiments();

        assertEquals(2, result.size());
        verify(batimentRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'un bâtiment par son identifiant (cas trouvé).
     */
    @Test
    void testGetBatimentById_Found() {
        Batiment b = new Batiment();
        b.setId(1);
        when(batimentRepo.findById(1)).thenReturn(Optional.of(b));

        Batiment result = batimentService.getBatimentById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    /**
     * Teste la récupération d'un bâtiment par son identifiant (cas non trouvé).
     */
    @Test
    void testGetBatimentById_NotFound() {
        when(batimentRepo.findById(1)).thenReturn(Optional.empty());

        Batiment result = batimentService.getBatimentById(1);

        assertNull(result);
    }

    /**
     * Teste la mise à jour d'un bâtiment (cas succès).
     */
    @Test
    void testUpdateBatiment() {
        Batiment b = new Batiment();
        b.setId(1);
        when(batimentRepo.save(b)).thenReturn(b);

        Batiment result = batimentService.updateBatiment(b);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(batimentRepo, times(1)).save(b);
    }

    /**
     * Teste la recherche de bâtiments par libellé (ignore la casse).
     */
    @Test
    void testGetByLibelleBatimentContainingIgnoreCase() {
        Batiment b1 = new Batiment();
        Batiment b2 = new Batiment();
        when(batimentRepo.findByLibelleBatimentContainingIgnoreCase("test"))
                .thenReturn(Arrays.asList(b1, b2));

        List<Batiment> result = batimentService.getByLibelleBatimentContainingIgnoreCase("test");

        assertEquals(2, result.size());
        verify(batimentRepo, times(1))
                .findByLibelleBatimentContainingIgnoreCase("test");
    }
}