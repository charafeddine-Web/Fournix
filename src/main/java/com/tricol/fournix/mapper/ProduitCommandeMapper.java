package com.tricol.fournix.mapper;

import com.tricol.fournix.dto.CommandeProduitDTO;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.ProduitCommande;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProduitCommandeMapper {
    CommandeProduitDTO toDTO(Produit produit);
    ProduitCommande toEntity(CommandeProduitDTO produitDTO);
}
