package com.sae_s6.S6.APIGestion.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.service.BatimentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class BatimentControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BatimentService batimentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllBatiments() throws Exception {
        Batiment batiment1 = new Batiment(1, "Bâtiment A");
        Batiment batiment2 = new Batiment(2, "Bâtiment B");
        List<Batiment> batiments = Arrays.asList(batiment1, batiment2);

        Mockito.when(batimentService.getAllBatiments())
                    .thenReturn(batiments);

        // Act
        MvcResult result = mockMvc.perform(get("/api/batiment/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        List<Batiment> resultat = objectMapper.readValue(json, new TypeReference<List<Batiment>>() {});
        assertThat(resultat).isNotEmpty();
        assertThat(resultat.get(0).getLibelleBatiment()).isEqualTo("Bâtiment A");
        assertThat(resultat.get(1).getLibelleBatiment()).isEqualTo("Bâtiment B");
    }

    @Test
    void testGetBatimentById() throws Exception {
        // Arrange
        int batimentId = 1;
        Batiment batiment = new Batiment(batimentId, "Bâtiment A");

        Mockito.when(batimentService.getBatimentById(batimentId))
                    .thenReturn(batiment);

        // Act
        MvcResult result = mockMvc.perform(get("/api/batiment/" + batimentId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Batiment resultat = objectMapper.readValue(json, Batiment.class);
        assertThat(resultat.getId()).isEqualTo(batimentId);
        assertThat(resultat.getLibelleBatiment()).isEqualTo("Bâtiment A"); // Assuming "Bâtiment A" is the expected title
    }    

    @Test
    void testCreateBatiment() throws Exception {
        // Arrange
        Batiment batiment = new Batiment(3, "Batiment C");

        // Act
        MvcResult result = mockMvc.perform(post("/batiments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(batiment)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Batiment createdBatiment = objectMapper.readValue(json, Batiment.class);
        assertThat(createdBatiment.getId()).isEqualTo(3);
        assertThat(createdBatiment.getLibelleBatiment()).isEqualTo("Batiment C");
    }

    @Test
    void testUpdateBatiment() throws Exception {
        // Arrange
        Batiment updatedBatiment = new Batiment(1, "Batiment A Updated");

        // Act
        MvcResult result = mockMvc.perform(put("/batiments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBatiment)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Batiment batiment = objectMapper.readValue(json, Batiment.class);
        assertThat(batiment.getId()).isEqualTo(1);
        assertThat(batiment.getLibelleBatiment()).isEqualTo("Batiment A Updated");
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