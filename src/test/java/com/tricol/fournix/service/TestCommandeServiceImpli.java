package com.tricol.fournix.service;

import com.tricol.fournix.dto.CommandeDTO;
import com.tricol.fournix.mapper.CommandeMapper;
import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.ProduitCommande;
import com.tricol.fournix.model.enums.StatusCommande;
import com.tricol.fournix.repository.CommandeRepository;
import com.tricol.fournix.repository.FournisseurRepository;
import com.tricol.fournix.repository.ProduitRepository;
import com.tricol.fournix.service.Implimentation.CommandeServiceImpli;
import com.tricol.fournix.service.Implimentation.MouvementStockServiceImpli;
import com.tricol.fournix.service.Implimentation.ProduitCommandeServiceImpli;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import jakarta.persistence.EntityNotFoundException;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestCommandeServiceImpli {

    @Mock
    private CommandeRepository commandeRepository;

    @Mock
    private ProduitCommandeServiceImpli produitCommandeServiceImpli;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private MouvementStockServiceImpli mouvementStockService;

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private CommandeMapper commandeMapper;

    @InjectMocks
    private CommandeServiceImpli commandeService;


    @Test
    @DisplayName("Doit enregistrer une commande avec produits")
    void shouldSaveCommandeCorrectly() {
        Commande cmd = new Commande();
        cmd.setId(1L);

        Produit produit = new Produit();
        produit.setId(10L);

        ProduitCommande pc = new ProduitCommande();
        pc.setProduit(produit);
        pc.setQuantite(5);
        pc.setPrixUnit(100.0);

        List<ProduitCommande> produits = List.of(pc);

        when(produitRepository.findById(10)).thenReturn(Optional.of(produit));
        when(commandeRepository.save(cmd)).thenReturn(cmd);
        when(commandeRepository.findById(1)).thenReturn(Optional.of(cmd));

        Commande result = commandeService.save(cmd, produits);

        assertNotNull(result);
        assertEquals(500.0, result.getPrix());
        verify(produitCommandeServiceImpli, times(1)).save(pc);
        verify(commandeRepository, times(1)).save(cmd);
    }



    @Test
    @DisplayName("Doit retourner une commande par ID")
    void shouldFindCommandeById() {
        Commande cmd = new Commande();
        cmd.setId(1L);

        when(commandeRepository.findById(1)).thenReturn(Optional.of(cmd));

        Optional<Commande> result = commandeService.findById(1);

        assertTrue(result.isPresent());
        verify(commandeRepository).findById(1);
    }


    @Test
    @DisplayName("Doit retourner une page de commandes")
    void shouldReturnPagedCommandes() {

        Commande c1 = new Commande(); c1.setId(1L);
        Commande c2 = new Commande(); c2.setId(2L);

        Pageable pageable = PageRequest.of(0, 2);
        Page<Commande> page = new PageImpl<>(List.of(c1, c2), pageable, 2);

        when(commandeRepository.findAll(pageable)).thenReturn(page);

        Page<Commande> result = commandeService.findAll(pageable);

        assertEquals(2, result.getContent().size());
        verify(commandeRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Doit supprimer une commande par ID")
    void shouldDeleteCommande() {
        commandeService.delete(1);
        verify(commandeRepository, times(1)).deleteById(1);
    }


    @Test
    @DisplayName("Doit mettre à jour une commande")
    void shouldUpdateCommande() {
        Commande cmd = new Commande();
        cmd.setId(1L);
        cmd.setPrix(999.0);

        when(commandeRepository.save(cmd)).thenReturn(cmd);

        Commande updated = commandeService.update(cmd);

        assertEquals(999.0, updated.getPrix());
        verify(commandeRepository).save(cmd);
    }


    @Test
    @DisplayName("Doit valider une commande et enregistrer une sortie de stock")
    void shouldValidateCommande() {
        Produit produit = new Produit();
        produit.setId(10L);

        ProduitCommande pc = new ProduitCommande();
        pc.setProduit(produit);
        pc.setQuantite(3);

        Commande cmd = new Commande();
        cmd.setId(1L);
        cmd.setStatut_commande(StatusCommande.EN_ATTENTE);
        cmd.setProduitCommandes(List.of(pc));

        CommandeDTO dto = new CommandeDTO();

        when(commandeRepository.findById(1)).thenReturn(Optional.of(cmd));
        when(produitRepository.findById(10)).thenReturn(Optional.of(produit));
        when(commandeRepository.save(cmd)).thenReturn(cmd);
        when(commandeMapper.toDTO(cmd)).thenReturn(dto);

        CommandeDTO result = commandeService.validerCommande(1L);

        assertNotNull(result);
        assertEquals(StatusCommande.LIVREE, cmd.getStatut_commande());

        verify(mouvementStockService).enregistrerSortie(produit, cmd, 3);
        verify(commandeRepository).save(cmd);
        verify(commandeMapper).toDTO(cmd);
    }


    @Test
    @DisplayName("Doit lancer une erreur si commande déjà livrée")
    void shouldThrowIfAlreadyDelivered() {

        Commande cmd = new Commande();
        cmd.setId(1L);
        cmd.setStatut_commande(StatusCommande.LIVREE);

        when(commandeRepository.findById(1)).thenReturn(Optional.of(cmd));

        assertThrows(IllegalStateException.class, () -> {
            commandeService.validerCommande(1L);
        });
    }
}
