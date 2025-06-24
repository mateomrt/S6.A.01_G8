package com.sae_s6.S6.APIGestion.controller.unit;

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

/**
 * Classe de test d'intégration pour le contrôleur DonneeController.
 * Utilise TestRestTemplate pour effectuer de vrais appels HTTP sur un serveur démarré aléatoirement.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DonneeControllerTest {

    // Port local utilisé par le serveur de test
    @LocalServerPort
    private int port;

    // Injection de TestRestTemplate pour effectuer les appels HTTP
    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Retourne l'URL de base pour les appels à l'API Donnee.
     * @return URL complète de l'API Donnee
     */
    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/donnee";
    }

    /**
     * Méthode utilitaire pour créer une Donnee via l'API.
     * @param id identifiant de la donnée (peut être null pour auto-génération)
     * @param libelle libellé de la donnée
     * @param unite unité de la donnée
     * @return Donnee créée
     */
    private Donnee createDonnee(Integer id, String libelle, String unite) {
        // Crée une donnée fictive
        Donnee donnee = new Donnee();
        donnee.setId(id);
        donnee.setLibelleDonnee(libelle);
        donnee.setUnite(unite);

        // Envoie une requête POST pour créer la donnée
        ResponseEntity<Donnee> response = restTemplate.postForEntity(getBaseUrl() + "/", donnee, Donnee.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    private int createdDonneeId;

    /**
     * Teste la récupération de toutes les données via l'API.
     */
    @Test
    void testGetAllDonnees() {
        // Appel GET pour récupérer toutes les données
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

    /**
     * Teste la récupération d'une donnée par son identifiant via l'API.
     */
    @Test
    void testGetDonneeById() {
        Integer id = 1;

        // Appel GET pour récupérer la donnée par son ID
        ResponseEntity<Donnee> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Donnee.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getLibelleDonnee()).isEqualTo("Température");
    }

    /**
     * Teste la création d'une donnée via l'API.
     */
    @Test
    void testSaveDonnee() {
        // Crée une nouvelle donnée
        Donnee donnee = createDonnee(null, "Température", "F");
        createdDonneeId = donnee.getId();

        assertThat(donnee.getLibelleDonnee()).isEqualTo("Température");
        assertThat(donnee.getUnite()).isEqualTo("F");
        assertThat(donnee.getId()).isNotNull(); // Vérifie que l'ID a été généré
        assertThat(donnee.getId()).isGreaterThan(0); // Vérifie que l'ID est positif
        
        // Nettoyage : suppression de la donnée créée
        restTemplate.delete(getBaseUrl() + "/" + createdDonneeId);
    }

    /**
     * Teste la mise à jour d'une donnée via l'API.
     */
    @Test
    void testUpdateDonnee() {
        // Crée une donnée à mettre à jour
        Donnee donnee = createDonnee(null, "Température", "F");
        createdDonneeId = donnee.getId();

        // Modifie le libellé de la donnée
        donnee.setLibelleDonnee("Température - MAJ");
        // Modifie l'unité de la donnée
        donnee.setUnite("F - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Donnee> entity = new HttpEntity<>(donnee, headers);

        // Appel PUT pour mettre à jour la donnée
        ResponseEntity<Donnee> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, Donnee.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleDonnee()).isEqualTo("Température - MAJ");
        assertThat(response.getBody().getUnite()).isEqualTo("F - MAJ");
        assertThat(response.getBody().getId()).isEqualTo(donnee.getId()); // Vérifie que l'ID est inchangé
        assertThat(response.getBody().getId()).isGreaterThan(0); // Vérifie que l'ID est positif
        
        // Nettoyage : suppression de la donnée créée
        restTemplate.delete(getBaseUrl() + "/" + createdDonneeId);
    }

    /**
     * Teste la suppression d'une donnée via l'API.
     */
    @Test
    void testDeleteDonneeById() {
        // Crée une donnée à supprimer
        Donnee donnee = createDonnee(null, "Température", "F");
        Integer id = donnee.getId();

        // Appel DELETE pour supprimer la donnée
        restTemplate.delete(getBaseUrl() + "/" + id);

        // Vérifie que la donnée n'existe plus
        ResponseEntity<Donnee> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Donnee.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}