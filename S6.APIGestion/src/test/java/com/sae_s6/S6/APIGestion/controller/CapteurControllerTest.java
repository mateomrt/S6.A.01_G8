package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.service.CapteurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CapteurControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CapteurService capteurService;

    @LocalServerPort
    private int port;
    
    @Test
    public void testGetAllCapteurs() {
        ResponseEntity<Capteur[]> response = restTemplate.getForEntity("/api/capteur/", Capteur[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Capteur[] capteurs = response.getBody();
        assertThat(capteurs).isNotNull();
        assertThat(capteurs.length).isGreaterThan(0); 
    }

    @Test
    public void testCreateAndGetCapteur() {
        Capteur capteur = new Capteur();
        capteur.setLibelleCapteur("Capteur température");
        capteur.setPositionXCapteur(10.0);
        capteur.setPositionYCapteur(20.0);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Capteur> entity = new HttpEntity<>(capteur, headers);

        ResponseEntity<Capteur> response = restTemplate.postForEntity("/api/capteur/", entity, Capteur.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLibelleCapteur()).isEqualTo("Capteur température");

        Integer id = response.getBody().getId();
        ResponseEntity<Capteur> getResponse = restTemplate.getForEntity("/api/capteur/" + id, Capteur.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getLibelleCapteur()).isEqualTo("Capteur température");
    }

    @Test
    public void testUpdateCapteur() {
        Capteur capteur = new Capteur();
        capteur.setLibelleCapteur("Capteur luminosité");
        capteur.setPositionXCapteur(15.0);
        capteur.setPositionYCapteur(25.0);

        Capteur saved = capteurService.saveCapteur(capteur);

        saved.setLibelleCapteur("Capteur luminosité modifié");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Capteur> entity = new HttpEntity<>(saved, headers);

        ResponseEntity<Capteur> response = restTemplate.exchange(
                "/api/capteur/" + saved.getId(),
                HttpMethod.PUT,
                entity,
                Capteur.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLibelleCapteur()).isEqualTo("Capteur luminosité modifié");
    }

    @Test
    public void testDeleteCapteur() {
        Capteur capteur = new Capteur();
        capteur.setLibelleCapteur("Capteur humidité");

        Capteur saved = capteurService.saveCapteur(capteur);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/capteur/" + saved.getId(),
                HttpMethod.DELETE,
                null,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("supprimé avec succès");
    }
}