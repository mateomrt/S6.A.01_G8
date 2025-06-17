package com.sae_s6.S6.APIGestion.service;

import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.repository.TypeCapteurRepo;
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

public class TypeCapteurServiceTest {

    @Mock
    private TypeCapteurRepo typeCapteurRepo;

    @InjectMocks
    private TypeCapteurService typeCapteurService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTypeCapteurs() {
        TypeCapteur capteur1 = new TypeCapteur();
        TypeCapteur capteur2 = new TypeCapteur();
        when(typeCapteurRepo.findAll()).thenReturn(Arrays.asList(capteur1, capteur2));

        List<TypeCapteur> result = typeCapteurService.getAllTypeCapteurs();

        assertEquals(2, result.size());
        verify(typeCapteurRepo, times(1)).findAll();
    }

    @Test
    public void testGetTypeCapteurById_Found() {
        TypeCapteur capteur = new TypeCapteur();
        capteur.setId(1);
        when(typeCapteurRepo.findById(1)).thenReturn(Optional.of(capteur));

        TypeCapteur result = typeCapteurService.getTypeCapteurById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetTypeCapteurById_NotFound() {
        when(typeCapteurRepo.findById(1)).thenReturn(Optional.empty());

        TypeCapteur result = typeCapteurService.getTypeCapteurById(1);

        assertNull(result);
    }

    @Test
    public void testSaveTypeCapteur() {
        TypeCapteur capteur = new TypeCapteur();
        capteur.setId(1);
        when(typeCapteurRepo.save(capteur)).thenReturn(capteur);

        TypeCapteur result = typeCapteurService.saveTypeCapteur(capteur);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(typeCapteurRepo, times(1)).save(capteur);
    }

    @Test
    public void testUpdateTypeCapteur() {
        TypeCapteur capteur = new TypeCapteur();
        capteur.setId(1);
        when(typeCapteurRepo.save(capteur)).thenReturn(capteur);

        TypeCapteur result = typeCapteurService.updateTypeCapteur(capteur);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(typeCapteurRepo, times(1)).save(capteur);
    }

    @Test
    public void testDeleteTypeCapteurById() {
        Integer id = 1;
        typeCapteurService.deleteTypeCapteurById(id);
        verify(typeCapteurRepo, times(1)).deleteById(id);
    }
}
