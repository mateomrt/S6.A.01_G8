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

public class DonneeServiceTest {

    @Mock
    private DonneeRepo donneeRepo;

    @InjectMocks
    private DonneeService donneeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDonnees() {
        Donnee donnee1 = new Donnee();
        Donnee donnee2 = new Donnee();
        when(donneeRepo.findAll()).thenReturn(Arrays.asList(donnee1, donnee2));

        List<Donnee> result = donneeService.getAllDonnees();

        assertEquals(2, result.size());
        verify(donneeRepo, times(1)).findAll();
    }

    @Test
    public void testGetDonneeById_Found() {
        Donnee donnee = new Donnee();
        donnee.setId(1);
        when(donneeRepo.findById(1)).thenReturn(Optional.of(donnee));

        Donnee result = donneeService.getDonneeById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetDonneeById_NotFound() {
        when(donneeRepo.findById(1)).thenReturn(Optional.empty());

        Donnee result = donneeService.getDonneeById(1);

        assertNull(result);
    }

    @Test
    public void testSaveDonnee() {
        Donnee donnee = new Donnee();
        donnee.setId(1);
        when(donneeRepo.save(donnee)).thenReturn(donnee);

        Donnee result = donneeService.saveDonnee(donnee);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(donneeRepo, times(1)).save(donnee);
    }

    @Test
    public void testUpdateDonnee() {
        Donnee donnee = new Donnee();
        donnee.setId(1);
        when(donneeRepo.save(donnee)).thenReturn(donnee);

        Donnee result = donneeService.updateDonnee(donnee);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(donneeRepo, times(1)).save(donnee);
    }

    @Test
    public void testDeleteDonneeById() {
        Integer id = 1;
        donneeService.deleteDonneeById(id);
        verify(donneeRepo, times(1)).deleteById(id);
    }
}
