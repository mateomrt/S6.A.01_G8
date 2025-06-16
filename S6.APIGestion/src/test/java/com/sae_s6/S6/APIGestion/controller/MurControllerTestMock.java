package com.sae_s6.S6.APIGestion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.service.MurService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@WebMvcTest(MurController.class)
class MurControllerTestMock {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MurService murService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private MurService murServiceMock;

    @InjectMocks
    private MurController murController;

    public MurControllerTestMock() {
        MockitoAnnotations.openMocks(this);
    }

    // Tests avec MockMvc
    @Test
    void testGetAllMursWithMockMvc() throws Exception {
        // Arrange
        Mur mur1 = new Mur(1, "Mur A", 300, 500, 1, null);
        Mur mur2 = new Mur(2, "Mur B", 400, 600, 2, null);
        when(murService.getAllMurs()).thenReturn(Arrays.asList(mur1, mur2));

        // Act & Assert
        mockMvc.perform(get("/murs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].titre", is("Mur A")))
                .andExpect(jsonPath("$[1].titre", is("Mur B")));

        verify(murService, times(1)).getAllMurs();
    }

    @Test
    void testGetMurByIdWithMockMvc() throws Exception {
        // Arrange
        Mur mur = new Mur(1, "Mur A", 300, 500, 1, null);
        when(murService.getMurById(1)).thenReturn(Optional.of(mur));

        // Act & Assert
        mockMvc.perform(get("/murs/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titre", is("Mur A")));

        verify(murService, times(1)).getMurById(1);
    }

    @Test
    void testCreateMurWithMockMvc() throws Exception {
        // Arrange
        Mur mur = new Mur(1, "Mur A", 300, 500, 1, null);
        when(murService.createMur(any(Mur.class))).thenReturn(mur);

        // Act & Assert
        mockMvc.perform(post("/murs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mur)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titre", is("Mur A")));

        verify(murService, times(1)).createMur(any(Mur.class));
    }

    @Test
    void testUpdateMurWithMockMvc() throws Exception {
        // Arrange
        Mur updatedMur = new Mur(1, "Mur A Updated", 350, 550, 1, null);
        when(murService.updateMur(eq(1), any(Mur.class))).thenReturn(updatedMur);

        // Act & Assert
        mockMvc.perform(put("/murs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedMur)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titre", is("Mur A Updated")));

        verify(murService, times(1)).updateMur(eq(1), any(Mur.class));
    }

    @Test
    void testDeleteMurWithMockMvc() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/murs/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(murService, times(1)).deleteMur(1);
    }

    // Tests avec Mockito
    @Test
    void testGetAllMurs() {
        // Arrange
        Mur mur1 = new Mur(1, "Mur A", 300, 500, 1, null);
        Mur mur2 = new Mur(2, "Mur B", 400, 600, 2, null);
        when(murServiceMock.getAllMurs()).thenReturn(Arrays.asList(mur1, mur2));

        // Act
        ResponseEntity<List<Mur>> response = murController.getAllMurs();

        // Assert
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        verify(murServiceMock, times(1)).getAllMurs();
    }

    @Test
    void testGetMurById() {
        // Arrange
        Mur mur = new Mur(1, "Mur A", 300, 500, 1, null);
        when(murServiceMock.getMurById(1)).thenReturn(Optional.of(mur));

        // Act
        ResponseEntity<Mur> response = murController.getMurById(1);

        // Assert
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getTitre()).isEqualTo("Mur A");
        verify(murServiceMock, times(1)).getMurById(1);
    }
}