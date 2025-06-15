package com.tfg.gestionproyectos.repositories;

import com.tfg.gestionproyectos.models.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    // Repositorio para la entidad Tarea que extiende JpaRepository
    // Proporciona operaciones CRUD básicas para las tareas
}
