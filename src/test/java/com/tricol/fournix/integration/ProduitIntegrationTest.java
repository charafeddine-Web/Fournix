package com.tricol.fournix.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.fournix.model.Produit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProduitIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Produit produit;

    @BeforeEach
    void setUp() {
        produit = new Produit();
        produit.setNom("Produit Test");
        produit.setPrixUnit(100.0);
        produit.setStockActuel(10);
        produit.setDescription("Description du produit test");
    }

    @Test
    void shouldCreateAndGetProduit() throws Exception {

        String produitJson = objectMapper.writeValueAsString(produit);
        mockMvc.perform(post("/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produitJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nom", is("Produit Test")));

        mockMvc.perform(get("/produits/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("Produit Test")));
    }

    @Test
    void shouldUpdateProduit() throws Exception {
        String produitJson = objectMapper.writeValueAsString(produit);
        mockMvc.perform(post("/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produitJson))
                .andExpect(status().isOk());

        produit.setNom("Produit Modifié");
        String updatedJson = objectMapper.writeValueAsString(produit);

        mockMvc.perform(put("/produits/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("Produit Modifié")));
    }

    @Test
    void shouldDeleteProduit() throws Exception {
        String produitJson = objectMapper.writeValueAsString(produit);
        mockMvc.perform(post("/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produitJson))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/produits/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/produits/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnPagedProduits() throws Exception {
        for (int i = 1; i <= 7; i++) {
            produit.setNom("Produit " + i);
            String produitJson = objectMapper.writeValueAsString(produit);
            mockMvc.perform(post("/produits")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(produitJson))
                    .andExpect(status().isOk());
        }

        mockMvc.perform(get("/produits?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.totalElements", is(7)));
    }
}
