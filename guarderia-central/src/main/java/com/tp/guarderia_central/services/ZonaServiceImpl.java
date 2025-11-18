package com.tp.guarderia_central.services;

import com.tp.guarderia_central.dtos.ZonaDTO;
import com.tp.guarderia_central.exceptions.ResourceNotFoundException;
import com.tp.guarderia_central.mappers.ZonaMapper;
import com.tp.guarderia_central.models.entities.Zona;
import com.tp.guarderia_central.repositories.ZonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZonaServiceImpl implements IZonaService {

    private final ZonaRepository zonaRepository;
    private final ZonaMapper zonaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ZonaDTO> findAll() {
        return zonaRepository.findAll().stream()
                .map(zonaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ZonaDTO findById(Long id) {
        Zona zona = zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona no encontrada con id: " + id));
        return zonaMapper.toDTO(zona);
    }

    @Override
    @Transactional(readOnly = true)
    public ZonaDTO findByLetra(String letra) {
        // Necesitaremos crear este método en ZonaRepository
        Zona zona = zonaRepository.findByLetra(letra.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Zona no encontrada con letra: " + letra));
        return zonaMapper.toDTO(zona);
    }

    @Override
    @Transactional
    public ZonaDTO save(ZonaDTO zonaDTO) {
        String letra = zonaDTO.getLetra().toUpperCase();
        if (zonaRepository.findByLetra(letra).isPresent()) {
            throw new RuntimeException("Error: La letra de zona '" + letra + "' ya está en uso.");
        }
        zonaDTO.setLetra(letra);
        Zona zona = zonaMapper.toEntity(zonaDTO);
        zona.setId(null);
        Zona zonaGuardada = zonaRepository.save(zona);
        return zonaMapper.toDTO(zonaGuardada);
    }

    @Override
    @Transactional
    public ZonaDTO update(Long id, ZonaDTO zonaDTO) {
        Zona zonaAActualizar = zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona no encontrada con id: " + id));
        String letra = zonaDTO.getLetra().toUpperCase();
        Optional<Zona> conMismaLetra = zonaRepository.findByLetra(letra);

        if (conMismaLetra.isPresent() && !conMismaLetra.get().getId().equals(id)) {
            throw new RuntimeException("Error: La letra de zona '" + letra + "' ya está en uso por otra zona.");
        }
        zonaDTO.setLetra(letra);
        zonaMapper.updateZonaFromDto(zonaDTO, zonaAActualizar);
        Zona zonaGuardada = zonaRepository.save(zonaAActualizar);
        return zonaMapper.toDTO(zonaGuardada);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Zona zona = zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona no encontrada con id: " + id));
        if (zona.getGarages() != null && !zona.getGarages().isEmpty()) {
            throw new RuntimeException("Error: No se puede eliminar la zona '" + zona.getLetra() + 
                                     "' porque tiene " + zona.getGarages().size() + " garages asignados.");
        }
        zonaRepository.deleteById(id);
    }
}