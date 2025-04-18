package com.tfg.gestionproyectos.dtos;

import com.tfg.gestionproyectos.models.Documento;

/**
 * DTO (Data Transfer Object) para la entidad Documento.
 * Este objeto simplifica la representación del Documento para evitar problemas
 * de serialización (como bucles infinitos) y exponer solo la información necesaria.
 */
public class DocumentoDTO {

    private Long idDocumento;
    private String nombreArchivo;
    private String rutaArchivo;
    private Long idProyecto; // Solo exponemos el ID del proyecto, no el objeto completo

    // Constructor que convierte una entidad Documento en su versión DTO
    public DocumentoDTO(Documento documento) {
        this.idDocumento = documento.getIdDocumento();
        this.nombreArchivo = documento.getNombreArchivo();
        this.rutaArchivo = documento.getRutaArchivo();
        this.idProyecto = documento.getProyecto().getIdProyecto(); // Obtenemos solo el ID
    }

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

