package com.tricol.fournix.service;

import com.tricol.fournix.dto.CommandeDTO;
import com.tricol.fournix.model.Commande;

import com.tricol.fournix.model.ProduitCommande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface CommandeService {

    Commande save(Commande commande,List<ProduitCommande> produits);
    Optional<Commande> findById(int id);
    Page<Commande> findAll(Pageable pageable);
    void delete(int id);
    Commande update(Commande commande);
    CommandeDTO validerCommande(Long id);

}
