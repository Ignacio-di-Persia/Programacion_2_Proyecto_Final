package com.guarderia.central.service;

import com.guarderia.central.entity.Zona;
import com.guarderia.central.exception.ZonaNotFoundException;
import com.guarderia.central.repository.ZonaRepository;
import com.guarderia.central.service.ZonaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ZonaServiceImpl implements ZonaService {

    private final ZonaRepository zonaRepository;

    @Override
    public List<Zona> listarZonas() {
        return zonaRepository.findAll();
    }

    @Override
    public Zona obtenerZonaPorCodigo(String codigo) {
        return zonaRepository.findById(codigo)
                .orElseThrow(() -> new ZonaNotFoundException("Zona con código '" + codigo + "' no encontrada"));
    }

    @Override
    public Zona guardarZona(Zona zona) {
        log.info("Guardando zona: {}", zona);
        return zonaRepository.save(zona);
    }

    @Override
    public void eliminarZona(String codigo) {
        if (!zonaRepository.existsById(codigo)) {
            throw new ZonaNotFoundException("No existe la zona con código '" + codigo + "'");
        }
        zonaRepository.deleteById(codigo);
    }

    @Override
    public boolean existeZona(String codigo) {
        return zonaRepository.existsById(codigo);
    }
}
