package com.tfg.gestionproyectos.controllers;

import com.tfg.gestionproyectos.models.Documento;
import com.tfg.gestionproyectos.services.DocumentoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.tfg.gestionproyectos.dtos.DocumentoDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    // Obtener todos los documentos
  @GetMapping
    public ResponseEntity<List<DocumentoDTO>> obtenerTodosLosDocumentos() {
        // Obtener la lista de documentos desde el servicio
        List<Documento> documentos = documentoService.obtenerTodosLosDocumentos();

        // Convertir cada documento a su correspondiente DTO
        List<DocumentoDTO> dtos = new ArrayList<>();
        for (Documento doc : documentos) {
            dtos.add(new DocumentoDTO(doc));
        }

        // Retornar la lista de DTOs
        return ResponseEntity.ok(dtos);
    }


    // Obtener documentos por proyecto
    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<DocumentoDTO>> obtenerDocumentosPorProyecto(@PathVariable Long idProyecto) {
        // Obtener los documentos desde el servicio
        List<Documento> documentos = documentoService.obtenerDocumentosPorProyecto(idProyecto);
        
        // Lista donde guardaremos los DTOs
        List<DocumentoDTO> documentoDTOs = new ArrayList<>();
        
        // Iteramos sobre cada documento y lo convertimos a DocumentoDTO
        for (Documento documento : documentos) {
            DocumentoDTO documentoDTO = new DocumentoDTO(documento);  // Convertimos Documento a DocumentoDTO
            documentoDTOs.add(documentoDTO);  // Añadimos el DTO a la lista
        }
        
        // Retornar la lista de DocumentoDTOs
        return ResponseEntity.ok(documentoDTOs);
    }

    // Obtener documento por ID
    @GetMapping("/{idDocumento}")
    public ResponseEntity<DocumentoDTO> obtenerDocumentoPorId(@PathVariable Long idDocumento) {
        // Obtener el documento a través del servicio (esto puede lanzar una excepción si no se encuentra)
        Documento documento = documentoService.obtenerDocumentoPorId(idDocumento);
    
        // Convertir la entidad Documento a un DTO para evitar exponer todo el objeto Proyecto en JSON
        DocumentoDTO dto = new DocumentoDTO(documento);
    
        // Retornar el DTO como respuesta
        return ResponseEntity.ok(dto);
    }
    

    // Crear un nuevo documento
    @PostMapping
    public ResponseEntity<DocumentoDTO> crearDocumento(@Valid @RequestBody Documento documento) {
        Documento nuevoDocumento = documentoService.crearDocumento(documento);
        DocumentoDTO documentoDTO = new DocumentoDTO(nuevoDocumento);
        return ResponseEntity.ok(documentoDTO);
    }

    // Actualizar un documento existente
    @PutMapping("/{idDocumento}")
    public ResponseEntity<DocumentoDTO> actualizarDocumento(@PathVariable Long idDocumento, @Valid @RequestBody Documento documentoDetalles) {
        Documento documentoActualizado = documentoService.actualizarDocumento(idDocumento, documentoDetalles);
        DocumentoDTO documentoDTOa = new DocumentoDTO(documentoActualizado);
        return ResponseEntity.ok(documentoDTOa);
    }

    // Eliminar documento por ID
    @DeleteMapping("/{idDocumento}")
    public ResponseEntity<Void> eliminarDocumento(@PathVariable Long idDocumento) {
        documentoService.eliminarDocumento(idDocumento);
        return ResponseEntity.noContent().build();
    }
}
