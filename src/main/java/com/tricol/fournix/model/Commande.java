package com.tricol.fournix.model;

import com.tricol.fournix.model.enums.StatusCommande;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

public class Commande {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCommande;

    private Double prix;
    private StatusCommande statusCommande;
    private String categorie;
    private Integer stockActuel;


    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<ProduitCommande> produitCommandes;

}
