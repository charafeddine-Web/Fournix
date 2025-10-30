package com.tricol.fournix.model;

import com.tricol.fournix.model.enums.TypeMovment;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class MovmentStock {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateMovment;

    private Integer quantite;
    private TypeMovment typeMovment;

    @ManyToOne
    @JoinColumn(name = "produitCommande_id")
    private ProduitCommande produitCommande;

}
