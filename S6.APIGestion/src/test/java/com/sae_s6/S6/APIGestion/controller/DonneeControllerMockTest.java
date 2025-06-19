package com.sae_s6.S6.APIGestion.controller;

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

class DonneeControllerMockTest {

    @Mock
    private DonneeService donneeService;

    @InjectMocks
    private DonneeController donneeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDonnees() {
        Donnee donnee1 = new Donnee(1, "Température", "°C");
        Donnee donnee2 = new Donnee(2, "Humidité", "%");
        List<Donnee> donnees = Arrays.asList(donnee1, donnee2);

        when(donneeService.getAllDonnees()).thenReturn(donnees);

        ResponseEntity<List<Donnee>> response = donneeController.getAllDonnees();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(donnees, response.getBody());
        verify(donneeService, times(1)).getAllDonnees();
    }

    @Test
    void testGetAllDonnees_NullList() {
        when(donneeService.getAllDonnees()).thenReturn(null);

        ResponseEntity<List<Donnee>> response = donneeController.getAllDonnees();

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testGetDonneeById() {
        Donnee donnee = new Donnee(1, "Pression", "Pa");
        when(donneeService.getDonneeById(1)).thenReturn(donnee);

        ResponseEntity<Donnee> response = donneeController.getDonneeById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(donnee, response.getBody());
        verify(donneeService, times(1)).getDonneeById(1);
    }

    @Test
    void testGetDonneeById_NotFound() {
        when(donneeService.getDonneeById(1)).thenReturn(null);

        ResponseEntity<Donnee> response = donneeController.getDonneeById(1);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testSaveDonnee() {
        Donnee donnee = new Donnee(null, "Vitesse", "m/s");
        Donnee savedDonnee = new Donnee(1, "Vitesse", "m/s");
        when(donneeService.saveDonnee(donnee)).thenReturn(savedDonnee);

        ResponseEntity<Donnee> response = donneeController.saveDonnee(donnee);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedDonnee, response.getBody());
        verify(donneeService, times(1)).saveDonnee(donnee);
    }

    @Test
    void testSaveDonnee_Failure() {
        Donnee donnee = new Donnee(null, "Vitesse", "m/s");
        when(donneeService.saveDonnee(donnee)).thenReturn(null);

        ResponseEntity<Donnee> response = donneeController.saveDonnee(donnee);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testUpdateDonnee() {
        Donnee donnee = new Donnee(1, "Vitesse", "m/s");
        when(donneeService.updateDonnee(donnee)).thenReturn(donnee);

        ResponseEntity<Donnee> response = donneeController.updateDonnee(donnee);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(donnee, response.getBody());
        verify(donneeService, times(1)).updateDonnee(donnee);
    }

    @Test
    void testUpdateDonnee_Failure() {
        Donnee donnee = new Donnee(1, "Vitesse", "m/s");
        when(donneeService.updateDonnee(donnee)).thenReturn(null);

        ResponseEntity<Donnee> response = donneeController.updateDonnee(donnee);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testDeleteDonneeById() {
        Donnee donnee = new Donnee(1, "Pression", "Pa");
        when(donneeService.getDonneeById(1)).thenReturn(donnee);
        doNothing().when(donneeService).deleteDonneeById(1);

        ResponseEntity<String> response = donneeController.deleteDonneeById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Donnée supprimée avec succès", response.getBody());
        verify(donneeService, times(1)).deleteDonneeById(1);
    }

    @Test
    void testDeleteDonneeById_NotFound() {
        when(donneeService.getDonneeById(1)).thenReturn(null);

        ResponseEntity<String> response = donneeController.deleteDonneeById(1);

        assertEquals(400, response.getStatusCodeValue());
        verify(donneeService, times(0)).deleteDonneeById(1);
    }
}
