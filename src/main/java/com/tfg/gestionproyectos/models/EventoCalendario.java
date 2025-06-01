package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "eventos_calendario")
public class EventoCalendario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;

    @NotBlank(message = "El título del evento no puede estar vacío.")
    @Size(max = 100, message = "El título no puede tener más de 100 caracteres.")
    @Column(name = "titulo")
    private String titulo;

    @NotNull(message = "La fecha del evento es obligatoria.")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fecha;

    @NotBlank(message = "La descripción del evento no puede estar vacía.")
    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres.")
    @Column(nullable = false)
    private String descripcion;

    @NotNull(message = "El evento debe estar vinculado a un proyecto.")
    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    // Constructores
    public EventoCalendario() {}

    public EventoCalendario(String titulo, Date fecha, String descripcion, Proyecto proyecto) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.proyecto = proyecto;
    }

    // Getters y Setters
    public Long getIdEvento() { return idEvento; }
    public void setIdEvento(Long id) { this.idEvento = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }
}
