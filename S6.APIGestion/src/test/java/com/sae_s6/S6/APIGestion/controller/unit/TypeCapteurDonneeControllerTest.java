package com.sae_s6.S6.APIGestion.controller.unit;

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonneeEmbedId;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de test d'intégration pour le contrôleur TypeCapteurDonneeController.
 * Utilise TestRestTemplate pour effectuer de vrais appels HTTP sur un serveur démarré aléatoirement.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TypeCapteurDonneeControllerTest {

    // Port local utilisé par le serveur de test
    @LocalServerPort
    private int port;

    // Injection de TestRestTemplate pour effectuer les appels HTTP
    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Retourne l'URL de base pour les appels à l'API TypeCapteurDonnee.
     * @return URL complète de l'API TypeCapteurDonnee
     */
    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/typecapteurdonnee";
    }

    /**
     * Méthode utilitaire pour créer un TypeCapteurDonnee via l'API.
     * @param donnee_id identifiant de la donnée
     * @param typecapteur_id identifiant du type de capteur
     * @return TypeCapteurDonnee créé
     */
    private TypeCapteurDonnee createTypeCapteurDonnee(Integer donnee_id, Integer typecapteur_id) {
        // Crée une donnée fictive
        Donnee donnee = new Donnee();
        donnee.setId(donnee_id);

        // Crée un type capteur fictif
        TypeCapteur typeCapteur = new TypeCapteur();
        typeCapteur.setId(typecapteur_id);

        // Crée un ID composite pour TypeCapteurDonnee
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(donnee_id, typecapteur_id);

        // Crée un TypeCapteurDonnee fictif
        TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        typeCapteurDonnee.setDonneeNavigation(donnee);
        typeCapteurDonnee.setTypeCapteurNavigation(typeCapteur);
        typeCapteurDonnee.setId(id);

        // Envoie une requête POST pour créer le TypeCapteurDonnee
        ResponseEntity<TypeCapteurDonnee> response = restTemplate.postForEntity(getBaseUrl() + "/", typeCapteurDonnee, TypeCapteurDonnee.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    /**
     * Teste la récupération de tous les types capteur-donnée via l'API.
     */
    @Test
    void testGetAllTypeCapteurDonnees() {
        // Appel GET pour récupérer tous les types capteur-donnée
        ResponseEntity<TypeCapteurDonnee[]> response = restTemplate.getForEntity(getBaseUrl() + "/", TypeCapteurDonnee[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);

        List<TypeCapteurDonnee> resultat = Arrays.asList(response.getBody());
        assertThat(resultat.get(0).getDonneeNavigation().getId()).isEqualTo(1);
        assertThat(resultat.get(0).getTypeCapteurNavigation().getId()).isEqualTo(1);
    }

    /**
     * Teste la récupération d'un type capteur-donnée par son identifiant via l'API.
     */
    @Test
    void testGetTypeCapteurDonneeById() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 1);

        // Appel GET pour récupérer le type capteur-donnée par son ID
        ResponseEntity<TypeCapteurDonnee> response = restTemplate.getForEntity(getBaseUrl() + "/" + id.getIdDonnee() + "/" + id.getIdTypeCapteur(), TypeCapteurDonnee.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
    }

    /**
     * Teste la création d'un type capteur-donnée via l'API.
     */
    @Test
    void testSaveTypeCapteurDonnee() {
        TypeCapteurDonnee typeCapteurDonnee = createTypeCapteurDonnee(1, 2);

        assertThat(typeCapteurDonnee.getId()).isNotNull();
        assertThat(typeCapteurDonnee.getDonneeNavigation().getId()).isEqualTo(1);
        assertThat(typeCapteurDonnee.getTypeCapteurNavigation().getId()).isEqualTo(2);

        // Nettoyage : suppression du type capteur-donnée créé
        restTemplate.delete(getBaseUrl() + "/" + typeCapteurDonnee.getId().getIdDonnee() + "/" + typeCapteurDonnee.getId().getIdTypeCapteur());
    }

    /**
     * Teste la mise à jour d'un type capteur-donnée via l'API.
     */
    @Test
    void testUpdateTypeCapteurDonnee() {
        TypeCapteurDonnee typeCapteurDonnee = createTypeCapteurDonnee(1, 2);

        // Modifie les relations
        Donnee donnee = new Donnee();
        donnee.setId(2);
        typeCapteurDonnee.setDonneeNavigation(donnee);

        TypeCapteur typeCapteur = new TypeCapteur();
        typeCapteur.setId(1);
        typeCapteurDonnee.setTypeCapteurNavigation(typeCapteur);

        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(donnee.getId(), typeCapteur.getId());
        typeCapteurDonnee.setId(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TypeCapteurDonnee> entity = new HttpEntity<>(typeCapteurDonnee, headers);

        // Appel PUT pour mettre à jour le type capteur-donnée
        ResponseEntity<TypeCapteurDonnee> response = restTemplate.exchange(
                getBaseUrl() + "/1/2", HttpMethod.PUT, entity, TypeCapteurDonnee.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);

        // Nettoyage : suppression du type capteur-donnée créé
        restTemplate.delete(getBaseUrl() + "/" + id.getIdDonnee() + "/" + id.getIdTypeCapteur());
    }

    /**
     * Teste la suppression d'un type capteur-donnée via l'API.
     */
    @Test
    void testDeleteTypeCapteurDonneeById() {
        TypeCapteurDonnee typeCapteurDonnee = createTypeCapteurDonnee(1, 2);

        // Appel DELETE pour supprimer le type capteur-donnée
        restTemplate.delete(getBaseUrl() + "/" + typeCapteurDonnee.getId().getIdDonnee() + "/" + typeCapteurDonnee.getId().getIdTypeCapteur());

        // Vérifie que le type capteur-donnée n'existe plus
        ResponseEntity<TypeCapteurDonnee> response = restTemplate.getForEntity(getBaseUrl() + "/" + typeCapteurDonnee.getId().getIdDonnee() + "/" + typeCapteurDonnee.getId().getIdTypeCapteur(), TypeCapteurDonnee.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}