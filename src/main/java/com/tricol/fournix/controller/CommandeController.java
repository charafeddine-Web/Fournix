package com.tricol.fournix.controller;

import com.tricol.fournix.dto.CommandeDTO;
import com.tricol.fournix.dto.CommandeRequestDTO;
import com.tricol.fournix.mapper.CommandeMapper;
import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.ProduitCommande;
import com.tricol.fournix.service.CommandeService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    private final CommandeService commandeService;
    private final CommandeMapper commandeMapper;

    @Autowired
    public CommandeController(CommandeService commandeService, CommandeMapper commandeMapper) {
        this.commandeService = commandeService;
        this.commandeMapper = commandeMapper;
    }

    @PostMapping
    public ResponseEntity<Commande> createCommande(@RequestBody CommandeRequestDTO requestDTO) {
        Commande savedCommande = commandeService.save(requestDTO.getCommande(), requestDTO.getProduits());
        return ResponseEntity.ok(savedCommande);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CommandeDTO> getCommandeById(@PathVariable int id) {
        return commandeService.findById(id)
                .map(commandeMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @GetMapping
//    public ResponseEntity<Page<CommandeDTO>> getAllCommandes(
//            @RequestParam(defaultValue = "0") Integer page,
//            @RequestParam(defaultValue = "10") @Min(1) Integer size) {
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date_commande"));
//
//        return ResponseEntity.ok(
//                commandeService.findAll(pageable).map(commandeMapper::toDTO)
//        );
//    }

    @GetMapping
    public ResponseEntity<Page<CommandeDTO>> getAllCommandes(Pageable pageable) {
        return ResponseEntity.ok(
                commandeService.findAll(pageable).map(commandeMapper::toDTO)
        );
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
