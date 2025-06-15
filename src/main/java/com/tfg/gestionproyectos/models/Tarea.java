package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "tareas")
public class Tarea {

    // Enum para los posibles estados de la tarea
    public enum EstadoTarea {
        PENDIENTE,
        EN_PROGRESO,
        COMPLETADA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea")
    private Long idTarea;

    @NotBlank(message = "El título de la tarea no puede estar vacío.")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres.")
    @Column(nullable = false)
    private String titulo;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres.")
    private String descripcion;

    @NotNull(message = "La fecha de inicio de la tarea es obligatoria.")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @NotNull(message = "La fecha de fin de la tarea es obligatoria.")
    @FutureOrPresent(message = "La fecha de fin no puede estar en el pasado.")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @NotNull(message = "El estado de la tarea es obligatorio.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTarea estado;

    // Relación con Proyecto (obligatoria)
    @NotNull(message = "La tarea debe estar asociada a un proyecto.")
    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    // Relación con Miembro al que se le asigna la tarea (puede ser null)
    @ManyToOne
    @JoinColumn(name = "id_miembro")
    private Miembro asignadoA;

    // Constructores
    public Tarea() {}

    public Tarea(String titulo, String descripcion, Date fechaInicio, Date fechaFin, EstadoTarea estado, Proyecto proyecto, Miembro asignadoA) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.proyecto = proyecto;
        this.asignadoA = asignadoA;
    }

    // Getters y setters
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

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }

    public Miembro getAsignadoA() { return asignadoA; }
    public void setAsignadoA(Miembro asignadoA) { this.asignadoA = asignadoA; }
}
