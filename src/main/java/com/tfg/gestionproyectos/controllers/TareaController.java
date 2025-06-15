package com.tfg.gestionproyectos.controllers;

import com.tfg.gestionproyectos.dtos.TareaDTO;
import com.tfg.gestionproyectos.models.Tarea;
import com.tfg.gestionproyectos.services.ProyectoService;
import com.tfg.gestionproyectos.services.TareaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Definición del controlador REST para manejar solicitudes HTTP relacionadas con tareas
@RestController
// Prefijo común para todas las rutas en este controlador
@RequestMapping("/tareas")
public class TareaController {

    // Inyección automática del servicio que maneja la lógica de tareas
    @Autowired
    private TareaService tareaService;

    // Inyección automática del servicio que maneja la lógica de proyectos
    @Autowired
    private ProyectoService proyectoService;

    /**
     * Obtener todas las tareas existentes
     * @return lista de tareas convertidas a DTO para no exponer información sensible
     */
    @GetMapping
    public ResponseEntity<List<TareaDTO>> obtenerTodasLasTareas() {
        // Obtener lista de tareas desde el servicio
        List<Tarea> tareas = tareaService.obtenerTodasLasTareas();

        // Convertir cada tarea a su DTO correspondiente
        List<TareaDTO> tareaDTOs = new ArrayList<>();
        for (Tarea tarea : tareas) {
            tareaDTOs.add(new TareaDTO(tarea)); // Conversión entidad a DTO
        }

        // Devolver la lista de DTOs con código HTTP 200 OK
        return ResponseEntity.ok(tareaDTOs);
    }

    /**
     * Obtener una tarea específica por su ID
     * @param idTarea ID de la tarea a buscar
     * @return tarea convertida a DTO o 404 si no existe
     */
    @GetMapping("/{idTarea}")
    public ResponseEntity<TareaDTO> obtenerTareaPorId(@PathVariable Long idTarea) {
        // Buscar tarea por ID (puede devolver null)
        Optional<Tarea> tareaOptional = Optional.ofNullable(tareaService.obtenerTareaPorId(idTarea));

        // Si existe, convertir a DTO y devolver con HTTP 200 OK
        if (tareaOptional.isPresent()) {
            TareaDTO tareaDTO = new TareaDTO(tareaOptional.get());
            return ResponseEntity.ok(tareaDTO);
        }
        // Si no existe, devolver HTTP 404 Not Found
        else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crear una nueva tarea
     * @param tarea objeto completo recibido en el body
     * @return tarea creada convertida a DTO con código HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<TareaDTO> crearTarea(@Valid @RequestBody Tarea tarea) {
        // Crear la tarea mediante el servicio
        Tarea nuevaTarea = tareaService.crearTarea(tarea);

        // Convertir la tarea creada a DTO para devolverla
        TareaDTO tareaDTO = new TareaDTO(nuevaTarea);

        // Devolver el DTO con código HTTP 201 Created
        return ResponseEntity.status(201).body(tareaDTO);
    }

    /**
     * Actualizar una tarea existente
     * @param idTarea ID de la tarea a actualizar
     * @param tareaDetalles detalles actualizados recibidos en el body
     * @return tarea actualizada convertida a DTO con HTTP 200 OK
     */
    @PutMapping("/{idTarea}")
    public ResponseEntity<TareaDTO> actualizarTarea(
            @PathVariable Long idTarea,
            @Valid @RequestBody Tarea tareaDetalles) {

        // Actualizar la tarea mediante el servicio
        Tarea tareaActualizada = tareaService.actualizarTarea(idTarea, tareaDetalles);

        // Convertir la tarea actualizada a DTO para devolverla
        TareaDTO tareaDTO = new TareaDTO(tareaActualizada);

        // Devolver el DTO con código HTTP 200 OK
        return ResponseEntity.ok(tareaDTO);
    }

    /**
     * Eliminar una tarea específica por su ID
     * Solo puede eliminarla un administrador del proyecto al que pertenece la tarea
     * @param idTarea ID de la tarea a eliminar
     * @param userDetails datos del usuario autenticado (para comprobar permisos)
     * @return respuesta vacía con código 204 No Content si se elimina correctamente, o 403 Forbidden si no tiene permisos
     */
    @DeleteMapping("/{idTarea}")
    public ResponseEntity<Void> eliminarTarea(
            @PathVariable Long idTarea,
            @AuthenticationPrincipal UserDetails userDetails) {

        // Obtener ID del solicitante a partir del username autenticado
        Long solicitanteId = proyectoService.getMiembroIdByUsername(userDetails.getUsername());

        // Obtener la tarea a eliminar (para obtener su proyecto)
        Tarea tareaElim = tareaService.obtenerTareaPorId(idTarea);

        // Verificar que el solicitante es administrador del proyecto de la tarea
        if (!proyectoService.esAdministradorDelProyecto(solicitanteId, tareaElim.getProyecto().getIdProyecto())) {
            // No autorizado, devolver HTTP 403 Forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Llamar al servicio para eliminar la tarea
        tareaService.eliminarTarea(idTarea);

        // Devolver HTTP 204 No Content para indicar eliminación exitosa
        return ResponseEntity.noContent().build();
    }
}
