package com.tricol.fournix.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class FournisseurDTO {

    private Integer id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @Size(max = 100, message = "Le nom de la société ne doit pas dépasser 100 caractères")
    private String societe;


    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    @NotBlank(message = "Le contact est obligatoire")
    private String contact;

    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    @NotBlank(message = "L'ICE est obligatoire")
    @Size(min = 10, max = 15, message = "L'ICE doit contenir entre 10 et 15 caractères")
    private String ice;

}
