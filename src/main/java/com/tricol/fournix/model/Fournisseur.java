package com.tricol.fournix.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Entity
public class Fournisseur {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String societe;
    private String adresse;
    private String contact;

    @Column(unique = true)
    private String email;
    private String telephone;
    private String ville;

    @Column(unique = true)
    private String ice;


}
