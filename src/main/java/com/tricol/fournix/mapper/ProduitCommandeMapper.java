package com.tricol.fournix.mapper;

import com.tricol.fournix.dto.CommandeProduitDTO;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.ProduitCommande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProduitMapper.class})
public interface ProduitCommandeMapper {

    @Mapping(source = "produit.id", target = "produitId")
    CommandeProduitDTO toDTO(ProduitCommande produitCommande);

    @Mapping(target = "produit", ignore = true)
    @Mapping(target = "commande", ignore = true)
    ProduitCommande toEntity(CommandeProduitDTO produitDTO);
}
