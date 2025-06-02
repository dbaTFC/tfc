package com.tfg.gestionproyectos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Endpoint donde los clientes se conectarán vía WebSocket
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("https://tfc-t00f.onrender.com/ws-chat") // URL a la que se conectará el frontend
                .setAllowedOriginPatterns("*") // Permitir todos los orígenes (ajustable para producción)
                .withSockJS(); // Compatibilidad con navegadores antiguos
    }

    // Configurar el broker de mensajes interno (in-memory)
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // prefijo de salida (mensajes del servidor al cliente)
        config.setApplicationDestinationPrefixes("/app"); // prefijo para los mensajes que vienen del cliente
    }
}
