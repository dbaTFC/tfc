package com.tfg.gestionproyectos.dtos;

import java.util.Date;

import com.tfg.gestionproyectos.models.MiembroProyecto;

public class MiembroProyectoDTO {
    private Long id;
    private Long idMiembro;
    private Long idProyecto;
    private String rol;
    private Date fechaIncorporacion;
    private String nombreProyecto; // Opcional: para mostrar en frontend
    private String nombreMiembro; // Opcional: para mostrar en frontend

    public MiembroProyectoDTO() {}

    public MiembroProyectoDTO(MiembroProyecto miembroProyecto) {
        this.id = miembroProyecto.getId();
        this.idMiembro = miembroProyecto.getMiembro().getIdMiembro();
        this.idProyecto = miembroProyecto.getProyecto().getIdProyecto();
        this.rol = miembroProyecto.getRol().name();
        this.fechaIncorporacion = miembroProyecto.getFechaIncorporacion();
        
        // Opcional: incluir nombres para facilitar el frontend
        this.nombreProyecto = miembroProyecto.getProyecto().getNombre();
        this.nombreMiembro = miembroProyecto.getMiembro().getNombreUsuario();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(Long idMiembro) {
        this.idMiembro = idMiembro;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Date getFechaIncorporacion() {
        return fechaIncorporacion;
    }

    public void setFechaIncorporacion(Date fechaIncorporacion) {
        this.fechaIncorporacion = fechaIncorporacion;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getNombreMiembro() {
        return nombreMiembro;
    }

    public void setNombreMiembro(String nombreMiembro) {
        this.nombreMiembro = nombreMiembro;
    }
}