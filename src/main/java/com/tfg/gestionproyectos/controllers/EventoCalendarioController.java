package com.tfg.gestionproyectos.controllers;

import com.tfg.gestionproyectos.dtos.EventoCalendarioDTO;
import com.tfg.gestionproyectos.models.EventoCalendario;
import com.tfg.gestionproyectos.services.EventoCalendarioService;
import com.tfg.gestionproyectos.services.ProyectoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// Define que esta clase es un controlador REST
@RestController
// Mapea todas las peticiones que comiencen con /eventos
@RequestMapping("/eventos")
public class EventoCalendarioController {

    // Inyecta el servicio para manejar la lógica de eventos de calendario
    @Autowired
    private EventoCalendarioService eventoCalendarioService;

    // Inyecta el servicio para manejar lógica relacionada con proyectos (roles, permisos)
    @Autowired
    private ProyectoService proyectoService;

    /**
     * Método para obtener todos los eventos
     * @return lista de eventos convertidos a DTOs
     */
    @GetMapping
    public ResponseEntity<List<EventoCalendarioDTO>> obtenerTodosLosEventos() {
        // Obtiene la lista completa de eventos desde la base de datos
        List<EventoCalendario> eventos = eventoCalendarioService.obtenerTodosLosEventos();

        // Crea una lista de DTOs para devolver en la respuesta
        List<EventoCalendarioDTO> dtos = new ArrayList<>();
        for (EventoCalendario evento : eventos) {
            dtos.add(new EventoCalendarioDTO(evento)); // Conversión de entidad a DTO
        }

        // Devuelve la lista con código HTTP 200 OK
        return ResponseEntity.ok(dtos);
    }

    /**
     * Método para obtener un evento específico por su ID
     * @param idEvento ID del evento a buscar
     * @return Evento encontrado en formato DTO
     */
    @GetMapping("/{idEvento}")
    public ResponseEntity<EventoCalendarioDTO> obtenerEventoPorId(@PathVariable Long idEvento) {
        // Busca el evento por ID (lanzará excepción si no existe)
        EventoCalendario evento = eventoCalendarioService.obtenerEventoPorId(idEvento);

        // Convierte la entidad a DTO para no exponer datos innecesarios
        EventoCalendarioDTO dto = new EventoCalendarioDTO(evento);

        // Devuelve el evento con HTTP 200 OK
        return ResponseEntity.ok(dto);
    }

    /**
     * Obtener eventos filtrados por ID de proyecto
     * @param idProyecto ID del proyecto para filtrar eventos
     * @return Lista de eventos como DTOs o NO_CONTENT si no hay eventos
     */
    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<EventoCalendarioDTO>> obtenerEventosPorProyecto(@PathVariable Long idProyecto) {
        // Obtiene la lista de eventos asociados a un proyecto específico
        List<EventoCalendario> eventos = eventoCalendarioService.obtenerEventosPorProyecto(idProyecto);

        // Si no hay eventos, devuelve un status 204 No Content
        if (eventos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // Convierte los eventos a DTOs
        List<EventoCalendarioDTO> eventoDTOs = new ArrayList<>();
        for (EventoCalendario evento : eventos) {
            eventoDTOs.add(new EventoCalendarioDTO(evento));
        }

        // Devuelve la lista con código HTTP 200 OK
        return new ResponseEntity<>(eventoDTOs, HttpStatus.OK);
    }

    /**
     * Crear un nuevo evento
     * @param evento objeto EventoCalendario enviado en el body de la petición
     * @return el evento creado en formato DTO con status CREATED
     */
    @PostMapping
    public ResponseEntity<EventoCalendarioDTO> crearEvento(@Valid @RequestBody EventoCalendario evento) {
        // Guarda el evento en la base de datos a través del servicio
        EventoCalendario nuevoEvento = eventoCalendarioService.guardarEvento(evento);

        // Convierte el evento guardado a DTO
        EventoCalendarioDTO eventoDTO = new EventoCalendarioDTO(nuevoEvento);

        // Retorna el evento creado con código 201 CREATED
        return new ResponseEntity<>(eventoDTO, HttpStatus.CREATED);
    }

    /**
     * Actualizar un evento existente
     * @param idEvento ID del evento a actualizar
     * @param eventoDetalles nuevos datos del evento en el body
     * @return evento actualizado o NOT_FOUND si no existe
     */
    @PutMapping("/{idEvento}")
    public ResponseEntity<EventoCalendarioDTO> actualizarEvento(
            @PathVariable Long idEvento,
            @Valid @RequestBody EventoCalendario eventoDetalles) {
        try {
            // Intenta actualizar el evento
            EventoCalendario eventoActualizado = eventoCalendarioService.actualizarEvento(idEvento, eventoDetalles);

            // Convierte el resultado a DTO
            EventoCalendarioDTO eventoDTOa = new EventoCalendarioDTO(eventoActualizado);

            // Devuelve el evento actualizado con status 200 OK
            return new ResponseEntity<>(eventoDTOa, HttpStatus.OK);

        } catch (RuntimeException e) {
            // Si no encuentra el evento, devuelve 404 Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Eliminar un evento por su ID
     * Solo permite eliminar si el usuario es administrador del proyecto
     * @param idEvento ID del evento a eliminar
     * @param userDetails detalles del usuario autenticado
     * @return NO_CONTENT si se elimina, FORBIDDEN si no tiene permiso
     */
    @DeleteMapping("/{idEvento}")
    public ResponseEntity<Void> eliminarEvento(
            @PathVariable Long idEvento,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        // Obtener el ID del miembro autenticado
        Long solicitanteId = proyectoService.getMiembroIdByUsername(userDetails.getUsername());

        // Obtener el evento a eliminar para saber a qué proyecto pertenece
        EventoCalendario eventoElim = eventoCalendarioService.obtenerEventoPorId(idEvento);

        // Validar si el usuario es administrador del proyecto
        if (!proyectoService.esAdministradorDelProyecto(solicitanteId, eventoElim.getProyecto().getIdProyecto())) {
            // Si no tiene permiso, devuelve 403 Forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Elimina el evento
        eventoCalendarioService.eliminarEvento(idEvento);

        // Devuelve 204 No Content para indicar éxito en eliminación
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
