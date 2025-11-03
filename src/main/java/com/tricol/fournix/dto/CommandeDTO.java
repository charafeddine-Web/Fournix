package com.tricol.fournix.dto;

import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.model.ProduitCommande;
import com.tricol.fournix.model.enums.StatusCommande;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class CommandeDTO {

    private Long id;

    @NotBlank(message = "Le date de Commande  est obligatoire")
    private LocalDateTime dateCommande;

    private String descritption;

    @NotBlank(message = "Le prix  est obligatoire")
    private Double prix;

    @NotBlank(message = "Le status  est obligatoire")
    private StatusCommande statusCommande;

    @NotBlank(message = "Le categorie  est obligatoire")
    private String categorie;

    private Fournisseur fournisseur;

    private List<ProduitCommande> produitCommandes;

}
