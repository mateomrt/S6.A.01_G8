package com.sae_s6.S6.APIGestion.service;

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonneeEmbedId;
import com.sae_s6.S6.APIGestion.repository.DonneeRepo;
import com.sae_s6.S6.APIGestion.repository.TypeCapteurDonneeRepo;
import com.sae_s6.S6.APIGestion.repository.TypeCapteurRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour le service TypeCapteurDonneeService.
 * Utilise Mockito pour simuler les repositories.
 */
public class TypeCapteurDonneeServiceTest {

    // Mocks des repositories nécessaires
    @Mock
    private TypeCapteurDonneeRepo typeCapteurDonneeRepo;

    @Mock
    private DonneeRepo donneeRepo;

    @Mock
    private TypeCapteurRepo typeCapteurRepo;

    // Injection du service avec les mocks
    @InjectMocks
    private TypeCapteurDonneeService typeCapteurDonneeService;

    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la récupération de toutes les associations TypeCapteurDonnee.
     */
    @Test
    public void testGetAllTypeCapteurDonnee() {
        // Arrange : préparation des données fictives
        TypeCapteurDonnee tcd1 = new TypeCapteurDonnee();
        TypeCapteurDonnee tcd2 = new TypeCapteurDonnee();
        when(typeCapteurDonneeRepo.findAll()).thenReturn(Arrays.asList(tcd1, tcd2));

        // Act : appel du service
        List<TypeCapteurDonnee> result = typeCapteurDonneeService.getAllTypeCapteurDonnee();

        // Assert : vérification des résultats
        assertEquals(2, result.size());
        verify(typeCapteurDonneeRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'une association TypeCapteurDonnee par son identifiant (cas trouvé).
     */
    @Test
    public void testGetTypeCapteurDonneeById_Found() {
        // Arrange : préparation des données fictives
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee tcd = new TypeCapteurDonnee();
        tcd.setId(id);
        when(typeCapteurDonneeRepo.findById(id)).thenReturn(Optional.of(tcd));

        // Act : appel du service
        TypeCapteurDonnee result = typeCapteurDonneeService.getTypeCapteurDonneeById(id);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    /**
     * Teste la récupération d'une association TypeCapteurDonnee par son identifiant (cas non trouvé).
     */
    @Test
    public void testGetTypeCapteurDonneeById_NotFound() {
        // Arrange : simulation d'un résultat vide
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        when(typeCapteurDonneeRepo.findById(id)).thenReturn(Optional.empty());

        // Act : appel du service
        TypeCapteurDonnee result = typeCapteurDonneeService.getTypeCapteurDonneeById(id);

        // Assert : vérification des résultats
        assertNull(result);
    }

    /**
     * Teste la sauvegarde d'une association TypeCapteurDonnee.
     */
    @Test
    public void testSaveTypeCapteurDonnee() {
        // Arrange : préparation des données fictives
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        Donnee donnee = new Donnee();
        donnee.setId(1);
        TypeCapteur typeCapteur = new TypeCapteur();
        typeCapteur.setId(2);

        TypeCapteurDonnee tcd = new TypeCapteurDonnee();
        tcd.setId(id);
        tcd.setDonneeNavigation(donnee);
        tcd.setTypeCapteurNavigation(typeCapteur);

        when(donneeRepo.findById(1)).thenReturn(Optional.of(donnee));
        when(typeCapteurRepo.findById(2)).thenReturn(Optional.of(typeCapteur));
        when(typeCapteurDonneeRepo.save(tcd)).thenReturn(tcd);

        // Act : appel du service
        TypeCapteurDonnee result = typeCapteurDonneeService.saveTypeCapteurDonnee(tcd);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(typeCapteurDonneeRepo, times(1)).save(tcd);
    }

    /**
     * Teste la mise à jour d'une association TypeCapteurDonnee.
     */
    @Test
    void updateTypeCapteurDonnee_WhenIdChanged_ShouldDeleteOldAndSaveNew() {
        // Arrange : préparation des données fictives
        TypeCapteurDonneeEmbedId oldId = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonneeEmbedId newId = new TypeCapteurDonneeEmbedId(2, 1);

        Donnee donnee = new Donnee();
        donnee.setId(2);
        TypeCapteur typeCapteur = new TypeCapteur();
        typeCapteur.setId(1);

        TypeCapteurDonnee newEntity = new TypeCapteurDonnee();
        newEntity.setId(newId);
        newEntity.setDonneeNavigation(donnee);
        newEntity.setTypeCapteurNavigation(typeCapteur);

        when(donneeRepo.findById(2)).thenReturn(Optional.of(donnee));
        when(typeCapteurRepo.findById(1)).thenReturn(Optional.of(typeCapteur));
        when(typeCapteurDonneeRepo.save(any())).thenReturn(newEntity);

        // Act : appel du service
        TypeCapteurDonnee result = typeCapteurDonneeService.updateTypeCapteurDonnee(oldId, newEntity);

        // Assert : vérification des résultats
        assertNotNull(result);
        assertEquals(2, result.getId().getIdDonnee());
        assertEquals(1, result.getId().getIdTypeCapteur());
        verify(typeCapteurDonneeRepo).save(newEntity);
        assertThat(result).isEqualTo(newEntity);
    }

    /**
     * Teste la suppression d'une association TypeCapteurDonnee par son identifiant.
     */
    @Test
    public void testDeleteTypeCapteurDonneeById() {
        // Arrange : préparation des données fictives
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        typeCapteurDonnee.setId(id);

        when(typeCapteurDonneeRepo.findById(id)).thenReturn(Optional.of(typeCapteurDonnee));

        // Act : appel du service
        typeCapteurDonneeService.deleteTypeCapteurDonneeById(id);

        // Assert : vérification des résultats
        verify(typeCapteurDonneeRepo, times(1)).deleteById(id);
    }
}