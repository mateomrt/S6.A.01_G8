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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TypeCapteurControllerTest {

    @LocalServerPort
private int port;

@Autowired
private TestRestTemplate restTemplate;

private final List<Integer> createdTypeCapteurs = new ArrayList<>();

private String getBaseUrl() {
    return "http://localhost:" + port + "/api/typeCapteur/";
}

private TypeCapteur buildTypeCapteur(String libelle) {
    TypeCapteur type = new TypeCapteur();
    type.setLibelleTypeCapteur(libelle);
    type.setModeTypeCapteur("Auto");
    return type;
}

@Test
void testCreateAndGetTypeCapteurById() {
    TypeCapteur type = buildTypeCapteur("Test Type");

    // POST
    ResponseEntity<TypeCapteur> postResponse = restTemplate.postForEntity(getBaseUrl(), type, TypeCapteur.class);
    assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    TypeCapteur created = postResponse.getBody();
    assertThat(created).isNotNull();
    assertThat(created.getId()).isNotNull();
    createdTypeCapteurs.add(created.getId());

    // GET
    ResponseEntity<TypeCapteur> getResponse = restTemplate.getForEntity(getBaseUrl() + created.getId(), TypeCapteur.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    TypeCapteur fetched = getResponse.getBody();
    assertThat(fetched).isNotNull();
    assertThat(fetched.getLibelleTypeCapteur()).isEqualTo("Test Type");
    assertThat(fetched.getModeTypeCapteur()).isEqualTo("Auto");
}

@Test
void testGetAllTypeCapteurs() {
    ResponseEntity<TypeCapteur[]> response = restTemplate.getForEntity(getBaseUrl(), TypeCapteur[].class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    TypeCapteur[] list = response.getBody();
    assertThat(list).isNotNull();
    assertThat(list.length).isGreaterThan(0);
}

@Test
void testUpdateTypeCapteur() {
    TypeCapteur type = buildTypeCapteur("Initial Type");
    ResponseEntity<TypeCapteur> post = restTemplate.postForEntity(getBaseUrl(), type, TypeCapteur.class);
    assertThat(post.getStatusCode()).isEqualTo(HttpStatus.OK);
    TypeCapteur created = post.getBody();
    assertThat(created).isNotNull();
    createdTypeCapteurs.add(created.getId());

    // Modification
    created.setLibelleTypeCapteur("Type Modifié");
    created.setModeTypeCapteur("Manuel");

    HttpEntity<TypeCapteur> entity = new HttpEntity<>(created);
    ResponseEntity<TypeCapteur> updatedResponse = restTemplate.exchange(getBaseUrl(), HttpMethod.PUT, entity, TypeCapteur.class);
    assertThat(updatedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    TypeCapteur updated = updatedResponse.getBody();
    assertThat(updated.getLibelleTypeCapteur()).isEqualTo("Type Modifié");
    assertThat(updated.getModeTypeCapteur()).isEqualTo("Manuel");
}

@Test
void testDeleteTypeCapteur() {
    TypeCapteur type = buildTypeCapteur("À supprimer");
    ResponseEntity<TypeCapteur> post = restTemplate.postForEntity(getBaseUrl(), type, TypeCapteur.class);
    assertThat(post.getStatusCode()).isEqualTo(HttpStatus.OK);
    TypeCapteur created = post.getBody();
    assertThat(created).isNotNull();

    // DELETE
    restTemplate.delete(getBaseUrl() + created.getId());

    // GET après suppression
    ResponseEntity<TypeCapteur> getAfterDelete = restTemplate.getForEntity(getBaseUrl() + created.getId(), TypeCapteur.class);
    assertThat(getAfterDelete.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
}

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