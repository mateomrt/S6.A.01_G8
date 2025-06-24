package com.sae_s6.S6.APIGestion.controller.mock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.service.TypeSalleService;

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
 * Classe de test d'intégration pour le contrôleur TypeSalleController.
 * Utilise MockMvc et Mockito pour simuler les appels HTTP et le service TypeSalleService.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TypeSalleControllerMockTest {

    // Injection de MockMvc pour simuler les appels HTTP
    @Autowired
    private MockMvc mockMvc;

    // Injection du service TypeSalleService simulé avec Mockito
    @MockitoBean
    private TypeSalleService typeSalleService;

    // Injection d'ObjectMapper pour la conversion JSON
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Teste la récupération de tous les types de salle via l'API.
     * Simule un appel GET et vérifie que les données retournées correspondent aux données fictives.
     * 
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetAllTypeSalles() throws Exception {
        // Arrange : création de types fictifs
        TypeSalle typeSalle1 = new TypeSalle(1, "Amphithéâtre");
        TypeSalle typeSalle2 = new TypeSalle(2, "Laboratoire");
        List<TypeSalle> typeSalles = Arrays.asList(typeSalle1, typeSalle2);

        // Simulation du service
        Mockito.when(typeSalleService.getAllTypeSalles())
                .thenReturn(typeSalles);

        // Act : appel GET simulé
        MvcResult result = mockMvc.perform(get("/api/typesalle/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        List<TypeSalle> resultat = objectMapper.readValue(json, new TypeReference<List<TypeSalle>>() {});
        assertThat(resultat).isNotEmpty();
        assertThat(resultat.get(0).getLibelleTypeSalle()).isEqualTo("Amphithéâtre");
        assertThat(resultat.get(1).getLibelleTypeSalle()).isEqualTo("Laboratoire");
    }

    /**
     * Teste la récupération d'un type de salle par son identifiant via l'API.
     * Simule un appel GET avec un identifiant et vérifie que le type retourné correspond.
     * 
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetTypeSalleById() throws Exception {
        // Arrange : préparation d'un type fictif
        int typeSalleId = 1;
        TypeSalle typeSalle = new TypeSalle(typeSalleId, "Amphithéâtre");

        // Simulation du service
        Mockito.when(typeSalleService.getTypeSalleById(typeSalleId))
                .thenReturn(typeSalle);

        // Act : appel GET simulé
        MvcResult result = mockMvc.perform(get("/api/typesalle/" + typeSalleId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        TypeSalle resultat = objectMapper.readValue(json, TypeSalle.class);
        assertThat(resultat.getId()).isEqualTo(typeSalleId);
        assertThat(resultat.getLibelleTypeSalle()).isEqualTo("Amphithéâtre");
    }

    /**
     * Teste la création d'un type de salle via l'API.
     * Simule un appel POST avec un type et vérifie que le type créé correspond.
     * 
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testCreateTypeSalle() throws Exception {
        // Arrange : préparation d'un nouveau type
        TypeSalle newTypeSalle = new TypeSalle(10, "Salle de Réunion");

        // Simulation du service
        Mockito.when(typeSalleService.saveTypeSalle(Mockito.any()))
                .thenReturn(newTypeSalle);

        // Act : appel POST simulé
        MvcResult result = mockMvc.perform(post("/api/typesalle/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTypeSalle)))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        TypeSalle createdTypeSalle = objectMapper.readValue(json, TypeSalle.class);
        assertThat(createdTypeSalle.getId()).isEqualTo(10);
        assertThat(createdTypeSalle.getLibelleTypeSalle()).isEqualTo("Salle de Réunion");
    }

    /**
     * Teste la mise à jour d'un type de salle via l'API.
     * Simule un appel PUT avec un type modifié et vérifie que les données retournées sont correctes.
     * 
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testUpdateTypeSalle() throws Exception {
        // Arrange : préparation d'un type modifié
        TypeSalle updatedTypeSalle = new TypeSalle(1, "Salle Informatique");

        // Simulation du service
        Mockito.when(typeSalleService.updateTypeSalle(Mockito.any()))
                .thenReturn(updatedTypeSalle);

        // Act : appel PUT simulé
        MvcResult result = mockMvc.perform(put("/api/typesalle/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTypeSalle)))
                .andReturn();

        // Assert : vérification du résultat et du code HTTP
        String json = result.getResponse().getContentAsString();
        TypeSalle typeSalle = objectMapper.readValue(json, TypeSalle.class);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(typeSalle.getId()).isEqualTo(1);
        assertThat(typeSalle.getLibelleTypeSalle()).isEqualTo("Salle Informatique");
    }

    /**
     * Teste la suppression d'un type de salle via l'API.
     * Simule un appel DELETE avec un identifiant et vérifie que le code HTTP retourné est correct.
     * 
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testDeleteTypeSalle() throws Exception {
        // Arrange : préparation d'un type à supprimer
        int typeSalleId = 1;
        TypeSalle typeSalle = new TypeSalle(typeSalleId, "Amphithéâtre");

        // Simulation du service pour la récupération et la suppression
        Mockito.when(typeSalleService.getTypeSalleById(typeSalleId))
                .thenReturn(typeSalle);

        Mockito.doNothing().when(typeSalleService).deleteTypeSalleById(typeSalleId);

        // Act : appel DELETE simulé
        MvcResult result = mockMvc.perform(delete("/api/typesalle/" + typeSalleId)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du code HTTP de succès
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}