package com.tfg.gestionproyectos.dtos;

import com.tfg.gestionproyectos.models.MensajeChat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

/**
 * DTO para la entidad MensajeChat.
 * Sirve para exponer únicamente los datos necesarios en la respuesta JSON,
 * evitando ciclos infinitos al serializar objetos relacionados (Proyecto, Miembro).
 */
public class MensajeChatDTO {

    private Long idMensaje;

    @NotNull(message = "El ID del proyecto es obligatorio.")
    private Long idProyecto;

    @NotNull(message = "El ID del miembro (autor) es obligatorio.")
    private Long idMiembro;

    @NotNull(message = "La fecha y hora del mensaje no puede ser nula.")
    private Date fechaHora;

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres.")
    private String nombreUsuario;

    @NotBlank(message = "El contenido del mensaje no puede estar vacío.")
    @Size(max = 1000, message = "El contenido no puede superar los 1000 caracteres.")
    private String contenido;

    // Constructor que transforma un MensajeChat a su versión DTO
    public MensajeChatDTO(MensajeChat mensaje) {
        this.idMensaje = mensaje.getIdMensaje();
        this.idProyecto = mensaje.getProyecto().getIdProyecto();
        this.idMiembro = mensaje.getMiembro().getIdMiembro();
        this.fechaHora = mensaje.getFechaHora();
        this.contenido = mensaje.getContenido();
         this.nombreUsuario = mensaje.getMiembro().getNombreUsuario();
    }

    // Constructor vacío requerido para deserialización JSON
    public MensajeChatDTO() {
    
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

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
}
