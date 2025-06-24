package com.sae_s6.S6.APIGestion.controller.unit;

import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test d'intégration pour le contrôleur CapteurController.
 * Utilise TestRestTemplate pour effectuer de vrais appels HTTP sur un serveur démarré aléatoirement.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CapteurControllerTest {

    // Port local utilisé par le serveur de test
    @LocalServerPort
    private int port;

    // Injection de TestRestTemplate pour effectuer les appels HTTP
    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Retourne l'URL de base pour les appels à l'API Capteur.
     * @return URL complète de l'API Capteur
     */
    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/capteur/";
    }

    // Liste pour garder trace des capteurs créés pendant les tests (pour nettoyage)
    private List<Integer> capteursCreeIds = new ArrayList<>();

    /**
     * Teste la création d'un capteur puis sa récupération par ID via l'API.
     */
    @Test
    void testPostCapteurAndGetById() {
        // Création du capteur avec ses relations valides
        Capteur capteur = new Capteur();
        capteur.setLibelleCapteur("Capteur Test");
        capteur.setPositionXCapteur(10.0);
        capteur.setPositionYCapteur(20.0);

        // Relations simulées
        Mur mur = new Mur();
        mur.setId(1);
        Salle salle = new Salle();
        salle.setId(1);
        TypeCapteur typeCapteur = new TypeCapteur();
        typeCapteur.setId(1);

        capteur.setMurNavigation(mur);
        capteur.setSalleNavigation(salle);
        capteur.setTypeCapteurNavigation(typeCapteur);

        // POST : création du capteur
        ResponseEntity<Capteur> postResponse = restTemplate.postForEntity(getBaseUrl(), capteur, Capteur.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Capteur capteurCree = postResponse.getBody();
        assertThat(capteurCree).isNotNull();
        assertThat(capteurCree.getId()).isNotNull();
        capteursCreeIds.add(capteurCree.getId());

        // GET : récupération du capteur par son ID
        ResponseEntity<Capteur> getResponse = restTemplate.getForEntity(getBaseUrl() + capteurCree.getId(), Capteur.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Capteur capteurFetched = getResponse.getBody();

        // Assertions sur les valeurs
        assertThat(capteurFetched).isNotNull();
        assertThat(capteurFetched.getLibelleCapteur()).isEqualTo("Capteur Test");
        assertThat(capteurFetched.getPositionXCapteur()).isEqualTo(10.0);
        assertThat(capteurFetched.getPositionYCapteur()).isEqualTo(20.0);
        assertThat(capteurFetched.getMurNavigation().getId()).isEqualTo(1);
        assertThat(capteurFetched.getSalleNavigation().getId()).isEqualTo(1);
        assertThat(capteurFetched.getTypeCapteurNavigation().getId()).isEqualTo(1);
    }

    /**
     * Teste la récupération de tous les capteurs via l'API.
     */
    @Test
    public void testGetAllCapteurs() {
        // GET : récupération de tous les capteurs
        ResponseEntity<Capteur[]> response = restTemplate.getForEntity(getBaseUrl(), Capteur[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Capteur[] capteurs = response.getBody();

        assertThat(capteurs).isNotNull();
        assertThat(capteurs.length).isGreaterThan(0);
    }

    /**
     * Teste la mise à jour d'un capteur via l'API.
     */
    @Test
    void testUpdateCapteur() {
        // Création du capteur initial
        Capteur capteur = new Capteur();
        capteur.setLibelleCapteur("Capteur Initial");
        capteur.setPositionXCapteur(2.0);
        capteur.setPositionYCapteur(3.0);

        Mur mur = new Mur();
        mur.setId(1);
        Salle salle = new Salle();
        salle.setId(1);
        TypeCapteur type = new TypeCapteur();
        type.setId(1);

        capteur.setMurNavigation(mur);
        capteur.setSalleNavigation(salle);
        capteur.setTypeCapteurNavigation(type);

        // POST : création du capteur
        ResponseEntity<Capteur> post = restTemplate.postForEntity(getBaseUrl(), capteur, Capteur.class);
        Capteur created = post.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        capteursCreeIds.add(created.getId());

        // Modification des valeurs du capteur
        created.setLibelleCapteur("Capteur Modifié");
        created.setPositionXCapteur(42.0);

        // PUT : mise à jour du capteur
        HttpEntity<Capteur> requestEntity = new HttpEntity<>(created);
        ResponseEntity<Capteur> response = restTemplate.exchange(getBaseUrl(), HttpMethod.PUT, requestEntity, Capteur.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Capteur updated = response.getBody();
        assertThat(updated).isNotNull();
        assertThat(updated.getLibelleCapteur()).isEqualTo("Capteur Modifié");
        assertThat(updated.getPositionXCapteur()).isEqualTo(42.0);
    }

    /**
     * Teste la suppression d'un capteur via l'API.
     */
    @Test
    void testDeleteCapteur() {
        // Création du capteur à supprimer
        Capteur capteur = new Capteur();
        capteur.setLibelleCapteur("Capteur à Supprimer");
        capteur.setPositionXCapteur(1.0);
        capteur.setPositionYCapteur(1.0);

        Mur mur = new Mur();
        mur.setId(1);
        Salle salle = new Salle();
        salle.setId(1);
        TypeCapteur type = new TypeCapteur();
        type.setId(1);

        capteur.setMurNavigation(mur);
        capteur.setSalleNavigation(salle);
        capteur.setTypeCapteurNavigation(type);

        // POST : création du capteur
        ResponseEntity<Capteur> post = restTemplate.postForEntity(getBaseUrl(), capteur, Capteur.class);
        Capteur created = post.getBody();
        assertThat(created).isNotNull();
        capteursCreeIds.add(created.getId());

        // DELETE : suppression du capteur
        restTemplate.delete(getBaseUrl() + created.getId());

        // Vérification que le capteur n'existe plus
        ResponseEntity<Capteur> getResponse = restTemplate.getForEntity(getBaseUrl() + created.getId(), Capteur.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * Nettoyage après chaque test : supprime tous les capteurs créés pendant les tests.
     */
    @AfterEach
    void cleanUpCapteurs() {
        for (Integer id : new ArrayList<>(capteursCreeIds)) {
            try {
                restTemplate.delete(getBaseUrl() + id);
            } catch (Exception e) {
                System.err.println("Erreur lors de la suppression du capteur ID " + id + " : " + e.getMessage());
            }
        }
        capteursCreeIds.clear();
    }
}