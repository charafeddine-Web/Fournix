package com.tricol.fournix.service;

import com.tricol.fournix.dto.FournisseurDTO;
import com.tricol.fournix.model.Fournisseur;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface FournisseurService {

    public Fournisseur createFournisseur(Fournisseur f);
    public Fournisseur  updateFournisseur(Fournisseur f);
    public void deleteFournisseur(Integer f);
    public Optional<FournisseurDTO> getFournisseur(Integer id);
    public Page<Fournisseur> getFournisseurs(Pageable pageable);

    List<Fournisseur> findFournisseurByNom(String nom);
    List<Fournisseur> findFournisseurByNomEndingWith(String ending);
}
