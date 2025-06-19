package com.sae_s6.S6.APIGestion.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.service.SalleService;

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
public class SalleControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SalleService salleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllSalles() throws Exception {
        // Arrange
        Salle salle1 = new Salle(1, "Salle 101", 30.0, null, null);
        Salle salle2 = new Salle(2, "Salle 102", 25.0, null, null);
        
        List<Salle> salles = Arrays.asList(salle1, salle2);

        Mockito.when(salleService.getAllSalles())
                .thenReturn(salles);

        // Act
        MvcResult result = mockMvc.perform(get("/api/salle/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        List<Salle> resultat = objectMapper.readValue(json, new TypeReference<List<Salle>>() {});
        assertThat(resultat).isNotEmpty();
        assertThat(resultat.get(0).getLibelleSalle()).isEqualTo("Salle 101");
        assertThat(resultat.get(1).getLibelleSalle()).isEqualTo("Salle 102");
    }

    @Test
    void testGetSalleById() throws Exception {
        // Arrange
        int salleId = 1;
        Salle salle = new Salle(1, "Salle 101", 30.0, null, null);

        Mockito.when(salleService.getSalleById(salleId))
                .thenReturn(salle);

        // Act
        MvcResult result = mockMvc.perform(get("/api/salle/" + salleId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Salle resultat = objectMapper.readValue(json, Salle.class);
        assertThat(resultat.getId()).isEqualTo(salleId);
        assertThat(resultat.getLibelleSalle()).isEqualTo("Salle 101");
    }

    @Test
    void testCreateSalle() throws Exception {
        // Arrange
        Salle newSalle = new Salle(10, "Salle 105", 40.0, null, null);

        Mockito.when(salleService.saveSalle(Mockito.any()))
                .thenReturn(newSalle);

        // Act
        MvcResult result = mockMvc.perform(post("/api/salle/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newSalle)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Salle createdSalle = objectMapper.readValue(json, Salle.class);
        assertThat(createdSalle.getId()).isEqualTo(10);
        assertThat(createdSalle.getLibelleSalle()).isEqualTo("Salle 105");
    }

    @Test
    void testUpdateSalle() throws Exception {
        // Arrange
        Salle updatedSalle = new Salle(1, "Salle 101 Modifiée", 35.0, null, null);

        Mockito.when(salleService.updateSalle(Mockito.any()))
                .thenReturn(updatedSalle);

        // Act
        MvcResult result = mockMvc.perform(put("/api/salle/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSalle)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Salle salle = objectMapper.readValue(json, Salle.class);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(salle.getId()).isEqualTo(1);
        assertThat(salle.getLibelleSalle()).isEqualTo("Salle 101 Modifiée");
    }

    @Test
    void testDeleteSalle() throws Exception {
        // Arrange
        int salleId = 1;
        Salle salle = new Salle(salleId, "Salle 101", 30.0, null, null);

        Mockito.when(salleService.getSalleById(salleId))
                .thenReturn(salle);

        Mockito.doNothing().when(salleService).deleteSalleById(salleId);

        // Act
        MvcResult result = mockMvc.perform(delete("/api/salle/" + salleId)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testGetSallesByLibelleSalle() throws Exception {
        // Arrange
        String libelleSalle = "Salle 101";
        Salle salle = new Salle(1, libelleSalle, 30.0, null, null);
        List<Salle> salles = Arrays.asList(salle);

        Mockito.when(salleService.getSallesByLibelleSalle(libelleSalle))
                .thenReturn(salles);

        // Act
        MvcResult result = mockMvc.perform(get("/api/salle/search")
                .param("libelleSalle", libelleSalle)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        List<Salle> resultat = objectMapper.readValue(json, new TypeReference<List<Salle>>() {});
        assertThat(resultat).isNotEmpty();
        assertThat(resultat.get(0).getLibelleSalle()).isEqualTo(libelleSalle);
    }
}
