package com.tricol.fournix.controller;


import com.tricol.fournix.model.Produit;
import com.tricol.fournix.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    private final ProduitService produitService;

    @Autowired
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @PostMapping
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
        Produit savedProduit = produitService.save(produit);
        return ResponseEntity.ok(savedProduit);
    }

    @GetMapping
    public ResponseEntity<List<Produit>> getProduits(){
        return ResponseEntity.ok(produitService.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<Produit>> getAllProduits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Produit> produitsPage = produitService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(produitsPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable int id) {
        Optional<Produit> produit = produitService.findById(id);
        return produit.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable int id, @RequestBody Produit updatedProduit) {
        Optional<Produit> existingProduit = produitService.findById(id);
        if (existingProduit.isPresent()) {
            updatedProduit.setId((long) id);
            Produit produit = produitService.update(updatedProduit);
            return ResponseEntity.ok(produit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable int id) {
        produitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
