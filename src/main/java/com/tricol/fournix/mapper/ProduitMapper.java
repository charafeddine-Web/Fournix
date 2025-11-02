package com.tricol.fournix.mapper;

import com.tricol.fournix.dto.ProduitDTO;
import com.tricol.fournix.model.Produit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    ProduitDTO toDTO(Produit produit);
    Produit toEntity(ProduitDTO produitDTO);
}
