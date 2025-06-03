package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "mensajes_chat")
public class MensajeChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMensaje;

    @NotNull(message = "El mensaje debe estar vinculado a un proyecto.")
    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    @JsonBackReference
    private Proyecto proyecto;

    @NotNull(message = "El mensaje debe estar vinculado a un miembro.")
    @ManyToOne
    @JoinColumn(name = "id_miembro", nullable = false)
    @JsonBackReference
    private Miembro miembro;

    @NotNull(message = "La fecha y hora del mensaje son obligatorias.")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaHora;

    @NotBlank(message = "El contenido del mensaje no puede estar vacío.")
    @Size(max = 1000, message = "El contenido del mensaje no puede superar los 1000 caracteres.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;

    // Constructores
    public MensajeChat() {

    }

    public MensajeChat(Proyecto proyecto, Miembro miembro, Date fechaHora, String contenido) {
        this.proyecto = proyecto;
        this.miembro = miembro;
        this.fechaHora = fechaHora;
        this.contenido = contenido;
    }

    // Getters y Setters
    public Long getIdMensaje() { return idMensaje; }
    public void setIdMensaje(Long idMensaje) { this.idMensaje = idMensaje; }

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }

    public Miembro getMiembro() { return miembro; }
    public void setMiembro(Miembro miembro) { this.miembro = miembro; }

    public Date getFechaHora() { return fechaHora; }
    public void setFechaHora(Date fechaHora) { this.fechaHora = fechaHora; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
}
