package com.tfg.gestionproyectos.dtos;

import com.tfg.gestionproyectos.models.Tarea;
import com.tfg.gestionproyectos.models.Tarea.EstadoTarea;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class TareaDTO {

    private Long idTarea;

    @NotBlank(message = "El título de la tarea no puede estar vacío.")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres.")
    private String titulo;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres.")
    private String descripcion;

    @NotNull(message = "La fecha de inicio es obligatoria.")
    private Date fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria.")
    private Date fechaFin;

    @NotNull(message = "El estado de la tarea es obligatorio.")
    private EstadoTarea estado;

    @NotNull(message = "El ID del proyecto es obligatorio.")
    private Long idProyecto;

    // Puede ser nulo si la tarea aún no está asignada
    private Long idMiembro;

    // Constructor que recibe una entidad Tarea
    public TareaDTO(Tarea tarea) {
        this.idTarea = tarea.getIdTarea();
        this.titulo = tarea.getTitulo();
        this.descripcion = tarea.getDescripcion();
        this.fechaInicio = tarea.getFechaInicio();
        this.fechaFin = tarea.getFechaFin();
        this.estado = tarea.getEstado();
        this.idProyecto = tarea.getProyecto().getIdProyecto();
        if (tarea.getAsignadoA() != null) {
            this.idMiembro = tarea.getAsignadoA().getIdMiembro();
        }
    }

    //constructor vacío necesario para Swagger
    public TareaDTO(){
        
    }

    // Getters y Setters
    public Long getIdTarea() { return idTarea; }
    public void setIdTarea(Long idTarea) { this.idTarea = idTarea; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public EstadoTarea getEstado() { return estado; }
    public void setEstado(EstadoTarea estado) { this.estado = estado; }

    public Long getIdProyecto() { return idProyecto; }
    public void setIdProyecto(Long idProyecto) { this.idProyecto = idProyecto; }

    public Long getIdMiembro() { return idMiembro; }
    public void setIdMiembro(Long idMiembro) { this.idMiembro = idMiembro; }
}
