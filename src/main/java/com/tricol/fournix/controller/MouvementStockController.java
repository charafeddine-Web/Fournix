package com.tricol.fournix.controller;

import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.MovmentStock;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.enums.TypeMovment;
import com.tricol.fournix.repository.CommandeRepository;
import com.tricol.fournix.repository.ProduitRepository;
import com.tricol.fournix.service.Implimentation.MouvementStockServiceImpli;
import com.tricol.fournix.service.MouvementStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class MouvementStockController {


    private final MouvementStockServiceImpli mouvementStockService;
    private final ProduitRepository produitRepository;
    private final CommandeRepository commandeRepository;

    @Autowired
    public MouvementStockController(MouvementStockServiceImpli mouvementStockService,
                                    ProduitRepository produitRepository,
                                    CommandeRepository commandeRepository) {
        this.mouvementStockService = mouvementStockService;
        this.produitRepository = produitRepository;
        this.commandeRepository = commandeRepository;
    }


    @GetMapping("/all")
    public List<MovmentStock> getAllMouvements() {
        return mouvementStockService.getAllMouvements();
    }

    @GetMapping("/produit/{produitId}")
    public List<MovmentStock> getMouvementsByProduit(@PathVariable Long produitId) {
        Produit produit = produitRepository.findById(Math.toIntExact(produitId))
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));
        return mouvementStockService.getByProduit(produit.getId());
    }

    @GetMapping("/commande/{commandeId}")
    public List<MovmentStock> getMouvementsByCommande(@PathVariable Long commandeId) {
        Commande commande = commandeRepository.findById(Math.toIntExact(commandeId))
                .orElseThrow(() -> new IllegalArgumentException("Commande introuvable"));
        return mouvementStockService.getByCommande(commande.getId());
    }

    @GetMapping("/commande/{type}")
    public List<MovmentStock> getMouvementsByType(@PathVariable TypeMovment type) {
        return mouvementStockService.findByTypeMovment(type);
    }

}
