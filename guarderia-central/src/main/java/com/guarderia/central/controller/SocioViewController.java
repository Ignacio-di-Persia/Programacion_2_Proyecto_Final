package com.guarderia.central.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class SocioViewController {

    // Página principal (index)
    @GetMapping({"/", "/index"})
    public String index() {
        log.info("Accediendo a la página principal");
        return "index";
    }

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
}