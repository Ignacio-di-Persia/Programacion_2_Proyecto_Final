package com.guarderia.central.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocioGarageId implements Serializable {

    @Column(name = "socio_codigo") 
    private Long socioDni;

    @Column(name = "garage_codigo")
    private String garageCodigo;

}
