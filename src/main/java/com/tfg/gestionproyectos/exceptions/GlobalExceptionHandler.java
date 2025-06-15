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
 * Clase para manejo global de excepciones en la aplicación.
 * Captura distintas excepciones y devuelve respuestas HTTP con mensajes claros.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación en objetos anotados con @Valid
     * (por ejemplo, DTOs recibidos en @RequestBody).
     * @param ex excepción lanzada por error de validación
     * @return respuesta HTTP 400 con detalle de errores por campo
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        // Recorre todos los errores de campo y los agrega al mapa
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        // Devuelve los errores agrupados bajo la clave "errores" con código 400 Bad Request
        return new ResponseEntity<>(Map.of("errores", errores), HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja errores de validación directa en parámetros como @PathVariable o @RequestParam.
     * @param ex excepción lanzada por violación de restricciones
     * @return respuesta HTTP 400 con detalle de errores por campo
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errores = new HashMap<>();

        // Recorre cada violación y agrega el campo y mensaje al mapa
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String campo = violation.getPropertyPath().toString();
            errores.put(campo, violation.getMessage());
        }

        // Devuelve los errores agrupados bajo la clave "errores" con código 400 Bad Request
        return new ResponseEntity<>(Map.of("errores", errores), HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja errores cuando el JSON recibido está mal formado o no puede mapearse.
     * @param ex excepción lanzada por error en el parseo del cuerpo de la solicitud
     * @return respuesta HTTP 400 con mensaje de error general
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleMalformedJson(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
                Map.of("error", "El cuerpo de la solicitud está mal formado o contiene datos inválidos."),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Maneja excepciones personalizadas para recursos no encontrados.
     * @param ex excepción personalizada con mensaje específico
     * @return respuesta HTTP 404 con mensaje de error
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                Map.of("error", ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * Captura cualquier otra excepción no manejada específicamente.
     * @param ex excepción genérica
     * @return respuesta HTTP 500 con mensaje de error interno
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(
                Map.of("error", "Error interno del servidor: " + ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
