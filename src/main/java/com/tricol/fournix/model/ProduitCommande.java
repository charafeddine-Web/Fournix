package com.tricol.fournix.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Data
public class ProduitCommande {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantite;
    private Double prixUnit;


    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;
}
