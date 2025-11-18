package com.tp.guarderia_central.mappers;

import com.tp.guarderia_central.dtos.ZonaDTO;
import com.tp.guarderia_central.models.entities.Zona;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {DimensionMapper.class})
public interface ZonaMapper {
    ZonaDTO toDTO(Zona zona);
    Zona toEntity(ZonaDTO zonaDTO);
    void updateZonaFromDto(ZonaDTO zonaDTO, @MappingTarget Zona zona);
}