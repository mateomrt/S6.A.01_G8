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

/**
 * Classe de test unitaire pour le service TypeCapteurService.
 * Utilise Mockito pour simuler le repository TypeCapteurRepo.
 */
public class TypeCapteurServiceTest {

    @Mock
    private TypeCapteurRepo typeCapteurRepo;

    @InjectMocks
    private TypeCapteurService typeCapteurService;

    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la récupération de tous les types de capteurs.
     */
    @Test
    public void testGetAllTypeCapteurs() {
        TypeCapteur capteur1 = new TypeCapteur();
        TypeCapteur capteur2 = new TypeCapteur();
        when(typeCapteurRepo.findAll()).thenReturn(Arrays.asList(capteur1, capteur2));

        List<TypeCapteur> result = typeCapteurService.getAllTypeCapteurs();

        assertEquals(2, result.size());
        verify(typeCapteurRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'un type de capteur par son identifiant (cas trouvé).
     */
    @Test
    public void testGetTypeCapteurById_Found() {
        TypeCapteur capteur = new TypeCapteur();
        capteur.setId(1);
        when(typeCapteurRepo.findById(1)).thenReturn(Optional.of(capteur));

        TypeCapteur result = typeCapteurService.getTypeCapteurById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    /**
     * Teste la récupération d'un type de capteur par son identifiant (cas non trouvé).
     */
    @Test
    public void testGetTypeCapteurById_NotFound() {
        when(typeCapteurRepo.findById(1)).thenReturn(Optional.empty());

        TypeCapteur result = typeCapteurService.getTypeCapteurById(1);

        assertNull(result);
    }

    /**
     * Teste la sauvegarde d'un type de capteur.
     */
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

    /**
     * Teste la mise à jour d'un type de capteur.
     */
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

    /**
     * Teste la suppression d'un type de capteur par son identifiant.
     */
    @Test
    public void testDeleteTypeCapteurById() {
        Integer id = 1;
        typeCapteurService.deleteTypeCapteurById(id);
        verify(typeCapteurRepo, times(1)).deleteById(id);
    }
}