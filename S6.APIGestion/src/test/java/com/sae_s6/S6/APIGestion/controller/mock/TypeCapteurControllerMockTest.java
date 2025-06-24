package com.sae_s6.S6.APIGestion.controller.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe de test d'intégration pour le contrôleur TypeCapteurController.
 * Utilise MockMvc pour simuler les appels HTTP sur l'API TypeCapteur.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TypeCapteurControllerMockTest {

    // Injection de MockMvc pour simuler les appels HTTP
    @Autowired
    private MockMvc mockMvc;

    // Injection d'ObjectMapper pour la conversion JSON
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Méthode utilitaire pour construire un objet TypeCapteur avec des valeurs par défaut.
     * @param libelle le libellé du type de capteur
     * @return un objet TypeCapteur prêt à être utilisé dans les tests
     */
    private TypeCapteur buildTypeCapteur(String libelle) {
        TypeCapteur type = new TypeCapteur();
        type.setLibelleTypeCapteur(libelle);
        type.setModeTypeCapteur("Automatique");
        return type;
    }

    /**
     * Teste la création d'un type de capteur puis sa récupération par ID via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testPostTypeCapteurAndGetById() throws Exception {
        // Arrange : création d'un type de capteur fictif
        TypeCapteur type = buildTypeCapteur("MockCapteur");

        // Act : POST pour créer le type de capteur
        MvcResult postResult = mockMvc.perform(post("/api/typeCapteur/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(type)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libelle_type_capteur").value("MockCapteur"))
                .andReturn();

        // Conversion de la réponse en objet TypeCapteur
        TypeCapteur saved = objectMapper.readValue(postResult.getResponse().getContentAsString(), TypeCapteur.class);

        // Act : GET pour récupérer le type de capteur par son ID
        mockMvc.perform(get("/api/typeCapteur/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libelle_type_capteur").value("MockCapteur"))
                .andExpect(jsonPath("$.mode_type_capteur").value("Automatique"));
    }

    /**
     * Teste la récupération de tous les types de capteurs via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetAllTypeCapteurs() throws Exception {
        // Act : GET pour récupérer tous les types de capteurs
        mockMvc.perform(get("/api/typeCapteur/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    /**
     * Teste la mise à jour d'un type de capteur via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testUpdateTypeCapteur() throws Exception {
        // Arrange : création d'un type de capteur fictif
        TypeCapteur type = buildTypeCapteur("Type Initial");

        // Act : POST pour créer le type de capteur
        MvcResult post = mockMvc.perform(post("/api/typeCapteur/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(type)))
                .andExpect(status().isOk())
                .andReturn();

        // Conversion de la réponse en objet TypeCapteur
        TypeCapteur created = objectMapper.readValue(post.getResponse().getContentAsString(), TypeCapteur.class);

        // Modification des valeurs du type de capteur
        created.setLibelleTypeCapteur("Type Modifié");
        created.setModeTypeCapteur("Manuel");

        // Act : PUT pour mettre à jour le type de capteur
        mockMvc.perform(put("/api/typeCapteur/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libelle_type_capteur").value("Type Modifié"))
                .andExpect(jsonPath("$.mode_type_capteur").value("Manuel"));
    }

    /**
     * Teste la suppression d'un type de capteur via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testDeleteTypeCapteur() throws Exception {
        // Arrange : création d'un type de capteur fictif
        TypeCapteur type = buildTypeCapteur("À Supprimer");

        // Act : POST pour créer le type de capteur
        MvcResult post = mockMvc.perform(post("/api/typeCapteur/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(type)))
                .andExpect(status().isOk())
                .andReturn();

        // Conversion de la réponse en objet TypeCapteur
        TypeCapteur created = objectMapper.readValue(post.getResponse().getContentAsString(), TypeCapteur.class);

        // Act : DELETE pour supprimer le type de capteur
        mockMvc.perform(delete("/api/typeCapteur/" + created.getId()))
                .andExpect(status().isOk());

        // Assert : vérification que le type de capteur n'existe plus
        mockMvc.perform(get("/api/typeCapteur/" + created.getId()))
                .andExpect(status().isBadRequest());
    }
}