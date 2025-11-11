package com.guarderia.central.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder    
public class SocioDTO {
    private Long codigo;
    private Integer dni;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private List<SocioGarageDTO> GaragePropios;
}
