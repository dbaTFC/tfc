package com.tfg.gestionproyectos.controllers;

import com.tfg.gestionproyectos.dtos.ProyectoDTO;
import com.tfg.gestionproyectos.dtos.TareaDTO;
import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.models.Proyecto;
import com.tfg.gestionproyectos.models.Tarea;
import com.tfg.gestionproyectos.services.ProyectoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
            proyectosDTOs.add(new ProyectoDTO(proyecto));  // Convertimos cada Proyecto a ProyectoDTO
        }

        return ResponseEntity.ok(proyectosDTOs);// Devolvemos la lista de DTOs
    }

    // Obtener un proyecto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDTO> obtenerProyectoPorId(@PathVariable Long id) {
        // Buscar el proyecto por su ID utilizando el servicio
        Optional<Proyecto> proyectoOptional = proyectoService.obtenerProyectoPorId(id);

        // Verificar si el proyecto existe
        if (proyectoOptional.isPresent()) {
            Proyecto proyecto = proyectoOptional.get(); // Obtener el proyecto
            ProyectoDTO proyectoDTO = new ProyectoDTO(proyecto); //lo convertimos a DTO
            return ResponseEntity.ok(proyectoDTO);         // Devolverlo como respuesta JSON
        } else {
            // Si no se encuentra, devolver 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }


    // Crear un nuevo proyecto
  @PostMapping
    public ResponseEntity<ProyectoDTO> crearProyecto(@Valid @RequestBody Proyecto proyectoDetalles) {
        // Crear un Set para almacenar los IDs de los miembros
        Set<Long> miembroIds = new HashSet<>();
        
        // Recorrer la lista de miembros del proyecto y agregar sus IDs al Set
        for (Miembro miembro : proyectoDetalles.getMiembros()) {
            miembroIds.add(miembro.getIdMiembro());
        }

        // Crear el proyecto con los miembros asignados
        Proyecto proyectoCreado = proyectoService.crearProyectoConMiembros(proyectoDetalles, miembroIds);
        //lo transformamos a DTO para mostrarlo
        ProyectoDTO proyectoDTO = new ProyectoDTO(proyectoCreado);
        // Retornar el proyecto creado con el código HTTP 201 (CREADO)
        return ResponseEntity.status(HttpStatus.CREATED).body(proyectoDTO);
    }


    // Actualizar un proyecto
    @PutMapping("/{id}")
    public ResponseEntity<ProyectoDTO> actualizarProyecto(@PathVariable Long id, @Valid @RequestBody Proyecto proyectoDetalles) {
        Proyecto proyecto = proyectoService.actualizarProyecto(id, proyectoDetalles);
        //lo transformamos a DTO para mostrarlo
        ProyectoDTO proyectoDTOa = new ProyectoDTO(proyecto);
        return ResponseEntity.ok(proyectoDTOa);
    }

    // Eliminar un proyecto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }

    // Eliminar a un miembro de un proyecto
    @DeleteMapping("/{proyectoId}/miembros/{miembroId}")
    public ResponseEntity<Proyecto> eliminarMiembroDeProyecto(
        @PathVariable Long proyectoId, @PathVariable Long miembroId) {
        
        Proyecto proyectoActualizado = proyectoService.eliminarMiembroDeProyecto(proyectoId, miembroId);
        return ResponseEntity.ok(proyectoActualizado);  // Devuelve el proyecto actualizado
    }

}
