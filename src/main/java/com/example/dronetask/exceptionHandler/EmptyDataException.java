package com.example.dronetask.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
public class EmptyDataException extends RuntimeException {
    public EmptyDataException(String message) {
            super(message);
        }
}
