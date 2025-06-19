package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.TypeEquipement;

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
public class TypeEquipementControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/typeequipement";
    }

    // Méthode utilitaire pour créer un TypeEquipement
    private TypeEquipement createTypeEquipement(Integer id, String titre) {
        TypeEquipement typeEquipement = new TypeEquipement();
        typeEquipement.setId(id);
        typeEquipement.setLibelleTypeEquipement(titre);

        ResponseEntity<TypeEquipement> response = restTemplate.postForEntity(getBaseUrl() + "/", typeEquipement, TypeEquipement.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    private int createdTypeEquipementId;

    @Test
    void testGetAllTypeEquipements() {

        ResponseEntity<TypeEquipement[]> response = restTemplate.getForEntity(getBaseUrl() + "/", TypeEquipement[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
        List<TypeEquipement> resultat = Arrays.asList(response.getBody());
        resultat.sort(Comparator.comparing(TypeEquipement::getLibelleTypeEquipement));
        assertThat(resultat.get(0).getLibelleTypeEquipement()).isEqualTo("Ordinateur");
        assertThat(resultat.get(1).getLibelleTypeEquipement()).isEqualTo("Projecteur");
    }

    @Test
    void testGetTypeEquipementById() {
        Integer id = 1;

        ResponseEntity<TypeEquipement> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, TypeEquipement.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getLibelleTypeEquipement()).isEqualTo("Ordinateur");
    }

    @Test
    void testSaveTypeEquipement() {
        TypeEquipement typeEquipement = createTypeEquipement(null, "PC");
        createdTypeEquipementId = typeEquipement.getId();

        assertThat(typeEquipement.getLibelleTypeEquipement()).isEqualTo("PC");
        assertThat(typeEquipement.getId()).isNotNull(); // Vérifie que l'ID a été généré
        assertThat(typeEquipement.getId()).isGreaterThan(0); // Vérifie que l'ID est positif
        
        restTemplate.delete(getBaseUrl() + "/" + createdTypeEquipementId);
    }

    @Test
    void testUpdateTypeEquipement() {

        

       TypeEquipement typeEquipement = createTypeEquipement(null, "PC");
        createdTypeEquipementId = typeEquipement.getId();

        typeEquipement.setLibelleTypeEquipement("PC - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TypeEquipement> entity = new HttpEntity<>(typeEquipement, headers);

        ResponseEntity<TypeEquipement> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, TypeEquipement.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleTypeEquipement()).isEqualTo("PC - MAJ");
        assertThat(response.getBody().getId()).isEqualTo(typeEquipement.getId()); // Vérifie que l'ID est inchangé
        assertThat(response.getBody().getId()).isGreaterThan(0); // Vérifie que l'ID est positif

        restTemplate.delete(getBaseUrl() + "/" + createdTypeEquipementId);
    }

    @Test
    void testDeleteTypeEquipementById() {
        TypeEquipement typeEquipement = createTypeEquipement(null, "Ordinateur");
        Integer id = typeEquipement.getId();

        restTemplate.delete(getBaseUrl() + "/" + id);

        ResponseEntity<TypeEquipement> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, TypeEquipement.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
