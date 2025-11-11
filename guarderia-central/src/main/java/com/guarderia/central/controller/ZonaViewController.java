package com.guarderia.central.controller;

import com.guarderia.central.entity.Zona;
import com.guarderia.central.service.ZonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/zonas")
@RequiredArgsConstructor
public class ZonaViewController {

    private final ZonaService zonaService;

    @GetMapping("/listar")
    public String listaZonas(Model model) {
        model.addAttribute("zonas", zonaService.listarZonas());
        return "lista-zonas";
    }

    @GetMapping("/agregar")
    public String agregarZona(Model model) {
        model.addAttribute("zona", new Zona());
        return "agregar-zona";
    }
}