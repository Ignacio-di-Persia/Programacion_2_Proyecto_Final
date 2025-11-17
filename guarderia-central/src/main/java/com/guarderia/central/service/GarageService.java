package com.guarderia.central.service;

import com.guarderia.central.entity.Garage;
import java.util.List;

public interface GarageService {

    List<Garage> listarGarages();

    Garage obtenerGaragePorId(String id);

    Garage guardarGarage(Garage garage);

    void eliminarGarage(String id);

    boolean existeGarage(String id);

    List<Garage> listarGaragesDisponibles();

    List<Garage> obtenerGaragesPorZona(String codigoZona);
}
