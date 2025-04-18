package com.tfg.gestionproyectos.controllers;

import com.tfg.gestionproyectos.dtos.MensajeChatDTO;
import com.tfg.gestionproyectos.models.MensajeChat;
import com.tfg.gestionproyectos.services.MensajeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mensajes")
public class MensajeChatController {

    @Autowired
    private MensajeChatService mensajeChatService;

    /**
     * Obtener todos los mensajes de un proyecto, en formato DTO.
     */
    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<MensajeChatDTO>> obtenerMensajesPorProyecto(@PathVariable Long idProyecto) {
        List<MensajeChat> mensajes = mensajeChatService.obtenerMensajesPorProyecto(idProyecto);

        // Convertimos cada mensaje a su DTO para evitar bucles infinitos
        List<MensajeChatDTO> dtos = mensajes.stream()
                .map(MensajeChatDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtener mensajes de un miembro específico, en formato DTO.
     */
    @GetMapping("/miembro/{idMiembro}")
    public ResponseEntity<List<MensajeChatDTO>> obtenerMensajesPorMiembro(@PathVariable Long idMiembro) {
        List<MensajeChat> mensajes = mensajeChatService.obtenerMensajesPorMiembro(idMiembro);

        List<MensajeChatDTO> dtos = mensajes.stream()
                .map(MensajeChatDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    /**
     * Crear un nuevo mensaje.
     * Se espera un JSON con el objeto completo (proyecto y miembro incluidos con sus IDs).
     */
    @PostMapping
    public ResponseEntity<MensajeChat> crearMensaje(@RequestBody MensajeChat mensajeChat) {
        MensajeChat nuevoMensaje = mensajeChatService.crearMensaje(mensajeChat);
        return ResponseEntity.status(201).body(nuevoMensaje);
    }

    /**
     * Eliminar un mensaje por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMensaje(@PathVariable Long id) {
        mensajeChatService.eliminarMensaje(id);
        return ResponseEntity.noContent().build();
    }
}
