package com.tfg.gestionproyectos.services;

import com.tfg.gestionproyectos.models.Documento;
import com.tfg.gestionproyectos.repositories.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    // Obtener todos los documentos
    public List<Documento> obtenerTodosLosDocumentos() {
        return documentoRepository.findAll();
    }

    // Obtener un documento por su ID
    public Documento obtenerDocumentoPorId(Long id) {
        // Buscar el documento por su ID en el repositorio
        Optional<Documento> documentoOptional = documentoRepository.findById(id);

        // Verificar si el documento existe
        if (documentoOptional.isPresent()) {
            return documentoOptional.get(); // Devolver el documento encontrado
        } else {
            // Si no se encuentra, lanzar una excepción
            throw new RuntimeException("Documento no encontrado");
        }
    }


    // Crear un nuevo documento
    public Documento crearDocumento(Documento documento) {
        return documentoRepository.save(documento);
    }

    // Eliminar un documento por su ID
    public void eliminarDocumento(Long id) {
        documentoRepository.deleteById(id);
    }

    // Obtener todos los documentos asociados a un proyecto
    public List<Documento> obtenerDocumentosPorProyecto(Long idProyecto) {
        return documentoRepository.findByProyecto_IdProyecto(idProyecto);
    }

    // Actualizar un documento existente
    public Documento actualizarDocumento(Long idDocumento, Documento documentoDetalles) {
        Optional<Documento> documentoOptional = documentoRepository.findById(idDocumento);

        if (documentoOptional.isPresent()) {
            Documento documento = documentoOptional.get();

            // Actualizamos los campos editables
            documento.setNombreArchivo(documentoDetalles.getNombreArchivo());
            documento.setRutaArchivo(documentoDetalles.getRutaArchivo());
            documento.setProyecto(documentoDetalles.getProyecto());

            return documentoRepository.save(documento);
        } else {
            throw new RuntimeException("Documento no encontrado");
        }
    }
}
