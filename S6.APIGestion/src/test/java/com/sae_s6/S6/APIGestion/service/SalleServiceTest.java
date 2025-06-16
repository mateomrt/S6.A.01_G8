package com.sae_s6.S6.APIGestion.service;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.repository.SalleRepo;
import com.sae_s6.S6.APIGestion.service.SalleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

public class SalleServiceTest {

    @Mock
    private SalleRepo salleRepo;

    @InjectMocks
    private SalleService salleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSalles() {
        Salle salle1 = new Salle();
        Salle salle2 = new Salle();
        when(salleRepo.findAll()).thenReturn(Arrays.asList(salle1, salle2));

        List<Salle> salles = salleService.getAllSalles();

        assertEquals(2, salles.size());
        verify(salleRepo, times(1)).findAll();
    }

    @Test
    public void testGetSalleById_Found() {
        Salle salle = new Salle();
        salle.setId(1);
        when(salleRepo.findById(1)).thenReturn(Optional.of(salle));

        Salle result = salleService.getSalleById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetSalleById_NotFound() {
        when(salleRepo.findById(1)).thenReturn(Optional.empty());

        Salle result = salleService.getSalleById(1);

        assertNull(result);
    }

    @Test
    public void testSaveSalle() {
        Salle salle = new Salle();
        salle.setId(1);
        when(salleRepo.save(salle)).thenReturn(salle);

        Salle result = salleService.saveSalle(salle);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testUpdateSalle() {
        Salle salle = new Salle();
        salle.setId(1);
        when(salleRepo.save(salle)).thenReturn(salle);

        Salle result = salleService.updateSalle(salle);

        assertEquals(1, result.getId());
    }

    @Test
    public void testDeleteSalleById() {
        salleService.deleteSalleById(1);
        verify(salleRepo, times(1)).deleteById(1);
    }
}
