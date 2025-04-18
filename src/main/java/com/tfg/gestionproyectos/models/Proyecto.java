package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    @Column(name = "id_proyecto")
    private Long idProyecto;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Temporal(TemporalType.DATE)//se almacenará solo la fecha (sin la hora)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    // Relación con Tareas (Un Proyecto tiene muchas Tareas)
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true) //cuando una entidad hija es desvinculada de su entidad padre, debe eliminarse automáticamente de la base de datos.
    private List<Tarea> tareas;

    // Relación con Documentos (Un Proyecto tiene muchos Documentos)
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos;

    // Relación con Eventos del Calendario
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventoCalendario> eventos;

    // Relación con Mensajes del Chat
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MensajeChat> mensajes;

    // Relación Muchos a Muchos con Miembros
    @ManyToMany
    @JoinTable(
        name = "miembro_proyecto",
        joinColumns = @JoinColumn(name = "id_proyecto"),
        inverseJoinColumns = @JoinColumn(name = "id_miembro")
    )
    private Set<Miembro> miembros;

    // Constructores (vacío como con parámetros)
    public Proyecto() {}

    public Proyecto(String nombre, String descripcion, Date fechaInicio, Date fechaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y Setters
    public Long getIdProyecto() { return idProyecto; }
    public void setIdProyecto(Long id) { this.idProyecto = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public Set<Miembro> getMiembros(){return miembros;}
    public void setMiembros(Set<Miembro> miembros){this.miembros = miembros;}
}
