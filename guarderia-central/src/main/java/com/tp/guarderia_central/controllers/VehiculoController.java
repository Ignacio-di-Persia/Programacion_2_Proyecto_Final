package com.tp.guarderia_central.controllers;

import com.tp.guarderia_central.dtos.SocioDTO;
import com.tp.guarderia_central.dtos.VehiculoDTO;
import com.tp.guarderia_central.services.ISocioService;
import com.tp.guarderia_central.services.IVehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final IVehiculoService vehiculoService;
    private final ISocioService socioService;

    @GetMapping("/listar")
    public String listarVehiculos(Model model) {
        List<VehiculoDTO> vehiculos = vehiculoService.findAll();
        model.addAttribute("vehiculos", vehiculos);
        return "vehiculos/lista-vehiculos";
    }

    @GetMapping("/agregar")
    public String agregarVehiculo(Model model) {
        model.addAttribute("vehiculo", new VehiculoDTO());
        model.addAttribute("titulo", "Nuevo Vehículo");
        model.addAttribute("actionUrl", "/vehiculos/guardar");
        cargarSociosEnModelo(model);
        return "vehiculos/form-vehiculo";
    }

    @GetMapping("/editar/{id}")
    public String editarVehiculo(@PathVariable Long id, Model model) {
        VehiculoDTO vehiculo = vehiculoService.findById(id);
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("titulo", "Editar Vehículo");
        model.addAttribute("actionUrl", "/vehiculos/actualizar/" + id);
        cargarSociosEnModelo(model); 
        return "vehiculos/form-vehiculo";
    }

    @PostMapping("/guardar")
    public String guardarVehiculo(@Valid @ModelAttribute("vehiculo") VehiculoDTO vehiculoDTO, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/vehiculos/guardar");
            cargarSociosEnModelo(model);
            return "vehiculos/form-vehiculo";
        }

        try {
            vehiculoService.save(vehiculoDTO);
        } catch (RuntimeException e) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/vehiculos/guardar");
            model.addAttribute("error", e.getMessage());
            cargarSociosEnModelo(model);
            return "vehiculos/form-vehiculo";
        }

        flash.addFlashAttribute("success", "Vehículo creado correctamente");
        return "redirect:/vehiculos/listar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarVehiculo(@PathVariable Long id, @Valid @ModelAttribute("vehiculo") VehiculoDTO vehiculoDTO, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/vehiculos/actualizar/" + id);
            cargarSociosEnModelo(model);
            return "vehiculos/form-vehiculo";
        }

        try {
            vehiculoService.update(id, vehiculoDTO);
        } catch (RuntimeException e) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/vehiculos/actualizar/" + id);
            model.addAttribute("error", e.getMessage());
            cargarSociosEnModelo(model);
            return "vehiculos/form-vehiculo";
        }

        flash.addFlashAttribute("success", "Vehículo actualizado correctamente");
        return "redirect:/vehiculos/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarVehiculo(@PathVariable Long id, RedirectAttributes flash) {
        try {
            vehiculoService.deleteById(id);
            flash.addFlashAttribute("success", "Vehículo eliminado correctamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/vehiculos/listar";
    }

    private void cargarSociosEnModelo(Model model) {
        List<SocioDTO> socios = socioService.findAll();
        model.addAttribute("socios", socios);
    }
}