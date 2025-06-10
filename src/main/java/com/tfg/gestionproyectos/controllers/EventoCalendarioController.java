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

@RestController
@RequestMapping("/eventos")
public class EventoCalendarioController {

    @Autowired
    private EventoCalendarioService eventoCalendarioService;

    @Autowired
    private ProyectoService proyectoService;

    /**
     * Obtener todos los eventos (como DTOs)
     */
    @GetMapping
    public ResponseEntity<List<EventoCalendarioDTO>> obtenerTodosLosEventos() {
        List<EventoCalendario> eventos = eventoCalendarioService.obtenerTodosLosEventos();

        // Convertir a lista de DTOs
        List<EventoCalendarioDTO> dtos = new ArrayList<>();
        for (EventoCalendario evento : eventos) {
            dtos.add(new EventoCalendarioDTO(evento));
        }

        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtener un evento por ID (como DTO)
     */
    @GetMapping("/{idEvento}")
    public ResponseEntity<EventoCalendarioDTO> obtenerEventoPorId(@PathVariable Long idEvento) {
        EventoCalendario evento = eventoCalendarioService.obtenerEventoPorId(idEvento);
        EventoCalendarioDTO dto = new EventoCalendarioDTO(evento);
        return ResponseEntity.ok(dto);
    }
    
    // Obtener eventos por proyecto
    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<EventoCalendarioDTO>> obtenerEventosPorProyecto(@PathVariable Long idProyecto) {
        // Obtener los eventos desde el servicio
        List<EventoCalendario> eventos = eventoCalendarioService.obtenerEventosPorProyecto(idProyecto);

        // Verificar si no hay eventos para el proyecto
        if (eventos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // No hay eventos para ese proyecto
        }

        // Convertir los eventos a EventoCalendarioDTO
        List<EventoCalendarioDTO> eventoDTOs = new ArrayList<>();
        for (EventoCalendario evento : eventos) {
            eventoDTOs.add(new EventoCalendarioDTO(evento));  // Convertimos cada EventoCalendario a su DTO
        }

        // Si hay eventos, devolver la lista de DTOs con status OK
        return new ResponseEntity<>(eventoDTOs, HttpStatus.OK);
    }


    // Crear un nuevo evento
    @PostMapping
    public ResponseEntity<EventoCalendarioDTO> crearEvento(@Valid @RequestBody EventoCalendario evento) {
        EventoCalendario nuevoEvento = eventoCalendarioService.guardarEvento(evento);
        EventoCalendarioDTO eventoDTO = new EventoCalendarioDTO(nuevoEvento);
        return new ResponseEntity<>(eventoDTO, HttpStatus.CREATED); // Evento creado
    }

    // Actualizar un evento existente
    @PutMapping("/{idEvento}")
    public ResponseEntity<EventoCalendarioDTO> actualizarEvento(@PathVariable Long idEvento, @Valid @RequestBody EventoCalendario eventoDetalles) {
        try {
            EventoCalendario eventoActualizado = eventoCalendarioService.actualizarEvento(idEvento, eventoDetalles);
             EventoCalendarioDTO eventoDTOa = new EventoCalendarioDTO(eventoActualizado);
            return new ResponseEntity<>(eventoDTOa, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Evento no encontrado
        }
    }

    // Eliminar un evento
    @DeleteMapping("/{idEvento}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Long idEvento, @AuthenticationPrincipal UserDetails userDetails) {
        Long solicitanteId = proyectoService.getMiembroIdByUsername(userDetails.getUsername());

        EventoCalendario eventoElim = eventoCalendarioService.obtenerEventoPorId(idEvento);

        if (!proyectoService.esAdministradorDelProyecto(solicitanteId, eventoElim.getProyecto().getIdProyecto())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        eventoCalendarioService.eliminarEvento(idEvento);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Evento eliminado
    }
}
