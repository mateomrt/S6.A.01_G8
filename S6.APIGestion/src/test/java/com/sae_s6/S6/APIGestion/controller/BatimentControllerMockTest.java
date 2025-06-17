package com.sae_s6.S6.APIGestion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.Batiment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BatimentControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetBatimentById() throws Exception {
        // Arrange
        int batimentId = 1;

        // Act
        MvcResult result = mockMvc.perform(get("/batiments/" + batimentId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Batiment batiment = objectMapper.readValue(json, Batiment.class);
        assertThat(batiment.getId()).isEqualTo(batimentId);
        assertThat(batiment.getTitre()).isEqualTo("Batiment A"); // Assuming "Batiment A" is the expected title
    }

    @Test
    void testGetAllBatiments() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(get("/batiments")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Batiment[] batiments = objectMapper.readValue(json, Batiment[].class);
        assertThat(batiments).isNotEmpty();
        assertThat(batiments[0].getTitre()).isEqualTo("Batiment A");
        assertThat(batiments[1].getTitre()).isEqualTo("Batiment B");
    }

    @Test
    void testCreateBatiment() throws Exception {
        // Arrange
        Batiment batiment = new Batiment(3, "Batiment C", null);

        // Act
        MvcResult result = mockMvc.perform(post("/batiments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(batiment)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Batiment createdBatiment = objectMapper.readValue(json, Batiment.class);
        assertThat(createdBatiment.getId()).isEqualTo(3);
        assertThat(createdBatiment.getTitre()).isEqualTo("Batiment C");
    }

    @Test
    void testUpdateBatiment() throws Exception {
        // Arrange
        Batiment updatedBatiment = new Batiment(1, "Batiment A Updated", null);

        // Act
        MvcResult result = mockMvc.perform(put("/batiments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBatiment)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Batiment batiment = objectMapper.readValue(json, Batiment.class);
        assertThat(batiment.getId()).isEqualTo(1);
        assertThat(batiment.getTitre()).isEqualTo("Batiment A Updated");
    }

    @Test
    void testDeleteBatiment() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(delete("/batiments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }
}