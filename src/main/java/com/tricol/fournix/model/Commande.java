package com.tricol.fournix.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tricol.fournix.model.enums.StatusCommande;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Commande {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_commande")
    private LocalDateTime date_commande;

    private Double prix;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_commande")
    private StatusCommande statut_commande;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ProduitCommande> produitCommandes = new ArrayList<>();

}
