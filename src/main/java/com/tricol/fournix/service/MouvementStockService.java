package com.tricol.fournix.service;

import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.MovmentStock;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.enums.TypeMovment;

import java.util.List;

public interface MouvementStockService {

    void enregistrerEntree(Produit produit, Commande commande, Integer quantite, Double prixUnitaire);
    void enregistrerSortie(Produit produit,Commande commande, Integer quantite);
    List<MovmentStock> getByProduit(Long produitId);
    List<MovmentStock> getByCommande(Long commandeId);
    List<MovmentStock> findByTypeMovment(TypeMovment typeMovment);

}
