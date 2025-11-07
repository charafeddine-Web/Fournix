package com.tricol.fournix.dto;

import com.tricol.fournix.model.enums.TypeMovment;
import lombok.Data;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MovmentStockDTO {
    Long id;
    LocalDateTime dateMouvement;
    Integer quantiteMouvement;
    TypeMovment typeMouvement;
    ProduitDTO produitDto;
    CommandeDTO commandeDto;
}
