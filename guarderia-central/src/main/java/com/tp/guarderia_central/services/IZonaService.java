package com.tp.guarderia_central.services;

import com.tp.guarderia_central.dtos.ZonaDTO;
import java.util.List;

public interface IZonaService {

    List<ZonaDTO> findAll();

    ZonaDTO findById(Long id);

    ZonaDTO save(ZonaDTO zonaDTO);

    ZonaDTO update(Long id, ZonaDTO zonaDTO);

    void deleteById(Long id);
    
    ZonaDTO findByLetra(String letra);
}