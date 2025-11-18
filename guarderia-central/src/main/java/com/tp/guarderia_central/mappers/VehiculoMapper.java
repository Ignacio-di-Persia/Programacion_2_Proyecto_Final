package com.tp.guarderia_central.mappers;

import com.tp.guarderia_central.dtos.VehiculoDTO;
import com.tp.guarderia_central.models.entities.Vehiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {DimensionMapper.class})
public interface VehiculoMapper {

    @Mappings({
        @Mapping(source = "socio.id", target = "socioId"),
        @Mapping(source = "socio.nombre", target = "socioNombre"),
        @Mapping(source = "garage.id", target = "garageId"),
        @Mapping(source = "garage.numeroGarage", target = "garageNumero"),
        @Mapping(source = "garage.zona.letra", target = "garageZona")
    })
    VehiculoDTO toDTO(Vehiculo vehiculo);
    Vehiculo toEntity(VehiculoDTO vehiculoDTO);
    void updateVehiculoFromDto(VehiculoDTO vehiculoDTO, @MappingTarget Vehiculo vehiculo);
}