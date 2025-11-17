package com.guarderia.central.service;

import com.guarderia.central.entity.Socio;
import com.guarderia.central.dto.SocioDTO;
import java.util.List;

public interface SocioService {


    //Metodos anteriores
    List<Socio> listar();
    Socio buscarPorId(Long codigo);
    Socio guardar(Socio socio);
    void eliminar(Long codigo);

    // Metodos para Thymeleaf y DTO
    List<SocioDTO> listarDTO();
    SocioDTO buscarDTO(Long id);
    SocioDTO guardarDTO(SocioDTO socioDTO);



    /* Consultar anteriores 

    List<Socio> obtenerTodosLosSocios();

    Socio obtenerSocioPorDni(Integer dni);

    Socio crearSocio(Socio socio, String garageCodigo);

    Socio actualizarSocio(Integer dni, Socio socioNuevo, String garageCodigo);

    void eliminarSocio(Integer dni);

    boolean existeDni(Integer dni);

    List<Socio> buscarPorApellido(String apellido);

    List<Socio> buscarPorDniParcial(String dniPrefix);

    List<Socio> buscarPorCriterio(String criterio);
    */
}