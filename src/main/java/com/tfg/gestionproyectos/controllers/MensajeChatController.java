package com.tfg.gestionproyectos.controllers;

import com.tfg.gestionproyectos.dtos.MensajeChatDTO;
import com.tfg.gestionproyectos.models.MensajeChat;
import com.tfg.gestionproyectos.services.MensajeChatService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Definición del controlador REST para manejar solicitudes HTTP relacionadas con mensajes de chat
@RestController
// Prefijo común para todas las rutas en este controlador
@RequestMapping("/mensajes")
public class MensajeChatController {

    // Inyección automática del servicio que maneja la lógica de mensajes de chat
    @Autowired
    private MensajeChatService mensajeChatService;

    /**
     * Obtener todos los mensajes asociados a un proyecto específico
     * @param idProyecto ID del proyecto
     * @return Lista de mensajes convertidos a DTO para evitar referencias circulares
     */
    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<MensajeChatDTO>> obtenerMensajesPorProyecto(@PathVariable Long idProyecto) {
        // Obtiene lista de mensajes del servicio
        List<MensajeChat> mensajes = mensajeChatService.obtenerMensajesPorProyecto(idProyecto);

        // Convierte cada mensaje a su DTO correspondiente
        List<MensajeChatDTO> dtos = new ArrayList<>();
        for (MensajeChat msj : mensajes) {
            dtos.add(new MensajeChatDTO(msj)); // Conversión de entidad a DTO
        }       

        // Devuelve la lista de DTOs con HTTP 200 OK
        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtener mensajes enviados por un miembro específico
     * @param idMiembro ID del miembro
     * @return Lista de mensajes convertidos a DTO
     */
    @GetMapping("/miembro/{idMiembro}")
    public ResponseEntity<List<MensajeChatDTO>> obtenerMensajesPorMiembro(@PathVariable Long idMiembro) {
        // Obtiene mensajes filtrados por el ID de miembro
        List<MensajeChat> mensajes = mensajeChatService.obtenerMensajesPorMiembro(idMiembro);

        // Usa streams para convertir cada mensaje a DTO de manera más concisa
        List<MensajeChatDTO> dtos = mensajes.stream()
                .map(MensajeChatDTO::new)  // Constructor DTO que recibe la entidad
                .collect(Collectors.toList());

        // Devuelve la lista con HTTP 200 OK
        return ResponseEntity.ok(dtos);
    }

    /**
     * Crear un nuevo mensaje de chat
     * @param mensajeChat objeto completo recibido en el body, con IDs de proyecto y miembro
     * @return mensaje creado en formato DTO con código HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<MensajeChatDTO> crearMensaje(@Valid @RequestBody MensajeChat mensajeChat){
        // Llama al servicio para guardar el mensaje en la base de datos
        MensajeChat nuevoMensaje = mensajeChatService.crearMensaje(mensajeChat);

        // Convierte el mensaje creado a DTO para devolverlo
        MensajeChatDTO mensajeDTO = new MensajeChatDTO(nuevoMensaje);

        // Devuelve el DTO con código HTTP 201 (Created)
        return ResponseEntity.status(201).body(mensajeDTO);
    }

    /**
     * Eliminar un mensaje específico por ID
     * @param id ID del mensaje a eliminar
     * @return respuesta vacía con código 204 No Content si se elimina correctamente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMensaje(@PathVariable Long id) {
        // Llama al servicio para eliminar el mensaje de la base de datos
        mensajeChatService.eliminarMensaje(id);

        // Devuelve respuesta con HTTP 204 No Content (eliminado correctamente)
        return ResponseEntity.noContent().build();
    }
}
