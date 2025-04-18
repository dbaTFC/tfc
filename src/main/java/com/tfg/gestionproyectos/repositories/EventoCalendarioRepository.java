package com.tfg.gestionproyectos.repositories;

import com.tfg.gestionproyectos.models.EventoCalendario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoCalendarioRepository extends JpaRepository<EventoCalendario, Long> {
    List<EventoCalendario> findByProyecto_IdProyecto(Long idProyecto); // Obtener eventos por proyecto
}
