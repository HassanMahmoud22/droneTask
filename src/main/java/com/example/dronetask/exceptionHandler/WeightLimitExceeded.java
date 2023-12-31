package com.example.dronetask.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (HttpStatus.BAD_REQUEST)
public class WeightLimitExceeded extends RuntimeException {
    public WeightLimitExceeded(String message) {
        super(message);
    }
}
