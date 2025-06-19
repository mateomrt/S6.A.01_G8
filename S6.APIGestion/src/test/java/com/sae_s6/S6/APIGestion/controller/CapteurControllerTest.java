package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.CapteurService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CapteurControllerTest {

    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/capteur/";
    }
    private List<Integer> capteursCreeIds = new ArrayList<>();

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
    
        // POST
        ResponseEntity<Capteur> postResponse = restTemplate.postForEntity(getBaseUrl(), capteur, Capteur.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Capteur capteurCree = postResponse.getBody();
        assertThat(capteurCree).isNotNull(); 
        assertThat(capteurCree.getId()).isNotNull();
        capteursCreeIds.add(capteurCree.getId());
    
        // GET
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

    @Test
    public void testGetAllCapteurs() {
        ResponseEntity<Capteur[]> response = restTemplate.getForEntity(getBaseUrl(), Capteur[].class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Capteur[] capteurs = response.getBody();
        
        assertThat(capteurs).isNotNull();
        assertThat(capteurs.length).isGreaterThan(0);
    }

    @Test
    void testUpdateCapteur() {
        // Création du capteur initial
        Capteur capteur = new Capteur();
        capteur.setLibelleCapteur("Capteur Initial");
        capteur.setPositionXCapteur(2.0);
        capteur.setPositionYCapteur(3.0);
    
        Mur mur = new Mur(); mur.setId(1);
        Salle salle = new Salle(); salle.setId(1);
        TypeCapteur type = new TypeCapteur(); type.setId(1);
    
        capteur.setMurNavigation(mur);
        capteur.setSalleNavigation(salle);
        capteur.setTypeCapteurNavigation(type);
    
        ResponseEntity<Capteur> post = restTemplate.postForEntity(getBaseUrl(), capteur, Capteur.class);
        Capteur created = post.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        capteursCreeIds.add(created.getId());
    
        // Modification
        created.setLibelleCapteur("Capteur Modifié");
        created.setPositionXCapteur(42.0);
    
        HttpEntity<Capteur> requestEntity = new HttpEntity<>(created);
        ResponseEntity<Capteur> response = restTemplate.exchange(getBaseUrl(), HttpMethod.PUT, requestEntity, Capteur.class);
    
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Capteur updated = response.getBody();
        assertThat(updated).isNotNull();
        assertThat(updated.getLibelleCapteur()).isEqualTo("Capteur Modifié");
        assertThat(updated.getPositionXCapteur()).isEqualTo(42.0);
    }

    @Test
    void testDeleteCapteur() {
        // Création
        Capteur capteur = new Capteur();
        capteur.setLibelleCapteur("Capteur à Supprimer");
        capteur.setPositionXCapteur(1.0);
        capteur.setPositionYCapteur(1.0);
    
        Mur mur = new Mur(); mur.setId(1);
        Salle salle = new Salle(); salle.setId(1);
        TypeCapteur type = new TypeCapteur(); type.setId(1);
    
        capteur.setMurNavigation(mur);
        capteur.setSalleNavigation(salle);
        capteur.setTypeCapteurNavigation(type);
    
        ResponseEntity<Capteur> post = restTemplate.postForEntity(getBaseUrl(), capteur, Capteur.class);
        Capteur created = post.getBody();
        assertThat(created).isNotNull();
        capteursCreeIds.add(created.getId());
    
        // DELETE
        restTemplate.delete(getBaseUrl() + created.getId());
    
        // Vérification que le capteur n'existe plus
        ResponseEntity<Capteur> getResponse = restTemplate.getForEntity(getBaseUrl() + created.getId(), Capteur.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
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