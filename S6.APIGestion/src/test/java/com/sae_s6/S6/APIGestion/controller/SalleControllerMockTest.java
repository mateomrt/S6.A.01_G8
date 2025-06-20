package com.sae_s6.S6.APIGestion.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.service.SalleService;

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
 * Classe de test d'intégration pour le contrôleur SalleController.
 * Utilise MockMvc et Mockito pour simuler les appels HTTP et le service SalleService.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SalleControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SalleService salleService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Teste la récupération de toutes les salles via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetAllSalles() throws Exception {
        // Arrange : création de salles fictives
        Salle salle1 = new Salle(1, "Salle 101", 30.0, null, null);
        Salle salle2 = new Salle(2, "Salle 102", 25.0, null, null);
        List<Salle> salles = Arrays.asList(salle1, salle2);

        // Simulation du service
        Mockito.when(salleService.getAllSalles())
                .thenReturn(salles);

        // Act : appel GET simulé
        MvcResult result = mockMvc.perform(get("/api/salle/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        List<Salle> resultat = objectMapper.readValue(json, new TypeReference<List<Salle>>() {});
        assertThat(resultat).isNotEmpty();
        assertThat(resultat.get(0).getLibelleSalle()).isEqualTo("Salle 101");
        assertThat(resultat.get(1).getLibelleSalle()).isEqualTo("Salle 102");
    }

    /**
     * Teste la récupération d'une salle par son identifiant via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetSalleById() throws Exception {
        // Arrange : préparation d'une salle fictive
        int salleId = 1;
        Salle salle = new Salle(1, "Salle 101", 30.0, null, null);

        // Simulation du service
        Mockito.when(salleService.getSalleById(salleId))
                .thenReturn(salle);

        // Act : appel GET simulé
        MvcResult result = mockMvc.perform(get("/api/salle/" + salleId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        Salle resultat = objectMapper.readValue(json, Salle.class);
        assertThat(resultat.getId()).isEqualTo(salleId);
        assertThat(resultat.getLibelleSalle()).isEqualTo("Salle 101");
    }

    /**
     * Teste la création d'une salle via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testCreateSalle() throws Exception {
        // Arrange : préparation d'une nouvelle salle
        Salle newSalle = new Salle(10, "Salle 105", 40.0, null, null);

        // Simulation du service
        Mockito.when(salleService.saveSalle(Mockito.any()))
                .thenReturn(newSalle);

        // Act : appel POST simulé
        MvcResult result = mockMvc.perform(post("/api/salle/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newSalle)))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        Salle createdSalle = objectMapper.readValue(json, Salle.class);
        assertThat(createdSalle.getId()).isEqualTo(10);
        assertThat(createdSalle.getLibelleSalle()).isEqualTo("Salle 105");
    }

    /**
     * Teste la mise à jour d'une salle via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testUpdateSalle() throws Exception {
        // Arrange : préparation d'une salle modifiée
        Salle updatedSalle = new Salle(1, "Salle 101 Modifiée", 35.0, null, null);

        // Simulation du service
        Mockito.when(salleService.updateSalle(Mockito.any()))
                .thenReturn(updatedSalle);

        // Act : appel PUT simulé
        MvcResult result = mockMvc.perform(put("/api/salle/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSalle)))
                .andReturn();

        // Assert : vérification du résultat et du code HTTP
        String json = result.getResponse().getContentAsString();
        Salle salle = objectMapper.readValue(json, Salle.class);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(salle.getId()).isEqualTo(1);
        assertThat(salle.getLibelleSalle()).isEqualTo("Salle 101 Modifiée");
    }

    /**
     * Teste la suppression d'une salle via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testDeleteSalle() throws Exception {
        // Arrange : préparation d'une salle à supprimer
        int salleId = 1;
        Salle salle = new Salle(salleId, "Salle 101", 30.0, null, null);

        // Simulation du service pour la récupération et la suppression
        Mockito.when(salleService.getSalleById(salleId))
                .thenReturn(salle);

        Mockito.doNothing().when(salleService).deleteSalleById(salleId);

        // Act : appel DELETE simulé
        MvcResult result = mockMvc.perform(delete("/api/salle/" + salleId)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du code HTTP de succès
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    /**
     * Teste la recherche de salles par libellé via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetSallesByLibelleSalle() throws Exception {
        // Arrange : préparation d'une salle fictive pour la recherche
        String libelleSalle = "Salle 101";
        Salle salle = new Salle(1, libelleSalle, 30.0, null, null);
        List<Salle> salles = Arrays.asList(salle);

        // Simulation du service
        Mockito.when(salleService.getSallesByLibelleSalle(libelleSalle))
                .thenReturn(salles);

        // Act : appel GET simulé avec paramètre de recherche
        MvcResult result = mockMvc.perform(get("/api/salle/search")
                .param("libelleSalle", libelleSalle)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        List<Salle> resultat = objectMapper.readValue(json, new TypeReference<List<Salle>>() {});
        assertThat(resultat).isNotEmpty();
        assertThat(resultat.get(0).getLibelleSalle()).isEqualTo(libelleSalle);
    }
}