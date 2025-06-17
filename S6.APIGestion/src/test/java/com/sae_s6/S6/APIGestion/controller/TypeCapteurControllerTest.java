package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TypeCapteurControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/typecapteur";
    }

    @Test
    public void testGetAllTypeCapteurs() {
        ResponseEntity<TypeCapteur[]> response = restTemplate.getForEntity(getBaseUrl() + "/", TypeCapteur[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void testCreateAndGetTypeCapteur() {
        TypeCapteur typeCapteur = new TypeCapteur();
        typeCapteur.setLibelleTypeCapteur("Type Température");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TypeCapteur> entity = new HttpEntity<>(typeCapteur, headers);

        // Create
        ResponseEntity<TypeCapteur> postResponse = restTemplate.postForEntity(getBaseUrl() + "/", entity, TypeCapteur.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postResponse.getBody()).isNotNull();
        assertThat(postResponse.getBody().getLibelleTypeCapteur()).isEqualTo("Type Température");

        Integer id = postResponse.getBody().getId();

        // Get
        ResponseEntity<TypeCapteur> getResponse = restTemplate.getForEntity(getBaseUrl() + "/" + id, TypeCapteur.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getLibelleTypeCapteur()).isEqualTo("Type Température");
    }

    @Test
    public void testUpdateTypeCapteur() {
        TypeCapteur typeCapteur = new TypeCapteur();
        typeCapteur.setLibelleTypeCapteur("Type Humidité");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TypeCapteur> entity = new HttpEntity<>(typeCapteur, headers);

        // Create
        ResponseEntity<TypeCapteur> postResponse = restTemplate.postForEntity(getBaseUrl() + "/", entity, TypeCapteur.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postResponse.getBody()).isNotNull();

        Integer id = postResponse.getBody().getId();

        // Update
        typeCapteur.setLibelleTypeCapteur("Type Humidité Modifié");
        HttpEntity<TypeCapteur> updateEntity = new HttpEntity<>(typeCapteur, headers);
        ResponseEntity<TypeCapteur> updateResponse = restTemplate.exchange(getBaseUrl() + "/" + id, HttpMethod.PUT, updateEntity, TypeCapteur.class);

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getLibelleTypeCapteur()).isEqualTo("Type Humidité Modifié");
    }

    @Test
    public void testDeleteTypeCapteur() {
        TypeCapteur typeCapteur = new TypeCapteur();
        typeCapteur.setLibelleTypeCapteur("Type Lumière");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TypeCapteur> entity = new HttpEntity<>(typeCapteur, headers);

        // Create
        ResponseEntity<TypeCapteur> postResponse = restTemplate.postForEntity(getBaseUrl() + "/", entity, TypeCapteur.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postResponse.getBody()).isNotNull();

        Integer id = postResponse.getBody().getId();

        // Delete
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(getBaseUrl() + "/" + id, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify Deletion
        ResponseEntity<TypeCapteur> getResponse = restTemplate.getForEntity(getBaseUrl() + "/" + id, TypeCapteur.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}