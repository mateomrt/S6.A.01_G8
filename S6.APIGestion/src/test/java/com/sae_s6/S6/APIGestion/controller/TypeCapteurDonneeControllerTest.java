package com.sae_s6.S6.APIGestion.controller;

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
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Retourne l'URL de base pour les appels à l'API TypeCapteurDonnee.
     * @return URL complète de l'API TypeCapteurDonnee
     */
    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/type_capteur_donnee";
    }

    /**
     * Méthode utilitaire pour créer un TypeCapteurDonnee via l'API.
     * @param donnee_id identifiant de la donnée (peut être null pour auto-génération)
     * @param typecapteur_id identifiant du type de capteur
     * @return TypeEquipement créé
     */
    private TypeCapteurDonnee createTypeCapteurDonnee(Integer donnee_id, Integer typecapteur_id) {
        // Crée une donnee fictive
        Donnee donnee = new Donnee();
        donnee.setId(donnee_id);


        // Crée un type capteur fictif
        TypeCapteur typeCapteur = new TypeCapteur();
        typeCapteur.setId(typecapteur_id);

        id = new TypeCapteurDonneeEmbedId(donnee_id, typecapteur_id);

        // Crée un type capteur donnee fictif
        TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        typeCapteurDonnee.setDonneeNavigation(donnee);
        typeCapteurDonnee.setTypeCapteurNavigation(typeCapteur);
        typeCapteurDonnee.setId(id);

        // Envoie une requête POST pour créer le type capteur donnée
        ResponseEntity<TypeCapteurDonnee> response = restTemplate.postForEntity(getBaseUrl() + "/", typeCapteurDonnee, TypeCapteurDonnee.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    TypeCapteurDonneeEmbedId id;
    /**
     * Teste la récupération de tous les types capteur donnée via l'API.
     */
    @Test
    void testGetAllTypeCapteurDonnees() {
        // Appel GET pour récupérer tous les types capteur donnée
        ResponseEntity<TypeCapteurDonnee[]> response = restTemplate.getForEntity(getBaseUrl() + "/", TypeCapteurDonnee[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
        List<TypeCapteurDonnee> resultat = Arrays.asList(response.getBody());
        
        assertThat(resultat.get(0).getDonneeNavigation().getId()).isEqualTo(1);
        assertThat(resultat.get(0).getTypeCapteurNavigation().getId()).isEqualTo(1);
        assertThat(resultat.get(1).getDonneeNavigation().getId()).isEqualTo(2);
        assertThat(resultat.get(1).getTypeCapteurNavigation().getId()).isEqualTo(2);
    }

    /**
     * Teste la récupération d'un type capteur donnee par son identifiant via l'API.
     */
    @Test
    void testGetTypeCapteurDonneeById() {
        id = new TypeCapteurDonneeEmbedId(1, 1);

        // Appel GET pour récupérer le type capteur donnee par son ID
        ResponseEntity<TypeCapteurDonnee> response = restTemplate.getForEntity(getBaseUrl() + "/" + id.getIdDonnee() + "/" + id.getIdTypeCapteur(), TypeCapteurDonnee.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getDonneeNavigation().getId()).isEqualTo(1);
        assertThat(response.getBody().getTypeCapteurNavigation().getId()).isEqualTo(1);

    }

    /**
     * Teste la création d'un type capteur donnee via l'API.
     */
    @Test
    void testSaveTypeCapteurDonnee() {
        // Crée un nouveau type capteur donnee
        TypeCapteurDonnee typeCapteurDonnee = createTypeCapteurDonnee(1, 3);
        id = new TypeCapteurDonneeEmbedId(1, 3);

        
        assertThat(typeCapteurDonnee.getId()).isNotNull(); // Vérifie que l'ID a été généré
        assertThat(typeCapteurDonnee.getId()).isEqualTo(id);
        assertThat(typeCapteurDonnee.getDonneeNavigation().getId()).isEqualTo(1); // Vérifie que l'ID donnee est bien le bon
        assertThat(typeCapteurDonnee.getTypeCapteurNavigation().getId()).isEqualTo(3); // Vérifie que l'ID type capteur est bien le bon
  
        // Nettoyage : suppression du type d'équipement créé
        restTemplate.delete(getBaseUrl() + "/" + id.getIdDonnee() + "/" + id.getIdTypeCapteur());
    }

    /**
     * Teste la mise à jour d'un type capteur donnee via l'API.
     */
    @Test
    void testUpdateTypeCapteurDonnee() {
        // Crée un type capteur donnee à mettre à jour
        TypeCapteurDonnee typeCapteurDonnee = createTypeCapteurDonnee(1, 3);
        
        TypeCapteur typeCapteur = new TypeCapteur();
        typeCapteur.setId(4);
        // Modifie l'id du type capteur
        typeCapteurDonnee.setTypeCapteurNavigation(typeCapteur);

        id = new TypeCapteurDonneeEmbedId(1, typeCapteur.getId());
        typeCapteurDonnee.setId(id);
        

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TypeCapteurDonnee> entity = new HttpEntity<>(typeCapteurDonnee, headers);

        // Appel PUT pour mettre à jour le type capteur donnee
        ResponseEntity<TypeCapteurDonnee> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, TypeCapteurDonnee.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getTypeCapteurNavigation().getId()).isEqualTo(4);

        // Nettoyage : suppression du type cpateur donnee créé
        //restTemplate.delete(getBaseUrl() + "/" + id.getIdDonnee() + "/" + id.getIdTypeCapteur());
    }

    /**
     * Teste la suppression d'un type capteur donnee via l'API.
     */
    @Test
    void testDeleteTypeCapteurDonneeById() {
        // Crée un type capteur donnee à supprimer
        createTypeCapteurDonnee(1, 3);
        id = new TypeCapteurDonneeEmbedId(1, 3);

        // Appel DELETE pour supprimer le type capteur donnee
        restTemplate.delete(getBaseUrl() + "/" + id.getIdDonnee() + "/" + id.getIdTypeCapteur());

        // Vérifie que le type capteur donnee n'existe plus
        ResponseEntity<TypeCapteurDonnee> response = restTemplate.getForEntity(getBaseUrl() + "/" + id.getIdDonnee() + "/" + id.getIdTypeCapteur(), TypeCapteurDonnee.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
