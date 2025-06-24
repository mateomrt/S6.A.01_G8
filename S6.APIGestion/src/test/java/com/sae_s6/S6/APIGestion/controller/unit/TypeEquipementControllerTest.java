package com.sae_s6.S6.APIGestion.controller.unit;

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

/**
 * Classe de test d'intégration pour le contrôleur TypeEquipementController.
 * Utilise TestRestTemplate pour effectuer de vrais appels HTTP sur un serveur démarré aléatoirement.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TypeEquipementControllerTest {

    // Port local utilisé par le serveur de test
    @LocalServerPort
    private int port;

    // Injection de TestRestTemplate pour effectuer les appels HTTP
    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Retourne l'URL de base pour les appels à l'API TypeEquipement.
     * @return URL complète de l'API TypeEquipement
     */
    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/typeequipement";
    }

    /**
     * Méthode utilitaire pour créer un TypeEquipement via l'API.
     * @param id identifiant du type d'équipement (peut être null pour auto-génération)
     * @param titre libellé du type d'équipement
     * @return TypeEquipement créé
     */
    private TypeEquipement createTypeEquipement(Integer id, String titre) {
        TypeEquipement typeEquipement = new TypeEquipement();
        typeEquipement.setId(id);
        typeEquipement.setLibelleTypeEquipement(titre);

        // Envoie une requête POST pour créer le type d'équipement
        ResponseEntity<TypeEquipement> response = restTemplate.postForEntity(getBaseUrl() + "/", typeEquipement, TypeEquipement.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    private int createdTypeEquipementId;

    /**
     * Teste la récupération de tous les types d'équipement via l'API.
     */
    @Test
    void testGetAllTypeEquipements() {
        // Appel GET pour récupérer tous les types d'équipement
        ResponseEntity<TypeEquipement[]> response = restTemplate.getForEntity(getBaseUrl() + "/", TypeEquipement[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
        List<TypeEquipement> resultat = Arrays.asList(response.getBody());
        assertThat(resultat.get(0).getLibelleTypeEquipement()).isEqualTo("Ordinateur");
        assertThat(resultat.get(1).getLibelleTypeEquipement()).isEqualTo("Projecteur");
    }

    /**
     * Teste la récupération d'un type d'équipement par son identifiant via l'API.
     */
    @Test
    void testGetTypeEquipementById() {
        Integer id = 1;

        // Appel GET pour récupérer le type d'équipement par son ID
        ResponseEntity<TypeEquipement> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, TypeEquipement.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getLibelleTypeEquipement()).isEqualTo("Ordinateur");
    }

    /**
     * Teste la création d'un type d'équipement via l'API.
     */
    @Test
    void testSaveTypeEquipement() {
        // Crée un nouveau type d'équipement
        TypeEquipement typeEquipement = createTypeEquipement(null, "PC");
        createdTypeEquipementId = typeEquipement.getId();

        assertThat(typeEquipement.getLibelleTypeEquipement()).isEqualTo("PC");
        assertThat(typeEquipement.getId()).isNotNull(); // Vérifie que l'ID a été généré
        assertThat(typeEquipement.getId()).isGreaterThan(0); // Vérifie que l'ID est positif
        
        // Nettoyage : suppression du type d'équipement créé
        restTemplate.delete(getBaseUrl() + "/" + createdTypeEquipementId);
    }

    /**
     * Teste la mise à jour d'un type d'équipement via l'API.
     */
    @Test
    void testUpdateTypeEquipement() {
        // Crée un type d'équipement à mettre à jour
        TypeEquipement typeEquipement = createTypeEquipement(null, "PC");
        createdTypeEquipementId = typeEquipement.getId();

        // Modifie le libellé du type d'équipement
        typeEquipement.setLibelleTypeEquipement("PC - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TypeEquipement> entity = new HttpEntity<>(typeEquipement, headers);

        // Appel PUT pour mettre à jour le type d'équipement
        ResponseEntity<TypeEquipement> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, TypeEquipement.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleTypeEquipement()).isEqualTo("PC - MAJ");
        assertThat(response.getBody().getId()).isEqualTo(typeEquipement.getId()); // Vérifie que l'ID est inchangé
        assertThat(response.getBody().getId()).isGreaterThan(0); // Vérifie que l'ID est positif

        // Nettoyage : suppression du type d'équipement créé
        restTemplate.delete(getBaseUrl() + "/" + createdTypeEquipementId);
    }

    /**
     * Teste la suppression d'un type d'équipement via l'API.
     */
    @Test
    void testDeleteTypeEquipementById() {
        // Crée un type d'équipement à supprimer
        TypeEquipement typeEquipement = createTypeEquipement(null, "Ordinateur");
        Integer id = typeEquipement.getId();

        // Appel DELETE pour supprimer le type d'équipement
        restTemplate.delete(getBaseUrl() + "/" + id);

        // Vérifie que le type d'équipement n'existe plus
        ResponseEntity<TypeEquipement> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, TypeEquipement.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}