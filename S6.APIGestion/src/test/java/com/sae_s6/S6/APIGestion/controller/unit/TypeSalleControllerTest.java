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

    // Port local utilisé par le serveur de test
    @LocalServerPort
    private int port;

    // Injection de TestRestTemplate pour effectuer les appels HTTP
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
        // Appel GET pour récupérer tous les types de salle
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
        // Crée un type de salle fictif pour le test
        TypeSalle typeSalle = createTypeSalle("Type B");
        Integer id = typeSalle.getId();

        // Appel GET pour récupérer le type de salle par son ID
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
        // Crée un nouveau type de salle
        TypeSalle typeSalle = createTypeSalle("Type C");

        // Vérifications sur le type de salle créé
        assertThat(typeSalle.getLibelleTypeSalle()).isEqualTo("Type C");
    }

    /**
     * Teste la mise à jour d'un type de salle via l'API.
     */
    @Test
    void testUpdateTypeSalle() {
        // Crée un type de salle à mettre à jour
        TypeSalle typeSalle = createTypeSalle("Type D");

        // Modifie le libellé du type de salle
        typeSalle.setLibelleTypeSalle("Type D - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TypeSalle> entity = new HttpEntity<>(typeSalle, headers);

        // Appel PUT pour mettre à jour le type de salle
        ResponseEntity<TypeSalle> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, TypeSalle.class);

        // Vérifications sur les résultats
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleTypeSalle()).isEqualTo("Type D - MAJ");
    }

    /**
     * Teste la suppression d'un type de salle via l'API.
     */
    @Test
    void testDeleteTypeSalleById() {
        // Crée un type de salle à supprimer
        TypeSalle typeSalle = createTypeSalle("Type à Supprimer");
        Integer id = typeSalle.getId();

        // Appel DELETE pour supprimer le type de salle
        restTemplate.delete(getBaseUrl() + "/" + id);

        // Vérifie que le type de salle n'existe plus
        ResponseEntity<TypeSalle> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, TypeSalle.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}