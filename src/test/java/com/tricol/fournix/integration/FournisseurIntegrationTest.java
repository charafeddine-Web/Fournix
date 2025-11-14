package com.tricol.fournix.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.fournix.dto.FournisseurDTO;
import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.repository.FournisseurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class FournisseurIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAndRetrieveFournisseur() throws Exception {

        FournisseurDTO dto = new FournisseurDTO();
        dto.setAdresse("safi");
        dto.setContact("0606060606");
        dto.setEmail("contact+" + System.currentTimeMillis() + "@gmail.com");
        dto.setIce("CC222");
        dto.setNom("charaf");
        dto.setSociete("Tricol");
        dto.setTelephone("0606060606");
        dto.setVille("casablanca");

        String jsonRequest = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/fournisseurs/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("charaf"));

        Fournisseur saved = fournisseurRepository.findAll().get(0);
        Long id = saved.getId();

        mockMvc.perform(get("/fournisseurs/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("charaf"));
    }
}
