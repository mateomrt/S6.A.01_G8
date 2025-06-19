package com.sae_s6.S6.APIGestion.service;

import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Mur.Orientation;
import com.sae_s6.S6.APIGestion.repository.MurRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MurServiceTest {

    @Mock
    private MurRepo murRepo;

    @InjectMocks
    private MurService murService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMurs() {
        Mur mur1 = new Mur();
        Mur mur2 = new Mur();
        when(murRepo.findAll()).thenReturn(Arrays.asList(mur1, mur2));

        List<Mur> result = murService.getAllMurs();

        assertEquals(2, result.size());
        verify(murRepo, times(1)).findAll();
    }

    @Test
    public void testGetMurById_Found() {
        Mur mur = new Mur();
        mur.setId(1);
        when(murRepo.findById(1)).thenReturn(Optional.of(mur));

        Mur result = murService.getMurById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetMurById_NotFound() {
        when(murRepo.findById(1)).thenReturn(Optional.empty());

        Mur result = murService.getMurById(1);

        assertNull(result);
    }

    @Test
    public void testSaveMur() {
        Mur mur = new Mur();
        mur.setId(1);
        when(murRepo.save(mur)).thenReturn(mur);

        Mur result = murService.saveMur(mur);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(murRepo, times(1)).save(mur);
    }

    @Test
    public void testUpdateMur() {
        Mur existingMur = new Mur();
        existingMur.setId(1);
        existingMur.setLibelleMur("Ancien titre");

        Mur updatedMur = new Mur();
        updatedMur.setId(1);
        updatedMur.setLibelleMur("Nouveau titre");

        when(murRepo.save(updatedMur)).thenReturn(updatedMur);

        Mur result = murService.updateMur(updatedMur);

        assertNotNull(result);
        assertEquals("Nouveau titre", result.getLibelleMur());
        verify(murRepo, times(1)).save(updatedMur);
    }

    @Test
    public void testDeleteMurById() {
        Integer id = 1;
        murService.deleteMurById(id);
        verify(murRepo, times(1)).deleteById(id);
    }

    @Test
    void testDeleteMurById2() {
        
    }

    @Test
    void testGetAllMurs2() {
        
    }

    @Test
    void testGetByLibelleMurContainingIgnoreCase() {
        
    }

    @Test
    void testGetMurById() {
        
    }

    @Test
    void testSaveMur2() {
        
    }

    @Test
    void testUpdateMur2() {
        
    }
}