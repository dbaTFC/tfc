package com.tfg.gestionproyectos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private Long idDocumento;

    @NotBlank(message = "El nombre del archivo no puede estar vacío.")
    @Size(max = 255, message = "El nombre del archivo no puede tener más de 255 caracteres.")
    @Column(nullable = false)
    private String nombreArchivo;

    @NotBlank(message = "La ruta del archivo no puede estar vacía.")
    @Size(max = 1024, message = "La ruta del archivo no puede tener más de 1024 caracteres.")
    @Column(nullable = false)
    private String rutaArchivo;

    @NotNull(message = "El documento debe estar asociado a un proyecto.")
    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    @JsonBackReference
    private Proyecto proyecto;

    // Constructores
    public Documento() {

    }

    public Documento(String nombreArchivo, String rutaArchivo, Proyecto proyecto) {
        this.nombreArchivo = nombreArchivo;
        this.rutaArchivo = rutaArchivo;
        this.proyecto = proyecto;
    }

    // Getters y Setters
    public Long getIdDocumento() { return idDocumento; }
    public void setIdDocumento(Long idDocumento) { this.idDocumento = idDocumento; }

    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }

    public String getRutaArchivo() { return rutaArchivo; }
    public void setRutaArchivo(String rutaArchivo) { this.rutaArchivo = rutaArchivo; }

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }
}
