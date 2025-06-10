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

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    // Obtener todos los proyectos
    @GetMapping
    public ResponseEntity<List<ProyectoDTO>> obtenerTodosLosProyectos() {
        List<Proyecto> proyectos = proyectoService.obtenerTodosLosProyectos();
        List<ProyectoDTO> proyectosDTOs = new ArrayList<>();

        for (Proyecto proyecto : proyectos) {
            proyectosDTOs.add(new ProyectoDTO(proyecto));
        }

        return ResponseEntity.ok(proyectosDTOs);
    }

    // Obtener un proyecto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDTO> obtenerProyectoPorId(@PathVariable Long id) {
        Optional<Proyecto> proyectoOptional = proyectoService.obtenerProyectoPorId(id);

        if (proyectoOptional.isPresent()) {
            Proyecto proyecto = proyectoOptional.get();
            return ResponseEntity.ok(new ProyectoDTO(proyecto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo proyecto
    @PostMapping
    public ResponseEntity<ProyectoDTO> crearProyecto(
            @Valid @RequestBody Proyecto proyectoDetalles,
            @AuthenticationPrincipal UserDetails userDetails) {

        Set<Long> miembroIds = new HashSet<>();
        for (MiembroProyecto miembroP : proyectoDetalles.getMiembrosProyecto()) {
            miembroIds.add(miembroP.getMiembro().getIdMiembro());
        }

        Long creadorId = obtenerIdMiembroDesdeUsername(userDetails.getUsername());

        Proyecto proyectoCreado = proyectoService.crearProyectoConMiembros(proyectoDetalles, miembroIds, creadorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProyectoDTO(proyectoCreado));
    }

    // Actualizar un proyecto
    @PutMapping("/{id}")
    public ResponseEntity<ProyectoDTO> actualizarProyecto(
            @PathVariable Long id,
            @Valid @RequestBody Proyecto proyectoDetalles,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long solicitanteId = obtenerIdMiembroDesdeUsername(userDetails.getUsername());
        if (!proyectoService.esAdministradorDelProyecto(solicitanteId, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Proyecto proyecto = proyectoService.actualizarProyecto(id, proyectoDetalles);
        return ResponseEntity.ok(new ProyectoDTO(proyecto));
    }

    // Eliminar un proyecto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long solicitanteId = obtenerIdMiembroDesdeUsername(userDetails.getUsername());
        if (!proyectoService.esAdministradorDelProyecto(solicitanteId, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }

    // Eliminar a un miembro de un proyecto
    @DeleteMapping("/{proyectoId}/miembros/{miembroId}")
    public ResponseEntity<Proyecto> eliminarMiembroDeProyecto(
            @PathVariable Long proyectoId,
            @PathVariable Long miembroId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long solicitanteId = obtenerIdMiembroDesdeUsername(userDetails.getUsername());
        if (!proyectoService.esAdministradorDelProyecto(solicitanteId, proyectoId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Proyecto proyectoActualizado = proyectoService.eliminarMiembroDeProyecto(proyectoId, miembroId);
        return ResponseEntity.ok(proyectoActualizado);
    }

    // Método auxiliar para convertir username en ID de miembro
    private Long obtenerIdMiembroDesdeUsername(String username) {
        return proyectoService.getMiembroIdByUsername(username);
    }
}
