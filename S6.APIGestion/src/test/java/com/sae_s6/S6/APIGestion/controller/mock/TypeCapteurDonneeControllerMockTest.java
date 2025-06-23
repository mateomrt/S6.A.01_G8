package com.sae_s6.S6.APIGestion.controller.mock;

import com.sae_s6.S6.APIGestion.controller.TypeCapteurDonneeController;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonneeEmbedId;
import com.sae_s6.S6.APIGestion.service.TypeCapteurDonneeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de test unitaire pour le contrôleur TypeCapteurDonneeController.
 * Utilise Mockito pour simuler le service TypeCapteurDonneeService.
 */
class TypeCapteurDonneeControllerMockTest {

    @Mock
    private TypeCapteurDonneeService typeCapteurDonneeService;

    @InjectMocks
    private TypeCapteurDonneeController typeCapteurDonneeController;

    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la récupération de toutes les associations TypeCapteurDonnee (cas succès).
     */
    @Test
    void getAllTypeCapteurDonnees_ReturnsList_WhenNotNull() {
        TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        when(typeCapteurDonneeService.getAllTypeCapteurDonnee())
                .thenReturn(Arrays.asList(typeCapteurDonnee));

        ResponseEntity<List<TypeCapteurDonnee>> response = typeCapteurDonneeController.getAllTypeCapteurDonnees();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    /**
     * Teste la récupération de toutes les associations TypeCapteurDonnee (cas liste nulle).
     */
    @Test
    void getAllTypeCapteurDonnees_ReturnsBadRequest_WhenNull() {
        when(typeCapteurDonneeService.getAllTypeCapteurDonnee()).thenReturn(null);

        ResponseEntity<List<TypeCapteurDonnee>> response = typeCapteurDonneeController.getAllTypeCapteurDonnees();

        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Teste la récupération d'une association TypeCapteurDonnee par son identifiant (cas succès).
     */
    @Test
    void getTypeCapteurDonneeById_ReturnsTypeCapteurDonnee_WhenFound() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(typeCapteurDonnee);

        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.getTypeCapteurDonneeById(1, 2);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    /**
     * Teste la récupération d'une association TypeCapteurDonnee par son identifiant (cas non trouvé).
     */
    @Test
    void getTypeCapteurDonneeById_ReturnsBadRequest_WhenNotFound() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(null);

        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.getTypeCapteurDonneeById(1, 2);

        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Teste la création d'une association TypeCapteurDonnee (cas succès).
     */
    @Test
    void saveTypeCapteurDonnee_ReturnsTypeCapteurDonnee_WhenSaved() {
        TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        when(typeCapteurDonneeService.saveTypeCapteurDonnee(typeCapteurDonnee)).thenReturn(typeCapteurDonnee);

        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.saveTypeCapteurDonnee(typeCapteurDonnee);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    /**
     * Teste la création d'une association TypeCapteurDonnee (cas échec).
     */
    @Test
    void saveTypeCapteurDonnee_ReturnsBadRequest_WhenNotSaved() {
        TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        when(typeCapteurDonneeService.saveTypeCapteurDonnee(typeCapteurDonnee)).thenReturn(null);

        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.saveTypeCapteurDonnee(typeCapteurDonnee);

        assertEquals(400, response.getStatusCodeValue());
    }

    // /**
    //  * Teste la mise à jour d'une association TypeCapteurDonnee (cas succès).
    //  */
    // @Test
    // void updateTypeCapteurDonnee_ReturnsUpdated_WhenUpdated() {
    //     TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
    //     when(typeCapteurDonneeService.updateTypeCapteurDonnee(typeCapteurDonnee)).thenReturn(typeCapteurDonnee);

    //     ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.updateTypeCapteurDonnee(typeCapteurDonnee);

    //     assertEquals(200, response.getStatusCodeValue());
    //     assertNotNull(response.getBody());
    // }

    // /**
    //  * Teste la mise à jour d'une association TypeCapteurDonnee (cas échec).
    //  */
    // @Test
    // void updateTypeCapteurDonnee_ReturnsBadRequest_WhenNotFound() {
    //     TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
    //     when(typeCapteurDonneeService.updateTypeCapteurDonnee(typeCapteurDonnee)).thenReturn(null);

    //     ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.updateTypeCapteurDonnee(typeCapteurDonnee);

    //     assertEquals(400, response.getStatusCodeValue());
    // }

    /**
     * Teste la suppression d'une association TypeCapteurDonnee par son identifiant (cas succès).
     */
    @Test
    void deleteTypeCapteurDonneeById_ReturnsOk_WhenFoundAndDeleted() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(typeCapteurDonnee);
        doNothing().when(typeCapteurDonneeService).deleteTypeCapteurDonneeById(id);

        ResponseEntity<String> response = typeCapteurDonneeController.deleteTypeCapteurDonneeById(1, 2);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Association TypeCapteurDonnee supprimée avec succès", response.getBody());
    }

    /**
     * Teste la suppression d'une association TypeCapteurDonnee par son identifiant (cas non trouvé).
     */
    @Test
    void deleteTypeCapteurDonneeById_ReturnsBadRequest_WhenNotFound() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(null);

        ResponseEntity<String> response = typeCapteurDonneeController.deleteTypeCapteurDonneeById(1, 2);

        assertEquals(400, response.getStatusCodeValue());
    }
}