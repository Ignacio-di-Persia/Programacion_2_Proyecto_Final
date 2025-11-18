package com.tp.guarderia_central.services;

import com.tp.guarderia_central.dtos.EmpleadoDTO;
import com.tp.guarderia_central.dtos.GarageDTO;

import java.util.List;

public interface IEmpleadoService {

    List<EmpleadoDTO> findAll();
    EmpleadoDTO findById(Long id);
    EmpleadoDTO save(EmpleadoDTO empleadoDTO);
    EmpleadoDTO update(Long id, EmpleadoDTO empleadoDTO);
    void deleteById(Long id);
    EmpleadoDTO findByCodigo(String codigo);
    void actualizarGaragesAsignados(Long empleadoId, List<Long> garageIds);
    List<Long> obtenerIdsGaragesAsignados(Long empleadoId);
    List<GarageDTO> findGaragesByUsername(String username);
}