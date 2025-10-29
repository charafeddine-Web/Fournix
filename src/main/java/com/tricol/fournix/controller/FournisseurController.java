package com.tricol.fournix.controller;

import com.tricol.fournix.dto.FournisseurDTO;
import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.repository.FournisseurRepository;
import com.tricol.fournix.service.FournisseurService;
import com.tricol.fournix.service.Implimentation.fournisseurServiceImpli;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("fournisseurs")
public class FournisseurController {

    private fournisseurServiceImpli fournisseurServiceImpli;

    public FournisseurController(fournisseurServiceImpli fournisseurServiceImpli) {
        this.fournisseurServiceImpli=fournisseurServiceImpli;
    }


    @PostMapping("/save")
    public void createFournisseur(@Valid @RequestBody Fournisseur fournisseur) {
        fournisseurServiceImpli.createFournisseur(fournisseur);
    }

    @GetMapping("/byId")
    public Optional<FournisseurDTO> getFournisseurById(Integer id) {
        return fournisseurServiceImpli.getFournisseur(id);
    }

    @GetMapping("all")
    public List<Fournisseur> getAllFournisseur() {
        return  fournisseurServiceImpli.getFournisseurs();
    }

    @GetMapping("delete")
    public void deleteFournisseur(Fournisseur fournisseur) {
        fournisseurServiceImpli.deleteFournisseur(fournisseur);
    }


    @PostMapping("update")
    public void updateFournisseur(Fournisseur fournisseur) {

    }


    @GetMapping("/byName")
    public ResponseEntity<List<Fournisseur>> findFournisseurByNom(@RequestParam("nom") String nom) {
        List<Fournisseur> fournisseurs = fournisseurServiceImpli.findFournisseurByNom(nom);
        return ResponseEntity.ok(fournisseurs);
    }

    @GetMapping("/byEmailEndingWith")
    public ResponseEntity<List<Fournisseur>> findByEmailEndingWith(@RequestParam("ending") String ending) {
        List<Fournisseur> fournisseurs = fournisseurServiceImpli.findFournisseurByNomEndingWith(ending);
        return ResponseEntity.ok(fournisseurs);
    }



}
