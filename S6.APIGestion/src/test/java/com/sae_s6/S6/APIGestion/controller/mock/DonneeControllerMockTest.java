package com.sae_s6.S6.APIGestion.controller.mock;

import com.sae_s6.S6.APIGestion.controller.DonneeController;
import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.service.DonneeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour le contrôleur DonneeController.
 * Utilise Mockito pour simuler le service DonneeService.
 */
class DonneeControllerMockTest {

    // Mock du service DonneeService
    @Mock
    private DonneeService donneeService;

    // Injection du contrôleur avec le mock
    @InjectMocks
    private DonneeController donneeController;

    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la récupération de toutes les données (cas succès).
     */
    @Test
    void testGetAllDonnees() {
        // Arrange : création de données fictives
        Donnee donnee1 = new Donnee(1, "Température", "°C");
        Donnee donnee2 = new Donnee(2, "Humidité", "%");
        List<Donnee> donnees = Arrays.asList(donnee1, donnee2);

        // Simulation du service
        when(donneeService.getAllDonnees()).thenReturn(donnees);

        // Act : appel du contrôleur
        ResponseEntity<List<Donnee>> response = donneeController.getAllDonnees();

        // Assert : vérification des résultats
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(donnees, response.getBody());
        verify(donneeService, times(1)).getAllDonnees();
    }

    /**
     * Teste la récupération de toutes les données (cas liste nulle).
     */
    @Test
    void testGetAllDonnees_NullList() {
        // Simulation du service retournant null
        when(donneeService.getAllDonnees()).thenReturn(null);

        // Act : appel du contrôleur
        ResponseEntity<List<Donnee>> response = donneeController.getAllDonnees();

        // Assert : vérification du code HTTP
        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Teste la récupération d'une donnée par son identifiant (cas succès).
     */
    @Test
    void testGetDonneeById() {
        // Arrange : création d'une donnée fictive
        Donnee donnee = new Donnee(1, "Pression", "Pa");

        // Simulation du service
        when(donneeService.getDonneeById(1)).thenReturn(donnee);

        // Act : appel du contrôleur
        ResponseEntity<Donnee> response = donneeController.getDonneeById(1);

        // Assert : vérification des résultats
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(donnee, response.getBody());
        verify(donneeService, times(1)).getDonneeById(1);
    }

    /**
     * Teste la récupération d'une donnée par son identifiant (cas non trouvé).
     */
    @Test
    void testGetDonneeById_NotFound() {
        // Simulation du service retournant null
        when(donneeService.getDonneeById(1)).thenReturn(null);

        // Act : appel du contrôleur
        ResponseEntity<Donnee> response = donneeController.getDonneeById(1);

        // Assert : vérification du code HTTP
        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Teste la création d'une donnée (cas succès).
     */
    @Test
    void testSaveDonnee() {
        // Arrange : création d'une donnée fictive
        Donnee donnee = new Donnee(null, "Vitesse", "m/s");
        Donnee savedDonnee = new Donnee(1, "Vitesse", "m/s");

        // Simulation du service
        when(donneeService.saveDonnee(donnee)).thenReturn(savedDonnee);

        // Act : appel du contrôleur
        ResponseEntity<Donnee> response = donneeController.saveDonnee(donnee);

        // Assert : vérification des résultats
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedDonnee, response.getBody());
        verify(donneeService, times(1)).saveDonnee(donnee);
    }

    /**
     * Teste la création d'une donnée (cas échec).
     */
    @Test
    void testSaveDonnee_Failure() {
        // Arrange : création d'une donnée fictive
        Donnee donnee = new Donnee(null, "Vitesse", "m/s");

        // Simulation du service retournant null
        when(donneeService.saveDonnee(donnee)).thenReturn(null);

        // Act : appel du contrôleur
        ResponseEntity<Donnee> response = donneeController.saveDonnee(donnee);

        // Assert : vérification du code HTTP
        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Teste la mise à jour d'une donnée (cas succès).
     */
    @Test
    void testUpdateDonnee() {
        // Arrange : création d'une donnée fictive
        Donnee donnee = new Donnee(1, "Vitesse", "m/s");

        // Simulation du service
        when(donneeService.updateDonnee(donnee)).thenReturn(donnee);

        // Act : appel du contrôleur
        ResponseEntity<Donnee> response = donneeController.updateDonnee(donnee);

        // Assert : vérification des résultats
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(donnee, response.getBody());
        verify(donneeService, times(1)).updateDonnee(donnee);
    }

    /**
     * Teste la mise à jour d'une donnée (cas échec).
     */
    @Test
    void testUpdateDonnee_Failure() {
        // Arrange : création d'une donnée fictive
        Donnee donnee = new Donnee(1, "Vitesse", "m/s");

        // Simulation du service retournant null
        when(donneeService.updateDonnee(donnee)).thenReturn(null);

        // Act : appel du contrôleur
        ResponseEntity<Donnee> response = donneeController.updateDonnee(donnee);

        // Assert : vérification du code HTTP
        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Teste la suppression d'une donnée par son identifiant (cas succès).
     */
    @Test
    void testDeleteDonneeById() {
        // Arrange : création d'une donnée fictive
        Donnee donnee = new Donnee(1, "Pression", "Pa");

        // Simulation du service
        when(donneeService.getDonneeById(1)).thenReturn(donnee);
        doNothing().when(donneeService).deleteDonneeById(1);

        // Act : appel du contrôleur
        ResponseEntity<String> response = donneeController.deleteDonneeById(1);

        // Assert : vérification des résultats
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Donnée supprimée avec succès", response.getBody());
        verify(donneeService, times(1)).deleteDonneeById(1);
    }

    /**
     * Teste la suppression d'une donnée par son identifiant (cas non trouvé).
     */
    @Test
    void testDeleteDonneeById_NotFound() {
        // Simulation du service retournant null
        when(donneeService.getDonneeById(1)).thenReturn(null);

        // Act : appel du contrôleur
        ResponseEntity<String> response = donneeController.deleteDonneeById(1);

        // Assert : vérification du code HTTP
        assertEquals(400, response.getStatusCodeValue());
        verify(donneeService, times(0)).deleteDonneeById(1);
    }
}