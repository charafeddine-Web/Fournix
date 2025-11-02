package com.tricol.fournix.mapper;

import com.tricol.fournix.dto.CommandeDTO;
import com.tricol.fournix.model.Commande;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandeMapper {
    CommandeDTO toDTO(Commande commande);
    Commande toEntity(CommandeDTO commandeDTO);
}
