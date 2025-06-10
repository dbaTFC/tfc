package com.tfg.gestionproyectos.models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "miembro_proyecto", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_miembro", "id_proyecto"})
})
public class MiembroProyecto {

    public enum RolProyecto {
        ADMINISTRADOR,
        MIEMBRO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_miembro", nullable = false)
    private Miembro miembro;

    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolProyecto rol;

    
    // @Temporal(TemporalType.TIMESTAMP)
    // @Column(nullable = false, updatable = false)
    // private Date fechaIncorporacion = new Date();

    // Constructores
    public MiembroProyecto() {}

    public MiembroProyecto(Miembro miembro, Proyecto proyecto, RolProyecto rol) {
        this.miembro = miembro;
        this.proyecto = proyecto;
        this.rol = rol;
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
