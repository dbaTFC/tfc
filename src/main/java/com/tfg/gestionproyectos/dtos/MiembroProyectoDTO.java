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

    // Constructor vacío necesario para frameworks de deserialización y Swagger
    public MiembroProyectoDTO() {}

    /**
     * Constructor que convierte una entidad MiembroProyecto a su DTO.
     * Extrae solo los datos necesarios para evitar enviar toda la entidad.
     * Incluye además nombres para facilitar su uso en el frontend.
     */
    public MiembroProyectoDTO(MiembroProyecto miembroProyecto) {
        this.id = miembroProyecto.getId();
        this.idMiembro = miembroProyecto.getMiembro().getIdMiembro();
        this.idProyecto = miembroProyecto.getProyecto().getIdProyecto();
        this.rol = miembroProyecto.getRol().name();
        // this.fechaIncorporacion = miembroProyecto.getFechaIncorporacion(); // Está comentado en el original
        
        // Se incluyen opcionalmente nombres para mostrar en frontend
        this.nombreProyecto = miembroProyecto.getProyecto().getNombre();
        this.nombreMiembro = miembroProyecto.getMiembro().getNombreUsuario();
    }

    // Getters y Setters para todos los campos
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
