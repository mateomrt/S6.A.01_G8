package com.sae_s6.S6.APIGestion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.Mur;
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
public class MurControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllMurs() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(get("/murs")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Mur[] murs = objectMapper.readValue(json, Mur[].class);
        assertThat(murs).isNotEmpty();
        assertThat(murs[0].getTitre()).isEqualTo("Mur A"); // Assuming "Mur A" is the first title
        assertThat(murs[1].getTitre()).isEqualTo("Mur B"); // Assuming "Mur B" is the second title
    }

    @Test
    void testGetMurById() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(get("/murs/1")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Mur mur = objectMapper.readValue(json, Mur.class);
        assertThat(mur.getId()).isEqualTo(1);
        assertThat(mur.getTitre()).isEqualTo("Mur A"); // Assuming "Mur A" is the expected title
    }

    @Test
    void testCreateMur() throws Exception {
        // Arrange
        Mur mur = new Mur(3, "Mur C", 300, 500, 1, null);

        // Act
        MvcResult result = mockMvc.perform(post("/murs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mur)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Mur createdMur = objectMapper.readValue(json, Mur.class);
        assertThat(createdMur.getId()).isEqualTo(3);
        assertThat(createdMur.getTitre()).isEqualTo("Mur C");
    }

    @Test
    void testUpdateMur() throws Exception {
        // Arrange
        Mur updatedMur = new Mur(1, "Mur A Updated", 350, 550, 1, null);

        // Act
        MvcResult result = mockMvc.perform(put("/murs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedMur)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Mur mur = objectMapper.readValue(json, Mur.class);
        assertThat(mur.getId()).isEqualTo(1);
        assertThat(mur.getTitre()).isEqualTo("Mur A Updated");
    }

    @Test
    void testDeleteMur() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(delete("/murs/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }
}