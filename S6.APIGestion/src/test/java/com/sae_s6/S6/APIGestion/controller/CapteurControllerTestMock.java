package com.sae_s6.S6.APIGestion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.repository.CapteurRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CapteurControllerTestMock {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CapteurRepo capteurRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        capteurRepository.deleteAll();
    }

    @Test
    public void testGetAllCapteurs() throws Exception {
        Capteur capteur1 = new Capteur(null, "Capteur température", 10.0, 20.0, null, null, null);
        Capteur capteur2 = new Capteur(null, "Capteur humidité", 15.0, 25.0, null, null, null);
        capteurRepository.save(capteur1);
        capteurRepository.save(capteur2);

        mockMvc.perform(get("/api/capteur/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].libelleCapteur").value("Capteur température"))
                .andExpect(jsonPath("$[1].libelleCapteur").value("Capteur humidité"));
    }

    @Test
    public void testCreateCapteur() throws Exception {
        Capteur capteur = new Capteur(null, "Capteur luminosité", 10.0, 20.0, null, null, null);

        mockMvc.perform(post("/api/capteur/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(capteur)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.libelleCapteur").value("Capteur luminosité"));
    }

    @Test
    public void testUpdateCapteur() throws Exception {
        Capteur capteur = new Capteur(null, "Capteur pression", 10.0, 20.0, null, null, null);
        Capteur savedCapteur = capteurRepository.save(capteur);

        savedCapteur.setLibelleCapteur("Capteur pression modifié");

        mockMvc.perform(put("/api/capteur/" + savedCapteur.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedCapteur)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libelleCapteur").value("Capteur pression modifié"));
    }

    @Test
    public void testDeleteCapteur() throws Exception {
        Capteur capteur = new Capteur(null, "Capteur humidité", 10.0, 20.0, null, null, null);
        Capteur savedCapteur = capteurRepository.save(capteur);

        mockMvc.perform(delete("/api/capteur/" + savedCapteur.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}