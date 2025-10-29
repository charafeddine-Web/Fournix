package com.tricol.fournix.mapper;

import com.tricol.fournix.dto.FournisseurDTO;
import com.tricol.fournix.model.Fournisseur;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface FournisseurMapper {

    FournisseurDTO toDTO(Optional<Fournisseur> fournisseur);
    Fournisseur toEntity(FournisseurDTO fournisseurDTO);

}

