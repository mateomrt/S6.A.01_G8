package com.sae_s6.S6.APIGestion.service;

import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonneeEmbedId;
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
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee tcd = new TypeCapteurDonnee();
        tcd.setId(id);
        when(typeCapteurDonneeRepo.findById(id)).thenReturn(Optional.of(tcd));

        TypeCapteurDonnee result = typeCapteurDonneeService.getTypeCapteurDonneeById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void testGetTypeCapteurDonneeById_NotFound() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        when(typeCapteurDonneeRepo.findById(id)).thenReturn(Optional.empty());

        TypeCapteurDonnee result = typeCapteurDonneeService.getTypeCapteurDonneeById(id);

        assertNull(result);
    }

    @Test
    public void testSaveTypeCapteurDonnee() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee tcd = new TypeCapteurDonnee();
        tcd.setId(id);
        when(typeCapteurDonneeRepo.save(tcd)).thenReturn(tcd);

        TypeCapteurDonnee result = typeCapteurDonneeService.saveTypeCapteurDonnee(tcd);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(typeCapteurDonneeRepo, times(1)).save(tcd);
    }

    @Test
    public void testUpdateTypeCapteurDonnee() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee tcd = new TypeCapteurDonnee();
        tcd.setId(id);
        when(typeCapteurDonneeRepo.save(tcd)).thenReturn(tcd);

        TypeCapteurDonnee result = typeCapteurDonneeService.updateTypeCapteurDonnee(tcd);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(typeCapteurDonneeRepo, times(1)).save(tcd);
    }

    @Test
    public void testDeleteTypeCapteurDonneeById() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        typeCapteurDonneeService.deleteTypeCapteurDonneeById(id);
        verify(typeCapteurDonneeRepo, times(1)).deleteById(id);
    }
}