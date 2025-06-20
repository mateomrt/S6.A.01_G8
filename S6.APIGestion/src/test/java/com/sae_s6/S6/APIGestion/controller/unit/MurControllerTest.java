package com.sae_s6.S6.APIGestion.controller.unit;

import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;

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
 * Classe de test d'intégration pour le contrôleur MurController.
 * Utilise TestRestTemplate pour effectuer de vrais appels HTTP sur un serveur démarré aléatoirement.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MurControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Retourne l'URL de base pour les appels à l'API Mur.
     * @return URL complète de l'API Mur
     */
    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/murs";
    }

    /**
     * Méthode utilitaire pour créer un Mur via l'API.
     * @param id identifiant du mur (peut être null pour auto-génération)
     * @param libelle libellé du mur
     * @return Mur créé
     */
    private Mur createMur(Integer id, String libelle) {
        Salle salle = new Salle();
        salle.setId(1); // On suppose que cette salle existe en base avec les bonnes relations

        Mur mur = new Mur();
        mur.setId(id);
        mur.setLibelleMur(libelle);
        mur.setHauteur(300.0);
        mur.setLongueur(500.0);
        mur.setOrientation(Mur.Orientation.N);
        mur.setSalleNavigation(salle); // seulement l’ID

        // Envoie une requête POST pour créer le mur
        ResponseEntity<Mur> response = restTemplate.postForEntity(getBaseUrl() + "/", mur, Mur.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    private int createdMurId;

    /**
     * Teste la récupération de tous les murs via l'API.
     */
    @Test
    void testGetAllMurs() {
        // Appel GET pour récupérer tous les murs
        ResponseEntity<Mur[]> response = restTemplate.getForEntity(getBaseUrl() + "/", Mur[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);

        // Trie les résultats pour vérifier les valeurs attendues
        List<Mur> murs = Arrays.asList(response.getBody());
        murs.sort(Comparator.comparing(Mur::getLibelleMur));

        assertThat(murs.get(0).getLibelleMur()).isEqualTo("Mur est");
        assertThat(murs.get(1).getLibelleMur()).isEqualTo("Mur nord");
        assertThat(murs.get(2).getLibelleMur()).isEqualTo("Mur ouest");
        assertThat(murs.get(3).getLibelleMur()).isEqualTo("Mur sud");
    }

    /**
     * Teste la récupération d'un mur par son identifiant via l'API.
     */
    @Test
    void testGetMurById() {
        Integer id = 1; // Assurez-vous que ce Mur existe dans la base de données

        // Appel GET pour récupérer le mur par son ID
        ResponseEntity<Mur> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Mur.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getLibelleMur()).isEqualTo("Mur nord");
        assertThat(response.getBody().getHauteur()).isEqualTo(250.0);
        assertThat(response.getBody().getLongueur()).isEqualTo(600.0);
        assertThat(response.getBody().getOrientation()).isEqualTo(Mur.Orientation.N);
        assertThat(response.getBody().getSalleNavigation()).isNotNull();
        assertThat(response.getBody().getSalleNavigation().getId()).isEqualTo(1);
    }

    /**
     * Teste la création d'un mur via l'API.
     */
    @Test
    void testSaveMur() {
        // Crée un nouveau mur
        Mur mur = createMur(null, "Mur nord 2");
        createdMurId = mur.getId();

        assertThat(mur.getLibelleMur()).isEqualTo("Mur nord 2");
        assertThat(mur.getId()).isNotNull(); // Vérifie que l'ID a été généré
        assertThat(mur.getId()).isGreaterThan(0); // Vérifie que l'ID est positif
        assertThat(mur.getHauteur()).isGreaterThan(0); // Vérifie que la hauteur est positive
        assertThat(mur.getLongueur()).isGreaterThan(0); // Vérifie que la longueur est positive
        assertThat(mur.getSalleNavigation()).isNotNull(); // Vérifie que la salle n'est pas nulle

        // Nettoyage : suppression du mur créé
        restTemplate.delete(getBaseUrl() + "/" + createdMurId);
    }

    /**
     * Teste la mise à jour d'un mur via l'API.
     */
    @Test
    void testUpdateMur() {
        // Crée un mur à mettre à jour
        Mur mur = createMur(null, "Mur secondaire");
        createdMurId = mur.getId();

        // Modifie le libellé du mur
        mur.setLibelleMur("Mur secondaire - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Mur> entity = new HttpEntity<>(mur, headers);

        // Appel PUT pour mettre à jour le mur
        ResponseEntity<Mur> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, Mur.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleMur()).isEqualTo("Mur secondaire - MAJ");
        assertThat(response.getBody().getId()).isEqualTo(mur.getId()); // Vérifie que l'ID est inchangé
        assertThat(response.getBody().getId()).isGreaterThan(0); // Vérifie que l'ID est positif

        // Nettoyage : suppression du mur créé
        restTemplate.delete(getBaseUrl() + "/" + createdMurId);    
    }

    /**
     * Teste la suppression d'un mur via l'API.
     */
    @Test
    void testDeleteMurById() {
        // Crée un mur à supprimer
        Mur mur = createMur(null, "Mur secondaire 2");
        Integer id = mur.getId();

        // Appel DELETE pour supprimer le mur
        restTemplate.delete(getBaseUrl() + "/" + id);

        // Vérifie que le mur n'existe plus
        ResponseEntity<Mur> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Mur.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}