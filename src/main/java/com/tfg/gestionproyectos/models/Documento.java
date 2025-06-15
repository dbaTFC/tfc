package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidad Documento que representa un archivo asociado a un proyecto.
 * Se mapea a la tabla "documentos" en la base de datos.
 */
@Entity
@Table(name = "documentos")
public class Documento {

    // Identificador único autogenerado del documento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private Long idDocumento;

    // Nombre del archivo, obligatorio y con límite máximo de 255 caracteres
    @NotBlank(message = "El nombre del archivo no puede estar vacío.")
    @Size(max = 255, message = "El nombre del archivo no puede tener más de 255 caracteres.")
    @Column(nullable = false)
    private String nombreArchivo;

    // Ruta donde se almacena el archivo, obligatorio y límite máximo 1024 caracteres
    @NotBlank(message = "La ruta del archivo no puede estar vacía.")
    @Size(max = 1024, message = "La ruta del archivo no puede tener más de 1024 caracteres.")
    @Column(nullable = false)
    private String rutaArchivo;

    // Relación ManyToOne con la entidad Proyecto: 
    // cada documento pertenece a un solo proyecto
    @NotNull(message = "El documento debe estar asociado a un proyecto.")
    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    // Constructor por defecto requerido por JPA
    public Documento() {
    }

    /**
     * Constructor con parámetros para crear un documento con datos completos.
     * @param nombreArchivo Nombre del archivo.
     * @param rutaArchivo Ruta física o URL donde está almacenado el archivo.
     * @param proyecto Proyecto al que pertenece el documento.
     */
    public Documento(String nombreArchivo, String rutaArchivo, Proyecto proyecto) {
        this.nombreArchivo = nombreArchivo;
        this.rutaArchivo = rutaArchivo;
        this.proyecto = proyecto;
    }

    // Getters y setters para todos los campos

    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
}
