package com.tp.guarderia_central.mappers;

import com.tp.guarderia_central.dtos.EmpleadoDTO;
import com.tp.guarderia_central.models.entities.Empleado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {DireccionMapper.class, GarageMapper.class})
public interface EmpleadoMapper {
    
    @Mappings({
        @Mapping(target = "garagesAsignados", source = "garagesAsignados")
    })
    EmpleadoDTO toDTO(Empleado empleado);

/*     @Mappings({
        @Mapping(target = "garagesAsignados", ignore = true), // <-- 3. IGNORAR EN EL REVERSO
        // ... (otros ignores) ...
    }) */
    Empleado toEntity(EmpleadoDTO empleadoDTO);

    void updateEmpleadoFromDto(EmpleadoDTO empleadoDTO, @MappingTarget Empleado empleado);
}
