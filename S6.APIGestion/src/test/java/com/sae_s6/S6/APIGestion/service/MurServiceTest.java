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

        Optional<Mur> result = murService.getMurById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    public void testGetMurById_NotFound() {
        when(murRepo.findById(1)).thenReturn(Optional.empty());

        Optional<Mur> result = murService.getMurById(1);

        assertFalse(result.isPresent());
    }

    @Test
    public void testCreateMur() {
        Mur mur = new Mur();
        mur.setId(1);
        when(murRepo.save(mur)).thenReturn(mur);

        Mur result = murService.createMur(mur);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(murRepo, times(1)).save(mur);
    }

    @Test
    public void testUpdateMur_Found() {
        Mur existingMur = new Mur();
        existingMur.setId(1);
        existingMur.setTitre("Ancien titre");

        Mur updatedMur = new Mur();
        updatedMur.setTitre("Nouveau titre");
        updatedMur.setHauteur(2.5);
        updatedMur.setLongueur(4.0);
        updatedMur.setOrientation(Orientation.N);
        updatedMur.setSalle(null);

        when(murRepo.findById(1)).thenReturn(Optional.of(existingMur));
        when(murRepo.save(any(Mur.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Mur result = murService.updateMur(1, updatedMur);

        assertNotNull(result);
        assertEquals("Nouveau titre", result.getTitre());
        assertEquals(2.5, result.getHauteur());
        assertEquals(4.0, result.getLongueur());
        assertEquals("Nord", result.getOrientation());
        verify(murRepo, times(1)).findById(1);
        verify(murRepo, times(1)).save(existingMur);
    }

    @Test
    public void testUpdateMur_NotFound() {
        Mur updatedMur = new Mur();
        when(murRepo.findById(1)).thenReturn(Optional.empty());

        Mur result = murService.updateMur(1, updatedMur);

        assertNull(result);
        verify(murRepo, times(1)).findById(1);
        verify(murRepo, times(0)).save(any());
    }

    @Test
    public void testDeleteMur() {
        Integer id = 1;
        murService.deleteMur(id);
        verify(murRepo, times(1)).deleteById(id);
    }
}
