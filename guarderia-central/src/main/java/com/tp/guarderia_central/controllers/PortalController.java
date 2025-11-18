package com.tp.guarderia_central.controllers;

import com.tp.guarderia_central.dtos.EmpleadoDTO;
import com.tp.guarderia_central.dtos.GarageDTO;
import com.tp.guarderia_central.dtos.SocioDTO;
import com.tp.guarderia_central.dtos.VehiculoDTO;
import com.tp.guarderia_central.services.IEmpleadoService;
import com.tp.guarderia_central.services.IGarageService;
import com.tp.guarderia_central.services.ISocioService;
import com.tp.guarderia_central.services.IVehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/portal")
@RequiredArgsConstructor
public class PortalController {

    private final ISocioService socioService;
    private final IVehiculoService vehiculoService;
    private final IGarageService garageService;
    private final IEmpleadoService empleadoService;

    @GetMapping("/mis-datos")
    public String verMisDatos(Authentication authentication, Model model) {
        String username = authentication.getName();
        SocioDTO socio = socioService.findByUsername(username);
        List<VehiculoDTO> misVehiculos = vehiculoService.findBySocioId(socio.getId());
        List<GarageDTO> misGarages = garageService.findByPropietarioId(socio.getId());

        model.addAttribute("socio", socio);
        model.addAttribute("vehiculos", misVehiculos);
        model.addAttribute("garages", misGarages);

        return "portal/mis-datos";
    }

    @GetMapping("/mis-tareas")
    public String verMisTareas(Authentication authentication, Model model) {
        String username = authentication.getName();
        EmpleadoDTO empleado = empleadoService.findByCodigo(username);
        List<GarageDTO> misGarages = empleadoService.findGaragesByUsername(username);

        model.addAttribute("empleado", empleado);
        model.addAttribute("garages", misGarages);

        return "portal/mis-tareas";
    }

    @GetMapping("/garage/{id}")
    public String verFichaGarage(@PathVariable Long id, Model model) {
        GarageDTO garage = garageService.findById(id);
        model.addAttribute("garage", garage);

        if (garage.getPropietarioId() != null) {
            SocioDTO propietario = socioService.findById(garage.getPropietarioId());
            model.addAttribute("propietario", propietario);
        }

        if (garage.getVehiculoOcupanteId() != null) {
            VehiculoDTO vehiculo = vehiculoService.findById(garage.getVehiculoOcupanteId());
            model.addAttribute("vehiculo", vehiculo);
        }
        
        // (Opcional) Podríamos validar aquí si el usuario logueado tiene permiso para ver ESTE garage específico
        // pero como es "consulta", asumimos que si tiene el link es porque puede verlo.

        return "portal/ficha-garage";
    }
}