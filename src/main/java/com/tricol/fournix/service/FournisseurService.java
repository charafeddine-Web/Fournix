package com.tricol.fournix.service;

import com.tricol.fournix.model.Fournisseur;

import java.util.List;
import java.util.Optional;


public interface FournisseurService {

    public void createFournisseur(Fournisseur f);
    public void updateFournisseur(Fournisseur f);
    public void deleteFournisseur(Fournisseur f);
    public Optional<Fournisseur> getFournisseur(Integer id);
    public List<Fournisseur> getFournisseurs();
}
