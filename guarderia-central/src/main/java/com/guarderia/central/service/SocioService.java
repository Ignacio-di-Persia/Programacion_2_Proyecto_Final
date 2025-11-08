package com.guarderia.central.service;


import com.guarderia.central.entity.Socio;
import java.util.List;

public interface SocioService {

    // Obtener todos los socios
    List<Socio> obtenerTodosLosSocios();

    // Obtener un socio por DNI
    Socio obtenerSocioPorDni(Integer dni);

    // Crear un nuevo socio
    Socio crearSocio(Socio socio);

    // Actualizar un socio existente
    Socio actualizarSocio(Integer dni, Socio socio);

    // Eliminar un socio por DNI
    void eliminarSocio(Integer dni);

    // Verificar si existe un DNI
    boolean existeDni(Integer dni);

    // Buscar socios por apellido
    List<Socio> buscarPorApellido(String apellido);

    // Buscar socios por DNI (parcial)
    List<Socio> buscarPorDniParcial(String dniPrefix);

    // Búsqueda combinada (apellido o DNI)
    List<Socio> buscarPorCriterio(String criterio);
}