package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.Mur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MurControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/murs";
    }

    // Méthode utilitaire pour créer un Mur
    private Mur createMur(Integer id, String libelle) {
        Mur mur = new Mur();
        mur.setId(id);
        mur.setLibelleMur(libelle);
        mur.setHauteur(300.0); // Hauteur par défaut pour les tests
        mur.setLongueur(500.0); // Longueur par défaut pour les tests  
        mur.setOrientation(Mur.Orientation.N); // Orientation par défaut pour les tests
        mur.setSalleNavigation(null); // Salle peut être null pour les tests

        ResponseEntity<Mur> response = restTemplate.postForEntity(getBaseUrl() + "/", mur, Mur.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    @Test
    void testGetAllMurs() {
        createMur(100, "Type A"); // Crée un type pour s'assurer qu'on a au moins un en base

        ResponseEntity<Mur[]> response = restTemplate.getForEntity(getBaseUrl() + "/", Mur[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }

    @Test
    void testGetMurById() {
        Mur mur = createMur(101, "Type B");
        Integer id = mur.getId();

        ResponseEntity<Mur> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Mur.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getLibelleMur()).isEqualTo("Type B");
        assertThat(response.getBody().getHauteur()).isEqualTo(300);
        assertThat(response.getBody().getLongueur()).isEqualTo(500);
        assertThat(response.getBody().getOrientation()).isEqualTo(1);
        assertThat(response.getBody().getSalleNavigation()).isNull(); // Salle peut être null pour les tests
    }

    @Test
    void testSaveMur() {
        Mur mur = createMur(102, "Type C");
        assertThat(mur.getLibelleMur()).isEqualTo("Type C");
        assertThat(mur.getHauteur()).isEqualTo(300);
        assertThat(mur.getLongueur()).isEqualTo(500);
        assertThat(mur.getOrientation()).isEqualTo(1);
        assertThat(mur.getSalleNavigation()).isNull(); // Salle peut être null pour les tests
        assertThat(mur.getId()).isNotNull(); // Vérifie que l'ID a été généré
        assertThat(mur.getId()).isGreaterThan(0); // Vérifie que l'ID est positif
    }

    @Test
    void testUpdateMur() {
        Mur mur = createMur(103, "Type D");
        mur.setLibelleMur("Type D - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Mur> entity = new HttpEntity<>(mur, headers);

        ResponseEntity<Mur> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, Mur.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleMur()).isEqualTo("Type D - MAJ");
        assertThat(response.getBody().getId()).isEqualTo(mur.getId());
        assertThat(response.getBody().getHauteur()).isEqualTo(300);
        assertThat(response.getBody().getLongueur()).isEqualTo(500);
        assertThat(response.getBody().getOrientation()).isEqualTo(1);
        assertThat(response.getBody().getSalleNavigation()).isNull(); // Salle peut être null pour les tests
    }

    @Test
    void testDeleteMurById() {
        Mur mur = createMur(104, "Type à Supprimer");
        Integer id = mur.getId();

        restTemplate.delete(getBaseUrl() + "/" + id);

        ResponseEntity<Mur> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Mur.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
