package com.tfg.gestionproyectos.services;

import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.repositories.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;

    // Obtener todos los miembros de la base de datos
    public List<Miembro> obtenerTodosLosMiembros() {
        return miembroRepository.findAll();
    }

    // Obtener un miembro por su ID
    public Miembro obtenerMiembroPorId(Long id) {
        Optional<Miembro> miembroOptional = miembroRepository.findById(id);
        if (miembroOptional.isPresent()) {
            return miembroOptional.get();
        } else {
            // Lanzar excepción si no se encuentra el miembro
            throw new RuntimeException("Miembro no encontrado");
        }
    }

    // Obtener un miembro por su nombre de usuario
    public Miembro obtenerPorNombreUsuario(String nombreUsuario) {
        Miembro miembro = miembroRepository.findByNombreUsuario(nombreUsuario);
        if (miembro == null) {
            // Lanzar excepción si no existe un usuario con ese nombre
            throw new RuntimeException("Usuario no encontrado: " + nombreUsuario);
        }
        return miembro;
    }

    // Obtener el ID de un miembro a partir de su nombre de usuario
    public Long obtenerIdPorNombreUsuario(String nombreUsuario) {
        Miembro miembro = obtenerPorNombreUsuario(nombreUsuario);
        return miembro.getIdMiembro();
    }

    // Crear un nuevo miembro y guardarlo en la base de datos
    public Miembro crearMiembro(Miembro miembro) {
        return miembroRepository.save(miembro);
    }

    // Actualizar los datos de un miembro existente (sin modificar roles)
    public Miembro actualizarMiembro(Long id, Miembro miembroDetalles) {
        Optional<Miembro> miembroOptional = miembroRepository.findById(id);
        if (miembroOptional.isPresent()) {
            Miembro miembro = miembroOptional.get();
            miembro.setNombreUsuario(miembroDetalles.getNombreUsuario());
            miembro.setCorreo(miembroDetalles.getCorreo());
            miembro.setContraseña(miembroDetalles.getContraseña());

            return miembroRepository.save(miembro);
        } else {
            // Lanzar excepción si no se encuentra el miembro a actualizar
            throw new RuntimeException("Miembro no encontrado");
        }
    }

    // Eliminar un miembro por su ID
    public void eliminarMiembro(Long id) {
        miembroRepository.deleteById(id);
    }
}
