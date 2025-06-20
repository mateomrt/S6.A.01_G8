package com.sae_s6.S6.APIGestion.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Mur.Orientation;
import com.sae_s6.S6.APIGestion.service.MurService;

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
 * Classe de test d'intégration pour le contrôleur MurController.
 * Utilise MockMvc et Mockito pour simuler les appels HTTP et le service MurService.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class MurControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MurService murService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Teste la récupération de tous les murs via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetAllMurs() throws Exception {
        // Arrange : création de données fictives
        Mur mur1 = new Mur(1, "Mur nord", 250.0, 600.0, Orientation.N, null);
        Mur mur2 = new Mur(2, "Mur sud", 250.0, 600.0, Orientation.S, null);
        List<Mur> murs = Arrays.asList(mur1, mur2);

        // Simulation du service
        Mockito.when(murService.getAllMurs())
                .thenReturn(murs);

        // Act : appel GET simulé
        MvcResult result = mockMvc.perform(get("/api/murs/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        List<Mur> resultat = objectMapper.readValue(json, new TypeReference<List<Mur>>() {});
        assertThat(resultat).isNotEmpty();
        assertThat(resultat.get(0).getLibelleMur()).isEqualTo("Mur nord");
        assertThat(resultat.get(1).getLibelleMur()).isEqualTo("Mur sud");
    }

    /**
     * Teste la récupération d'un mur par son identifiant via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetMurById() throws Exception {
        // Arrange : préparation d'un mur fictif
        int murId = 1;
        Mur mur = new Mur(1, "Mur nord", 250.0, 600.0, Orientation.N, null);

        // Simulation du service
        Mockito.when(murService.getMurById(murId))
                .thenReturn(mur);

        // Act : appel GET simulé
        MvcResult result = mockMvc.perform(get("/api/murs/" + murId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        Mur resultat = objectMapper.readValue(json, Mur.class);
        assertThat(resultat.getId()).isEqualTo(murId);
        assertThat(resultat.getLibelleMur()).isEqualTo("Mur nord");
    }

    /**
     * Teste la création d'un mur via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testCreateMur() throws Exception {
        // Arrange : préparation d'un nouveau mur
        Mur newMur = new Mur(3, "Mur sud", 250.0, 600.0, Orientation.S, null);

        // Simulation du service
        Mockito.when(murService.saveMur(Mockito.any()))
                .thenReturn(newMur);

        // Act : appel POST simulé
        MvcResult result = mockMvc.perform(post("/api/murs/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMur)))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        Mur createdMur = objectMapper.readValue(json, Mur.class);
        assertThat(createdMur.getId()).isEqualTo(3);
        assertThat(createdMur.getLibelleMur()).isEqualTo("Mur sud");
    }

    /**
     * Teste la mise à jour d'un mur via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testUpdateMur() throws Exception {
        // Arrange : préparation d'un mur mis à jour
        Mur updatedMur = new Mur(1, "Mur nord - update", 250.0, 600.0, Orientation.N, null);

        // Simulation du service
        Mockito.when(murService.updateMur(Mockito.any()))
                .thenReturn(updatedMur);

        // Act : appel PUT simulé
        MvcResult result = mockMvc.perform(put("/api/murs/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedMur)))
                .andReturn();

        // Assert : vérification du résultat et du code HTTP
        String json = result.getResponse().getContentAsString();
        Mur mur = objectMapper.readValue(json, Mur.class);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(mur.getId()).isEqualTo(1);
        assertThat(mur.getLibelleMur()).isEqualTo("Mur nord - update");
    }

    /**
     * Teste la suppression d'un mur via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testDeleteMur() throws Exception {
        // Arrange : préparation d'un mur à supprimer
        int murId = 1;
        Mur mur = new Mur(1, "Mur nord", 250.0, 600.0, Orientation.N, null);

        // Simulation du service pour la récupération et la suppression
        Mockito.when(murService.getMurById(murId))
                .thenReturn(mur);

        Mockito.doNothing().when(murService).deleteMurById(murId);

        // Act : appel DELETE simulé
        MvcResult result = mockMvc.perform(delete("/api/murs/" + murId)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du code HTTP de succès
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}