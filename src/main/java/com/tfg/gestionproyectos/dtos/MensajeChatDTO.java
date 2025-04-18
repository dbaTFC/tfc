package com.tfg.gestionproyectos.dtos;

import com.tfg.gestionproyectos.models.MensajeChat;

import java.util.Date;

/**
 * DTO para la entidad MensajeChat.
 * Sirve para exponer únicamente los datos necesarios en la respuesta JSON,
 * evitando ciclos infinitos al serializar objetos relacionados (Proyecto, Miembro).
 */
public class MensajeChatDTO {

    private Long idMensaje;
    private Long idProyecto;   // Solo mostramos el ID del proyecto
    private Long idMiembro;    // Solo mostramos el ID del miembro (autor)
    private Date fechaHora;
    private String contenido;

    // Constructor que transforma un MensajeChat a su versión DTO
    public MensajeChatDTO(MensajeChat mensaje) {
        this.idMensaje = mensaje.getIdMensaje();
        this.idProyecto = mensaje.getProyecto().getIdProyecto();
        this.idMiembro = mensaje.getMiembro().getIdMiembro();
        this.fechaHora = mensaje.getFechaHora();
        this.contenido = mensaje.getContenido();
    }

    // Getters y Setters
    public Long getIdMensaje() { return idMensaje; }
    public void setIdMensaje(Long idMensaje) { this.idMensaje = idMensaje; }

    public Long getIdProyecto() { return idProyecto; }
    public void setIdProyecto(Long idProyecto) { this.idProyecto = idProyecto; }

    public Long getIdMiembro() { return idMiembro; }
    public void setIdMiembro(Long idMiembro) { this.idMiembro = idMiembro; }

    public Date getFechaHora() { return fechaHora; }
    public void setFechaHora(Date fechaHora) { this.fechaHora = fechaHora; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
}
