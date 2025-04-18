package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "eventos_calendario")
public class EventoCalendario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    @Column(name = "id_evento")
    private Long idEvento;

    @Temporal(TemporalType.TIMESTAMP) // Almacena fecha y hora
    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private String descripcion;

    // Relación con Proyecto (Un Proyecto puede tener muchos Eventos)
    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    // Constructores
    public EventoCalendario() {}

    public EventoCalendario(Date fecha, String descripcion, Proyecto proyecto) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.proyecto = proyecto;
    }

    // Getters y Setters
    public Long getIdEvento() { return idEvento; }
    public void setIdEvento(Long id) { this.idEvento = id; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }
}
