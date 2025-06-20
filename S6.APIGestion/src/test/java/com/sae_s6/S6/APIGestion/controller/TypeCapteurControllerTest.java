package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.TypeCapteur;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de test d'intégration pour le contrôleur TypeCapteurController.
 * Utilise TestRestTemplate pour effectuer de vrais appels HTTP sur un serveur démarré aléatoirement.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TypeCapteurControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // Liste pour garder trace des types créés pendant les tests (pour nettoyage)
    private final List<Integer> createdTypeCapteurs = new ArrayList<>();

    /**
     * Retourne l'URL de base pour les appels à l'API TypeCapteur.
     * @return URL complète de l'API TypeCapteur
     */
    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/typeCapteur/";
    }

    /**
     * Méthode utilitaire pour construire un objet TypeCapteur avec des valeurs par défaut.
     * @param libelle le libellé du type de capteur
     * @return un objet TypeCapteur prêt à être utilisé dans les tests
     */
    private TypeCapteur buildTypeCapteur(String libelle) {
        TypeCapteur type = new TypeCapteur();
        type.setLibelleTypeCapteur(libelle);
        type.setModeTypeCapteur("Auto");
        return type;
    }

    /**
     * Teste la création d'un type de capteur puis sa récupération par ID via l'API.
     */
    @Test
    void testCreateAndGetTypeCapteurById() {
        TypeCapteur type = buildTypeCapteur("Test Type");

        // POST : création du type de capteur
        ResponseEntity<TypeCapteur> postResponse = restTemplate.postForEntity(getBaseUrl(), type, TypeCapteur.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        TypeCapteur created = postResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        createdTypeCapteurs.add(created.getId());

        // GET : récupération du type de capteur par son ID
        ResponseEntity<TypeCapteur> getResponse = restTemplate.getForEntity(getBaseUrl() + created.getId(), TypeCapteur.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        TypeCapteur fetched = getResponse.getBody();
        assertThat(fetched).isNotNull();
        assertThat(fetched.getLibelleTypeCapteur()).isEqualTo("Test Type");
        assertThat(fetched.getModeTypeCapteur()).isEqualTo("Auto");
    }

    /**
     * Teste la récupération de tous les types de capteurs via l'API.
     */
    @Test
    void testGetAllTypeCapteurs() {
        ResponseEntity<TypeCapteur[]> response = restTemplate.getForEntity(getBaseUrl(), TypeCapteur[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TypeCapteur[] list = response.getBody();
        assertThat(list).isNotNull();
        assertThat(list.length).isGreaterThan(0);
    }

    /**
     * Teste la mise à jour d'un type de capteur via l'API.
     */
    @Test
    void testUpdateTypeCapteur() {
        TypeCapteur type = buildTypeCapteur("Initial Type");
        ResponseEntity<TypeCapteur> post = restTemplate.postForEntity(getBaseUrl(), type, TypeCapteur.class);
        assertThat(post.getStatusCode()).isEqualTo(HttpStatus.OK);
        TypeCapteur created = post.getBody();
        assertThat(created).isNotNull();
        createdTypeCapteurs.add(created.getId());

        // Modification des champs
        created.setLibelleTypeCapteur("Type Modifié");
        created.setModeTypeCapteur("Manuel");

        // PUT : mise à jour du type de capteur
        HttpEntity<TypeCapteur> entity = new HttpEntity<>(created);
        ResponseEntity<TypeCapteur> updatedResponse = restTemplate.exchange(getBaseUrl(), HttpMethod.PUT, entity, TypeCapteur.class);
        assertThat(updatedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        TypeCapteur updated = updatedResponse.getBody();
        assertThat(updated.getLibelleTypeCapteur()).isEqualTo("Type Modifié");
        assertThat(updated.getModeTypeCapteur()).isEqualTo("Manuel");
    }

    /**
     * Teste la suppression d'un type de capteur via l'API.
     */
    @Test
    void testDeleteTypeCapteur() {
        TypeCapteur type = buildTypeCapteur("À supprimer");
        ResponseEntity<TypeCapteur> post = restTemplate.postForEntity(getBaseUrl(), type, TypeCapteur.class);
        assertThat(post.getStatusCode()).isEqualTo(HttpStatus.OK);
        TypeCapteur created = post.getBody();
        assertThat(created).isNotNull();

        // DELETE : suppression du type de capteur
        restTemplate.delete(getBaseUrl() + created.getId());

        // GET après suppression
        ResponseEntity<TypeCapteur> getAfterDelete = restTemplate.getForEntity(getBaseUrl() + created.getId(), TypeCapteur.class);
        assertThat(getAfterDelete.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * Nettoyage après chaque test : supprime tous les types créés pendant les tests.
     */
    @AfterEach
    void cleanup() {
        for (Integer id : createdTypeCapteurs) {
            try {
                restTemplate.delete(getBaseUrl() + id);
            } catch (Exception ignored) {}
        }
        createdTypeCapteurs.clear();
    }
}