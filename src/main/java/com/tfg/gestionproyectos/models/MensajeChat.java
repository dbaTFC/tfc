package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

/**
 * Entidad MensajeChat que representa un mensaje enviado en el chat de un proyecto.
 * Se mapea a la tabla "mensajes_chat" en la base de datos.
 */
@Entity
@Table(name = "mensajes_chat")
public class MensajeChat {

    // Identificador único autogenerado del mensaje
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMensaje;

    // Relación ManyToOne con Proyecto: cada mensaje pertenece a un proyecto, es obligatorio
    @NotNull(message = "El mensaje debe estar vinculado a un proyecto.")
    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    // Relación ManyToOne con Miembro: cada mensaje es enviado por un miembro, obligatorio
    @NotNull(message = "El mensaje debe estar vinculado a un miembro.")
    @ManyToOne
    @JoinColumn(name = "id_miembro", nullable = false)
    private Miembro miembro;

    // Fecha y hora en que se envió el mensaje, obligatorio
    @NotNull(message = "La fecha y hora del mensaje son obligatorias.")
    @Temporal(TemporalType.TIMESTAMP)  // Indica que se guarda fecha y hora
    @Column(nullable = false)
    private Date fechaHora;

    // Contenido textual del mensaje, obligatorio, máximo 1000 caracteres, guardado en campo tipo TEXT
    @NotBlank(message = "El contenido del mensaje no puede estar vacío.")
    @Size(max = 1000, message = "El contenido del mensaje no puede superar los 1000 caracteres.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;

    // Constructor vacío requerido por JPA
    public MensajeChat() {
    }

    /**
     * Constructor con parámetros para crear un mensaje completo.
     * @param proyecto Proyecto al que pertenece el mensaje.
     * @param miembro Miembro que envió el mensaje.
     * @param fechaHora Fecha y hora del mensaje.
     * @param contenido Contenido del mensaje.
     */
    public MensajeChat(Proyecto proyecto, Miembro miembro, Date fechaHora, String contenido) {
        this.proyecto = proyecto;
        this.miembro = miembro;
        this.fechaHora = fechaHora;
        this.contenido = contenido;
    }

    // Getters y setters para acceder y modificar los atributos

    public Long getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(Long idMensaje) {
        this.idMensaje = idMensaje;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
