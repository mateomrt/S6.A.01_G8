package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.Salle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalleControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/salle";
    }


    private Salle createSalle(String libelleSalle, double superficie) {
        Salle salle = new Salle();
        salle.setId(null); // Laisser l'auto-génération
        salle.setLibelleSalle(libelleSalle);
        salle.setSuperficie(superficie);
        salle.setBatimentNavigation(null); // Plus nécessaire
        salle.setTypeSalleNavigation(null); // Plus nécessaire

        ResponseEntity<Salle> response = restTemplate.postForEntity(getBaseUrl() + "/", salle, Salle.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    @Test
    void testGetAllSalles() {
        createSalle("Salle A", 30.0);

        ResponseEntity<Salle[]> response = restTemplate.getForEntity(getBaseUrl() + "/", Salle[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }
  

    @Test
    void testGetSalleById() {
        Salle salle = createSalle("Salle B", 40.0);
        Integer id = salle.getId();

        ResponseEntity<Salle> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Salle.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
    }

    @Test
    void testSaveSalle() {
        Salle salle = createSalle("Salle C", 25.0);
        assertThat(salle.getLibelleSalle()).isEqualTo("Salle C");
    }

    @Test
    void testUpdateSalle() {
        Salle salle = createSalle("Salle D", 35.0);
        salle.setLibelleSalle("Salle D - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Salle> entity = new HttpEntity<>(salle, headers);

        ResponseEntity<Salle> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, Salle.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleSalle()).isEqualTo("Salle D - MAJ");
    }

    @Test
    void testDeleteSalleById() {
        Salle salle = createSalle("Salle à Supprimer", 50.0);
        Integer id = salle.getId();

        restTemplate.delete(getBaseUrl() + "/" + id);

        ResponseEntity<Salle> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Salle.class);
        // Ton contrôleur doit retourner NOT_FOUND ou BAD_REQUEST après suppression
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }
}
