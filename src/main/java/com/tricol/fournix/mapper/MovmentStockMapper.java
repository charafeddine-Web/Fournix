package com.tricol.fournix.mapper;

import com.tricol.fournix.dto.MovmentStockDTO;
import com.tricol.fournix.model.MovmentStock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MovmentStockMapper.class})
public interface MovmentStockMapper {
//    MovmentStockDTO toDTO(MovmentStock);
//    MovmentStock toEntity(MovmentStockDTO);
}
