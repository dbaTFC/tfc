package com.tfg.gestionproyectos.dtos;

import com.tfg.gestionproyectos.models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProyectoDTO {

    private Long idProyecto;
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;

    private List<Long> idTareas = new ArrayList<>();
    private List<Long> idDocumentos = new ArrayList<>();
    private List<Long> idEventos = new ArrayList<>();
    private List<Long> idMensajes = new ArrayList<>();
    private Set<Long> idMiembros = new HashSet<>();

    public ProyectoDTO() {}

    public ProyectoDTO(Proyecto proyecto) {
        this.idProyecto = proyecto.getIdProyecto();
        this.nombre = proyecto.getNombre();
        this.descripcion = proyecto.getDescripcion();
        this.fechaInicio = proyecto.getFechaInicio();
        this.fechaFin = proyecto.getFechaFin();

        if (proyecto.getTareas() != null) {
            for (Tarea tarea : proyecto.getTareas()) {
                this.idTareas.add(tarea.getIdTarea());
            }
        }

        if (proyecto.getDocumentos() != null) {
            for (Documento doc : proyecto.getDocumentos()) {
                this.idDocumentos.add(doc.getIdDocumento());
            }
        }

        if (proyecto.getEventos() != null) {
            for (EventoCalendario evento : proyecto.getEventos()) {
                this.idEventos.add(evento.getIdEvento());
            }
        }

        if (proyecto.getMensajes() != null) {
            for (MensajeChat mensaje : proyecto.getMensajes()) {
                this.idMensajes.add(mensaje.getIdMensaje());
            }
        }

        if (proyecto.getMiembros() != null) {
            for (Miembro miembro : proyecto.getMiembros()) {
                this.idMiembros.add(miembro.getIdMiembro());
            }
        }
    }

    // Getters y Setters

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
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

    public List<Long> getIdTareas() {
        return idTareas;
    }

    public void setIdTareas(List<Long> idTareas) {
        this.idTareas = idTareas;
    }

    public List<Long> getIdDocumentos() {
        return idDocumentos;
    }

    public void setIdDocumentos(List<Long> idDocumentos) {
        this.idDocumentos = idDocumentos;
    }

    public List<Long> getIdEventos() {
        return idEventos;
    }

    public void setIdEventos(List<Long> idEventos) {
        this.idEventos = idEventos;
    }

    public List<Long> getIdMensajes() {
        return idMensajes;
    }

    public void setIdMensajes(List<Long> idMensajes) {
        this.idMensajes = idMensajes;
    }

    public Set<Long> getIdMiembros() {
        return idMiembros;
    }

    public void setIdMiembros(Set<Long> idMiembros) {
        this.idMiembros = idMiembros;
    }
}
