package com.tfg.gestionproyectos.models;

import java.util.Date;

import jakarta.persistence.*;

/**
 * Entidad que representa la relación entre un Miembro y un Proyecto,
 * incluyendo el rol que el miembro tiene en dicho proyecto.
 * La tabla 'miembro_proyecto' asegura que un mismo miembro no se pueda asignar
 * dos veces al mismo proyecto (unique constraint).
 */
@Entity
@Table(name = "miembro_proyecto", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_miembro", "id_proyecto"})
})
public class MiembroProyecto {

    /**
     * Enumeración que define los posibles roles que un miembro puede tener en un proyecto.
     */
    public enum RolProyecto {
        ADMINISTRADOR,
        MIEMBRO
    }

    // Identificador único autogenerado para la relación
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Miembro asociado a este proyecto
    @ManyToOne
    @JoinColumn(name = "id_miembro", nullable = false)
    private Miembro miembro;

    // Proyecto asociado al miembro
    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    // Rol que tiene el miembro en el proyecto, almacenado como String
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolProyecto rol;

    // Fecha de incorporación comentada. Si quieres usarla, descomenta y maneja la asignación.
    // @Temporal(TemporalType.TIMESTAMP)
    // @Column(nullable = false, updatable = false)
    // private Date fechaIncorporacion = new Date();

    // Constructores

    public MiembroProyecto() {}

    public MiembroProyecto(Miembro miembro, Proyecto proyecto, RolProyecto rol) {
        this.miembro = miembro;
        this.proyecto = proyecto;
        this.rol = rol;
        // Si quieres, aquí podrías asignar la fecha de incorporación, ej:
        // this.fechaIncorporacion = new Date();
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public RolProyecto getRol() {
        return rol;
    }

    public void setRol(RolProyecto rol) {
        this.rol = rol;
    }

    // public Date getFechaIncorporacion() {
    //     return fechaIncorporacion;
    // }
}
