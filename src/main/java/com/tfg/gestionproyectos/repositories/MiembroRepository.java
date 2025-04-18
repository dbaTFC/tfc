package com.tfg.gestionproyectos.repositories;

import com.tfg.gestionproyectos.models.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Long> {
    Miembro findByNombreUsuario(String nombreUsuario); // Método para buscar un usuario por su nombre
}
