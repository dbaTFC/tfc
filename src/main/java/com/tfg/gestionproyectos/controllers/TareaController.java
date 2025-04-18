package com.tfg.gestionproyectos.controllers;

import com.tfg.gestionproyectos.dtos.TareaDTO;
import com.tfg.gestionproyectos.models.Tarea;
import com.tfg.gestionproyectos.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    // Obtener todas las tareas
    @GetMapping
    public ResponseEntity<List<TareaDTO>> obtenerTodasLasTareas() {
        List<Tarea> tareas = tareaService.obtenerTodasLasTareas();
        List<TareaDTO> tareaDTOs = new ArrayList<>();

        for (Tarea tarea : tareas) {
            tareaDTOs.add(new TareaDTO(tarea));  // Convertimos cada Tarea a TareaDTO
        }

        return ResponseEntity.ok(tareaDTOs);  // Devolvemos la lista de DTOs
    }

    // Obtener tarea por ID
    @GetMapping("/{idTarea}")
    public ResponseEntity<TareaDTO> obtenerTareaPorId(@PathVariable Long idTarea) {
        Optional<Tarea> tareaOptional = Optional.of(tareaService.obtenerTareaPorId(idTarea));

        if (tareaOptional.isPresent()) {
            Tarea tarea = tareaOptional.get();  // Obtener la tarea
            TareaDTO tareaDTO = new TareaDTO(tarea);  // Convertimos la tarea a TareaDTO
            return ResponseEntity.ok(tareaDTO);  // Retornamos el DTO
        } else {
            return ResponseEntity.notFound().build();  // Si no se encuentra, devolver 404
        }
    }

    // Crear nueva tarea
    @PostMapping
    public ResponseEntity<TareaDTO> crearTarea(@RequestBody Tarea tarea) {
        Tarea nuevaTarea = tareaService.crearTarea(tarea);
        TareaDTO tareaDTO = new TareaDTO(nuevaTarea);  // Convertimos la nueva tarea a DTO
        return ResponseEntity.status(201).body(tareaDTO);  // Retornamos el DTO con el código de estado 201
    }

    // Actualizar tarea existente
    @PutMapping("/{idTarea}")
    public ResponseEntity<TareaDTO> actualizarTarea(@PathVariable Long idTarea, @RequestBody Tarea tareaDetalles) {
        Tarea tareaActualizada = tareaService.actualizarTarea(idTarea, tareaDetalles);
        TareaDTO tareaDTO = new TareaDTO(tareaActualizada);  // Convertimos la tarea actualizada a DTO
        return ResponseEntity.ok(tareaDTO);  // Retornamos el DTO actualizado
    }

    // Eliminar tarea por ID
    @DeleteMapping("/{idTarea}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long idTarea) {
        tareaService.eliminarTarea(idTarea);
        return ResponseEntity.noContent().build();  // Retorna un No Content (204)
    }
}
