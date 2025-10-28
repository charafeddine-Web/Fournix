package com.tricol.fournix.controller;

import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.repository.FournisseurRepository;
import com.tricol.fournix.service.FournisseurService;
import com.tricol.fournix.service.Implimentation.fournisseurServiceImpli;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("fournisseurs")
public class FournisseurController {

    private fournisseurServiceImpli fournisseurServiceImpli;

    public FournisseurController(fournisseurServiceImpli fournisseurServiceImpli) {
        this.fournisseurServiceImpli=fournisseurServiceImpli;
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
