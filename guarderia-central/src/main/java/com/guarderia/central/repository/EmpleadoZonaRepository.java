package com.guarderia.central.repository;

import com.guarderia.central.entity.EmpleadoZona;
import com.guarderia.central.entity.EmpleadoZonaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoZonaRepository extends JpaRepository<EmpleadoZona, EmpleadoZonaId> {

    // Zonas asignadas a un empleado
    List<EmpleadoZona> findByEmpleadoCodigo(Long empleadoCodigo);

    // Empleados asignados a una zona
    List<EmpleadoZona> findByZonaCodigo(String zonaCodigo);

    // Buscar asignación específica
    EmpleadoZona findByEmpleadoCodigoAndZonaCodigo(Long empleadoCodigo, String zonaCodigo);
}
