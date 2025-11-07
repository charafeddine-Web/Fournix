package com.tricol.fournix.mapper;

import com.tricol.fournix.dto.MovmentStockDTO;
import com.tricol.fournix.model.MovmentStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CommandeMapper.class , ProduitMapper.class})

public interface MovmentStockMapper {
    @Mapping(source = "dateMovment", target = "dateMouvement")
    @Mapping(source = "quantite", target = "quantiteMouvement")
    @Mapping(source = "typeMovment", target = "typeMouvement")
    @Mapping(source = "produit", target = "produitDto")
    @Mapping(source = "commande", target = "commandeDto")
    MovmentStockDTO toDTO(MovmentStock movmentStock);

    @Mapping(source = "dateMouvement", target = "dateMovment")
    @Mapping(source = "quantiteMouvement", target = "quantite")
    @Mapping(source = "typeMouvement", target = "typeMovment")
    @Mapping(source = "produitDto", target = "produit")
    @Mapping(source = "commandeDto", target = "commande")
    MovmentStock toEntity(MovmentStockDTO movmentStockDTO);
    
}
