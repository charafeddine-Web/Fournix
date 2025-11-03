package com.tricol.fournix.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ProduitDTO {


    private Long id;

    @NotBlank(message = "Le nom  est obligatoire")
    private String nom;

    private String descritption;

    @NotBlank(message = "Le prixUnit  est obligatoire")
    private Double prixUnit;

    @NotBlank(message = "Le categorie  est obligatoire")
    private String categorie;

    @NotBlank(message = "Le stockActuel  est obligatoire")
    private Integer stockActuel;
}
