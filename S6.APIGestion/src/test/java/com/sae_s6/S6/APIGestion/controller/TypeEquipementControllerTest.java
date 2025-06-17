package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.TypeEquipement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TypeEquipementControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/typeequipement";
    }

    // Méthode utilitaire pour créer un TypeEquipement
    private TypeEquipement createTypeEquipement(Integer id, String titre) {
        TypeEquipement typeEquipement = new TypeEquipement();
        typeEquipement.setId(id);
        typeEquipement.setTitre(titre);

        ResponseEntity<TypeEquipement> response = restTemplate.postForEntity(getBaseUrl() + "/", typeEquipement, TypeEquipement.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    @Test
    void testGetAllTypeEquipements() {
        //createTypeEquipement(100, "Type A"); // Crée un type pour s'assurer qu'on a au moins un en base

        ResponseEntity<TypeEquipement[]> response = restTemplate.getForEntity(getBaseUrl() + "/", TypeEquipement[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }

    @Test
    void testGetTypeEquipementById() {
        TypeEquipement typeEquipement = createTypeEquipement(1, "Ordinateur");
        Integer id = typeEquipement.getId();

        ResponseEntity<TypeEquipement> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, TypeEquipement.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getTitre()).isEqualTo("Ordinateur");
    }

    @Test
    void testSaveTypeEquipement() {
        TypeEquipement typeEquipement = createTypeEquipement(2, "Projecteur");
        assertThat(typeEquipement.getTitre()).isEqualTo("Projecteur");
    }

    @Test
    void testUpdateTypeEquipement() {
        TypeEquipement typeEquipement = createTypeEquipement(2, "Projecteur");
        typeEquipement.setTitre("Projecteur - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TypeEquipement> entity = new HttpEntity<>(typeEquipement, headers);

        ResponseEntity<TypeEquipement> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, TypeEquipement.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitre()).isEqualTo("Projecteur - MAJ");
    }

    @Test
    void testDeleteTypeEquipementById() {
        TypeEquipement typeEquipement = createTypeEquipement(1, "Ordinateur");
        Integer id = typeEquipement.getId();

        restTemplate.delete(getBaseUrl() + "/" + id);

        ResponseEntity<TypeEquipement> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, TypeEquipement.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
