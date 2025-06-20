package com.sae_s6.S6.APIGestion.controller.unit;

import com.sae_s6.S6.APIGestion.entity.Batiment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Classe de test d'intégration pour le contrôleur BatimentController.
 * Utilise TestRestTemplate pour effectuer de vrais appels HTTP sur un serveur démarré aléatoirement.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BatimentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Retourne l'URL de base pour les appels à l'API Batiment.
     * @return URL complète de l'API Batiment
     */
    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/batiment";
    }

    /**
     * Méthode utilitaire pour créer un Batiment via l'API.
     * @param id identifiant du bâtiment (peut être null pour auto-génération)
     * @param libelle libellé du bâtiment
     * @return Batiment créé
     */
    private Batiment createBatiment(Integer id, String libelle) {
        Batiment batiment = new Batiment();
        batiment.setId(id);
        batiment.setLibelleBatiment(libelle);

        // Envoie une requête POST pour créer le bâtiment
        ResponseEntity<Batiment> response = restTemplate.postForEntity(getBaseUrl() + "/", batiment, Batiment.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    private int createdBatimentId;

    /**
     * Teste la récupération de tous les bâtiments via l'API.
     */
    @Test
    void testGetAllBatiments() {
        // Appel GET pour récupérer tous les bâtiments
        ResponseEntity<Batiment[]> response = restTemplate.getForEntity(getBaseUrl() + "/", Batiment[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);

        // Trie les résultats pour vérifier les valeurs attendues
        List<Batiment> resultat = Arrays.asList(response.getBody());
        resultat.sort(Comparator.comparing(Batiment::getLibelleBatiment));
        assertThat(resultat.get(0).getLibelleBatiment()).isEqualTo("Bâtiment A");
        assertThat(resultat.get(1).getLibelleBatiment()).isEqualTo("Bâtiment B");
    }

    /**
     * Teste la récupération d'un bâtiment par son identifiant via l'API.
     */
    @Test
    void testGetBatimentById() {
        Integer id = 1;

        // Appel GET pour récupérer le bâtiment par son ID
        ResponseEntity<Batiment> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Batiment.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getLibelleBatiment()).isEqualTo("Bâtiment A");
    }

    /**
     * Teste la création d'un bâtiment via l'API.
     */
    @Test
    void testSaveBatiment() {
        // Crée un nouveau bâtiment
        Batiment batiment = createBatiment(null, "Bâtiment C");
        createdBatimentId = batiment.getId();

        assertThat(batiment.getLibelleBatiment()).isEqualTo("Bâtiment C");
        assertThat(batiment.getId()).isNotNull(); // Vérifie que l'ID a été généré
        assertThat(batiment.getId()).isGreaterThan(0); // Vérifie que l'ID est positif

        // Nettoyage : suppression du bâtiment créé
        restTemplate.delete(getBaseUrl() + "/" + createdBatimentId);
    }

    /**
     * Teste la mise à jour d'un bâtiment via l'API.
     */
    @Test
    void testUpdateBatiment() {
        // Crée un bâtiment à mettre à jour
        Batiment batiment = createBatiment(null, "Bâtiment D");
        createdBatimentId = batiment.getId();

        // Modifie le libellé du bâtiment
        batiment.setLibelleBatiment("Bâtiment D - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Batiment> entity = new HttpEntity<>(batiment, headers);

        // Appel PUT pour mettre à jour le bâtiment
        ResponseEntity<Batiment> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, Batiment.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleBatiment()).isEqualTo("Bâtiment D - MAJ");
        assertThat(response.getBody().getId()).isEqualTo(batiment.getId()); // Vérifie que l'ID est inchangé
        assertThat(response.getBody().getId()).isGreaterThan(0); // Vérifie que l'ID est positif

        // Nettoyage : suppression du bâtiment créé
        restTemplate.delete(getBaseUrl() + "/" + createdBatimentId);    
    }

    /**
     * Teste la suppression d'un bâtiment via l'API.
     */
    @Test
    void testDeleteBatimentById() {
        // Crée un bâtiment à supprimer
        Batiment batiment = createBatiment(null, "Type à Supprimer");
        Integer id = batiment.getId();

        // Appel DELETE pour supprimer le bâtiment
        restTemplate.delete(getBaseUrl() + "/" + id);

        // Vérifie que le bâtiment n'existe plus
        ResponseEntity<Batiment> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Batiment.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}