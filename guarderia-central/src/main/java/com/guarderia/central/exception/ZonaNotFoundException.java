package com.guarderia.central.exception;

public class ZonaNotFoundException extends RuntimeException {

    public ZonaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
