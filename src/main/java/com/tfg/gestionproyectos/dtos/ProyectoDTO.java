package com.tfg.gestionproyectos.dtos;

import com.tfg.gestionproyectos.models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    private List<MiembroProyectoDTO> miembros = new ArrayList<>();

    public ProyectoDTO() {}

      public ProyectoDTO(Proyecto proyecto) {
        this.idProyecto = proyecto.getIdProyecto();
        this.nombre = proyecto.getNombre();
        this.descripcion = proyecto.getDescripcion();
        this.fechaInicio = proyecto.getFechaInicio();
        this.fechaFin = proyecto.getFechaFin();

        // Tareas - for tradicional
        if (proyecto.getTareas() != null) {
            for (Tarea tarea : proyecto.getTareas()) {
                this.idTareas.add(tarea.getIdTarea());
            }
        }

        // Documentos - for tradicional
        if (proyecto.getDocumentos() != null) {
            for (Documento doc : proyecto.getDocumentos()) {
                this.idDocumentos.add(doc.getIdDocumento());
            }
        }

        // Eventos - for tradicional
        if (proyecto.getEventos() != null) {
            for (EventoCalendario evento : proyecto.getEventos()) {
                this.idEventos.add(evento.getIdEvento());
            }
        }

        // Mensajes - for tradicional
        if (proyecto.getMensajes() != null) {
            for (MensajeChat mensaje : proyecto.getMensajes()) {
                this.idMensajes.add(mensaje.getIdMensaje());
            }
        }

        // Miembros - for tradicional
        if (proyecto.getMiembrosProyecto() != null) {
            for (MiembroProyecto mp : proyecto.getMiembrosProyecto()) {
                this.miembros.add(new MiembroProyectoDTO(mp));
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

    public List<MiembroProyectoDTO> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<MiembroProyectoDTO> miembros) {
        this.miembros = miembros;
    }
}
