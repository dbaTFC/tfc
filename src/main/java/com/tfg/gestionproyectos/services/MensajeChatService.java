package com.tfg.gestionproyectos.services;

import com.tfg.gestionproyectos.dtos.MensajeChatDTO;
import com.tfg.gestionproyectos.exceptions.ResourceNotFoundException;
import com.tfg.gestionproyectos.models.MensajeChat;
import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.models.Proyecto;
import com.tfg.gestionproyectos.repositories.MensajeChatRepository;
import com.tfg.gestionproyectos.repositories.MiembroRepository;
import com.tfg.gestionproyectos.repositories.ProyectoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MensajeChatService {

    @Autowired
    private MensajeChatRepository mensajeChatRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private MiembroRepository miembroRepository;

    // Obtener todos los mensajes de un proyecto en orden cronológico ascendente
    public List<MensajeChat> obtenerMensajesPorProyecto(Long idProyecto) {
        return mensajeChatRepository.findByProyecto_IdProyectoOrderByFechaHoraAsc(idProyecto);
    }

    // Obtener todos los mensajes enviados por un miembro específico en orden cronológico
    public List<MensajeChat> obtenerMensajesPorMiembro(Long idMiembro) {
        return mensajeChatRepository.findByMiembro_IdMiembroOrderByFechaHoraAsc(idMiembro);
    }

    // Crear un nuevo mensaje y guardarlo en la base de datos
    public MensajeChat crearMensaje(MensajeChat mensajeChat) {
        return mensajeChatRepository.save(mensajeChat);
    }

    // Eliminar un mensaje por su ID
    public void eliminarMensaje(Long id) {
        mensajeChatRepository.deleteById(id);
    }

    // Crear un mensaje a partir de un DTO (data transfer object)
    public MensajeChat crearDesdeDTO(MensajeChatDTO chatDTO) {
        // Buscar el proyecto asociado al mensaje, lanzar excepción si no existe
        Proyecto proyecto = proyectoRepository.findById(chatDTO.getIdProyecto()).orElse(null);
        if (proyecto == null) {
            throw new ResourceNotFoundException("Proyecto no encontrado con ID: " + chatDTO.getIdProyecto());
        }

        // Buscar el miembro que envía el mensaje, lanzar excepción si no existe
        Miembro miembro = miembroRepository.findById(chatDTO.getIdMiembro()).orElse(null);
        if (miembro == null) {
            throw new ResourceNotFoundException("Miembro no encontrado con ID: " + chatDTO.getIdMiembro());
        }

        // Crear entidad MensajeChat y asignar sus propiedades
        MensajeChat mensaje = new MensajeChat();
        mensaje.setProyecto(proyecto);
        mensaje.setMiembro(miembro);
        mensaje.setContenido(chatDTO.getContenido());
        mensaje.setFechaHora(new Date()); // Establecer fecha y hora actual

        // Guardar y devolver el mensaje persistido
        return mensajeChatRepository.save(mensaje);
    }
}
