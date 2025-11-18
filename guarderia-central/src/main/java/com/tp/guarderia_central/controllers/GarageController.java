package com.tp.guarderia_central.controllers;

import com.tp.guarderia_central.dtos.AsignacionGarageVehiculoDTO;
import com.tp.guarderia_central.dtos.CompraGarageDTO;
import com.tp.guarderia_central.dtos.GarageDTO;
import com.tp.guarderia_central.dtos.VehiculoDTO;
import com.tp.guarderia_central.dtos.ZonaDTO;
import com.tp.guarderia_central.services.IGarageService;
import com.tp.guarderia_central.services.ISocioService;
import com.tp.guarderia_central.services.IVehiculoService;
import com.tp.guarderia_central.services.IZonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/garages")
@RequiredArgsConstructor
public class GarageController {

    private final IGarageService garageService;
    private final IZonaService zonaService;
    private final IVehiculoService vehiculoService;
    private final ISocioService socioService;

    @GetMapping("/listar")
    public String listarGarages(Model model) {
        List<GarageDTO> garages = garageService.findAll();
        model.addAttribute("garages", garages);
        return "garages/lista-garages";
    }

    @GetMapping("/agregar")
    public String agregarGarage(Model model) {
        model.addAttribute("garage", new GarageDTO());
        model.addAttribute("titulo", "Nuevo Garage");
        model.addAttribute("actionUrl", "/garages/guardar");
        cargarZonasEnModelo(model);
        return "garages/form-garage"; 
    }

    @GetMapping("/editar/{id}")
    public String editarGarage(@PathVariable Long id, Model model) {
        model.addAttribute("garage", garageService.findById(id));
        model.addAttribute("titulo", "Editar Garage");
        model.addAttribute("actionUrl", "/garages/actualizar/" + id);
        cargarZonasEnModelo(model);
        return "garages/form-garage";
    }

    @PostMapping("/guardar")
    public String guardarGarage(@Valid @ModelAttribute("garage") GarageDTO garageDTO, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/garages/guardar");
            cargarZonasEnModelo(model);
            return "garages/form-garage";
        }
        try {
            garageService.save(garageDTO);
        } catch (RuntimeException e) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/garages/guardar");
            model.addAttribute("error", e.getMessage());
            cargarZonasEnModelo(model);
            return "garages/form-garage";
        }
        flash.addFlashAttribute("success", "Garage creado correctamente");
        return "redirect:/garages/listar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarGarage(@PathVariable Long id, @Valid @ModelAttribute("garage") GarageDTO garageDTO, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/garages/actualizar/" + id);
            cargarZonasEnModelo(model);
            return "garages/form-garage";
        }
        try {
            garageService.update(id, garageDTO);
        } catch (RuntimeException e) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/garages/actualizar/" + id);
            model.addAttribute("error", e.getMessage());
            cargarZonasEnModelo(model);
            return "garages/form-garage";
        }
        flash.addFlashAttribute("success", "Garage actualizado correctamente");
        return "redirect:/garages/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarGarage(@PathVariable Long id, RedirectAttributes flash) {
        try {
            garageService.deleteById(id);
            flash.addFlashAttribute("success", "Garage eliminado correctamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/garages/listar";
    }

    // Funciones de asignacion de vehiculos

    @GetMapping("/asignar/{id}")
    public String mostrarAsignarVehiculo(@PathVariable Long id, Model model) {
        GarageDTO garage = garageService.findById(id);
        AsignacionGarageVehiculoDTO asignacion = new AsignacionGarageVehiculoDTO();
        asignacion.setGarageId(id);
        model.addAttribute("asignacion", asignacion);
        model.addAttribute("garage", garage);
        model.addAttribute("titulo", "Asignar Vehículo a Garage " + garage.getNumeroGarage());
        cargarVehiculosDisponibles(model);
        return "garages/asignar-vehiculo";
    }

    @PostMapping("/asignar")
    public String asignarVehiculo(@Valid @ModelAttribute("asignacion") AsignacionGarageVehiculoDTO dto, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            GarageDTO garage = garageService.findById(dto.getGarageId());
            model.addAttribute("garage", garage);
            model.addAttribute("titulo", "Asignar Vehículo - Error");
            cargarVehiculosDisponibles(model);
            return "garages/asignar-vehiculo";
        }

        try {
            garageService.asignarVehiculo(dto.getGarageId(), dto.getVehiculoId());
        } catch (RuntimeException e) {
            GarageDTO garage = garageService.findById(dto.getGarageId());
            model.addAttribute("garage", garage);
            model.addAttribute("titulo", "Asignar Vehículo - Error");
            model.addAttribute("error", e.getMessage());
            cargarVehiculosDisponibles(model);
            return "garages/asignar-vehiculo";
        }
        flash.addFlashAttribute("success", "Vehículo asignado correctamente.");
        return "redirect:/garages/listar";
    }

    @GetMapping("/liberar/{id}")
    public String liberarGarage(@PathVariable Long id, RedirectAttributes flash) {
        try {
            garageService.liberarGarage(id);
            flash.addFlashAttribute("success", "Garage liberado correctamente.");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/garages/listar";
    }

    @GetMapping("/comprar/{id}")
    public String mostrarCompraGarage(@PathVariable Long id, Model model) {
        GarageDTO garage = garageService.findById(id);
        
        CompraGarageDTO compra = new CompraGarageDTO();
        compra.setGarageId(id);
        compra.setFechaCompra(java.time.LocalDate.now());

        model.addAttribute("compra", compra);
        model.addAttribute("garage", garage);
        model.addAttribute("titulo", "Vender Garage " + garage.getNumeroGarage());
        model.addAttribute("socios", socioService.findAll());
        
        cargarSociosYDatosAsociados(model);

        return "garages/compra-garage";
    }

    @PostMapping("/comprar")
    public String procesarCompra(@Valid @ModelAttribute("compra") CompraGarageDTO dto, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            reestablecerModeloCompra(model, dto.getGarageId());
            model.addAttribute("titulo", "Vender Garage - Error");
            return "garages/compra-garage";
        }
        try {
            garageService.asignarPropietario(dto);
        } catch (RuntimeException e) {
            reestablecerModeloCompra(model, dto.getGarageId());
            model.addAttribute("titulo", "Vender Garage - Error");
            model.addAttribute("error", e.getMessage());
            return "garages/compra-garage";
        }

        flash.addFlashAttribute("success", "Garage vendido correctamente.");
        return "redirect:/garages/listar";
    }


    @GetMapping("/liberar-dueno/{id}")
    public String liberarDueno(@PathVariable Long id, RedirectAttributes flash) {
        try {
            garageService.liberarPropietario(id);
            flash.addFlashAttribute("success", "Propietario removido correctamente.");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/garages/listar";
    }

    // Metodos auxiliares del controlador

    private void cargarZonasEnModelo(Model model) {
        List<ZonaDTO> zonas = zonaService.findAll();
        model.addAttribute("zonas", zonas);
    }

    private void cargarVehiculosDisponibles(Model model) {
        List<VehiculoDTO> vehiculos = vehiculoService.findAll();
        model.addAttribute("vehiculos", vehiculos);
    }

    private void cargarSociosYDatosAsociados(Model model) {
        model.addAttribute("socios", socioService.findAll());
    }

    private void reestablecerModeloCompra(Model model, Long garageId) {
        GarageDTO garage = garageService.findById(garageId);
        model.addAttribute("garage", garage);
        cargarSociosYDatosAsociados(model);
    }
}