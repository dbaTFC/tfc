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

    // Obtener todos los miembros
    public List<Miembro> obtenerTodosLosMiembros() {
        return miembroRepository.findAll();
    }

    // Obtener un miembro por ID
    public Miembro obtenerMiembroPorId(Long id) {
        Optional<Miembro> miembroOptional = miembroRepository.findById(id);
        if (miembroOptional.isPresent()) {
            return miembroOptional.get();
        } else {
            throw new RuntimeException("Miembro no encontrado");
        }
    }

    // Obtener miembro por nombre de usuario
    public Miembro obtenerPorNombreUsuario(String nombreUsuario) {
        Miembro miembro = miembroRepository.findByNombreUsuario(nombreUsuario);
        if (miembro == null) {
            throw new RuntimeException("Usuario no encontrado: " + nombreUsuario);
        }
        return miembro;
    }

    // Obtener ID de miembro por nombre de usuario
    public Long obtenerIdPorNombreUsuario(String nombreUsuario) {
        Miembro miembro = obtenerPorNombreUsuario(nombreUsuario);
        return miembro.getIdMiembro();
    }

    // Crear un nuevo miembro
    public Miembro crearMiembro(Miembro miembro) {
        return miembroRepository.save(miembro);
    }

    // Actualizar un miembro (sin tocar el rol, porque ahora va por proyecto)
    public Miembro actualizarMiembro(Long id, Miembro miembroDetalles) {
        Optional<Miembro> miembroOptional = miembroRepository.findById(id);
        if (miembroOptional.isPresent()) {
            Miembro miembro = miembroOptional.get();
            miembro.setNombreUsuario(miembroDetalles.getNombreUsuario());
            miembro.setCorreo(miembroDetalles.getCorreo());
            miembro.setContraseña(miembroDetalles.getContraseña());

            return miembroRepository.save(miembro);
        } else {
            throw new RuntimeException("Miembro no encontrado");
        }
    }

    // Eliminar un miembro
    public void eliminarMiembro(Long id) {
        miembroRepository.deleteById(id);
    }
}
