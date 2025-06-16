package com.sae_s6.S6.APIGestion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.service.BatimentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BatimentController.class)
class BatimentControllerTestMock {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BatimentService batimentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllBatiments() throws Exception {
        // Arrange
        Batiment batiment1 = new Batiment(1, "Batiment A", null);
        Batiment batiment2 = new Batiment(2, "Batiment B", null);
        when(batimentService.getAllBatiments()).thenReturn(Arrays.asList(batiment1, batiment2));

        // Act & Assert
        mockMvc.perform(get("/batiments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].titre", is("Batiment A")))
                .andExpect(jsonPath("$[1].titre", is("Batiment B")));

        verify(batimentService, times(1)).getAllBatiments();
    }

    @Test
    void testGetBatimentById() throws Exception {
        // Arrange
        Batiment batiment = new Batiment(1, "Batiment A", null);
        when(batimentService.getBatimentById(1)).thenReturn(Optional.of(batiment));

        // Act & Assert
        mockMvc.perform(get("/batiments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titre", is("Batiment A")));

        verify(batimentService, times(1)).getBatimentById(1);
    }

    @Test
    void testCreateBatiment() throws Exception {
        // Arrange
        Batiment batiment = new Batiment(1, "Batiment A", null);
        when(batimentService.createBatiment(any(Batiment.class))).thenReturn(batiment);

        // Act & Assert
        mockMvc.perform(post("/batiments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(batiment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titre", is("Batiment A")));

        verify(batimentService, times(1)).createBatiment(any(Batiment.class));
    }

    @Test
    void testUpdateBatiment() throws Exception {
        // Arrange
        Batiment updatedBatiment = new Batiment(1, "Batiment A Updated", null);
        when(batimentService.updateBatiment(eq(1), any(Batiment.class))).thenReturn(updatedBatiment);

        // Act & Assert
        mockMvc.perform(put("/batiments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBatiment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titre", is("Batiment A Updated")));

        verify(batimentService, times(1)).updateBatiment(eq(1), any(Batiment.class));
    }

    @Test
    void testDeleteBatiment() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/batiments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(batimentService, times(1)).deleteBatiment(1);
    }
}