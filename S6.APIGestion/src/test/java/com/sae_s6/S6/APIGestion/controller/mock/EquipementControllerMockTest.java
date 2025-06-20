package com.sae_s6.S6.APIGestion.controller.mock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.Equipement;
import com.sae_s6.S6.APIGestion.service.EquipementService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * Classe de test d'intégration pour le contrôleur EquipementController.
 * Utilise MockMvc et Mockito pour simuler les appels HTTP et le service EquipementService.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class EquipementControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EquipementService equipementService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Teste la récupération de tous les équipements via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetAllEquipements() throws Exception {
        // Arrange : création de données fictives
        Equipement equipement1 = new Equipement(1, "PC principal", 50.0, 30.0, 100.0, 200.0, null, null, null);
        Equipement equipement2 = new Equipement(2, "Vidéo projecteur", 20.0, 40.0, 250.0, 100.0, null, null, null);
        List<Equipement> equipements = Arrays.asList(equipement1, equipement2);

        // Simulation du service
        Mockito.when(equipementService.getAllEquipements())
                .thenReturn(equipements);

        // Act : appel GET simulé
        MvcResult result = mockMvc.perform(get("/api/equipement/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        List<Equipement> resultat = objectMapper.readValue(json, new TypeReference<List<Equipement>>() {});
        assertThat(resultat).isNotEmpty();
        assertThat(resultat.get(0).getLibelleEquipement()).isEqualTo("PC principal");
        assertThat(resultat.get(1).getLibelleEquipement()).isEqualTo("Vidéo projecteur");
    }

    /**
     * Teste la récupération d'un équipement par son identifiant via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetEquipementById() throws Exception {
        // Arrange : préparation d'un équipement fictif
        int equipementId = 1;
        Equipement equipement = new Equipement(1, "PC principal", 50.0, 30.0, 100.0, 200.0, null, null, null);

        // Simulation du service
        Mockito.when(equipementService.getEquipementById(equipementId))
                .thenReturn(equipement);

        // Act : appel GET simulé
        MvcResult result = mockMvc.perform(get("/api/equipement/" + equipementId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        Equipement resultat = objectMapper.readValue(json, Equipement.class);
        assertThat(resultat.getId()).isEqualTo(equipementId);
        assertThat(resultat.getLibelleEquipement()).isEqualTo("PC principal");
    }

    /**
     * Teste la création d'un équipement via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testCreateEquipement() throws Exception {
        // Arrange : préparation d'un nouvel équipement
        Equipement newEquipement = new Equipement(3, "PC secondaire", 30.0, 30.0, 100.0, 200.0, null, null, null);

        // Simulation du service
        Mockito.when(equipementService.saveEquipement(Mockito.any()))
                .thenReturn(newEquipement);

        // Act : appel POST simulé
        MvcResult result = mockMvc.perform(post("/api/equipement/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEquipement)))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        Equipement createdEquipement = objectMapper.readValue(json, Equipement.class);
        assertThat(createdEquipement.getId()).isEqualTo(3);
        assertThat(createdEquipement.getLibelleEquipement()).isEqualTo("PC secondaire");
    }

    /**
     * Teste la mise à jour d'un équipement via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testUpdateEquipement() throws Exception {
        // Arrange : préparation d'un équipement mis à jour
        Equipement updatedEquipement = new Equipement(1, "PC principal - update", 50.0, 30.0, 100.0, 200.0, null, null, null);

        // Simulation du service
        Mockito.when(equipementService.updateEquipement(Mockito.any()))
                .thenReturn(updatedEquipement);

        // Act : appel PUT simulé
        MvcResult result = mockMvc.perform(put("/api/equipement/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEquipement)))
                .andReturn();

        // Assert : vérification du résultat et du code HTTP
        String json = result.getResponse().getContentAsString();
        Equipement equipement = objectMapper.readValue(json, Equipement.class);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(equipement.getId()).isEqualTo(1);
        assertThat(equipement.getLibelleEquipement()).isEqualTo("PC principal - update");
    }

    /**
     * Teste la suppression d'un équipement via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testDeleteEquipement() throws Exception {
        // Arrange : préparation d'un équipement à supprimer
        int equipementId = 1;
        Equipement equipement = new Equipement(1, "PC principal", 50.0, 30.0, 100.0, 200.0, null, null, null);

        // Simulation du service pour la récupération et la suppression
        Mockito.when(equipementService.getEquipementById(equipementId))
                .thenReturn(equipement);

        Mockito.doNothing().when(equipementService).deleteEquipementById(equipementId);

        // Act : appel DELETE simulé
        MvcResult result = mockMvc.perform(delete("/api/equipement/" + equipementId)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du code HTTP de succès
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}