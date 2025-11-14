package com.tricol.fournix.service;

import com.tricol.fournix.model.ProduitCommande;

import java.util.Optional;

public interface ProduitCommandeService {

    ProduitCommande save(ProduitCommande produitCommande);
    Optional<ProduitCommande> findById(Integer id);
    void Delete(Integer id);
    ProduitCommande update(ProduitCommande produitCommande);


}
