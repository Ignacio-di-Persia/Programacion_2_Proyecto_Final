package com.guarderia.central.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.guarderia.central.service.SocioService;
import com.guarderia.central.service.GarageService;
import com.guarderia.central.dto.SocioDTO;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

 
@Controller
@Slf4j
@RequestMapping("/socios")
@RequiredArgsConstructor
public class SocioViewController {

    private final SocioService socioService;
    private final GarageService garageService;


    @GetMapping("/lista-socios")
    public String listaSocios(Model model) {
        List<SocioDTO> socios = socioService.listarDTO();
        model.addAttribute("socios", socios);
        return "lista-socios";
    }

    @GetMapping("/agregar-socio")
    public String mostrarFormulario(Model model) {
        model.addAttribute("socio", new SocioDTO());
        model.addAttribute("garages", garageService.listarGarages());
        return "agregar-socio";
    }

    @GetMapping("/editar-socio/{id}")
    public String mostrarEditar(@PathVariable Long id, Model model) {
        model.addAttribute("socio", socioService.buscarDTO(id));
        model.addAttribute("garages", garageService.listarGarages());
        return "agregar-socio";
    }

    @PostMapping
    public String guardar(@ModelAttribute SocioDTO socioDTO) {
        socioService.guardarDTO(socioDTO); // Guarda socio y garages en un solo paso
        return "redirect:/socios/lista-socios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        socioService.eliminar(id);
        return "redirect:/socios/lista-socios";
    }
    
    /*  Metodos sin DTO
    // Página de lista de socios
    @GetMapping("/lista-socios")
    public String listaSocios() {
        log.info("Accediendo a la lista de socios");
        return "lista-socios";
    }

    // Página de agregar socio
    @GetMapping("/agregar-socio")
    public String agregarSocio() {
        log.info("Accediendo a agregar socio");
        return "agregar-socio";
    }
        */
}