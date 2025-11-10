package com.guarderia.central.service;

import com.guarderia.central.entity.Empleado;

import java.util.List;

public interface EmpleadoService {

    List<Empleado> listar();

    Empleado buscarPorId(Long codigo);

    Empleado guardar(Empleado empleado);

    void eliminar(Long codigo);

    Empleado actualizarEmpleado(Long codigo, Empleado empleado);
}
