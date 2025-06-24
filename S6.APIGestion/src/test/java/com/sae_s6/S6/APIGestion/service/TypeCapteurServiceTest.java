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

    // Mock du repository TypeCapteurRepo
    @Mock
    private TypeCapteurRepo typeCapteurRepo;

    // Injection du service avec le mock
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
        // Arrange : préparation des données fictives
        TypeCapteur capteur1 = new TypeCapteur();
        TypeCapteur capteur2 = new TypeCapteur();
        when(typeCapteurRepo.findAll()).thenReturn(Arrays.asList(capteur1, capteur2));

        // Act : appel du service
        List<TypeCapteur> result = typeCapteurService.getAllTypeCapteurs();

        // Assert : vérification des résultats
        assertEquals(2, result.size());
        verify(typeCapteurRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'un type de capteur par son identifiant (cas trouvé).
     */
    @Test
    public void testGetTypeCapteurById_Found() {
        // Arrange : préparation des données fictives
        TypeCapteur capteur = new TypeCapteur();
        capteur.setId(1);
        when(typeCapteurRepo.findById(1)).thenReturn(Optional.of(capteur));

        // Act : appel du service
        TypeCapteur result = typeCapteurService.getTypeCapteurById(1);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    /**
     * Teste la récupération d'un type de capteur par son identifiant (cas non trouvé).
     */
    @Test
    public void testGetTypeCapteurById_NotFound() {
        // Arrange : simulation d'un résultat vide
        when(typeCapteurRepo.findById(1)).thenReturn(Optional.empty());

        // Act : appel du service
        TypeCapteur result = typeCapteurService.getTypeCapteurById(1);

        // Assert : vérification des résultats
        assertNull(result);
    }

    /**
     * Teste la sauvegarde d'un type de capteur.
     */
    @Test
    public void testSaveTypeCapteur() {
        // Arrange : préparation des données fictives
        TypeCapteur capteur = new TypeCapteur();
        capteur.setId(1);
        when(typeCapteurRepo.save(capteur)).thenReturn(capteur);

        // Act : appel du service
        TypeCapteur result = typeCapteurService.saveTypeCapteur(capteur);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(typeCapteurRepo, times(1)).save(capteur);
    }

    /**
     * Teste la mise à jour d'un type de capteur.
     */
    @Test
    public void testUpdateTypeCapteur() {
        // Arrange : préparation des données fictives
        TypeCapteur capteur = new TypeCapteur();
        capteur.setId(1);
        when(typeCapteurRepo.save(capteur)).thenReturn(capteur);

        // Act : appel du service
        TypeCapteur result = typeCapteurService.updateTypeCapteur(capteur);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(typeCapteurRepo, times(1)).save(capteur);
    }

    /**
     * Teste la suppression d'un type de capteur par son identifiant.
     */
    @Test
    public void testDeleteTypeCapteurById() {
        // Arrange : préparation de l'identifiant
        Integer id = 1;

        // Act : appel du service
        typeCapteurService.deleteTypeCapteurById(id);

        // Assert : vérification des résultats
        verify(typeCapteurRepo, times(1)).deleteById(id);
    }
}