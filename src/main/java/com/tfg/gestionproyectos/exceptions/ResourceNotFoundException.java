package com.tfg.gestionproyectos.exceptions;

/**
 * Excepción personalizada que representa el caso cuando un recurso
 * (como un Miembro, Proyecto, Tarea, etc.) no se encuentra en la base de datos.
 * Se utiliza para lanzar errores claros y específicos en la aplicación.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor que recibe un mensaje descriptivo del error.
     * @param mensaje mensaje con detalle del recurso no encontrado
     */
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
