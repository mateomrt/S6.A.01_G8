package com.sae_s6.S6.APIGestion.controller.mock;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.service.BatimentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.List;

/**
 * Classe de test pour le contrôleur BatimentController.
 * Utilise MockMvc et Mockito pour simuler les appels HTTP et le service BatimentService.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BatimentControllerMockTest {

    // Injection de MockMvc pour simuler les appels HTTP
    @Autowired
    private MockMvc mockMvc;

    // Injection de BatimentService simulé avec Mockito
    @MockitoBean
    private BatimentService batimentService;

    // Injection d'ObjectMapper pour la conversion JSON
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Teste la récupération de tous les bâtiments via l'API.
     * Simule un appel GET et vérifie que les données retournées correspondent aux données fictives.
     * 
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetAllBatiments() throws Exception {
        // Arrange : création de données fictives
        Batiment batiment1 = new Batiment(1, "Bâtiment A");
        Batiment batiment2 = new Batiment(2, "Bâtiment B");
        List<Batiment> batiments = Arrays.asList(batiment1, batiment2);

        // Simulation du service
        Mockito.when(batimentService.getAllBatiments())
                    .thenReturn(batiments);

        // Act : appel GET simulé
        MvcResult result = mockMvc.perform(get("/api/batiment/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        List<Batiment> resultat = objectMapper.readValue(json, new TypeReference<List<Batiment>>() {});
        assertThat(resultat).isNotEmpty();
        assertThat(resultat.get(0).getLibelleBatiment()).isEqualTo("Bâtiment A");
        assertThat(resultat.get(1).getLibelleBatiment()).isEqualTo("Bâtiment B");
    }

    /**
     * Teste la récupération d'un bâtiment par son identifiant via l'API.
     * Simule un appel GET avec un identifiant et vérifie que le bâtiment retourné correspond.
     * 
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetBatimentById() throws Exception {
        // Arrange : préparation d'un bâtiment fictif
        int batimentId = 1;
        Batiment batiment = new Batiment(batimentId, "Bâtiment A");

        // Simulation du service
        Mockito.when(batimentService.getBatimentById(batimentId))
                    .thenReturn(batiment);

        // Act : appel GET simulé
        MvcResult result = mockMvc.perform(get("/api/batiment/" + batimentId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        Batiment resultat = objectMapper.readValue(json, Batiment.class);
        assertThat(resultat.getId()).isEqualTo(batimentId);
        assertThat(resultat.getLibelleBatiment()).isEqualTo("Bâtiment A"); // Vérifie le libellé attendu
    }    

    /**
     * Teste la création d'un bâtiment via l'API.
     * Simule un appel POST avec un bâtiment et vérifie que le bâtiment créé correspond.
     * 
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testCreateBatiment() throws Exception {
        // Arrange : préparation d'un nouveau bâtiment
        Batiment newBatiment = new Batiment(10, "Bâtiment D");

        // Simulation du service
        Mockito.when(batimentService.saveBatiment(Mockito.any()))
                    .thenReturn(newBatiment);
        
        // Act : appel POST simulé
        MvcResult result = mockMvc.perform(post("/api/batiment/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBatiment)))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        Batiment createdBatiment = objectMapper.readValue(json, Batiment.class);
        assertThat(createdBatiment.getId()).isEqualTo(10);
        assertThat(createdBatiment.getLibelleBatiment()).isEqualTo("Bâtiment D");
    }

    /**
     * Teste la mise à jour d'un bâtiment via l'API.
     * Simule un appel PUT avec un bâtiment mis à jour et vérifie que les données retournées sont correctes.
     * 
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testUpdateBatiment() throws Exception {
        // Arrange : préparation d'un bâtiment mis à jour
        Batiment updatedBatiment = new Batiment(1, "Bâtiment F");

        // Simulation du service
        Mockito.when(batimentService.updateBatiment(Mockito.any()))
                    .thenReturn(updatedBatiment);

        // Act : appel PUT simulé
        MvcResult result = mockMvc.perform(put("/api/batiment/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBatiment)))
                .andReturn();

        // Assert : vérification du résultat et du code HTTP
        String json = result.getResponse().getContentAsString();
        Batiment batiment = objectMapper.readValue(json, Batiment.class);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value()); 
        assertThat(batiment.getId()).isEqualTo(1);
        assertThat(batiment.getLibelleBatiment()).isEqualTo("Bâtiment F");
    }

    /**
     * Teste la suppression d'un bâtiment via l'API.
     * Simule un appel DELETE avec un identifiant et vérifie que le code HTTP retourné est correct.
     * 
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testDeleteBatiment() throws Exception {
        // Arrange : préparation d'un bâtiment à supprimer
        int batimentId = 1;
        Batiment batiment = new Batiment(batimentId, "Bâtiment A");

        // Simulation du service pour la récupération et la suppression
        Mockito.when(batimentService.getBatimentById(batimentId))
            .thenReturn(batiment);
        Mockito.doNothing().when(batimentService).deleteBatimentById(batimentId);

        // Act : appel DELETE simulé
        MvcResult result = mockMvc.perform(delete("/api/batiment/" + batimentId)
                .contentType(MediaType.APPLICATION_JSON))                
                .andReturn();

        // Assert : vérification du code HTTP de succès
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }
}