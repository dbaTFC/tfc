package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Long idProyecto;

    @NotBlank(message = "El nombre del proyecto no puede estar vacío.")
    @Size(min = 3, max = 100, message = "El nombre del proyecto debe tener entre 3 y 100 caracteres.")
    @Column(nullable = false)
    private String nombre;

    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres.")
    private String descripcion;

    @NotNull(message = "La fecha de inicio del proyecto es obligatoria.")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @NotNull(message = "La fecha de fin del proyecto es obligatoria.")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareas;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventoCalendario> eventos;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MensajeChat> mensajes;

    @ManyToMany
    @JoinTable(
        name = "miembro_proyecto",
        joinColumns = @JoinColumn(name = "id_proyecto"),
        inverseJoinColumns = @JoinColumn(name = "id_miembro")
    )
    private Set<Miembro> miembros;

    // Constructores
    public Proyecto() {

    }

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

    public Set<Miembro> getMiembros() { return miembros; }
    public void setMiembros(Set<Miembro> miembros) { this.miembros = miembros; }
}
