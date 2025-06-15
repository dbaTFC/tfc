package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Entidad Proyecto que representa un proyecto dentro del sistema.
 * Se mapea a la tabla "proyectos" en la base de datos.
 */
@Entity
@Table(name = "proyectos")
public class Proyecto {

    // Identificador único autogenerado para cada proyecto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Long idProyecto;

    // Nombre del proyecto obligatorio, con validación de longitud
    @NotBlank(message = "El nombre del proyecto no puede estar vacío.")
    @Size(min = 3, max = 100, message = "El nombre del proyecto debe tener entre 3 y 100 caracteres.")
    @Column(nullable = false)
    private String nombre;

    // Descripción opcional con límite de caracteres
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres.")
    private String descripcion;

    // Fecha de inicio obligatoria, solo fecha (sin hora)
    @NotNull(message = "La fecha de inicio del proyecto es obligatoria.")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    // Fecha de fin obligatoria, solo fecha (sin hora)
    @NotNull(message = "La fecha de fin del proyecto es obligatoria.")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    // Relación OneToMany con Tarea (un proyecto puede tener muchas tareas)
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareas;

    // Relación OneToMany con Documento (un proyecto puede tener muchos documentos)
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos;

    // Relación OneToMany con EventoCalendario (eventos vinculados al proyecto)
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventoCalendario> eventos;

    // Relación OneToMany con MensajeChat (mensajes relacionados con el proyecto)
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MensajeChat> mensajes;

    // Relación OneToMany con MiembroProyecto (miembros que participan en el proyecto)
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MiembroProyecto> miembrosProyecto;

    // Constructor vacío necesario para JPA
    public Proyecto() {
    }

    /**
     * Constructor para crear un proyecto con los datos esenciales.
     *
     * @param nombre Nombre del proyecto.
     * @param descripcion Descripción del proyecto.
     * @param fechaInicio Fecha de inicio.
     * @param fechaFin Fecha de finalización.
     */
    public Proyecto(String nombre, String descripcion, Date fechaInicio, Date fechaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y setters para los atributos

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long id) {
        this.idProyecto = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Set<MiembroProyecto> getMiembrosProyecto() {
        return miembrosProyecto;
    }

    public void setMiembrosProyecto(Set<MiembroProyecto> miembrosProyecto) {
        this.miembrosProyecto = miembrosProyecto;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public List<EventoCalendario> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoCalendario> eventos) {
        this.eventos = eventos;
    }

    public List<MensajeChat> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<MensajeChat> mensajes) {
        this.mensajes = mensajes;
    }
}
