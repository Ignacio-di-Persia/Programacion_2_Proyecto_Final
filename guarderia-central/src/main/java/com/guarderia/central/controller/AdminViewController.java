package com.guarderia.central.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AdminViewController {
    @GetMapping({"/", "/index"})
    public String index() {
        log.info("Accediendo a la página principal");
        return "index";
    }

    @GetMapping({"/menu-socios"})
    public String menuSocios() {
        log.info("Accediendo al menu de socios");
        return "menu-socios";
    }

    @GetMapping({"/menu-empleados"})
    public String menuEmpleados() {
        log.info("Accediendo al menu de socios");
        return "menu-empleados";
    }

    @GetMapping({"/menu-zonas"})
    public String menuZonas() {
        log.info("Accediendo al menu de socios");
        return "menu-zonas";
    }
}
