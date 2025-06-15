package com.tfg.gestionproyectos.repositories;

import com.tfg.gestionproyectos.models.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    /**
     * Método personalizado para obtener todos los documentos que pertenecen
     * a un proyecto específico, identificado por su ID.
     * 
     * @param idProyecto ID del proyecto
     * @return Lista de documentos asociados al proyecto
     */
    List<Documento> findByProyecto_IdProyecto(Long idProyecto); // Obtener documentos por proyecto
}
