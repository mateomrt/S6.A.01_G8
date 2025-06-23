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

public class TypeCapteurDonneeServiceTest {

    @Mock
    private TypeCapteurDonneeRepo typeCapteurDonneeRepo;

    @Mock
    private DonneeRepo donneeRepo;

    @Mock
    private TypeCapteurRepo typeCapteurRepo;

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
        TypeCapteurDonnee tcd1 = new TypeCapteurDonnee();
        TypeCapteurDonnee tcd2 = new TypeCapteurDonnee();
        when(typeCapteurDonneeRepo.findAll()).thenReturn(Arrays.asList(tcd1, tcd2));

        List<TypeCapteurDonnee> result = typeCapteurDonneeService.getAllTypeCapteurDonnee();

        assertEquals(2, result.size());
        verify(typeCapteurDonneeRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'une association TypeCapteurDonnee par son identifiant (cas trouvé).
     */
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

    /**
     * Teste la récupération d'une association TypeCapteurDonnee par son identifiant (cas non trouvé).
     */
    @Test
    public void testGetTypeCapteurDonneeById_NotFound() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        when(typeCapteurDonneeRepo.findById(id)).thenReturn(Optional.empty());

        TypeCapteurDonnee result = typeCapteurDonneeService.getTypeCapteurDonneeById(id);

        assertNull(result);
    }

    /**
     * Teste la sauvegarde d'une association TypeCapteurDonnee.
     */
    @Test
    public void testSaveTypeCapteurDonnee() {
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

        TypeCapteurDonnee result = typeCapteurDonneeService.saveTypeCapteurDonnee(tcd);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(typeCapteurDonneeRepo, times(1)).save(tcd);
    }

    /**
     * Teste la mise à jour d'une association TypeCapteurDonnee.
     */
    @Test
    void updateTypeCapteurDonnee_WhenIdChanged_ShouldDeleteOldAndSaveNew() {
        // Arrange
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

        // Act
        TypeCapteurDonnee result = typeCapteurDonneeService.updateTypeCapteurDonnee(oldId, newEntity);

        // Assert
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
            // Arrange
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        typeCapteurDonnee.setId(id);

        when(typeCapteurDonneeRepo.findById(id)).thenReturn(Optional.of(typeCapteurDonnee));

        // Act
        typeCapteurDonneeService.deleteTypeCapteurDonneeById(id);

        // Assert
        verify(typeCapteurDonneeRepo, times(1)).deleteById(id);
    }
}