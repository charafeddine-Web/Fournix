package com.tricol.fournix.dto;

import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.model.ProduitCommande;
import com.tricol.fournix.model.enums.StatusCommande;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class CommandeDTO {

    private Long id;

    @NotBlank(message = "La date de Commande  est obligatoire")
    private LocalDateTime date_commande;


    @NotBlank(message = "Le prix  est obligatoire")
    private Double prix;

    @NotBlank(message = "Le status  est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(name = "statut_commande")
    private StatusCommande statut_commande;


    private Fournisseur fournisseur;


}
