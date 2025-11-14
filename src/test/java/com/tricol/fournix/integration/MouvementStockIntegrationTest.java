package com.tricol.fournix.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.enums.TypeMovment;
import com.tricol.fournix.repository.CommandeRepository;
import com.tricol.fournix.repository.ProduitRepository;
import com.tricol.fournix.service.Implimentation.MouvementStockServiceImpli;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@TestPropertySource(locations = "classpath:application-test.properties")
public class MouvementStockIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private MouvementStockServiceImpli mouvementStockService;

    @Autowired
    private ObjectMapper objectMapper;

    private Produit produit;
    private Commande commande;

    @BeforeEach
    void setUp() {
        produit = new Produit();
        produit.setNom("Produit Test");
        produit.setPrixUnit(100.0);
        produit.setStockActuel(50);
        produit = produitRepository.save(produit);

        commande = new Commande();
        commande.setPrix(200.0);
        commande = commandeRepository.save(commande);

        mouvementStockService.enregistrerEntree(produit, commande, produit.getStockActuel(),produit.getPrixUnit());
        mouvementStockService.enregistrerSortie(produit, commande, produit.getStockActuel());
    }

    @Test
    void shouldGetAllMouvements() throws Exception {
        mockMvc.perform(get("/mouvements/all")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2))); // 2 mouvements créés
    }

    @Test
    void shouldGetMouvementsByProduit() throws Exception {
        mockMvc.perform(get("/mouvements/produit/" + produit.getId())
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    void shouldGetMouvementsByCommande() throws Exception {
        mockMvc.perform(get("/mouvements/commande/" + commande.getId())
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    void shouldGetMouvementsByType() throws Exception {
        mockMvc.perform(get("/mouvements/by_type/ENTREE")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));

        mockMvc.perform(get("/mouvements/by_type/SORTIE")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }


}
