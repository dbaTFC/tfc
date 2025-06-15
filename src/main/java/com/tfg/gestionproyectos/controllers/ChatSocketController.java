package com.tfg.gestionproyectos.controllers;

import com.tfg.gestionproyectos.dtos.MensajeChatDTO;
import com.tfg.gestionproyectos.models.MensajeChat;
import com.tfg.gestionproyectos.services.MensajeChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


// Indica que esta clase es un controlador de Spring, específico para manejar lógica WebSocket
@Controller
public class ChatSocketController {

    // Inyecta el objeto SimpMessagingTemplate, utilizado para enviar mensajes a través de WebSocket
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Inyecta el servicio de MensajeChat para guardar mensajes y gestionar lógica de negocio
    @Autowired
    private MensajeChatService mensajeChatService;

    /**
     * Método que maneja mensajes entrantes enviados a través del WebSocket.
     * Se invoca cuando un mensaje es enviado al endpoint "/app/chat.sendMessage"
     */
    @MessageMapping("/chat.sendMessage") // Ruta del mensaje que se espera desde el frontend
    public void recibirMensaje(@Valid MensajeChatDTO chatDTO) {
        // Muestra por consola el contenido del mensaje recibido (útil para depuración)
        System.out.println("Mensaje recibido via WebSocket: " + chatDTO.getContenido());

        try {
            // Convierte el DTO en una entidad MensajeChat y lo guarda en la base de datos
            MensajeChat mensaje = mensajeChatService.crearDesdeDTO(chatDTO);

            // Envía el mensaje a todos los clientes suscritos al canal correspondiente al proyecto
            messagingTemplate.convertAndSend(
                "/topic/proyecto/" + chatDTO.getIdProyecto(), // Canal específico del proyecto
                new MensajeChatDTO(mensaje) // Se envía una versión DTO del mensaje guardado
            );

        } catch (Exception e) {
            // En caso de error, se muestra el mensaje y traza en consola para diagnóstico
            System.err.println("Error procesando mensaje WebSocket: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
