package com.tp.guarderia_central.mappers;

import com.tp.guarderia_central.dtos.DimensionDTO;
import com.tp.guarderia_central.models.embeddable.Dimension;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DimensionMapper {
    DimensionDTO toDTO(Dimension dimension);
    Dimension toEntity(DimensionDTO dimensionDTO);
}
