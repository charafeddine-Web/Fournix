package com.tricol.fournix.dto;

import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.ProduitCommande;
import lombok.Data;

import java.util.List;

@Data
public class CommandeRequestDTO {
    private Commande commande;
    private List<ProduitCommande> produits ;
}
