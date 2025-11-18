package com.tp.guarderia_central.services;

import com.tp.guarderia_central.dtos.VehiculoDTO;
import java.util.List;

public interface IVehiculoService {

    List<VehiculoDTO> findAll();

    VehiculoDTO findById(Long id);

    VehiculoDTO save(VehiculoDTO vehiculoDTO);

    VehiculoDTO update(Long id, VehiculoDTO vehiculoDTO);

    void deleteById(Long id);

    List<VehiculoDTO> findBySocioId(Long socioId);
}