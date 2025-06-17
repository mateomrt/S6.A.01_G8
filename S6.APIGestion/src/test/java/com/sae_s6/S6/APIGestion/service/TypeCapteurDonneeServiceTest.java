package com.sae_s6.S6.APIGestion.service;

import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.repository.TypeCapteurDonneeRepo;
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

public class TypeCapteurDonneeServiceTest {

    @Mock
    private TypeCapteurDonneeRepo typeCapteurDonneeRepo;

    @InjectMocks
    private TypeCapteurDonneeService typeCapteurDonneeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTypeCapteurDonnee() {
        TypeCapteurDonnee tcd1 = new TypeCapteurDonnee();
        TypeCapteurDonnee tcd2 = new TypeCapteurDonnee();
        when(typeCapteurDonneeRepo.findAll()).thenReturn(Arrays.asList(tcd1, tcd2));

        List<TypeCapteurDonnee> result = typeCapteurDonneeService.getAllTypeCapteurDonnee();

        assertEquals(2, result.size());
        verify(typeCapteurDonneeRepo, times(1)).findAll();
    }

    @Test
    public void testGetTypeCapteurDonneeById_Found() {
        TypeCapteurDonnee tcd = new TypeCapteurDonnee();
        tcd.setIdTypeCapteur(1);
        when(typeCapteurDonneeRepo.findById(1)).thenReturn(Optional.of(tcd));

        TypeCapteurDonnee result = typeCapteurDonneeService.getTypeCapteurDonneeById(1);

        assertNotNull(result);
        assertEquals(1, result.getIdTypeCapteur());
    }

    @Test
    public void testGetTypeCapteurDonneeById_NotFound() {
        when(typeCapteurDonneeRepo.findById(1)).thenReturn(Optional.empty());

        TypeCapteurDonnee result = typeCapteurDonneeService.getTypeCapteurDonneeById(1);

        assertNull(result);
    }

    @Test
    public void testSaveTypeCapteurDonnee() {
        TypeCapteurDonnee tcd = new TypeCapteurDonnee();
        tcd.setIdTypeCapteur(1);
        when(typeCapteurDonneeRepo.save(tcd)).thenReturn(tcd);

        TypeCapteurDonnee result = typeCapteurDonneeService.saveTypeCapteurDonnee(tcd);

        assertNotNull(result);
        assertEquals(1, result.getIdTypeCapteur());
        verify(typeCapteurDonneeRepo, times(1)).save(tcd);
    }

    @Test
    public void testUpdateTypeCapteurDonnee() {
        TypeCapteurDonnee tcd = new TypeCapteurDonnee();
        tcd.setIdTypeCapteur(1);
        when(typeCapteurDonneeRepo.save(tcd)).thenReturn(tcd);

        TypeCapteurDonnee result = typeCapteurDonneeService.updateTypeCapteurDonnee(tcd);

        assertNotNull(result);
        assertEquals(1, result.getIdTypeCapteur());
        verify(typeCapteurDonneeRepo, times(1)).save(tcd);
    }

    @Test
    public void testDeleteTypeCapteurDonneeById() {
        Integer id = 1;
        typeCapteurDonneeService.deleteTypeCapteurDonneeById(id);
        verify(typeCapteurDonneeRepo, times(1)).deleteById(id);
    }
}
