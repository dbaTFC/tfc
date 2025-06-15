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

    // Listas de IDs para relacionar las entidades sin exponerlas completamente
    private List<Long> idTareas = new ArrayList<>();
    private List<Long> idDocumentos = new ArrayList<>();
    private List<Long> idEventos = new ArrayList<>();
    private List<Long> idMensajes = new ArrayList<>();
    
    // Lista de miembros del proyecto representados como DTO para incluir info específica
    private List<MiembroProyectoDTO> miembros = new ArrayList<>();

    // Constructor vacío requerido para frameworks de serialización y deserialización
    public ProyectoDTO() {}

    /**
     * Constructor que transforma una entidad Proyecto en su DTO.
     * Se extraen los IDs de las entidades relacionadas para evitar enviar datos completos.
     * También convierte cada relación de MiembroProyecto a su DTO para facilitar el frontend.
     */
    public ProyectoDTO(Proyecto proyecto) {
        this.idProyecto = proyecto.getIdProyecto();
        this.nombre = proyecto.getNombre();
        this.descripcion = proyecto.getDescripcion();
        this.fechaInicio = proyecto.getFechaInicio();
        this.fechaFin = proyecto.getFechaFin();

        // Extraer IDs de las tareas relacionadas
        if (proyecto.getTareas() != null) {
            for (Tarea tarea : proyecto.getTareas()) {
                this.idTareas.add(tarea.getIdTarea());
            }
        }

        // Extraer IDs de los documentos relacionados
        if (proyecto.getDocumentos() != null) {
            for (Documento doc : proyecto.getDocumentos()) {
                this.idDocumentos.add(doc.getIdDocumento());
            }
        }

        // Extraer IDs de los eventos relacionados
        if (proyecto.getEventos() != null) {
            for (EventoCalendario evento : proyecto.getEventos()) {
                this.idEventos.add(evento.getIdEvento());
            }
        }

        // Extraer IDs de los mensajes relacionados
        if (proyecto.getMensajes() != null) {
            for (MensajeChat mensaje : proyecto.getMensajes()) {
                this.idMensajes.add(mensaje.getIdMensaje());
            }
        }

        // Convertir miembros del proyecto a DTO para exponer solo la información necesaria
        if (proyecto.getMiembrosProyecto() != null) {
            for (MiembroProyecto mp : proyecto.getMiembrosProyecto()) {
                this.miembros.add(new MiembroProyectoDTO(mp));
            }
        }
    }

    // Getters y Setters para todos los campos del DTO
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
