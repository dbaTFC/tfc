package com.tfg.gestionproyectos.dtos;

import com.tfg.gestionproyectos.models.EventoCalendario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

/**
 * DTO para EventoCalendario.
 * Facilita la transferencia de datos simplificada entre la capa de presentación y lógica.
 */
public class EventoCalendarioDTO {

    private Long idEvento;

    @NotBlank(message = "El título del evento no puede estar vacío.")
    @Size(max = 100, message = "El título no puede tener más de 100 caracteres.")
    private String titulo;

    @NotNull(message = "La fecha del evento es obligatoria.")
    private Date fecha;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres.")
    @NotBlank(message = "La descripción no puede estar vacía.")
    private String descripcion;

    @NotNull(message = "El ID del proyecto asociado no puede ser nulo.")
    private Long idProyecto;

    // Constructor para mapear entidad a DTO
    public EventoCalendarioDTO(EventoCalendario evento) {
        this.idEvento = evento.getIdEvento();
        this.titulo = evento.getTitulo();
        this.fecha = evento.getFecha();
        this.descripcion = evento.getDescripcion();
        this.idProyecto = evento.getProyecto().getIdProyecto();
    }

    // Constructor vacío para frameworks (Jackson, Swagger, etc.)
    public EventoCalendarioDTO() { }

    // Getters y Setters
    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }
}
