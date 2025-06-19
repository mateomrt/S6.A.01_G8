package com.sae_s6.S6.APIGestion.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Mur.Orientation;
import com.sae_s6.S6.APIGestion.service.MurService;

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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MurControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MurService murService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllMurs() throws Exception {
        // Arrange
        Mur mur1 = new Mur(1, "Mur nord", 250.0, 600.0, Orientation.N, null);
        Mur mur2 = new Mur(2, "Mur sud", 250.0, 600.0, Orientation.S, null);
        
        List<Mur> murs = Arrays.asList(mur1, mur2);

        Mockito.when(murService.getAllMurs())
                .thenReturn(murs);

        // Act
        MvcResult result = mockMvc.perform(get("/api/murs/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        List<Mur> resultat = objectMapper.readValue(json, new TypeReference<List<Mur>>() {});
        assertThat(resultat).isNotEmpty();
        assertThat(resultat.get(0).getLibelleMur()).isEqualTo("Mur nord");
        assertThat(resultat.get(1).getLibelleMur()).isEqualTo("Mur sud");
    }

    @Test
    void testGetMurById() throws Exception {
        // Arrange
        int murId = 1;
        Mur mur = new Mur(1, "Mur nord", 250.0, 600.0, Orientation.N, null);

        Mockito.when(murService.getMurById(murId))
                .thenReturn(mur);

        // Act
        MvcResult result = mockMvc.perform(get("/api/murs/" + murId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Mur resultat = objectMapper.readValue(json, Mur.class);
        assertThat(resultat.getId()).isEqualTo(murId);
        assertThat(resultat.getLibelleMur()).isEqualTo("Mur nord");
    }

    @Test
    void testCreateMur() throws Exception {
        // Arrange
        Mur newMur = new Mur(3, "Mur sud", 250.0, 600.0, Orientation.S, null);

        Mockito.when(murService.saveMur(Mockito.any()))
                .thenReturn(newMur);

        // Act
        MvcResult result = mockMvc.perform(post("/api/murs/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMur)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Mur createdMur = objectMapper.readValue(json, Mur.class);
        assertThat(createdMur.getId()).isEqualTo(3);
        assertThat(createdMur.getLibelleMur()).isEqualTo("Mur sud");
    }

    @Test
    void testUpdateMur() throws Exception {
        // Arrange
        Mur updatedMur = new Mur(1, "Mur nord - update", 250.0, 600.0, Orientation.N, null);

        Mockito.when(murService.updateMur(Mockito.any()))
                .thenReturn(updatedMur);

        // Act
        MvcResult result = mockMvc.perform(put("/api/murs/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedMur)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Mur mur = objectMapper.readValue(json, Mur.class);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(mur.getId()).isEqualTo(1);
        assertThat(mur.getLibelleMur()).isEqualTo("Mur nord - update");
    }

    @Test
    void testDeleteMur() throws Exception {
        // Arrange
        int murId = 1;
        Mur mur = new Mur(1, "Mur nord", 250.0, 600.0, Orientation.N, null);

        Mockito.when(murService.getMurById(murId))
                .thenReturn(mur);

        Mockito.doNothing().when(murService).deleteMurById(murId);

        // Act
        MvcResult result = mockMvc.perform(delete("/api/mursss/" + murId)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}