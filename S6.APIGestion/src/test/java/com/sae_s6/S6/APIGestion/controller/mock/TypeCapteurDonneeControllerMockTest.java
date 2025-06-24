package com.sae_s6.S6.APIGestion.controller.mock;

import com.sae_s6.S6.APIGestion.controller.TypeCapteurDonneeController;
import com.sae_s6.S6.APIGestion.entity.*;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Classe de test unitaire pour le contrôleur TypeCapteurDonneeController.
 * Utilise Mockito pour simuler le service TypeCapteurDonneeService.
 */
class TypeCapteurDonneeControllerMockTest {

    // Mock du service TypeCapteurDonneeService
    @Mock
    private TypeCapteurDonneeService typeCapteurDonneeService;

    // Injection du contrôleur avec le mock
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
        // Arrange : création d'une association fictive
        TypeCapteurDonnee tcd = createValidTypeCapteurDonnee(1, 2);

        // Simulation du service
        when(typeCapteurDonneeService.getAllTypeCapteurDonnee())
                .thenReturn(Arrays.asList(tcd));

        // Act : appel du contrôleur
        ResponseEntity<List<TypeCapteurDonnee>> response = typeCapteurDonneeController.getAllTypeCapteurDonnees();

        // Assert : vérification des résultats
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    /**
     * Teste la récupération de toutes les associations TypeCapteurDonnee (cas liste nulle).
     */
    @Test
    void getAllTypeCapteurDonnees_ReturnsBadRequest_WhenNull() {
        // Simulation du service retournant null
        when(typeCapteurDonneeService.getAllTypeCapteurDonnee()).thenReturn(null);

        // Act : appel du contrôleur
        ResponseEntity<List<TypeCapteurDonnee>> response = typeCapteurDonneeController.getAllTypeCapteurDonnees();

        // Assert : vérification du code HTTP
        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Teste la récupération d'une association TypeCapteurDonnee par son ID (cas succès).
     */
    @Test
    void getTypeCapteurDonneeById_ReturnsTypeCapteurDonnee_WhenFound() {
        // Arrange : création d'une association fictive
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee tcd = createValidTypeCapteurDonnee(1, 2);

        // Simulation du service
        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(tcd);

        // Act : appel du contrôleur
        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.getTypeCapteurDonneeById(1, 2);

        // Assert : vérification des résultats
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    /**
     * Teste la récupération d'une association TypeCapteurDonnee par son ID (cas non trouvé).
     */
    @Test
    void getTypeCapteurDonneeById_ReturnsBadRequest_WhenNotFound() {
        // Arrange : création d'un ID fictif
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);

        // Simulation du service retournant null
        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(null);

        // Act : appel du contrôleur
        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.getTypeCapteurDonneeById(1, 2);

        // Assert : vérification du code HTTP
        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Teste la création d'une association TypeCapteurDonnee (cas succès).
     */
    @Test
    void saveTypeCapteurDonnee_ReturnsTypeCapteurDonnee_WhenSaved() {
        // Arrange : création d'une association fictive
        TypeCapteurDonnee tcd = createValidTypeCapteurDonnee(1, 2);

        // Simulation du service
        when(typeCapteurDonneeService.saveTypeCapteurDonnee(tcd)).thenReturn(tcd);

        // Act : appel du contrôleur
        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.saveTypeCapteurDonnee(tcd);

        // Assert : vérification des résultats
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    /**
     * Teste la création d'une association TypeCapteurDonnee (cas échec).
     */
    @Test
    void saveTypeCapteurDonnee_ReturnsBadRequest_WhenNotSaved() {
        // Arrange : création d'une association fictive
        TypeCapteurDonnee tcd = createValidTypeCapteurDonnee(1, 2);

        // Simulation du service retournant null
        when(typeCapteurDonneeService.saveTypeCapteurDonnee(tcd)).thenReturn(null);

        // Act : appel du contrôleur
        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.saveTypeCapteurDonnee(tcd);

        // Assert : vérification du code HTTP
        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Teste la mise à jour d'une association TypeCapteurDonnee (cas succès).
     */
    @Test
    void updateTypeCapteurDonnee_ReturnsUpdated_WhenUpdated() {
        // Arrange : création d'une association fictive
        TypeCapteurDonnee oldTcd = createValidTypeCapteurDonnee(1, 2);
        TypeCapteurDonneeEmbedId id = oldTcd.getId();

        TypeCapteurDonnee updatedTcd = createValidTypeCapteurDonnee(3, 4);

        // Simulation du service
        when(typeCapteurDonneeService.updateTypeCapteurDonnee(eq(id), any(TypeCapteurDonnee.class)))
                .thenReturn(updatedTcd);

        // Act : appel du contrôleur
        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.updateTypeCapteurOrDonnee(1, 2, updatedTcd);

        // Assert : vérification des résultats
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(3, response.getBody().getDonneeNavigation().getId());
        assertEquals(4, response.getBody().getTypeCapteurNavigation().getId());
    }

    /**
     * Teste la mise à jour d'une association TypeCapteurDonnee (cas échec).
     */
    @Test
    void updateTypeCapteurDonnee_ReturnsBadRequest_WhenNotFound() {
        // Arrange : création d'une association fictive
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee tcd = createValidTypeCapteurDonnee(1, 2);

        // Simulation du service retournant null
        when(typeCapteurDonneeService.updateTypeCapteurDonnee(eq(id), any(TypeCapteurDonnee.class)))
                .thenReturn(null);

        // Act : appel du contrôleur
        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.updateTypeCapteurOrDonnee(1, 2, tcd);

        // Assert : vérification du code HTTP
        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Teste la suppression d'une association TypeCapteurDonnee par son ID (cas succès).
     */
    @Test
    void deleteTypeCapteurDonneeById_ReturnsOk_WhenFoundAndDeleted() {
        // Arrange : création d'une association fictive
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee tcd = createValidTypeCapteurDonnee(1, 2);

        // Simulation du service
        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(tcd);
        doNothing().when(typeCapteurDonneeService).deleteTypeCapteurDonneeById(id);

        // Act : appel du contrôleur
        ResponseEntity<String> response = typeCapteurDonneeController.deleteTypeCapteurDonneeById(1, 2);

        // Assert : vérification des résultats
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Association TypeCapteurDonnee supprimée avec succès", response.getBody());
    }

    /**
     * Teste la suppression d'une association TypeCapteurDonnee par son ID (cas non trouvé).
     */
    @Test
    void deleteTypeCapteurDonneeById_ReturnsBadRequest_WhenNotFound() {
        // Arrange : création d'un ID fictif
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);

        // Simulation du service retournant null
        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(null);

        // Act : appel du contrôleur
        ResponseEntity<String> response = typeCapteurDonneeController.deleteTypeCapteurDonneeById(1, 2);

        // Assert : vérification du code HTTP
        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Crée un TypeCapteurDonnee complet avec navigation et ID.
     * @param donneeId l'identifiant de la donnée
     * @param capteurId l'identifiant du type de capteur
     * @return un objet TypeCapteurDonnee prêt à être utilisé dans les tests
     */
    private TypeCapteurDonnee createValidTypeCapteurDonnee(int donneeId, int capteurId) {
        Donnee donnee = new Donnee();
        donnee.setId(donneeId);

        TypeCapteur capteur = new TypeCapteur();
        capteur.setId(capteurId);

        TypeCapteurDonnee tcd = new TypeCapteurDonnee();
        tcd.setId(new TypeCapteurDonneeEmbedId(donneeId, capteurId));
        tcd.setDonneeNavigation(donnee);
        tcd.setTypeCapteurNavigation(capteur);
        return tcd;
    }
}