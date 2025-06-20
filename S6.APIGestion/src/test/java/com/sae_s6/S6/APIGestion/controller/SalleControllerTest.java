package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de test d'intégration pour le contrôleur SalleController.
 * Utilise TestRestTemplate pour effectuer de vrais appels HTTP sur un serveur démarré aléatoirement.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalleControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Retourne l'URL de base pour les appels à l'API Salle.
     * @return URL complète de l'API Salle
     */
    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/salle";
    }

    /**
     * Méthode utilitaire pour créer une salle via l'API.
     * @param libelleSalle libellé de la salle
     * @param superficie superficie de la salle
     * @return Salle créée
     */
    private Salle createSalle(String libelleSalle, double superficie) {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(1);

        Batiment batiment = new Batiment();
        batiment.setId(1);

        Salle salle = new Salle();
        salle.setId(null); // Laisser l'auto-génération
        salle.setLibelleSalle(libelleSalle);
        salle.setSuperficie(superficie);
        salle.setBatimentNavigation(batiment); // Plus nécessaire
        salle.setTypeSalleNavigation(typeSalle); // Plus nécessaire

        // Envoie une requête POST pour créer la salle
        ResponseEntity<Salle> response = restTemplate.postForEntity(getBaseUrl() + "/", salle, Salle.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    /**
     * Teste la récupération de toutes les salles via l'API.
     */
    @Test
    void testGetAllSalles() {
        createSalle("Salle A", 30.0);

        // Appel GET pour récupérer toutes les salles
        ResponseEntity<Salle[]> response = restTemplate.getForEntity(getBaseUrl() + "/", Salle[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }

    /**
     * Teste la récupération d'une salle par son identifiant via l'API.
     */
    @Test
    void testGetSalleById() {
        Salle salle = createSalle("Salle B", 40.0);
        Integer id = salle.getId();

        // Appel GET pour récupérer la salle par son ID
        ResponseEntity<Salle> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Salle.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
    }

    /**
     * Teste la création d'une salle via l'API.
     */
    @Test
    void testSaveSalle() {
        Salle salle = createSalle("Salle C", 25.0);
        assertThat(salle.getLibelleSalle()).isEqualTo("Salle C");
    }

    /**
     * Teste la mise à jour d'une salle via l'API.
     */
    @Test
    void testUpdateSalle() {
        Salle salle = createSalle("Salle D", 35.0);
        salle.setLibelleSalle("Salle D - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Salle> entity = new HttpEntity<>(salle, headers);

        // Appel PUT pour mettre à jour la salle
        ResponseEntity<Salle> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, Salle.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleSalle()).isEqualTo("Salle D - MAJ");
    }

    /**
     * Teste la suppression d'une salle via l'API.
     */
    @Test
    void testDeleteSalleById() {
        Salle salle = createSalle("Salle à Supprimer", 50.0);
        Integer id = salle.getId();

        // Appel DELETE pour supprimer la salle
        restTemplate.delete(getBaseUrl() + "/" + id);

        // Vérifie que la salle n'existe plus
        ResponseEntity<Salle> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Salle.class);
        // Ton contrôleur doit retourner NOT_FOUND ou BAD_REQUEST après suppression
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }
}