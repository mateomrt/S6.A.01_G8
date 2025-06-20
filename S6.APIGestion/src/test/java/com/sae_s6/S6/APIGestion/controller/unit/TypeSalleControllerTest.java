package com.sae_s6.S6.APIGestion.controller.unit;

import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de test d'intégration pour le contrôleur TypeSalleController.
 * Utilise TestRestTemplate pour effectuer de vrais appels HTTP sur un serveur démarré aléatoirement.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TypeSalleControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Retourne l'URL de base pour les appels à l'API TypeSalle.
     * @return URL complète de l'API TypeSalle
     */
    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/typesalle";
    }

    /**
     * Méthode utilitaire pour créer un TypeSalle via l'API.
     * @param libelle libellé du type de salle
     * @return TypeSalle créé
     */
    private TypeSalle createTypeSalle(String libelle) {
        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setLibelleTypeSalle(libelle);

        ResponseEntity<TypeSalle> response = restTemplate.postForEntity(getBaseUrl() + "/", typeSalle, TypeSalle.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    /**
     * Teste la récupération de tous les types de salle via l'API.
     */
    @Test
    void testGetAllTypeSalles() {
        //createTypeSalle("Type A"); // Crée un type pour s'assurer qu'on a au moins un en base

        ResponseEntity<TypeSalle[]> response = restTemplate.getForEntity(getBaseUrl() + "/", TypeSalle[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }

    /**
     * Teste la récupération d'un type de salle par son identifiant via l'API.
     */
    @Test
    void testGetTypeSalleById() {
        TypeSalle typeSalle = createTypeSalle("Type B");
        Integer id = typeSalle.getId();

        ResponseEntity<TypeSalle> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, TypeSalle.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getLibelleTypeSalle()).isEqualTo("Type B");
    }

    /**
     * Teste la création d'un type de salle via l'API.
     */
    @Test
    void testSaveTypeSalle() {
        TypeSalle typeSalle = createTypeSalle("Type C");
        assertThat(typeSalle.getLibelleTypeSalle()).isEqualTo("Type C");
    }

    /**
     * Teste la mise à jour d'un type de salle via l'API.
     */
    @Test
    void testUpdateTypeSalle() {
        TypeSalle typeSalle = createTypeSalle("Type D");
        typeSalle.setLibelleTypeSalle("Type D - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TypeSalle> entity = new HttpEntity<>(typeSalle, headers);

        ResponseEntity<TypeSalle> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, TypeSalle.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleTypeSalle()).isEqualTo("Type D - MAJ");
    }

    /**
     * Teste la suppression d'un type de salle via l'API.
     */
    @Test
    void testDeleteTypeSalleById() {
        TypeSalle typeSalle = createTypeSalle("Type à Supprimer");
        Integer id = typeSalle.getId();

        restTemplate.delete(getBaseUrl() + "/" + id);

        ResponseEntity<TypeSalle> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, TypeSalle.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}