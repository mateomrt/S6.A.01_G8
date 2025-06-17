package com.sae_s6.S6.APIGestion.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.TypeEquipement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TypeEquipementControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    

    @Test
    void testGetAllTypeEquipements() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(get("/api/typeequipement/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        TypeEquipement[] typeEquipements = objectMapper.readValue(json, TypeEquipement[].class);
        assertThat(typeEquipements).isNotEmpty();
        assertThat(typeEquipements[0].getLibelleTypeEquipement()).isEqualTo("Ordinateur");
        assertThat(typeEquipements[1].getLibelleTypeEquipement()).isEqualTo("Projecteur");
    }

    @Test
    void testGetTypeEquipementById() throws Exception {
        // Arrange
        int typeEquipementId = 1;

        // Act
        MvcResult result = mockMvc.perform(get("/api/typeequipement/" + typeEquipementId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        TypeEquipement typeEquipement = objectMapper.readValue(json, TypeEquipement.class);
        assertThat(typeEquipement.getId()).isEqualTo(typeEquipementId);
        assertThat(typeEquipement.getLibelleTypeEquipement()).isEqualTo("Ordinateur"); // Assuming "Ordinateur" is the expected title
    }

    @Test
    void testCreateTypeEquipement() throws Exception {
        // Arrange
        TypeEquipement typeEquipement = new TypeEquipement(3, "Peripherique", null);

        // Act
        MvcResult result = mockMvc.perform(post("/api/typeequipement/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(typeEquipement)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        TypeEquipement createdTypeEquipement = objectMapper.readValue(json, TypeEquipement.class);
        assertThat(createdTypeEquipement.getId()).isEqualTo(3);
        assertThat(createdTypeEquipement.getLibelleTypeEquipement()).isEqualTo("Peripherique");
    }

    @Test
    void testUpdateTypeEquipement() throws Exception {
        // Arrange
        TypeEquipement updatedTypeEquipement = new TypeEquipement(1, "PC", null);

        // Act
        MvcResult result = mockMvc.perform(put("/api/typeequipement/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTypeEquipement)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        TypeEquipement typeEquipement = objectMapper.readValue(json, TypeEquipement.class);
        assertThat(typeEquipement.getId()).isEqualTo(1);
        assertThat(typeEquipement.getLibelleTypeEquipement()).isEqualTo("PC");
    }

    @Test
    void testDeleteTypeEquipement() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(delete("/api/typeequipement/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }
}
