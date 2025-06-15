package com.tfg.gestionproyectos.controllers;

import com.tfg.gestionproyectos.models.Documento;
import com.tfg.gestionproyectos.services.DocumentoService;
import com.tfg.gestionproyectos.services.ProyectoService;
import com.tfg.gestionproyectos.dtos.DocumentoDTO;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// Indica que esta clase es un controlador REST
@RestController
// Ruta base para todas las peticiones relacionadas con documentos
@RequestMapping("/documentos")
public class DocumentoController {

    // Inyección del servicio que maneja la lógica de negocio de los documentos
    @Autowired
    private DocumentoService documentoService;

    // Inyección del servicio de proyectos (para validar roles en eliminación, por ejemplo)
    @Autowired
    private ProyectoService proyectoService;

    // --- ENDPOINT: Obtener todos los documentos ---
    @GetMapping
    public ResponseEntity<List<DocumentoDTO>> obtenerTodosLosDocumentos() {
        // Se obtienen todos los documentos desde la base de datos
        List<Documento> documentos = documentoService.obtenerTodosLosDocumentos();

        // Se convierten los documentos a DTO para enviarlos como respuesta
        List<DocumentoDTO> dtos = new ArrayList<>();
        for (Documento doc : documentos) {
            dtos.add(new DocumentoDTO(doc)); // Conversión manual de entidad a DTO
        }

        // Se retorna la lista de documentos con código 200 OK
        return ResponseEntity.ok(dtos);
    }

    // --- ENDPOINT: Obtener documentos por ID de proyecto ---
    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<DocumentoDTO>> obtenerDocumentosPorProyecto(@PathVariable Long idProyecto) {
        // Se obtienen los documentos filtrados por proyecto
        List<Documento> documentos = documentoService.obtenerDocumentosPorProyecto(idProyecto);

        // Se transforman a DTOs
        List<DocumentoDTO> documentoDTOs = new ArrayList<>();
        for (Documento documento : documentos) {
            documentoDTOs.add(new DocumentoDTO(documento));
        }

        // Se retorna la lista con código 200 OK
        return ResponseEntity.ok(documentoDTOs);
    }

    // --- ENDPOINT: Obtener un documento por su ID ---
    @GetMapping("/{idDocumento}")
    public ResponseEntity<DocumentoDTO> obtenerDocumentoPorId(@PathVariable Long idDocumento) {
        // Se obtiene el documento (si no existe, lanza excepción)
        Documento documento = documentoService.obtenerDocumentoPorId(idDocumento);

        // Se convierte a DTO
        DocumentoDTO dto = new DocumentoDTO(documento);

        // Se retorna el DTO con código 200 OK
        return ResponseEntity.ok(dto);
    }

    // --- ENDPOINT: Crear un nuevo documento ---
    @PostMapping
    public ResponseEntity<DocumentoDTO> crearDocumento(@Valid @RequestBody Documento documento) {
        // Se crea el documento en la base de datos
        Documento nuevoDocumento = documentoService.crearDocumento(documento);

        // Se convierte a DTO
        DocumentoDTO documentoDTO = new DocumentoDTO(nuevoDocumento);

        // Se retorna el nuevo documento con código 200 OK
        return ResponseEntity.ok(documentoDTO);
    }

    // --- ENDPOINT: Actualizar un documento existente ---
    @PutMapping("/{idDocumento}")
    public ResponseEntity<DocumentoDTO> actualizarDocumento(
            @PathVariable Long idDocumento,
            @Valid @RequestBody Documento documentoDetalles) {
        // Se actualiza el documento con los nuevos datos
        Documento documentoActualizado = documentoService.actualizarDocumento(idDocumento, documentoDetalles);

        // Se convierte a DTO
        DocumentoDTO documentoDTOa = new DocumentoDTO(documentoActualizado);

        // Se retorna el DTO actualizado con código 200 OK
        return ResponseEntity.ok(documentoDTOa);
    }

    // --- ENDPOINT: Eliminar un documento por su ID ---
    @DeleteMapping("/{idDocumento}")
    public ResponseEntity<Void> eliminarDocumento(
            @PathVariable Long idDocumento,
            @AuthenticationPrincipal UserDetails userDetails) {

        // Se obtiene el ID del usuario autenticado a partir del nombre de usuario
        Long solicitanteId = proyectoService.getMiembroIdByUsername(userDetails.getUsername());

        // Se obtiene el documento para consultar a qué proyecto pertenece
        Documento docElim = documentoService.obtenerDocumentoPorId(idDocumento);

        // Se comprueba si el usuario autenticado es administrador del proyecto asociado
        if (!proyectoService.esAdministradorDelProyecto(solicitanteId, docElim.getProyecto().getIdProyecto())) {
            // Si no lo es, se devuelve código 403 FORBIDDEN
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Se procede a eliminar el documento
        documentoService.eliminarDocumento(idDocumento);

        // Se devuelve respuesta vacía con código 204 NO CONTENT
        return ResponseEntity.noContent().build();
    }
}
