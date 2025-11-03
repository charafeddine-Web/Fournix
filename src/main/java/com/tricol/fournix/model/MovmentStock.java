package com.tricol.fournix.model;

import com.tricol.fournix.model.enums.TypeMovment;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Data
public class MovmentStock {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateMovment;

    private Integer quantite;
    private TypeMovment typeMovment;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

}
