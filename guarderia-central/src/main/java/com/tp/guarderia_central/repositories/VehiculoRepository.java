package com.tp.guarderia_central.repositories;

import com.tp.guarderia_central.models.entities.Vehiculo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findBySocioId(Long id);
    Optional<Vehiculo> findByMatricula(String matricula);
}