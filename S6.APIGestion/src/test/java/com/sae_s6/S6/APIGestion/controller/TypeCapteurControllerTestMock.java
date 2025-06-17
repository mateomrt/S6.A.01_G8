package com.sae_s6.S6.APIGestion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
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
public class TypeCapteurControllerTestMock {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllTypeCapteurs() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/typecapteur")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        TypeCapteur[] typeCapteurs = objectMapper.readValue(json, TypeCapteur[].class);

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(typeCapteurs).isNotNull();
        assertThat(typeCapteurs.length).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void testCreateAndGetTypeCapteur() throws Exception {
        TypeCapteur typeCapteur = new TypeCapteur();
        typeCapteur.setLibelleTypeCapteur("Type Température");

        MvcResult createResult = mockMvc.perform(post("/api/typecapteur")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(typeCapteur)))
                .andReturn();

        String createJson = createResult.getResponse().getContentAsString();
        TypeCapteur createdTypeCapteur = objectMapper.readValue(createJson, TypeCapteur.class);

        assertThat(createResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(createdTypeCapteur).isNotNull();
        assertThat(createdTypeCapteur.getLibelleTypeCapteur()).isEqualTo("Type Température");

        MvcResult getResult = mockMvc.perform(get("/api/typecapteur/" + createdTypeCapteur.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String getJson = getResult.getResponse().getContentAsString();
        TypeCapteur fetchedTypeCapteur = objectMapper.readValue(getJson, TypeCapteur.class);

        assertThat(getResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(fetchedTypeCapteur).isNotNull();
        assertThat(fetchedTypeCapteur.getLibelleTypeCapteur()).isEqualTo("Type Température");
    }

    @Test
    public void testUpdateTypeCapteur() throws Exception {
        TypeCapteur typeCapteur = new TypeCapteur();
        typeCapteur.setLibelleTypeCapteur("Type Humidité");

        MvcResult createResult = mockMvc.perform(post("/api/typecapteur")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(typeCapteur)))
                .andReturn();

        String createJson = createResult.getResponse().getContentAsString();
        TypeCapteur createdTypeCapteur = objectMapper.readValue(createJson, TypeCapteur.class);

        createdTypeCapteur.setLibelleTypeCapteur("Type Humidité Modifié");

        MvcResult updateResult = mockMvc.perform(put("/api/typecapteur/" + createdTypeCapteur.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdTypeCapteur)))
                .andReturn();

        String updateJson = updateResult.getResponse().getContentAsString();
        TypeCapteur updatedTypeCapteur = objectMapper.readValue(updateJson, TypeCapteur.class);

        assertThat(updateResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(updatedTypeCapteur).isNotNull();
        assertThat(updatedTypeCapteur.getLibelleTypeCapteur()).isEqualTo("Type Humidité Modifié");
    }

    @Test
    public void testDeleteTypeCapteur() throws Exception {
        TypeCapteur typeCapteur = new TypeCapteur();
        typeCapteur.setLibelleTypeCapteur("Type Lumière");

        MvcResult createResult = mockMvc.perform(post("/api/typecapteur")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(typeCapteur)))
                .andReturn();

        String createJson = createResult.getResponse().getContentAsString();
        TypeCapteur createdTypeCapteur = objectMapper.readValue(createJson, TypeCapteur.class);

        MvcResult deleteResult = mockMvc.perform(delete("/api/typecapteur/" + createdTypeCapteur.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(deleteResult.getResponse().getStatus()).isEqualTo(200);

        MvcResult getResult = mockMvc.perform(get("/api/typecapteur/" + createdTypeCapteur.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(getResult.getResponse().getStatus()).isEqualTo(404);
    }
}