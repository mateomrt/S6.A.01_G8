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

    @Mock
    private TypeCapteurDonneeService typeCapteurDonneeService;

    @InjectMocks
    private TypeCapteurDonneeController typeCapteurDonneeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTypeCapteurDonnees_ReturnsList_WhenNotNull() {
        TypeCapteurDonnee tcd = createValidTypeCapteurDonnee(1, 2);

        when(typeCapteurDonneeService.getAllTypeCapteurDonnee())
                .thenReturn(Arrays.asList(tcd));

        ResponseEntity<List<TypeCapteurDonnee>> response = typeCapteurDonneeController.getAllTypeCapteurDonnees();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getAllTypeCapteurDonnees_ReturnsBadRequest_WhenNull() {
        when(typeCapteurDonneeService.getAllTypeCapteurDonnee()).thenReturn(null);

        ResponseEntity<List<TypeCapteurDonnee>> response = typeCapteurDonneeController.getAllTypeCapteurDonnees();

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void getTypeCapteurDonneeById_ReturnsTypeCapteurDonnee_WhenFound() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee tcd = createValidTypeCapteurDonnee(1, 2);

        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(tcd);

        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.getTypeCapteurDonneeById(1, 2);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void getTypeCapteurDonneeById_ReturnsBadRequest_WhenNotFound() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(null);

        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.getTypeCapteurDonneeById(1, 2);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void saveTypeCapteurDonnee_ReturnsTypeCapteurDonnee_WhenSaved() {
        TypeCapteurDonnee tcd = createValidTypeCapteurDonnee(1, 2);

        when(typeCapteurDonneeService.saveTypeCapteurDonnee(tcd)).thenReturn(tcd);

        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.saveTypeCapteurDonnee(tcd);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void saveTypeCapteurDonnee_ReturnsBadRequest_WhenNotSaved() {
        TypeCapteurDonnee tcd = createValidTypeCapteurDonnee(1, 2);
        when(typeCapteurDonneeService.saveTypeCapteurDonnee(tcd)).thenReturn(null);

        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.saveTypeCapteurDonnee(tcd);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void updateTypeCapteurDonnee_ReturnsUpdated_WhenUpdated() {
        TypeCapteurDonnee oldTcd = createValidTypeCapteurDonnee(1, 2);
        TypeCapteurDonneeEmbedId id = oldTcd.getId();

        TypeCapteurDonnee updatedTcd = createValidTypeCapteurDonnee(3, 4);

        when(typeCapteurDonneeService.updateTypeCapteurDonnee(eq(id), any(TypeCapteurDonnee.class)))
                .thenReturn(updatedTcd);

        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.updateTypeCapteurOrDonnee(1, 2, updatedTcd);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(3, response.getBody().getDonneeNavigation().getId());
        assertEquals(4, response.getBody().getTypeCapteurNavigation().getId());
    }

    @Test
    void updateTypeCapteurDonnee_ReturnsBadRequest_WhenNotFound() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee tcd = createValidTypeCapteurDonnee(1, 2);

        when(typeCapteurDonneeService.updateTypeCapteurDonnee(eq(id), any(TypeCapteurDonnee.class)))
                .thenReturn(null);

        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.updateTypeCapteurOrDonnee(1, 2, tcd);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void deleteTypeCapteurDonneeById_ReturnsOk_WhenFoundAndDeleted() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee tcd = createValidTypeCapteurDonnee(1, 2);

        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(tcd);
        doNothing().when(typeCapteurDonneeService).deleteTypeCapteurDonneeById(id);

        ResponseEntity<String> response = typeCapteurDonneeController.deleteTypeCapteurDonneeById(1, 2);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Association TypeCapteurDonnee supprimée avec succès", response.getBody());
    }

    @Test
    void deleteTypeCapteurDonneeById_ReturnsBadRequest_WhenNotFound() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(null);

        ResponseEntity<String> response = typeCapteurDonneeController.deleteTypeCapteurDonneeById(1, 2);

        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Crée un TypeCapteurDonnee complet avec navigation et ID.
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
