package com.tfg.gestionproyectos.repositories;

import com.tfg.gestionproyectos.models.MensajeChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeChatRepository extends JpaRepository<MensajeChat, Long> {

    /**
     * Obtiene todos los mensajes de chat asociados a un proyecto específico,
     * ordenados cronológicamente de manera ascendente.
     *
     * @param idProyecto ID del proyecto
     * @return Lista de mensajes del proyecto ordenados por fecha y hora ascendente
     */
    List<MensajeChat> findByProyecto_IdProyectoOrderByFechaHoraAsc(Long idProyecto); // Obtener mensajes de un proyecto en orden cronológico

    /**
     * Obtiene todos los mensajes enviados por un miembro específico,
     * ordenados cronológicamente de manera ascendente.
     *
     * @param idMiembro ID del miembro
     * @return Lista de mensajes enviados por el miembro ordenados por fecha y hora ascendente
     */
    List<MensajeChat> findByMiembro_IdMiembroOrderByFechaHoraAsc(Long idMiembro); // Obtener mensajes por miembro en orden cronológico
}
