package com.tricol.fournix.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.fournix.dto.CommandeDTO;
import com.tricol.fournix.dto.CommandeRequestDTO;
import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.ProduitCommande;
import com.tricol.fournix.model.enums.StatusCommande;
import com.tricol.fournix.repository.CommandeRepository;
import com.tricol.fournix.repository.FournisseurRepository;
import com.tricol.fournix.repository.ProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@TestPropertySource(locations = "classpath:application-test.properties")
public class CommandeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Produit produit;

    @BeforeEach
    void setUp() {
        produit = new Produit();
        produit.setNom("Produit Test");
        produit.setPrixUnit(100.0);
        produit.setStockActuel(20);

        produitRepository.save(produit);


    }
    
    @Test
    void shouldCreateCommande_andAffectStock() throws Exception {

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNom("Fournisseur Test");
        fournisseur.setSociete("Societe X");
        fournisseur.setAdresse("Adresse");
        fournisseur.setContact("Contact");
        fournisseur.setEmail("charaf@gmail.com");
        fournisseur.setTelephone("06519285");
        fournisseur.setVille("casa");
        fournisseur.setIce("aaa222");
        fournisseur = fournisseurRepository.save(fournisseur);

        ProduitCommande pc = new ProduitCommande();
        pc.setProduit(produit);
        pc.setQuantite(2);
        pc.setPrixUnit(produit.getPrixUnit());

        CommandeRequestDTO requestDTO = new CommandeRequestDTO();
        Commande commandeDTO = new Commande();
        commandeDTO.setDate_commande(LocalDateTime.now());
        commandeDTO.setPrix(0.0);
        commandeDTO.setFournisseur(fournisseur);
        commandeDTO.setStatut_commande(StatusCommande.EN_ATTENTE);
        requestDTO.setCommande(commandeDTO);
        requestDTO.setProduits(List.of(pc));

        pc.setCommande(commandeDTO);

        String json = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(post("/commandes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.produitCommandes[0].quantite").value(2));

        long count = commandeRepository.count();
        assert(count == 1);

        Produit updated = produitRepository.findById(Math.toIntExact(produit.getId())).get();
        assert(updated.getStockActuel() == 18);

        Commande savedCommande = commandeRepository.findAll().get(0);
        assert(savedCommande.getPrix() == 200.0);
    }

    @Test
    void shouldReturn404_whenCommandeNotFound() throws Exception {

        mockMvc.perform(get("/commandes/999"))
                .andExpect(status().isNotFound());
    }
    @Test
    void shouldValidateCommande() throws Exception {

        Commande commande = new Commande();
        commande.setStatut_commande(StatusCommande.EN_ATTENTE);
        commande = commandeRepository.save(commande);

        mockMvc.perform(patch("/commandes/valider/" + commande.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commande.getId()))
                .andExpect(jsonPath("$.statut_commande", is("LIVREE")));
    }

    @Test
    void shouldReturnPagedCommandes() throws Exception {

        for (int i = 1; i <= 7; i++) {
            commandeRepository.save(new Commande());
        }

        mockMvc.perform(get("/commandes?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.totalElements").value(7));
    }
    @Test
    void shouldDeleteCommande() throws Exception {

        Commande cmd = commandeRepository.save(new Commande());
        mockMvc.perform(delete("/commandes/" + cmd.getId()))
                .andExpect(status().isNoContent());
        assert(commandeRepository.count() == 0);
    }
}
