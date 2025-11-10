package com.guarderia.central.service;

import com.guarderia.central.entity.Zona;

import java.util.List;

public interface ZonaService {

    List<Zona> listarZonas();

    Zona obtenerZonaPorCodigo(String codigo);

    Zona guardarZona(Zona zona);

    void eliminarZona(String codigo);

    boolean existeZona(String codigo);
}
