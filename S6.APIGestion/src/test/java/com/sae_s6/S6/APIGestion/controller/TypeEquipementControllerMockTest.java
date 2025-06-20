package com.sae_s6.S6.APIGestion.controller;

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
import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.service.TypeEquipementService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.List;

/**
 * Classe de test d'intégration pour le contrôleur TypeEquipementController.
 * Utilise MockMvc et Mockito pour simuler les appels HTTP et le service TypeEquipementService.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TypeEquipementControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TypeEquipementService typeEquipementService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Teste la récupération de tous les types d'équipement via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetAllTypeEquipements() throws Exception {
        // Arrange : création de types fictifs
        TypeEquipement typeEquipement1 = new TypeEquipement(1, "Ordinateur");
        TypeEquipement typeEquipement2 = new TypeEquipement(2, "Projecteur");
        List<TypeEquipement> typeEquipements = Arrays.asList(typeEquipement1, typeEquipement2);

        // Simulation du service
        Mockito.when(typeEquipementService.getAllTypeEquipements())
                    .thenReturn(typeEquipements);

        // Act : appel GET simulé
        MvcResult result = mockMvc.perform(get("/api/typeequipement/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        List<TypeEquipement> resultat = objectMapper.readValue(json, new TypeReference<List<TypeEquipement>>() {});
        assertThat(resultat).isNotEmpty();
        assertThat(resultat.get(0).getLibelleTypeEquipement()).isEqualTo("Ordinateur");
        assertThat(resultat.get(1).getLibelleTypeEquipement()).isEqualTo("Projecteur");
    }

    /**
     * Teste la récupération d'un type d'équipement par son identifiant via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetTypeEquipementById() throws Exception {
        // Arrange : préparation d'un type fictif
        int typeEquipementId = 1;
        TypeEquipement typeEquipement = new TypeEquipement(typeEquipementId, "Ordinateur");

        // Simulation du service
        Mockito.when(typeEquipementService.getTypeEquipementById(typeEquipementId))
                    .thenReturn(typeEquipement);

        // Act : appel GET simulé
        MvcResult result = mockMvc.perform(get("/api/typeequipement/" + typeEquipementId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        TypeEquipement resultat = objectMapper.readValue(json, TypeEquipement.class);
        assertThat(resultat.getId()).isEqualTo(typeEquipementId);
        assertThat(resultat.getLibelleTypeEquipement()).isEqualTo("Ordinateur");
    }

    /**
     * Teste la création d'un type d'équipement via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testCreateTypeEquipement() throws Exception {
        // Arrange : préparation d'un nouveau type
        TypeEquipement newTypeEquipement = new TypeEquipement(10, "Cable");

        // Simulation du service
        Mockito.when(typeEquipementService.saveTypeEquipement(Mockito.any()))
                    .thenReturn(newTypeEquipement);
                            
        // Act : appel POST simulé
        MvcResult result = mockMvc.perform(post("/api/typeequipement/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTypeEquipement)))
                .andReturn();

        // Assert : vérification du résultat
        String json = result.getResponse().getContentAsString();
        TypeEquipement createdTypeEquipement = objectMapper.readValue(json, TypeEquipement.class);
        assertThat(createdTypeEquipement.getId()).isEqualTo(10);
        assertThat(createdTypeEquipement.getLibelleTypeEquipement()).isEqualTo("Cable");
    }

    /**
     * Teste la mise à jour d'un type d'équipement via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testUpdateTypeEquipement() throws Exception {
        // Arrange : préparation d'un type modifié
        TypeEquipement updatedTypeEquipement = new TypeEquipement(1, "PC");

        // Simulation du service
        Mockito.when(typeEquipementService.updateTypeEquipement(Mockito.any()))
                    .thenReturn(updatedTypeEquipement);

        // Act : appel PUT simulé
        MvcResult result = mockMvc.perform(put("/api/typeequipement/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTypeEquipement)))
                .andReturn();

        // Assert : vérification du résultat et du code HTTP
        String json = result.getResponse().getContentAsString();
        TypeEquipement typeEquipement = objectMapper.readValue(json, TypeEquipement.class);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value()); 
        assertThat(typeEquipement.getId()).isEqualTo(1);
        assertThat(typeEquipement.getLibelleTypeEquipement()).isEqualTo("PC");
    }

    /**
     * Teste la suppression d'un type d'équipement via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testDeleteTypeEquipement() throws Exception {
        // Arrange : préparation d'un type à supprimer
        int typeEquipementId = 1;
        TypeEquipement typeEquipement = new TypeEquipement(typeEquipementId, "Ordinateur");

        // Simulation du service pour la récupération et la suppression
        Mockito.when(typeEquipementService.getTypeEquipementById(typeEquipementId))
            .thenReturn(typeEquipement);
        
        Mockito.doNothing().when(typeEquipementService).deleteTypeEquipementById(typeEquipementId);

        // Act : appel DELETE simulé
        MvcResult result = mockMvc.perform(delete("/api/typeequipement/" + typeEquipementId)
                .contentType(MediaType.APPLICATION_JSON))                
                .andReturn();

        // Assert : vérification du code HTTP de succès
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }
}