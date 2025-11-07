package com.tricol.fournix.service;

import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.MovmentStock;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.enums.TypeMovment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MouvementStockService {

    void enregistrerEntree(Produit produit, Commande commande, Integer quantite, Double prixUnitaire);
    void enregistrerSortie(Produit produit,Commande commande, Integer quantite);
    void enregistrerAjustement(Produit produit, Integer nouveauStock);

    Page<MovmentStock> getAllMouvements(Pageable pageable);

    Page<MovmentStock> findByProduitId(Long produitId, Pageable pageable);
    Page<MovmentStock> findByCommandeId(Long commandeId,  Pageable pageable);
    Page<MovmentStock> findByTypeMovment(TypeMovment typeMovment,  Pageable pageable);
}
