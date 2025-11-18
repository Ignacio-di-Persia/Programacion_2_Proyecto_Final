package com.tp.guarderia_central.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tp.guarderia_central.models.embeddable.Direccion;
import com.tp.guarderia_central.models.security.Usuario;

@Entity
@Table(name = "socios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, updatable = true)
    @Size(min = 2, max=40, message = "El nombre debe tener entre 2 y 40 caracteres.")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Embedded
    @NotNull(message = "La direccion no puede ser nula")
    private Direccion direccion;

    @NotBlank(message = "El DNI no puede estar vacío")
    @Column(unique = true, nullable = false, length = 8)
    private String dni;

    @Size(min = 8, max = 20, message = "El telefono debe estar entre los 8 y los 20 caracteres")
    @Column(name = "telefono", length = 20, unique = true)
    private String telefono;

    @NotNull(message = "La fecha de ingreso no puede estar vacía")
    @PastOrPresent(message = "La fecha de ingreso no puede ser posterior al día de hoy")
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @CreatedDate
    @Column(name = "fecha_registro", updatable = false, nullable = false)
    private LocalDate fechaRegistro;

    @CreatedBy
    @Column(name = "registrado_por", updatable = false, nullable = false)
    private String registradoPor;

    @LastModifiedBy
    @Column(name = "ultima_modificacion_por", nullable = true)
    private String ultimaModificacionPor;

    @LastModifiedDate
    @Column(name = "fecha_ultima_modificacion", updatable = true, nullable = true)
    private LocalDate fechaUltimaModificacion;

    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehiculo> vehiculos;

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Garage> garagesPropios;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}