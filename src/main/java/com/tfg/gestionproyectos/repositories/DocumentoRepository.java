package com.tfg.gestionproyectos.repositories;

import com.tfg.gestionproyectos.models.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByProyecto_IdProyecto(Long idProyecto); // Obtener documentos por proyecto
}
