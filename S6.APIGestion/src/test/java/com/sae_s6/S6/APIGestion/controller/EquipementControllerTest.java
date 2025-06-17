package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.Equipement;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeEquipement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EquipementControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/equipement";
    }

    // Méthode utilitaire pour créer un Equipement
    private Equipement createEquipement(Integer id, String titre, Integer hauteur, Integer largeur, Integer position_x, Integer position_y) {
        // Crée un mur fictif
        Mur mur = new Mur();
        mur.setId(1); // Doit exister dans la base ou avoir un contrôleur pour le créer

        // Crée une salle fictive
        Salle salle = new Salle();
        salle.setId(1); // Doit exister également ou être créé en amont

        // Crée un type équipement fictif
        TypeEquipement typeEquipement = new TypeEquipement();
        typeEquipement.setId(1); // Doit exister également ou être créé en amont
        
        Equipement equipement = new Equipement();
        equipement.setId(id);
        equipement.setLibelleEquipement(titre);
        equipement.setHauteur(hauteur); 
        equipement.setLargeur(largeur); 
        equipement.setPosition_x(position_x); 
        equipement.setPosition_y(position_y); 
        equipement.setMurNavigation(mur); 
        equipement.setSalleNavigation(salle); 
        equipement.setTypeEquipementNavigation(typeEquipement); 

        ResponseEntity<Equipement> response = restTemplate.postForEntity(getBaseUrl() + "/", equipement, Equipement.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    @Test
    void testGetAllEquipements() {
        createEquipement(1, "PC principal", 5, 5, 100, 200); // Crée un équipement pour s'assurer qu'on a au moins un en base

        ResponseEntity<Equipement[]> response = restTemplate.getForEntity(getBaseUrl() + "/", Equipement[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }

    @Test
    void testGetEquipementById() {
        Equipement equipement = createEquipement(100, "Equipement B", 5, 5, 100, 200); 
        Integer id = equipement.getId();

        ResponseEntity<Equipement> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Equipement.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getLibelleEquipement()).isEqualTo("Equipement B");
        assertThat(response.getBody().getMurNavigation()).isNotNull(); 
        assertThat(response.getBody().getSalleNavigation()).isNotNull(); 
        assertThat(response.getBody().getTypeEquipementNavigation()).isNotNull(); 
    }

    @Test
    void testSaveEquipement() {
        Equipement equipement = createEquipement(100, "Equipement C", 5, 5, 100, 200); 
        assertThat(equipement.getId()).isNotNull(); // Vérifie que l'ID a été généré
        assertThat(equipement.getLibelleEquipement()).isEqualTo("Type C");
        assertThat(equipement.getHauteur()).isGreaterThan(0); // Vérifie que la hauteur est positive
        assertThat(equipement.getLargeur()).isGreaterThan(0); // Vérifie que la largeur est positive
        assertThat(equipement.getPosition_x()).isGreaterThan(0); // Vérifie que la position_x est positive
        assertThat(equipement.getPosition_y()).isGreaterThan(0); // Vérifie que la postion_y est positive
        assertThat(equipement.getMurNavigation()).isNotNull(); // Vérifie que le mur n'est pas nulle
        assertThat(equipement.getSalleNavigation()).isNotNull(); // Vérifie que la salle n'est pas nulle
        assertThat(equipement.getTypeEquipementNavigation()).isNotNull(); // Vérifie que le TypeEquipement n'est pas nulle
    }

    @Test
    void testUpdateEquipement() {
        Equipement equipement = createEquipement(100, "Equipement D", 5, 5, 100, 200); 
        equipement.setLibelleEquipement("Type D - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Equipement> entity = new HttpEntity<>(equipement, headers);

        ResponseEntity<Equipement> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, Equipement.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleEquipement()).isEqualTo("Type D - MAJ");
        assertThat(response.getBody().getId()).isEqualTo(equipement.getId()); // Vérifie que l'ID est inchangé
        assertThat(response.getBody().getId()).isGreaterThan(0); // Vérifie que l'ID est positif
    }

    @Test
    void testDeleteEquipementById() {
        Equipement equipement = createEquipement(100, "Equipement E", 5, 5, 100, 200); 
        Integer id = equipement.getId();

        restTemplate.delete(getBaseUrl() + "/" + id);

        ResponseEntity<Equipement> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Equipement.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
