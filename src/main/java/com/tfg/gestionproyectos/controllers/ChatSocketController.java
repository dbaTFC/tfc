package com.tfg.gestionproyectos.controllers;

import com.tfg.gestionproyectos.dtos.MensajeChatDTO;
import com.tfg.gestionproyectos.models.MensajeChat;
import com.tfg.gestionproyectos.services.MensajeChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class ChatSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MensajeChatService mensajeChatService;

    /**
     * Endpoint STOMP para recibir mensajes del frontend
     */
   
    @MessageMapping("/chat.sendMessage")
    public void recibirMensaje(@Valid MensajeChatDTO chatDTO) {
        System.out.println("Mensaje recibido via WebSocket: " + chatDTO.getContenido());
        try {
            MensajeChat mensaje = mensajeChatService.crearDesdeDTO(chatDTO);

            messagingTemplate.convertAndSend(
                "/topic/proyecto/" + chatDTO.getIdProyecto(),
                new MensajeChatDTO(mensaje)
            );
        } catch (Exception e) {
            System.err.println("Error procesando mensaje WebSocket: " + e.getMessage());
            e.printStackTrace();
        }
}

}
