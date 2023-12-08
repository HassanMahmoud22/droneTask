package com.example.dronetask.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (HttpStatus.BAD_REQUEST)
public class MedicationExistsException extends RuntimeException {
    public MedicationExistsException(String message) {
        super(message);
    }
}
