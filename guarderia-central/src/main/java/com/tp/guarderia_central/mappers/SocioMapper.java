package com.tp.guarderia_central.mappers;

import com.tp.guarderia_central.dtos.SocioDTO;
import com.tp.guarderia_central.models.entities.Socio;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {DireccionMapper.class})
public interface SocioMapper {
    SocioDTO toDTO(Socio socio);
    Socio toEntity(SocioDTO socioDTO);
    void updateSocioFromDto(SocioDTO socioDTO, @MappingTarget Socio socio);
}