package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BatimentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/typesalle";
    }

    // Méthode utilitaire pour créer un Batiment
    private Batiment createBatiment(Integer id, String libelle) {
        Batiment batiment = new Batiment();
        batiment.setId(id);
        batiment.setTitre(libelle);
        batiment.setSalles(null); // Salles peut être null pour les tests

        ResponseEntity<Batiment> response = restTemplate.postForEntity(getBaseUrl() + "/", batiment, Batiment.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    @Test
    void testGetAllBatiments() {
        createBatiment(100, "Type A"); // Crée un type pour s'assurer qu'on a au moins un en base

        ResponseEntity<Batiment[]> response = restTemplate.getForEntity(getBaseUrl() + "/", Batiment[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }

    @Test
    void testGetBatimentById() {
        Batiment batiment = createBatiment(101, "Type B");
        Integer id = batiment.getId();

        ResponseEntity<Batiment> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Batiment.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getTitre()).isEqualTo("Type B");
        assertThat(response.getBody().getSalles()).isNull(); // Vérifie que les salles sont nulles
    }

    @Test
    void testSaveBatiment() {
        Batiment batiment = createBatiment(102, "Type C");
        assertThat(batiment.getTitre()).isEqualTo("Type C");
        assertThat(batiment.getSalles()).isNull(); // Vérifie que les salles sont nulles
        assertThat(batiment.getId()).isNotNull(); // Vérifie que l'ID a été généré
        assertThat(batiment.getId()).isGreaterThan(0); // Vérifie que l'ID est positif
    }

    @Test
    void testUpdateBatiment() {
        Batiment batiment = createBatiment(103, "Type D");
        batiment.setTitre("Type D - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Batiment> entity = new HttpEntity<>(batiment, headers);

        ResponseEntity<Batiment> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, Batiment.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitre()).isEqualTo("Type D - MAJ");
        assertThat(response.getBody().getSalles()).isNull(); // Vérifie que les salles sont nulles
        assertThat(response.getBody().getId()).isEqualTo(batiment.getId()); // Vérifie que l'ID est inchangé
        assertThat(response.getBody().getId()).isGreaterThan(0); // Vérifie que l'ID est positif
    }

    @Test
    void testDeleteBatimentById() {
        Batiment batiment = createBatiment(104, "Type à Supprimer");
        Integer id = batiment.getId();

        restTemplate.delete(getBaseUrl() + "/" + id);

        ResponseEntity<Batiment> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Batiment.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
