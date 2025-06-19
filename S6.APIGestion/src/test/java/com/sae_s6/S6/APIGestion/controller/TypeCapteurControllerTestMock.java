package com.sae_s6.S6.APIGestion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.TypeCapteurService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Arrays;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TypeCapteurControllerTestMock {

   @Mock
    private TypeCapteurService typeCapteurService;

    @InjectMocks
    private TypeCapteurController typeCapteurController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void getTypeCapteurById_ReturnsBadRequest_WhenNotFound() {
        when(typeCapteurService.getTypeCapteurById(1)).thenReturn(null);

        ResponseEntity<TypeCapteur> response = typeCapteurController.getTypeCapteurById(1);

        assertEquals(400, response.getStatusCodeValue());
    }



    @Test
    void saveTypeCapteur_ReturnsBadRequest_WhenNotSaved() {
        TypeCapteur typeCapteur = new TypeCapteur();
        when(typeCapteurService.saveTypeCapteur(typeCapteur)).thenReturn(null);

        ResponseEntity<TypeCapteur> response = typeCapteurController.saveTypeCapteur(typeCapteur);

        assertEquals(400, response.getStatusCodeValue());
    }



    @Test
    void updateTypeCapteur_ReturnsBadRequest_WhenNotFound() {
        TypeCapteur typeCapteur = new TypeCapteur();
        when(typeCapteurService.updateTypeCapteur(typeCapteur)).thenReturn(null);

        ResponseEntity<TypeCapteur> response = typeCapteurController.updateTypeCapteur(typeCapteur);

        assertEquals(400, response.getStatusCodeValue());
    }



    @Test
    void deleteTypeCapteurById_ReturnsBadRequest_WhenNotFound() {
        when(typeCapteurService.getTypeCapteurById(1)).thenReturn(null);

        ResponseEntity<String> response = typeCapteurController.deleteTypeCapteurById(1);

        assertEquals(400, response.getStatusCodeValue());
    }
}