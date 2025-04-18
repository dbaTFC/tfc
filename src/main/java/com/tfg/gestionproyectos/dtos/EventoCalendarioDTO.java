package com.tfg.gestionproyectos.dtos;

import com.tfg.gestionproyectos.models.EventoCalendario;

import java.util.Date;

/**
 * DTO (Data Transfer Object) para la entidad EventoCalendario.
 * Este DTO se usa para controlar la salida en las respuestas JSON,
 * evitando ciclos infinitos y controlando qué campos se exponen.
 */
public class EventoCalendarioDTO {

    private Long idEvento;
    private Date fecha;
    private String descripcion;
    private Long idProyecto; // Solo se expone el ID del proyecto

    // Constructor que transforma un objeto EventoCalendario a su DTO
    public EventoCalendarioDTO(EventoCalendario evento) {
        this.idEvento = evento.getIdEvento();
        this.fecha = evento.getFecha();
        this.descripcion = evento.getDescripcion();
        this.idProyecto = evento.getProyecto().getIdProyecto();
    }

    // Getters y Setters
    public Long getIdEvento() { return idEvento; }
    public void setIdEvento(Long idEvento) { this.idEvento = idEvento; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Long getIdProyecto() { return idProyecto; }
    public void setIdProyecto(Long idProyecto) { this.idProyecto = idProyecto; }
}
