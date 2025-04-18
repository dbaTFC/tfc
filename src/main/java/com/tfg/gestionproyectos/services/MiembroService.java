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
        // Buscar el miembro por su ID en el repositorio
        Optional<Miembro> miembroOptional = miembroRepository.findById(id);

        // Verificar si el miembro fue encontrado
        if (miembroOptional.isPresent()) {
            return miembroOptional.get(); // Devolver el miembro encontrado
        } else {
            // Si no se encuentra, lanzar una excepción
            throw new RuntimeException("Miembro no encontrado");
        }
    }


    // Crear un nuevo miembro
    public Miembro crearMiembro(Miembro miembro) {
        return miembroRepository.save(miembro);
    }

    // Actualizar un miembro
    public Miembro actualizarMiembro(Long id, Miembro miembroDetalles) {
        Miembro miembro = miembroRepository.findById(id).orElseThrow(() -> new RuntimeException("Miembro no encontrado"));
        miembro.setNombreUsuario(miembroDetalles.getNombreUsuario());
        miembro.setCorreo(miembroDetalles.getCorreo());
        miembro.setContraseña(miembroDetalles.getContraseña());
        miembro.setRol(miembroDetalles.getRol());
        return miembroRepository.save(miembro);
    }

    // Eliminar un miembro
    public void eliminarMiembro(Long id) {
        miembroRepository.deleteById(id);
    }
}
