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

    @Autowired
    private MockMvc mockMvc;

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
        TypeCapteur type = buildTypeCapteur("MockCapteur");

        // POST : création du type de capteur
        MvcResult postResult = mockMvc.perform(post("/api/typeCapteur/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(type)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libelle_type_capteur").value("MockCapteur"))
                .andReturn();

        TypeCapteur saved = objectMapper.readValue(postResult.getResponse().getContentAsString(), TypeCapteur.class);

        // GET : récupération du type de capteur par son ID
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
        TypeCapteur type = buildTypeCapteur("Type Initial");

        // POST : création du type de capteur
        MvcResult post = mockMvc.perform(post("/api/typeCapteur/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(type)))
                .andExpect(status().isOk())
                .andReturn();

        TypeCapteur created = objectMapper.readValue(post.getResponse().getContentAsString(), TypeCapteur.class);
        created.setLibelleTypeCapteur("Type Modifié");
        created.setModeTypeCapteur("Manuel");

        // PUT : mise à jour du type de capteur
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
        TypeCapteur type = buildTypeCapteur("À Supprimer");

        // POST : création du type de capteur
        MvcResult post = mockMvc.perform(post("/api/typeCapteur/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(type)))
                .andExpect(status().isOk())
                .andReturn();

        TypeCapteur created = objectMapper.readValue(post.getResponse().getContentAsString(), TypeCapteur.class);

        // DELETE : suppression du type de capteur
        mockMvc.perform(delete("/api/typeCapteur/" + created.getId()))
                .andExpect(status().isOk());

        // Vérifie que l'élément n'existe plus
        mockMvc.perform(get("/api/typeCapteur/" + created.getId()))
                .andExpect(status().isBadRequest());
    }
}