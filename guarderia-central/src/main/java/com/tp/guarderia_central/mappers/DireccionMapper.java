package com.tp.guarderia_central.mappers;

import com.tp.guarderia_central.dtos.DireccionDTO;
import com.tp.guarderia_central.models.embeddable.Direccion;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DireccionMapper {
    DireccionDTO toDTO(Direccion direccion);
    Direccion toEntity(DireccionDTO direccionDTO);
}
