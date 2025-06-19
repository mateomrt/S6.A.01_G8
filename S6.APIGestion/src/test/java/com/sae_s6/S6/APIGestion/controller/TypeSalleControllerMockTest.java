package com.sae_s6.S6.APIGestion.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.service.TypeSalleService;

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
public class TypeSalleControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TypeSalleService typeSalleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllTypeSalles() throws Exception {
        // Arrange
        TypeSalle typeSalle1 = new TypeSalle(1, "Amphithéâtre");
        TypeSalle typeSalle2 = new TypeSalle(2, "Laboratoire");
        List<TypeSalle> typeSalles = Arrays.asList(typeSalle1, typeSalle2);

        Mockito.when(typeSalleService.getAllTypeSalles())
                .thenReturn(typeSalles);

        // Act
        MvcResult result = mockMvc.perform(get("/api/typesalle/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        List<TypeSalle> resultat = objectMapper.readValue(json, new TypeReference<List<TypeSalle>>() {});
        assertThat(resultat).isNotEmpty();
        assertThat(resultat.get(0).getLibelleTypeSalle()).isEqualTo("Amphithéâtre");
        assertThat(resultat.get(1).getLibelleTypeSalle()).isEqualTo("Laboratoire");
    }

    @Test
    void testGetTypeSalleById() throws Exception {
        // Arrange
        int typeSalleId = 1;
        TypeSalle typeSalle = new TypeSalle(typeSalleId, "Amphithéâtre");

        Mockito.when(typeSalleService.getTypeSalleById(typeSalleId))
                .thenReturn(typeSalle);

        // Act
        MvcResult result = mockMvc.perform(get("/api/typesalle/" + typeSalleId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        TypeSalle resultat = objectMapper.readValue(json, TypeSalle.class);
        assertThat(resultat.getId()).isEqualTo(typeSalleId);
        assertThat(resultat.getLibelleTypeSalle()).isEqualTo("Amphithéâtre");
    }

    @Test
    void testCreateTypeSalle() throws Exception {
        // Arrange
        TypeSalle newTypeSalle = new TypeSalle(10, "Salle de Réunion");

        Mockito.when(typeSalleService.saveTypeSalle(Mockito.any()))
                .thenReturn(newTypeSalle);

        // Act
        MvcResult result = mockMvc.perform(post("/api/typesalle/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTypeSalle)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        TypeSalle createdTypeSalle = objectMapper.readValue(json, TypeSalle.class);
        assertThat(createdTypeSalle.getId()).isEqualTo(10);
        assertThat(createdTypeSalle.getLibelleTypeSalle()).isEqualTo("Salle de Réunion");
    }

    @Test
    void testUpdateTypeSalle() throws Exception {
        // Arrange
        TypeSalle updatedTypeSalle = new TypeSalle(1, "Salle Informatique");

        Mockito.when(typeSalleService.updateTypeSalle(Mockito.any()))
                .thenReturn(updatedTypeSalle);

        // Act
        MvcResult result = mockMvc.perform(put("/api/typesalle/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTypeSalle)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        TypeSalle typeSalle = objectMapper.readValue(json, TypeSalle.class);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(typeSalle.getId()).isEqualTo(1);
        assertThat(typeSalle.getLibelleTypeSalle()).isEqualTo("Salle Informatique");
    }

    @Test
    void testDeleteTypeSalle() throws Exception {
        // Arrange
        int typeSalleId = 1;
        TypeSalle typeSalle = new TypeSalle(typeSalleId, "Amphithéâtre");

        Mockito.when(typeSalleService.getTypeSalleById(typeSalleId))
                .thenReturn(typeSalle);

        Mockito.doNothing().when(typeSalleService).deleteTypeSalleById(typeSalleId);

        // Act
        MvcResult result = mockMvc.perform(delete("/api/typesalle/" + typeSalleId)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
