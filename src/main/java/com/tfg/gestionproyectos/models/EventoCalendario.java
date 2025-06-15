package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

/**
 * Entidad EventoCalendario que representa un evento dentro del calendario de un proyecto.
 * Se mapea a la tabla "eventos_calendario" en la base de datos.
 */
@Entity
@Table(name = "eventos_calendario")
public class EventoCalendario {

    // Identificador único autogenerado del evento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;

    // Título del evento, obligatorio y con límite máximo de 100 caracteres
    @NotBlank(message = "El título del evento no puede estar vacío.")
    @Size(max = 100, message = "El título no puede tener más de 100 caracteres.")
    @Column(name = "titulo")
    private String titulo;

    // Fecha y hora del evento, obligatoria y debe ser igual o posterior a la fecha actual
    @NotNull(message = "La fecha del evento es obligatoria.")
    @Temporal(TemporalType.TIMESTAMP) // Para indicar que es fecha y hora
    @Column(nullable = false)
    private Date fecha;

    // Descripción del evento, obligatoria y con límite máximo de 500 caracteres
    @NotBlank(message = "La descripción del evento no puede estar vacía.")
    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres.")
    @Column(nullable = false)
    private String descripcion;

    // Relación ManyToOne con la entidad Proyecto: cada evento está vinculado a un solo proyecto
    @NotNull(message = "El evento debe estar vinculado a un proyecto.")
    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    // Constructor vacío requerido por JPA
    public EventoCalendario() {
    }

    /**
     * Constructor con parámetros para crear un evento completo.
     * @param titulo Título del evento.
     * @param fecha Fecha y hora del evento.
     * @param descripcion Descripción del evento.
     * @param proyecto Proyecto asociado al evento.
     */
    public EventoCalendario(String titulo, Date fecha, String descripcion, Proyecto proyecto) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.proyecto = proyecto;
    }

    // Getters y setters para todos los atributos

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long id) {
        this.idEvento = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
}
