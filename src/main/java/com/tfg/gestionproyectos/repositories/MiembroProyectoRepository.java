package com.tfg.gestionproyectos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.models.MiembroProyecto;
import com.tfg.gestionproyectos.models.MiembroProyecto.RolProyecto;
import com.tfg.gestionproyectos.models.Proyecto;

public interface MiembroProyectoRepository extends JpaRepository<MiembroProyecto, Long> {

    /**
     * Busca la relación entre un miembro y un proyecto.
     *
     * @param miembro el miembro
     * @param proyecto el proyecto
     * @return Optional con el objeto MiembroProyecto si existe la relación
     */
    Optional<MiembroProyecto> findByMiembroAndProyecto(Miembro miembro, Proyecto proyecto);

    /**
     * Comprueba si existe una relación entre un miembro y un proyecto.
     *
     * @param miembro el miembro
     * @param proyecto el proyecto
     * @return true si existe la relación, false en caso contrario
     */
    boolean existsByMiembroAndProyecto(Miembro miembro, Proyecto proyecto);

    /**
     * Obtiene una lista de proyectos para un miembro con un rol específico.
     *
     * @param miembro el miembro
     * @param rol el rol del proyecto
     * @return lista de proyectos que cumplen el criterio
     */
    List<Proyecto> findProyectosByMiembroAndRol(Miembro miembro, RolProyecto rol);

    /**
     * Obtiene todas las relaciones de miembro-proyecto de un miembro.
     *
     * @param miembro el miembro
     * @return lista de objetos MiembroProyecto para el miembro
     */
    List<MiembroProyecto> findByMiembro(Miembro miembro);
}
