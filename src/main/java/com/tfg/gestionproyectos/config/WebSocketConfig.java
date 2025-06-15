package com.tfg.gestionproyectos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * Configuración de WebSocket usando STOMP para manejar comunicación en tiempo real.
 */
@Configuration
// Habilita el soporte de WebSocket con STOMP (Protocolo de texto simple para mensajería)
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Registro del endpoint STOMP para que los clientes puedan conectarse.
     * Se configura un endpoint con soporte SockJS para fallback si WebSocket no está disponible.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")              // Endpoint para conexión WebSocket
                .setAllowedOriginPatterns("*")        // Permite CORS desde cualquier origen (ajustar en producción)
                .withSockJS();                        // Habilita soporte SockJS como fallback
    }

    /**
     * Configuración del broker de mensajes que se encarga de enrutar los mensajes
     * a los destinos apropiados.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");          // Broker simple para enviar mensajes a suscriptores con prefijo /topic
        config.setApplicationDestinationPrefixes("/app"); // Prefijo para mensajes que serán manejados por métodos @MessageMapping
    }
}
