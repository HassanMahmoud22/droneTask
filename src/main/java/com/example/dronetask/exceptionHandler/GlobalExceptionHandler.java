package com.example.dronetask.exceptionHandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * handles Validation Error Exceptios
     *
     * @param ex    Validation error exception
     * @return      Response Entity with bad request code and Map of Errors
     */
    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * handles DroneNotFound Exception
     *
     * @param ex DroneNotFound Exception
     * @return   Response Entity with bad request code and Map of Errors
     */
    @ExceptionHandler(DroneNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleDroneNotFoundException(DroneNotFoundException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * handles WeightLimitExceeded Exception
     *
     * @param ex WeightLimitExceeded Exception
     * @return   Response Entity with bad request code and Map of Errors
     */
    @ExceptionHandler(WeightLimitExceeded.class)
    public ResponseEntity<Map<String, List<String>>> handleWeightLimitExceededException(WeightLimitExceeded ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * handles General Exceptions
     *
     * @param ex    The General Exception
     * @return      Response Entity with Internal server error code and Map of Errors
     */
    @ExceptionHandler(MedicationExistsException.class)
    public final ResponseEntity<Map<String, List<String>>> handleMedicationExistsException(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * handles DroneNotFound Exception
     *
     * @param ex EmptyDataException Exception
     * @return   Response Entity with Bad Request error code and Map of Errors
     */
    @ExceptionHandler(EmptyDataException.class)
    public ResponseEntity<Map<String, List<String>>> handleEmptyDataException(EmptyDataException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * handles General Exceptions
     *
     * @param ex    The General Exception
     * @return      Response Entity with Internal server error code and Map of Errors
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * handles RunTime Exceptions
     *
     * @param ex    The RunTime Exception
     * @return      Response Entity with Internal server error code and Map of Errors
     */
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * creates Error Response Format
     *
     * @param errors    The list of errors
     * @return          Map of the errros
     */
    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}