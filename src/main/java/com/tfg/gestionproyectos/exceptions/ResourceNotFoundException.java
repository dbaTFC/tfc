package com.tfg.gestionproyectos.exceptions;

/**
 * Excepción personalizada para representar que un recurso no ha sido encontrado.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
