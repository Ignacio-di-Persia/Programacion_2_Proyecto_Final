package com.guarderia.central.repository;

import com.guarderia.central.entity.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, String> {

    // Verificar si existe una zona por código
    boolean existsByCodigo(String codigo);

    // Buscar zonas por tipo de vehículo (parcial, case insensitive)
    List<Zona> findByTipoVehiculosContainingIgnoreCase(String tipoVehiculo);
}