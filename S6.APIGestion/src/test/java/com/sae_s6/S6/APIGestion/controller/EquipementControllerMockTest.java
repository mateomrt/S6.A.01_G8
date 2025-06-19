package com.sae_s6.S6.APIGestion.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.Equipement;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.service.EquipementService;
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
public class EquipementControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EquipementService equipementService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllEquipements() throws Exception {
        // Arrange
        Equipement equipement1 = new Equipement(1, "PC principal", 50.0, 30.0, 100.0, 200.0, null, null, null);
        Equipement equipement2 = new Equipement(2, "Vidéo projecteur", 20.0, 40.0, 250.0, 100.0, null, null, null);
        
        List<Equipement> equipements = Arrays.asList(equipement1, equipement2);

        Mockito.when(equipementService.getAllEquipements())
                .thenReturn(equipements);

        // Act
        MvcResult result = mockMvc.perform(get("/api/equipement/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        List<Equipement> resultat = objectMapper.readValue(json, new TypeReference<List<Equipement>>() {});
        assertThat(resultat).isNotEmpty();
        assertThat(resultat.get(0).getLibelleEquipement()).isEqualTo("PC principal");
        assertThat(resultat.get(1).getLibelleEquipement()).isEqualTo("Vidéo projecteur");
    }

    @Test
    void testGetEquipementById() throws Exception {
        // Arrange
        int equipementId = 1;
        Equipement equipement = new Equipement(1, "PC principal", 50.0, 30.0, 100.0, 200.0, null, null, null);

        Mockito.when(equipementService.getEquipementById(equipementId))
                .thenReturn(equipement);

        // Act
        MvcResult result = mockMvc.perform(get("/api/equipement/" + equipementId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Equipement resultat = objectMapper.readValue(json, Equipement.class);
        assertThat(resultat.getId()).isEqualTo(equipementId);
        assertThat(resultat.getLibelleEquipement()).isEqualTo("PC principal");
    }

    @Test
    void testCreateEquipement() throws Exception {
        // Arrange
        Equipement newEquipement = new Equipement(3, "PC secondaire", 30.0, 30.0, 100.0, 200.0, null, null, null);

        Mockito.when(equipementService.saveEquipement(Mockito.any()))
                .thenReturn(newEquipement);

        // Act
        MvcResult result = mockMvc.perform(post("/api/equipement/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEquipement)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Equipement createdEquipement = objectMapper.readValue(json, Equipement.class);
        assertThat(createdEquipement.getId()).isEqualTo(3);
        assertThat(createdEquipement.getLibelleEquipement()).isEqualTo("PC secondaire");
    }

    @Test
    void testUpdateEquipement() throws Exception {
        // Arrange
        Equipement updatedEquipement = new Equipement(1, "PC principal - update", 50.0, 30.0, 100.0, 200.0, null, null, null);

        Mockito.when(equipementService.updateEquipement(Mockito.any()))
                .thenReturn(updatedEquipement);

        // Act
        MvcResult result = mockMvc.perform(put("/api/equipement/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEquipement)))
                .andReturn();

        // Assert
        String json = result.getResponse().getContentAsString();
        Equipement equipement = objectMapper.readValue(json, Equipement.class);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(equipement.getId()).isEqualTo(1);
        assertThat(equipement.getLibelleEquipement()).isEqualTo("PC principal - update");
    }

    @Test
    void testDeleteEquipement() throws Exception {
        // Arrange
        int equipementId = 1;
        Equipement equipement = new Equipement(1, "PC principal", 50.0, 30.0, 100.0, 200.0, null, null, null);

        Mockito.when(equipementService.getEquipementById(equipementId))
                .thenReturn(equipement);

        Mockito.doNothing().when(equipementService).deleteEquipementById(equipementId);

        // Act
        MvcResult result = mockMvc.perform(delete("/api/equipement/" + equipementId)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    
}
