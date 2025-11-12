package com.guarderia.central.service;

import com.guarderia.central.entity.Garage;
import java.util.List;

public interface GarageService {

    List<Garage> listarGarages();

    Garage obtenerGaragePorId(Long id);

    Garage guardarGarage(Garage garage);

    void eliminarGarage(Long id);

    boolean existeGarage(Long id);

    List<Garage> listarGaragesDisponibles();

    List<Garage> obtenerGaragesPorZona(String codigoZona);
}
