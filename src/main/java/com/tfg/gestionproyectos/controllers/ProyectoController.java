package com.tfg.gestionproyectos.controllers;

import com.tfg.gestionproyectos.dtos.ProyectoDTO;
import com.tfg.gestionproyectos.models.MiembroProyecto;
import com.tfg.gestionproyectos.models.Proyecto;
import com.tfg.gestionproyectos.services.ProyectoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// Definición del controlador REST para manejar solicitudes HTTP relacionadas con proyectos
@RestController
// Prefijo común para todas las rutas en este controlador
@RequestMapping("/proyectos")
public class ProyectoController {

    // Inyección automática del servicio que maneja la lógica de proyectos
    @Autowired
    private ProyectoService proyectoService;

    /**
     * Obtener todos los proyectos
     * @return lista de proyectos convertidos a DTO para no exponer información sensible
     */
    @GetMapping
    public ResponseEntity<List<ProyectoDTO>> obtenerTodosLosProyectos() {
        // Obtener lista de proyectos desde el servicio
        List<Proyecto> proyectos = proyectoService.obtenerTodosLosProyectos();

        // Convertir cada proyecto a su DTO correspondiente
        List<ProyectoDTO> dtos = new ArrayList<>();
        for (Proyecto proyecto : proyectos) {
            dtos.add(new ProyectoDTO(proyecto));
        }

        // Devolver la lista de DTOs con código HTTP 200 OK
        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtener un proyecto específico por su ID
     * @param id ID del proyecto a buscar
     * @return proyecto convertido a DTO o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDTO> obtenerProyectoPorId(@PathVariable Long id) {
        // Buscar proyecto por ID en el servicio (devuelve Optional)
        Optional<Proyecto> proyectoOpt = proyectoService.obtenerProyectoPorId(id);

        // Si existe, devolver DTO con HTTP 200 OK
        if (proyectoOpt.isPresent()) {
            return ResponseEntity.ok(new ProyectoDTO(proyectoOpt.get()));
        }
        // Si no, devolver HTTP 404 Not Found
        else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crear un nuevo proyecto incluyendo miembros
     * @param proyectoDetalles detalles del proyecto recibido en el body
     * @param userDetails datos del usuario autenticado
     * @return proyecto creado convertido a DTO con código HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<ProyectoDTO> crearProyecto(
            @Valid @RequestBody Proyecto proyectoDetalles,
            @AuthenticationPrincipal UserDetails userDetails) {

        // Extraer IDs de los miembros incluidos en el proyecto recibido
        Set<Long> miembroIds = new HashSet<>();
        for (MiembroProyecto mp : proyectoDetalles.getMiembrosProyecto()) {
            miembroIds.add(mp.getMiembro().getIdMiembro());
        }

        // Obtener ID del creador a partir del username autenticado
        Long creadorId = obtenerIdMiembroDesdeUsername(userDetails.getUsername());

        // Crear proyecto junto con miembros mediante el servicio
        Proyecto proyectoCreado = proyectoService.crearProyectoConMiembros(proyectoDetalles, miembroIds, creadorId);

        // Devolver proyecto creado convertido a DTO con código HTTP 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProyectoDTO(proyectoCreado));
    }

    /**
     * Actualizar un proyecto existente
     * @param id ID del proyecto a actualizar
     * @param proyectoDetalles detalles actualizados recibidos en el body
     * @param userDetails datos del usuario autenticado
     * @return proyecto actualizado convertido a DTO o 403 si no tiene permisos
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProyectoDTO> actualizarProyecto(
            @PathVariable Long id,
            @Valid @RequestBody Proyecto proyectoDetalles,
            @AuthenticationPrincipal UserDetails userDetails) {

        // Obtener ID del solicitante a partir del username autenticado
        Long solicitanteId = obtenerIdMiembroDesdeUsername(userDetails.getUsername());

        // Verificar si el solicitante es administrador del proyecto
        if (!proyectoService.esAdministradorDelProyecto(solicitanteId, id)) {
            // Si no es admin, devolver HTTP 403 Forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Actualizar el proyecto mediante el servicio
        Proyecto proyectoActualizado = proyectoService.actualizarProyecto(id, proyectoDetalles);

        // Devolver proyecto actualizado convertido a DTO con HTTP 200 OK
        return ResponseEntity.ok(new ProyectoDTO(proyectoActualizado));
    }

    /**
     * Eliminar un proyecto específico
     * @param id ID del proyecto a eliminar
     * @param userDetails datos del usuario autenticado
     * @return respuesta vacía con código 204 No Content o 403 Forbidden si no tiene permisos
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        // Obtener ID del solicitante a partir del username autenticado
        Long solicitanteId = obtenerIdMiembroDesdeUsername(userDetails.getUsername());

        // Verificar permisos de administrador
        if (!proyectoService.esAdministradorDelProyecto(solicitanteId, id)) {
            // No autorizado para eliminar, devolver HTTP 403 Forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Llamar al servicio para eliminar el proyecto
        proyectoService.eliminarProyecto(id);

        // Devolver HTTP 204 No Content para indicar eliminación exitosa
        return ResponseEntity.noContent().build();
    }

    /**
     * Eliminar un miembro de un proyecto específico
     * @param proyectoId ID del proyecto
     * @param miembroId ID del miembro a eliminar
     * @param userDetails datos del usuario autenticado
     * @return proyecto actualizado tras eliminar el miembro o 403 si no tiene permisos
     */
    @DeleteMapping("/{proyectoId}/miembros/{miembroId}")
    public ResponseEntity<Proyecto> eliminarMiembroDeProyecto(
            @PathVariable Long proyectoId,
            @PathVariable Long miembroId,
            @AuthenticationPrincipal UserDetails userDetails) {

        // Obtener ID del solicitante a partir del username autenticado
        Long solicitanteId = obtenerIdMiembroDesdeUsername(userDetails.getUsername());

        // Verificar si solicitante es admin del proyecto
        if (!proyectoService.esAdministradorDelProyecto(solicitanteId, proyectoId)) {
            // No autorizado, devolver HTTP 403 Forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Eliminar miembro del proyecto mediante el servicio
        Proyecto proyectoActualizado = proyectoService.eliminarMiembroDeProyecto(proyectoId, miembroId);

        // Devolver proyecto actualizado con HTTP 200 OK
        return ResponseEntity.ok(proyectoActualizado);
    }

    /**
     * Método auxiliar para obtener el ID del miembro a partir del username
     * @param username nombre de usuario
     * @return ID del miembro correspondiente
     */
    private Long obtenerIdMiembroDesdeUsername(String username) {
        return proyectoService.getMiembroIdByUsername(username);
    }
}
