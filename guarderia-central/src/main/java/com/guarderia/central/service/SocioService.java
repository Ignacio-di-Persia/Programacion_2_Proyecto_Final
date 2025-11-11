package com.guarderia.central.service;

import com.guarderia.central.entity.Socio;
import java.util.List;

public interface SocioService {

    List<Socio> obtenerTodosLosSocios();

    Socio obtenerSocioPorDni(Integer dni);

    Socio crearSocio(Socio socio, String garageCodigo);

    Socio actualizarSocio(Integer dni, Socio socioNuevo, String garageCodigo);

    void eliminarSocio(Integer dni);

    boolean existeDni(Integer dni);

    List<Socio> buscarPorApellido(String apellido);

    List<Socio> buscarPorDniParcial(String dniPrefix);

    List<Socio> buscarPorCriterio(String criterio);
}