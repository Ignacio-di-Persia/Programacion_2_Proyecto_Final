package com.guarderia.central.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoZonaDTO {
    private String zonaCodigo;
    private Integer vehiculosAsignados;
}
