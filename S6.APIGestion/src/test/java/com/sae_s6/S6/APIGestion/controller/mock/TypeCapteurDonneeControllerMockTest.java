package com.sae_s6.S6.APIGestion.controller.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.controller.TypeCapteurDonneeController;
import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonneeEmbedId;
import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.service.TypeCapteurDonneeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

/**
 * Classe de test unitaire pour le contrôleur TypeCapteurDonneeController.
 * Utilise Mockito pour simuler le service TypeCapteurDonneeService.
 */
@SpringBootTest
@AutoConfigureMockMvc
class TypeCapteurDonneeControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TypeCapteurDonneeService typeCapteurDonneeService;

    @InjectMocks
    private TypeCapteurDonneeController typeCapteurDonneeController;

    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la récupération de toutes les associations TypeCapteurDonnee (cas succès).
     */
    @Test
    void getAllTypeCapteurDonnees_ReturnsList_WhenNotNull() {
        TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        when(typeCapteurDonneeService.getAllTypeCapteurDonnee())
                .thenReturn(Arrays.asList(typeCapteurDonnee));

        ResponseEntity<List<TypeCapteurDonnee>> response = typeCapteurDonneeController.getAllTypeCapteurDonnees();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    /**
     * Teste la récupération de toutes les associations TypeCapteurDonnee (cas liste nulle).
     */
    @Test
    void getAllTypeCapteurDonnees_ReturnsBadRequest_WhenNull() {
        when(typeCapteurDonneeService.getAllTypeCapteurDonnee()).thenReturn(null);

        ResponseEntity<List<TypeCapteurDonnee>> response = typeCapteurDonneeController.getAllTypeCapteurDonnees();

        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Teste la récupération d'une association TypeCapteurDonnee par son identifiant (cas succès).
     */
    @Test
    void getTypeCapteurDonneeById_ReturnsTypeCapteurDonnee_WhenFound() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(typeCapteurDonnee);

        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.getTypeCapteurDonneeById(1, 2);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    /**
     * Teste la récupération d'une association TypeCapteurDonnee par son identifiant (cas non trouvé).
     */
    @Test
    void getTypeCapteurDonneeById_ReturnsBadRequest_WhenNotFound() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(null);

        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.getTypeCapteurDonneeById(1, 2);

        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Teste la création d'une association TypeCapteurDonnee (cas succès).
     */
    @Test
    void saveTypeCapteurDonnee_ReturnsTypeCapteurDonnee_WhenSaved() {
        TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        when(typeCapteurDonneeService.saveTypeCapteurDonnee(typeCapteurDonnee)).thenReturn(typeCapteurDonnee);

        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.saveTypeCapteurDonnee(typeCapteurDonnee);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    /**
     * Teste la création d'une association TypeCapteurDonnee (cas échec).
     */
    @Test
    void saveTypeCapteurDonnee_ReturnsBadRequest_WhenNotSaved() {
        TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        when(typeCapteurDonneeService.saveTypeCapteurDonnee(typeCapteurDonnee)).thenReturn(null);

        ResponseEntity<TypeCapteurDonnee> response = typeCapteurDonneeController.saveTypeCapteurDonnee(typeCapteurDonnee);

        assertEquals(400, response.getStatusCodeValue());
    }

    /**
     * Teste la mise à jour d'une association TypeCapteurDonnee (cas succès).
     * @throws Exception 
     * @throws JsonProcessingException 
     */
    @Test
    void updateTypeCapteurDonnee_ReturnsUpdated_WhenUpdated() throws JsonProcessingException, Exception {
        // Arrange : Crée l'entité initiale
    TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
    Donnee donnee = new Donnee();
    donnee.setId(1);
    // Modifie l'id du type capteur
    typeCapteurDonnee.setDonneeNavigation(donnee);

    TypeCapteur typeCapteur = new TypeCapteur();
    typeCapteur.setId(2);
    // Modifie l'id du type capteur
    typeCapteurDonnee.setTypeCapteurNavigation(typeCapteur);

    TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(donnee.getId(), typeCapteur.getId());
    typeCapteurDonnee.setId(id);
    Integer firstDonneeId = typeCapteurDonnee.getDonneeNavigation().getId();
    Integer firstTypeCapteurId = typeCapteurDonnee.getTypeCapteurNavigation().getId();


    // Prépare les nouvelles données (modification)
    Donnee newDonnee = new Donnee();
    newDonnee.setId(2);

    TypeCapteur newTypeCapteur = new TypeCapteur();
    newTypeCapteur.setId(1);

    TypeCapteurDonneeEmbedId newId = new TypeCapteurDonneeEmbedId(newDonnee.getId(), newTypeCapteur.getId());

    TypeCapteurDonnee updatedTypeCapteurDonnee = new TypeCapteurDonnee();
    updatedTypeCapteurDonnee.setId(newId);
    updatedTypeCapteurDonnee.setDonneeNavigation(newDonnee);
    updatedTypeCapteurDonnee.setTypeCapteurNavigation(newTypeCapteur);

    // Mock du service
    when(typeCapteurDonneeService.updateTypeCapteurDonnee(eq(id), any(TypeCapteurDonnee.class)))
        .thenReturn(updatedTypeCapteurDonnee);

    // Act : Appel simulé du contrôleur
    MvcResult result = mockMvc.perform(put("/api/typecapteurdonnee/" + firstDonneeId + "/" + firstTypeCapteurId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedTypeCapteurDonnee))
            .accept(MediaType.APPLICATION_JSON))
            .andReturn();

    // Assert
    String responseBody = result.getResponse().getContentAsString();
    TypeCapteurDonnee resultat = objectMapper.readValue(responseBody, TypeCapteurDonnee.class);

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(resultat.getId()).isEqualTo(newId);
    assertThat(resultat.getDonneeNavigation().getId()).isEqualTo(2);
    assertThat(resultat.getTypeCapteurNavigation().getId()).isEqualTo(1);
    }

    /**
     * Teste la suppression d'une association TypeCapteurDonnee par son identifiant (cas succès).
     */
    @Test
    void deleteTypeCapteurDonneeById_ReturnsOk_WhenFoundAndDeleted() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(typeCapteurDonnee);
        doNothing().when(typeCapteurDonneeService).deleteTypeCapteurDonneeById(id);

        ResponseEntity<String> response = typeCapteurDonneeController.deleteTypeCapteurDonneeById(1, 2);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Association TypeCapteurDonnee supprimée avec succès", response.getBody());
    }

    /**
     * Teste la suppression d'une association TypeCapteurDonnee par son identifiant (cas non trouvé).
     */
    @Test
    void deleteTypeCapteurDonneeById_ReturnsBadRequest_WhenNotFound() {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(1, 2);
        when(typeCapteurDonneeService.getTypeCapteurDonneeById(id)).thenReturn(null);

        ResponseEntity<String> response = typeCapteurDonneeController.deleteTypeCapteurDonneeById(1, 2);

        assertEquals(400, response.getStatusCodeValue());
    }
}