package com.guarderia.central.exception;

public class SocioException extends RuntimeException {

    public SocioException(String mensaje) {
        super(mensaje);
    }

    public SocioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}