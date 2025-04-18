package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mensajes_chat")
public class MensajeChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    private Long idMensaje;

    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false) // Relación con Proyecto
    private Proyecto proyecto;

    @ManyToOne
    @JoinColumn(name = "id_miembro", nullable = false) // Relación con Miembro (autor del mensaje)
    private Miembro miembro;

    @Temporal(TemporalType.TIMESTAMP) // Guarda fecha y hora del mensaje
    @Column(nullable = false)
    private Date fechaHora;

    @Column(nullable = false, columnDefinition = "TEXT") // Permitir mensajes largos
    private String contenido;

    // Constructores
    public MensajeChat() {}

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
