package com.tp.guarderia_central.repositories;

import com.tp.guarderia_central.models.entities.Garage;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Long> {

    Optional<Garage> findByNumeroGarage(Integer numeroGarage);
    List<Garage> findByPropietarioId(Long socioId);
}