package com.guarderia.central.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GarageNotFoundException extends RuntimeException {

    public GarageNotFoundException(String mensaje) {
        super(mensaje);
    }
}
