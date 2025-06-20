package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.Donnee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DonneeControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/donnee";
    }

    // Méthode utilitaire pour créer une donnée
    private Donnee createDonnee(Integer id, String titre, String unite) {
        Donnee donnee = new Donnee();
        donnee.setId(id);
        donnee.setLibelleDonnee(titre);
        donnee.setUnite(unite);

        ResponseEntity<Donnee> response = restTemplate.postForEntity(getBaseUrl() + "/", donnee, Donnee.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    private int createdDonneeId;

    @Test
    void testGetAllDonnees() {

        ResponseEntity<Donnee[]> response = restTemplate.getForEntity(getBaseUrl() + "/", Donnee[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
        List<Donnee> resultat = Arrays.asList(response.getBody());
        resultat.sort(Comparator.comparing(Donnee::getLibelleDonnee));
        assertThat(resultat.get(0).getLibelleDonnee()).isEqualTo("Humidité relative");
        assertThat(resultat.get(0).getUnite()).isEqualTo("%");
        assertThat(resultat.get(1).getLibelleDonnee()).isEqualTo("Température");
        assertThat(resultat.get(1).getUnite()).isEqualTo("°C");
    }

    @Test
    void testGetDonneeById() {
        Integer id = 1;

        ResponseEntity<Donnee> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Donnee.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getLibelleDonnee()).isEqualTo("Température");
    }

    @Test
    void testSaveDonnee() {
        Donnee donnee = createDonnee(null, "Température", "F");
        createdDonneeId = donnee.getId();

        assertThat(donnee.getLibelleDonnee()).isEqualTo("Température");
        assertThat(donnee.getUnite()).isEqualTo("F");
        assertThat(donnee.getId()).isNotNull(); // Vérifie que l'ID a été généré
        assertThat(donnee.getId()).isGreaterThan(0); // Vérifie que l'ID est positif
        
        restTemplate.delete(getBaseUrl() + "/" + createdDonneeId);
    }

    @Test
    void testUpdateDonnee() {

       Donnee donnee = createDonnee(null, "Température", "F");
        createdDonneeId = donnee.getId();

        donnee.setLibelleDonnee("Température - MAJ");
        donnee.setUnite("F - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Donnee> entity = new HttpEntity<>(donnee, headers);

        ResponseEntity<Donnee> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, Donnee.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleDonnee()).isEqualTo("Température - MAJ");
        assertThat(response.getBody().getUnite()).isEqualTo("F - MAJ");
        assertThat(response.getBody().getId()).isEqualTo(donnee.getId()); // Vérifie que l'ID est inchangé
        assertThat(response.getBody().getId()).isGreaterThan(0); // Vérifie que l'ID est positif

        restTemplate.delete(getBaseUrl() + "/" + createdDonneeId);
    }

    @Test
    void testDeleteDonneeById() {
        Donnee donnee = createDonnee(null, "Température", "F");
        Integer id = donnee.getId();

        restTemplate.delete(getBaseUrl() + "/" + id);

        ResponseEntity<Donnee> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Donnee.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
