package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tareas")
public class Tarea {
    public enum EstadoTarea {
        PENDIENTE,
        EN_PROGRESO,
        COMPLETADA
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    @Column(name = "id_tarea")
    private Long idTarea;

    @Column(nullable = false)
    private String titulo;

    private String descripcion;

    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Enumerated(EnumType.STRING) // Almacenar el estado como texto en la base de datos
    @Column(nullable = false)
    private EstadoTarea estado;

    // Relación con Proyecto (Muchas Tareas pertenecen a un Proyecto)
    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    // Relación con Miembro (Una Tarea es asignada a un Miembro)
    @ManyToOne
    @JoinColumn(name = "id_miembro")
    private Miembro asignadoA;

    // Constructores
    public Tarea() {}

    public Tarea(String titulo, String descripcion, Date fechaInicio, Date fechaFin, EstadoTarea estado, Proyecto proyecto, Miembro asignadoA) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio= fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.proyecto = proyecto;
        this.asignadoA = asignadoA;
    }

    // Getters y Setters
    public Long getIdTarea() { return idTarea; }
    public void setIdTarea(Long id) { this.idTarea = id; }

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
