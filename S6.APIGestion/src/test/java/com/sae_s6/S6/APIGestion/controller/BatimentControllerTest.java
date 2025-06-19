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
        return "http://localhost:" + port + "/api/batiment";
    }

    // Méthode utilitaire pour créer un Batiment
    private Batiment createBatiment(Integer id, String libelle) {
        Batiment batiment = new Batiment();
        batiment.setId(id);
        batiment.setLibelleBatiment(libelle);

        ResponseEntity<Batiment> response = restTemplate.postForEntity(getBaseUrl() + "/", batiment, Batiment.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    private int createdBatimentId;

    @Test
    void testGetAllBatiments() {

        ResponseEntity<Batiment[]> response = restTemplate.getForEntity(getBaseUrl() + "/", Batiment[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }

    @Test
    void testGetBatimentById() {
        Integer id = 1;

        ResponseEntity<Batiment> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Batiment.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getLibelleBatiment()).isEqualTo("Bâtiment A");
    }

    @Test
    void testSaveBatiment() {
        Batiment batiment = createBatiment(null, "Bâtiment C");
        createdBatimentId = batiment.getId();

        assertThat(batiment.getLibelleBatiment()).isEqualTo("Bâtiment C");
        assertThat(batiment.getId()).isNotNull(); // Vérifie que l'ID a été généré
        assertThat(batiment.getId()).isGreaterThan(0); // Vérifie que l'ID est positif

        restTemplate.delete(getBaseUrl() + "/" + createdBatimentId);
    }

    @Test
    void testUpdateBatiment() {
        Batiment batiment = createBatiment(null, "Bâtiment D");
        createdBatimentId = batiment.getId();
        
        batiment.setLibelleBatiment("Bâtiment D - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Batiment> entity = new HttpEntity<>(batiment, headers);

        ResponseEntity<Batiment> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, Batiment.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleBatiment()).isEqualTo("Type D - MAJ");
        assertThat(response.getBody().getId()).isEqualTo(batiment.getId()); // Vérifie que l'ID est inchangé
        assertThat(response.getBody().getId()).isGreaterThan(0); // Vérifie que l'ID est positif
       
        restTemplate.delete(getBaseUrl() + "/" + createdBatimentId);    
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
