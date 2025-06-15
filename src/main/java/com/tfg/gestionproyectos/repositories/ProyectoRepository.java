package com.tfg.gestionproyectos.repositories;

import com.tfg.gestionproyectos.models.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    // Repositorio para la entidad Proyecto que extiende JpaRepository
    // Proporciona métodos CRUD básicos para manejar proyectos
}
