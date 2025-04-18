package com.tfg.gestionproyectos.repositories;

import com.tfg.gestionproyectos.models.MensajeChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeChatRepository extends JpaRepository<MensajeChat, Long> {
    List<MensajeChat> findByProyecto_IdProyectoOrderByFechaHoraAsc(Long idProyecto); // Obtener mensajes de un proyecto en orden cronológico
    List<MensajeChat> findByMiembro_IdMiembroOrderByFechaHoraAsc(Long idMiembro); // Obtener mensajes por miembro en orden cronológico
}
