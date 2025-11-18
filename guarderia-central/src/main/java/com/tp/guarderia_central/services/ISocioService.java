package com.tp.guarderia_central.services;

import com.tp.guarderia_central.dtos.SocioDTO;
import java.util.List;

public interface ISocioService {
    List<SocioDTO> findAll();
    SocioDTO findById(Long id);
    SocioDTO save(SocioDTO socioDTO);
    SocioDTO update(Long id, SocioDTO socioDTO);
    void deleteById(Long id);
    SocioDTO findByUsername(String username);
}