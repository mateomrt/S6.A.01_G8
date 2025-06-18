package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.CapteurService;
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

import java.lang.reflect.Type;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CapteurControllerTest {

    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/salle";
    }

    @Test
    void testPostCapteurAndGetAll() {

        // Création d'un capteur
        Capteur capteur = new Capteur();
        Mur mur = new Mur();
        Salle salle = new Salle();
        TypeCapteur typeCapteur = new TypeCapteur();
        capteur.setId(99); 
        capteur.setLibelleCapteur("Capteur Test");
        capteur.setPositionXCapteur(10.0);
        capteur.setPositionYCapteur(20.0);
        mur.setId(99);
        salle.setId(99);
        typeCapteur.setId(99);
        capteur.setMurNavigation(mur);
        capteur.setSalleNavigation(salle);
        capteur.setTypeCapteurNavigation(typeCapteur);
        // Envoi de la requête POST pour créer le capteur
        this.restTemplate.postForObject(getBaseUrl(), capteur,String.class);
        // Récupération de tous les capteurs
        assertThat(this.restTemplate.getForObject(getBaseUrl(), Capteur[].class))
        .satisfies(capteurs -> {
            assertThat(capteurs).isNotEmpty();
            assertThat(capteurs[0].getLibelleCapteur()).isEqualTo("Capteur Test");
            assertThat(capteurs[0].getPositionXCapteur()).isEqualTo(10.0);
            assertThat(capteurs[0].getPositionYCapteur()).isEqualTo(20.0);
            assertThat(capteurs[0].getMurNavigation().getId()).isEqualTo(99);
            assertThat(capteurs[0].getSalleNavigation().getId()).isEqualTo(99);
            assertThat(capteurs[0].getTypeCapteurNavigation().getId()).isEqualTo(99);
        });
    }


    @Test
    public void testCreateAndGetCapteur() {
    }

    @Test
    public void testUpdateCapteur() {
    }

    @Test
    public void testDeleteCapteur() {
    }
}