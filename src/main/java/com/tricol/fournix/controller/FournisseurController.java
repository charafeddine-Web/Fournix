package com.tricol.fournix.controller;

import com.tricol.fournix.dto.FournisseurDTO;
import com.tricol.fournix.mapper.FournisseurMapper;
import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.repository.FournisseurRepository;
import com.tricol.fournix.service.FournisseurService;
import com.tricol.fournix.service.Implimentation.fournisseurServiceImpli;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("fournisseurs")
public class FournisseurController {

    private fournisseurServiceImpli fournisseurServiceImpli;
    private FournisseurMapper fournisseurMapper;

    public FournisseurController(fournisseurServiceImpli fournisseurServiceImpli, FournisseurMapper fournisseurMapper) {
        this.fournisseurServiceImpli=fournisseurServiceImpli;
        this.fournisseurMapper=fournisseurMapper;
    }


    @PostMapping("/save")
    public ResponseEntity<FournisseurDTO> createFournisseur( @RequestBody FournisseurDTO fournisseurdto) {
        Fournisseur fournisseur = fournisseurMapper.toEntity(fournisseurdto);
        Fournisseur saved= fournisseurServiceImpli.createFournisseur(fournisseur);
        return ResponseEntity.ok(fournisseurMapper.toDTO(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FournisseurDTO> getFournisseurById(@PathVariable Integer id) {
        FournisseurDTO dto = fournisseurServiceImpli.getFournisseur(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
        return ResponseEntity.ok(dto);
    }



    @GetMapping
    public ResponseEntity<Page<Fournisseur>> getAllFournisseur(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return  ResponseEntity.ok(fournisseurServiceImpli.getFournisseurs(PageRequest.of(page, size, sort)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Integer id) {
        fournisseurServiceImpli.deleteFournisseur(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public  ResponseEntity<FournisseurDTO>  updateFournisseur(@RequestBody FournisseurDTO fournisseurDTO, @PathVariable Integer id) {
        Fournisseur f = fournisseurMapper.toEntity(fournisseurDTO);
        f.setId(Long.valueOf(id));
        fournisseurServiceImpli.updateFournisseur(f);
        return ResponseEntity.ok(fournisseurMapper.toDTO(f));
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
