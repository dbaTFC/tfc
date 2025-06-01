package com.tfg.gestionproyectos.controllers;

import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.models.Proyecto;
import com.tfg.gestionproyectos.services.ProyectoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Proyecto>> obtenerTodosLosProyectos() {
        List<Proyecto> proyectos = proyectoService.obtenerTodosLosProyectos();
        return ResponseEntity.ok(proyectos);
    }

    // Obtener un proyecto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable Long id) {
        // Buscar el proyecto por su ID utilizando el servicio
        Optional<Proyecto> proyectoOptional = proyectoService.obtenerProyectoPorId(id);

        // Verificar si el proyecto existe
        if (proyectoOptional.isPresent()) {
            Proyecto proyecto = proyectoOptional.get(); // Obtener el proyecto
            return ResponseEntity.ok(proyecto);         // Devolverlo como respuesta JSON
        } else {
            // Si no se encuentra, devolver 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }


    // Crear un nuevo proyecto
  @PostMapping
    public ResponseEntity<Proyecto> crearProyecto(@Valid @RequestBody Proyecto proyectoDetalles) {
        // Crear un Set para almacenar los IDs de los miembros
        Set<Long> miembroIds = new HashSet<>();
        
        // Recorrer la lista de miembros del proyecto y agregar sus IDs al Set
        for (Miembro miembro : proyectoDetalles.getMiembros()) {
            miembroIds.add(miembro.getIdMiembro());
        }

        // Crear el proyecto con los miembros asignados
        Proyecto proyectoCreado = proyectoService.crearProyectoConMiembros(proyectoDetalles, miembroIds);
        
        // Retornar el proyecto creado con el código HTTP 201 (CREADO)
        return ResponseEntity.status(HttpStatus.CREATED).body(proyectoCreado);
    }


    // Actualizar un proyecto
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyecto(@PathVariable Long id, @Valid @RequestBody Proyecto proyectoDetalles) {
        Proyecto proyecto = proyectoService.actualizarProyecto(id, proyectoDetalles);
        return ResponseEntity.ok(proyecto);
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
