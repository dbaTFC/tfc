package com.tfg.gestionproyectos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.models.MiembroProyecto;
import com.tfg.gestionproyectos.models.MiembroProyecto.RolProyecto;
import com.tfg.gestionproyectos.models.Proyecto;

public interface MiembroProyectoRepository extends JpaRepository<MiembroProyecto, Long> {
    Optional<MiembroProyecto> findByMiembroAndProyecto(Miembro miembro, Proyecto proyecto);
    boolean existsByMiembroAndProyecto(Miembro miembro, Proyecto proyecto);
    List<Proyecto> findProyectosByMiembroAndRol(Miembro miembro, RolProyecto rol);
    List<MiembroProyecto> findByMiembro(Miembro miembro);
}
