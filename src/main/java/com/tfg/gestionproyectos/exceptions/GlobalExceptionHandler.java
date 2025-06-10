package com.tfg.gestionproyectos.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejo global de excepciones para toda la aplicación.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura errores de validación de objetos anotados con @Valid
     * (por ejemplo, en @RequestBody con DTOs o entidades).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        return new ResponseEntity<>(Map.of("errores", errores), HttpStatus.BAD_REQUEST);
    }

    /**
     * Captura errores de validación directa (por ejemplo, en @PathVariable o @RequestParam).
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errores = new HashMap<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String campo = violation.getPropertyPath().toString();
            errores.put(campo, violation.getMessage());
        }

        return new ResponseEntity<>(Map.of("errores", errores), HttpStatus.BAD_REQUEST);
    }

    /**
     * Captura errores cuando el JSON recibido está mal formado o no puede mapearse.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleMalformedJson(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
                Map.of("error", "El cuerpo de la solicitud está mal formado o contiene datos inválidos."),
                HttpStatus.BAD_REQUEST
        );
    }


    /**
     * Captura errores personalizados para recursos no encontrados.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                Map.of("error", ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * Captura cualquier otro error no controlado.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(
                Map.of("error", "Error interno del servidor: " + ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
