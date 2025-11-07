package com.tricol.fournix.controller;

import com.tricol.fournix.dto.MovmentStockDTO;
import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.MovmentStock;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.enums.TypeMovment;
import com.tricol.fournix.repository.CommandeRepository;
import com.tricol.fournix.repository.ProduitRepository;
import com.tricol.fournix.service.Implimentation.MouvementStockServiceImpli;
import com.tricol.fournix.service.MouvementStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mouvements")
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
    public Page<MovmentStockDTO> getAllMouvements(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return mouvementStockService.getAllMouvements(PageRequest.of(page, size, sort));
    }


    @GetMapping("/produit/{produitId}")
    public Page<MovmentStockDTO> getMouvementsByProduit(
            @PathVariable Long produitId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Produit produit = produitRepository.findById(Math.toIntExact(produitId))
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));
        return mouvementStockService.findByProduitId(produit.getId(), PageRequest.of(page, size, sort));
    }

    @GetMapping("/commande/{commandeId}")
    public Page<MovmentStockDTO> getMouvementsByCommande(
            @PathVariable Long commandeId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Commande commande = commandeRepository.findById(Math.toIntExact(commandeId))
                .orElseThrow(() -> new IllegalArgumentException("Commande introuvable"));
        return mouvementStockService.findByCommandeId(commande.getId(),  PageRequest.of(page, size, sort));
    }

    @GetMapping("/by_type/{type}")
    public Page<MovmentStockDTO> getMouvementsByType(
            @PathVariable TypeMovment type,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return mouvementStockService.findByTypeMovment(type,  PageRequest.of(page, size, sort));
    }

}
