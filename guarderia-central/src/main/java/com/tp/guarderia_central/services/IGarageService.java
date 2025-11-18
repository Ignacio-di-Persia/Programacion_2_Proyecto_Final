package com.tp.guarderia_central.services;

import com.tp.guarderia_central.dtos.CompraGarageDTO;
import com.tp.guarderia_central.dtos.GarageDTO;

import java.util.List;


public interface IGarageService {

    List<GarageDTO> findAll();
    GarageDTO findById(Long id);
    GarageDTO save(GarageDTO garageDTO);
    GarageDTO update(Long id, GarageDTO garageDTO);
    void deleteById(Long id);
    void asignarVehiculo(Long garageId, Long vehiculoId);
    void liberarGarage(Long garageId);
    void cambiarGarage(Long vehiculoId, Long nuevoGarageId);
    void asignarPropietario(CompraGarageDTO compraDTO);
    void liberarPropietario(Long garageId);
    List<GarageDTO> findByPropietarioId(Long socioId);
}