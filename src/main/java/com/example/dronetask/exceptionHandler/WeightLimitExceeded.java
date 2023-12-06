package com.example.dronetask.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (HttpStatus.NOT_FOUND)

public class WeightLimitExceeded extends RuntimeException {
    public WeightLimitExceeded(String message) {
        super(message);
    }
}
