package com.guarderia.central.service;

import com.guarderia.central.entity.Garage;
import com.guarderia.central.entity.Socio;

public interface SocioGarageService {

    /**
     * Asigna un garage a un socio
     * @param socio Socio al que se asigna
     * @param garage Garage a asignar
     */
    void asignarGarage(Socio socio, Garage garage);

    /**
     * Cambia el garage de un socio por otro
     * @param socio Socio a actualizar
     * @param garageNuevo Nuevo garage a asignar
     */
    void cambiarGarage(Socio socio, Garage garageNuevo);

    /**
     * Libera el garage asignado a un socio
     * @param socio Socio cuyo garage se libera
     */
    void liberarGarage(Socio socio);
}
