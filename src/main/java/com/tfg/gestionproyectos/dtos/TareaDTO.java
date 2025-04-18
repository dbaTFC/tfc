package com.tfg.gestionproyectos.dtos;

import com.tfg.gestionproyectos.models.Tarea;
import com.tfg.gestionproyectos.models.Tarea.EstadoTarea;

import java.util.Date;

public class TareaDTO {

    private Long idTarea;
    private String titulo;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private EstadoTarea estado;
    private Long idProyecto;  // Solo el id del Proyecto
    private Long idMiembro;   // Solo el id del Miembro asignado

    // Constructor que recibe una entidad Tarea
    public TareaDTO(Tarea tarea) {
        this.idTarea = tarea.getIdTarea();
        this.titulo = tarea.getTitulo();
        this.descripcion = tarea.getDescripcion();
        this.fechaInicio = tarea.getFechaInicio();
        this.fechaFin = tarea.getFechaFin();
        this.estado = tarea.getEstado();
        this.idProyecto = tarea.getProyecto().getIdProyecto();  // Solo incluimos el ID del proyecto
        if (tarea.getAsignadoA() != null) {
            this.idMiembro = tarea.getAsignadoA().getIdMiembro();  // Solo incluimos el ID del miembro asignado
        }
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
