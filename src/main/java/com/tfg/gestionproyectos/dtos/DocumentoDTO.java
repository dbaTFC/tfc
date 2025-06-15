package com.tfg.gestionproyectos.dtos;

import com.tfg.gestionproyectos.models.Documento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para la entidad Documento.
 * Se usa para enviar o recibir datos en la capa de presentación (API).
 */
public class DocumentoDTO {

    private Long idDocumento;

    @NotBlank(message = "El nombre del archivo no puede estar vacío.")
    @Size(max = 255, message = "El nombre del archivo no puede tener más de 255 caracteres.")
    private String nombreArchivo;

    @NotBlank(message = "La ruta del archivo no puede estar vacía.")
    @Size(max = 1024, message = "La ruta del archivo no puede tener más de 1024 caracteres.")
    private String rutaArchivo;

    @NotNull(message = "El ID del proyecto asociado no puede ser nulo.")
    private Long idProyecto;

    // Constructor que mapea la entidad Documento a este DTO
    public DocumentoDTO(Documento documento) {
        this.idDocumento = documento.getIdDocumento();
        this.nombreArchivo = documento.getNombreArchivo();
        this.rutaArchivo = documento.getRutaArchivo();
        this.idProyecto = documento.getProyecto().getIdProyecto();
    }

    // Constructor vacío necesario para frameworks como Jackson, Swagger, etc.
    public DocumentoDTO() { }

    // Getters y Setters
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

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }
}
