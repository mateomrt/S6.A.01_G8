package com.sae_s6.S6.APIGestion.controller.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
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
 * Classe de test d'intégration pour le contrôleur CapteurController.
 * Utilise MockMvc pour simuler les appels HTTP sur l'API Capteur.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CapteurControllerMockTest {

    // Injection de MockMvc pour simuler les appels HTTP
    @Autowired
    private MockMvc mockMvc;

    // Injection d'ObjectMapper pour la conversion JSON
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Méthode utilitaire pour construire un objet Capteur avec des valeurs par défaut.
     * @param libelle le libellé du capteur
     * @return un objet Capteur prêt à être utilisé dans les tests
     */
    private Capteur buildCapteur(String libelle) {
        // Création d'un capteur avec des valeurs par défaut
        Capteur capteur = new Capteur();
        capteur.setLibelleCapteur(libelle);
        capteur.setPositionXCapteur(10.0);
        capteur.setPositionYCapteur(20.0);

        // Création des entités associées avec des IDs fictifs
        Mur mur = new Mur(); mur.setId(1);
        Salle salle = new Salle(); salle.setId(1);
        TypeCapteur type = new TypeCapteur(); type.setId(1);

        capteur.setMurNavigation(mur);
        capteur.setSalleNavigation(salle);
        capteur.setTypeCapteurNavigation(type);
        return capteur;
    }

    /**
     * Teste la création d'un capteur puis sa récupération par ID via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testPostCapteurAndGetById() throws Exception {
        // Arrange : création d'un capteur fictif
        Capteur capteur = buildCapteur("Capteur Test");

        // Act : POST pour créer le capteur
        MvcResult postResult = mockMvc.perform(post("/api/capteur/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(capteur)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libelle_capteur").value("Capteur Test"))
                .andReturn();

        // Conversion de la réponse en objet Capteur
        Capteur saved = objectMapper.readValue(postResult.getResponse().getContentAsString(), Capteur.class);

        // Act : GET pour récupérer le capteur par son ID
        mockMvc.perform(get("/api/capteur/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libelle_capteur").value("Capteur Test"))
                .andExpect(jsonPath("$.position_xcapteur").value(10.0))
                .andExpect(jsonPath("$.position_ycapteur").value(20.0))
                .andExpect(jsonPath("$.mur_navigation.id").value(1))
                .andExpect(jsonPath("$.salle_navigation.id").value(1))
                .andExpect(jsonPath("$.type_capteur_navigation.id").value(1));
    }

    /**
     * Teste la récupération de tous les capteurs via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testGetAllCapteurs() throws Exception {
        // Act : GET pour récupérer tous les capteurs
        mockMvc.perform(get("/api/capteur/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    /**
     * Teste la mise à jour d'un capteur via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testUpdateCapteur() throws Exception {
        // Arrange : création d'un capteur fictif
        Capteur capteur = buildCapteur("Capteur Initial");

        // Act : POST pour créer le capteur
        MvcResult post = mockMvc.perform(post("/api/capteur/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(capteur)))
                .andExpect(status().isOk())
                .andReturn();

        // Conversion de la réponse en objet Capteur
        Capteur created = objectMapper.readValue(post.getResponse().getContentAsString(), Capteur.class);

        // Modification des valeurs du capteur
        created.setLibelleCapteur("Capteur Modifié");
        created.setPositionXCapteur(42.0);

        // Act : PUT pour mettre à jour le capteur
        mockMvc.perform(put("/api/capteur/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libelle_capteur").value("Capteur Modifié"))
                .andExpect(jsonPath("$.position_xcapteur").value(42.0));
    }

    /**
     * Teste la suppression d'un capteur via l'API.
     * @throws Exception en cas d'erreur lors de l'appel HTTP simulé
     */
    @Test
    void testDeleteCapteur() throws Exception {
        // Arrange : création d'un capteur fictif
        Capteur capteur = buildCapteur("Capteur à Supprimer");

        // Act : POST pour créer le capteur
        MvcResult post = mockMvc.perform(post("/api/capteur/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(capteur)))
                .andExpect(status().isOk())
                .andReturn();

        // Conversion de la réponse en objet Capteur
        Capteur created = objectMapper.readValue(post.getResponse().getContentAsString(), Capteur.class);

        // Act : DELETE pour supprimer le capteur
        mockMvc.perform(delete("/api/capteur/" + created.getId()))
                .andExpect(status().isOk());

        // Assert : vérification que le capteur n'existe plus
        mockMvc.perform(get("/api/capteur/" + created.getId()))
                .andExpect(status().isBadRequest());
    }
}