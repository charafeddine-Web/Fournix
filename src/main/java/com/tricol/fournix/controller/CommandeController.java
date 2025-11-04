package com.tricol.fournix.controller;

import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    private final CommandeService commandeService;

    @Autowired
    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
        Commande savedCommande = commandeService.save(commande);
        return ResponseEntity.ok(savedCommande);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable int id) {
        Optional<Commande> commande = commandeService.findById(id);
        return commande.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<Commande>> getAllCommandes(@RequestParam (defaultValue = "0") Integer size , @RequestParam(defaultValue = "5")  Integer page  ) {
        Pageable commandes = PageRequest.of(page, size);
        return ResponseEntity.ok(commandeService.findAll(commandes));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable int id, @RequestBody Commande updatedCommande) {
        Optional<Commande> existingCommande = commandeService.findById(id);
        if (existingCommande.isPresent()) {
            updatedCommande.setId((long) id);
            Commande commande = commandeService.update(updatedCommande);
            return ResponseEntity.ok(commande);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable int id) {
        Optional<Commande> commande = commandeService.findById(id);
        if (commande.isPresent()) {
            commandeService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
