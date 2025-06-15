package com.tfg.gestionproyectos.repositories;

import com.tfg.gestionproyectos.models.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Long> {

    /**
     * Busca un miembro por su nombre de usuario.
     *
     * @param nombreUsuario el nombre de usuario a buscar
     * @return el miembro con el nombre de usuario especificado, o null si no existe
     */
    Miembro findByNombreUsuario(String nombreUsuario);

    /**
     * Comprueba si existe un miembro con el nombre de usuario dado.
     *
     * @param nombreUsuario el nombre de usuario a verificar
     * @return true si existe un miembro con ese nombre, false en caso contrario
     */
    boolean existsByNombreUsuario(String nombreUsuario);
}
