package com.tricol.fournix.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CommandeProduitDTO {

    private Long id;

    @NotNull(message = "La quantité est obligatoire")
    @Positive(message = "La quantité doit être supérieure à 0")
    private Integer quantite;

    @NotNull(message = "Le prix unitaire est obligatoire")
    @Positive(message = "Le prix unitaire doit être supérieur à 0")
    private Double prixUnit;

    @NotNull(message = "Le produit est obligatoire")
    private Long produitId;

    private Long commandeId;
}
