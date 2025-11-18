package com.tp.guarderia_central.mappers;

import com.tp.guarderia_central.dtos.GarageDTO;
import com.tp.guarderia_central.models.entities.Garage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface GarageMapper {

    @Mappings({
        // Mapeo de Zona a DTO
        @Mapping(source = "zona.id", target = "zonaId"),
        @Mapping(source = "zona.letra", target = "zonaLetra"),
        
        // Mapeo de Propietario (MapStruct maneja nulls automáticamente)
        @Mapping(source = "propietario.id", target = "propietarioId"),
        @Mapping(source = "propietario.nombre", target = "propietarioNombre"),
        
        // Mapeo de Vehículo Ocupante
        @Mapping(source = "vehiculoOcupante.id", target = "vehiculoOcupanteId"),
        @Mapping(source = "vehiculoOcupante.matricula", target = "vehiculoOcupanteMatricula")
    })
    GarageDTO toDTO(Garage garage);

    Garage toEntity(GarageDTO garageDTO);

    void updateGarageFromDto(GarageDTO garageDTO, @MappingTarget Garage garage);
}