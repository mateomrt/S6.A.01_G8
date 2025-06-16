package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TypeSalleControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/typesalle";
    }

    // Méthode utilitaire pour créer un TypeSalle
    private TypeSalle createTypeSalle(Integer id, String libelle) {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(id);
        typeSalle.setLibelleTypeSalle(libelle);

        ResponseEntity<TypeSalle> response = restTemplate.postForEntity(getBaseUrl() + "/", typeSalle, TypeSalle.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    @Test
    void testGetAllTypeSalles() {
        createTypeSalle(100, "Type A"); // Crée un type pour s'assurer qu'on a au moins un en base

        ResponseEntity<TypeSalle[]> response = restTemplate.getForEntity(getBaseUrl() + "/", TypeSalle[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }

    @Test
    void testGetTypeSalleById() {
        TypeSalle typeSalle = createTypeSalle(101, "Type B");
        Integer id = typeSalle.getId();

        ResponseEntity<TypeSalle> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, TypeSalle.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getLibelleTypeSalle()).isEqualTo("Type B");
    }

    @Test
    void testSaveTypeSalle() {
        TypeSalle typeSalle = createTypeSalle(102, "Type C");
        assertThat(typeSalle.getLibelleTypeSalle()).isEqualTo("Type C");
    }

    @Test
    void testUpdateTypeSalle() {
        TypeSalle typeSalle = createTypeSalle(103, "Type D");
        typeSalle.setLibelleTypeSalle("Type D - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TypeSalle> entity = new HttpEntity<>(typeSalle, headers);

        ResponseEntity<TypeSalle> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, TypeSalle.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleTypeSalle()).isEqualTo("Type D - MAJ");
    }

    @Test
    void testDeleteTypeSalleById() {
        TypeSalle typeSalle = createTypeSalle(104, "Type à Supprimer");
        Integer id = typeSalle.getId();

        restTemplate.delete(getBaseUrl() + "/" + id);

        ResponseEntity<TypeSalle> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, TypeSalle.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
