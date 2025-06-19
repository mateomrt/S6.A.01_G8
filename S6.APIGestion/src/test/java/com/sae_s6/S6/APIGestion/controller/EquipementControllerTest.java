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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
    private Equipement createEquipement(Integer id, String titre, Double hauteur, Double largeur, Double position_x, Double position_y) {
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

    private int createdEquipementId;

    @Test
    void testGetAllEquipements() {
        ResponseEntity<Equipement[]> response = restTemplate.getForEntity(getBaseUrl() + "/", Equipement[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
        List<Equipement> resultat = Arrays.asList(response.getBody());
        resultat.sort(Comparator.comparing(Equipement::getLibelleEquipement));
        assertThat(resultat.get(0).getLibelleEquipement()).isEqualTo("PC principal");
        assertThat(resultat.get(1).getLibelleEquipement()).isEqualTo("Vidéo projecteur");
    }

    @Test
    void testGetEquipementById() {
        Integer id = 1;

        ResponseEntity<Equipement> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Equipement.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getLibelleEquipement()).isEqualTo("PC principal");
    }

    @Test
    void testSaveEquipement() {
        Equipement equipement = createEquipement(null, "PC secondaire",  100.0, 200.0, 150.0, 250.0);
        createdEquipementId = equipement.getId();

        assertThat(equipement.getLibelleEquipement()).isEqualTo("PC secondaire");
        assertThat(equipement.getId()).isNotNull(); // Vérifie que l'ID a été généré
        assertThat(equipement.getId()).isGreaterThan(0); // Vérifie que l'ID est positif
        assertThat(equipement.getHauteur()).isGreaterThan(0); // Vérifie que la hauteur est positive
        assertThat(equipement.getLargeur()).isGreaterThan(0); // Vérifie que la largeur est positive
        assertThat(equipement.getPosition_x()).isGreaterThan(0); // Vérifie que la position_x est positive
        assertThat(equipement.getPosition_y()).isGreaterThan(0); // Vérifie que la postion_y est positive
        assertThat(equipement.getMurNavigation()).isNotNull(); // Vérifie que le mur n'est pas nulle
        assertThat(equipement.getSalleNavigation()).isNotNull(); // Vérifie que la salle n'est pas nulle
        assertThat(equipement.getTypeEquipementNavigation()).isNotNull(); // Vérifie que le TypeEquipement n'est pas nulle

        restTemplate.delete(getBaseUrl() + "/" + createdEquipementId);
    }

    @Test
    void testUpdateEquipement() {
        Equipement equipement = createEquipement(null, "PC secondaire",  100.0, 200.0, 150.0, 250.0);
        createdEquipementId = equipement.getId();

        equipement.setLibelleEquipement("PC secondaire - MAJ");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Equipement> entity = new HttpEntity<>(equipement, headers);

        ResponseEntity<Equipement> response = restTemplate.exchange(
                getBaseUrl() + "/", HttpMethod.PUT, entity, Equipement.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLibelleEquipement()).isEqualTo("PC secondaire - MAJ");
        assertThat(response.getBody().getId()).isEqualTo(equipement.getId()); // Vérifie que l'ID est inchangé
        assertThat(response.getBody().getId()).isGreaterThan(0); // Vérifie que l'ID est positif
       
       restTemplate.delete(getBaseUrl() + "/" + createdEquipementId);    
    }

    @Test
    void testDeleteEquipementById() {
        Equipement equipement = createEquipement(null, "PC secondaire",  100.0, 200.0, 150.0, 250.0);
        Integer id = equipement.getId();

        restTemplate.delete(getBaseUrl() + "/" + id);

        ResponseEntity<Equipement> response = restTemplate.getForEntity(getBaseUrl() + "/" + id, Equipement.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
