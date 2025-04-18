package com.tfg.gestionproyectos.services;

import com.tfg.gestionproyectos.models.MensajeChat;
import com.tfg.gestionproyectos.repositories.MensajeChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensajeChatService {

    @Autowired
    private MensajeChatRepository mensajeChatRepository;

    // Obtener todos los mensajes de un proyecto en orden cronológico
    public List<MensajeChat> obtenerMensajesPorProyecto(Long idProyecto) {
        return mensajeChatRepository.findByProyecto_IdProyectoOrderByFechaHoraAsc(idProyecto);
    }

    // Obtener mensajes por miembro
    public List<MensajeChat> obtenerMensajesPorMiembro(Long idMiembro) {
        return mensajeChatRepository.findByMiembro_IdMiembroOrderByFechaHoraAsc(idMiembro);
    }

    // Crear un nuevo mensaje
    public MensajeChat crearMensaje(MensajeChat mensajeChat) {
        return mensajeChatRepository.save(mensajeChat);
    }

    // Eliminar un mensaje
    public void eliminarMensaje(Long id) {
        mensajeChatRepository.deleteById(id);
    }
}
