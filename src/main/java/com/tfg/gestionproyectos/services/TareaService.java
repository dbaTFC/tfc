package com.tfg.gestionproyectos.services;

import com.tfg.gestionproyectos.models.Tarea;
import com.tfg.gestionproyectos.models.Proyecto;
import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.repositories.TareaRepository;
import com.tfg.gestionproyectos.repositories.ProyectoRepository;
import com.tfg.gestionproyectos.repositories.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Indica que esta clase es un servicio gestionado por Spring
@Service
public class TareaService {

    // Inyección del repositorio de tareas para realizar operaciones CRUD sobre la entidad Tarea
    @Autowired
    private TareaRepository tareaRepository;

    // Inyección del repositorio de proyectos para validar o recuperar el proyecto asociado a una tarea
    @Autowired
    private ProyectoRepository proyectoRepository;

    // Inyección del repositorio de miembros para validar o recuperar el miembro asignado a una tarea
    @Autowired
    private MiembroRepository miembroRepository;

    // Método para obtener todas las tareas almacenadas en la base de datos
    public List<Tarea> obtenerTodasLasTareas() {
        return tareaRepository.findAll(); // Recupera y devuelve la lista completa de tareas
    }

    // Método para obtener una tarea específica por su ID
    public Tarea obtenerTareaPorId(Long id) {
        // Se busca la tarea con el ID especificado
        Optional<Tarea> tareaOptional = tareaRepository.findById(id);

        // Si se encuentra, se devuelve
        if (tareaOptional.isPresent()) {
            return tareaOptional.get();
        } else {
            // Si no existe, se lanza una excepción
            throw new RuntimeException("Tarea no encontrada");
        }
    }

    // Método para crear una nueva tarea en la base de datos
    public Tarea crearTarea(Tarea tarea) {
        // ---- ASIGNACIÓN DEL PROYECTO ----

        // Se obtiene el ID del proyecto asociado a la tarea
        Long idProyecto = tarea.getProyecto().getIdProyecto();

        // Se busca el proyecto en la base de datos para asegurarse de que existe
        Optional<Proyecto> proyectoOptional = proyectoRepository.findById(idProyecto);

        // Si no se encuentra, se lanza una excepción
        if (!proyectoOptional.isPresent()) {
            throw new RuntimeException("Proyecto no encontrado con ID: " + idProyecto);
        }

        // Se asigna el objeto proyecto completo recuperado desde la base de datos
        tarea.setProyecto(proyectoOptional.get());

        // ---- ASIGNACIÓN DEL MIEMBRO (si aplica) ----

        // Si la tarea viene con un miembro asignado
        if (tarea.getAsignadoA() != null) {
            // Se extrae el ID del miembro
            Long idMiembro = tarea.getAsignadoA().getIdMiembro();

            // Se busca el miembro en la base de datos
            Optional<Miembro> miembroOptional = miembroRepository.findById(idMiembro);

            // Si no existe, se lanza una excepción
            if (!miembroOptional.isPresent()) {
                throw new RuntimeException("Miembro no encontrado con ID: " + idMiembro);
            }

            // Se asigna el miembro real (completo) a la tarea
            tarea.setAsignadoA(miembroOptional.get());
        }

        // Finalmente, se guarda la tarea en la base de datos
        return tareaRepository.save(tarea);
    }

    // Método para actualizar los datos de una tarea existente
    public Tarea actualizarTarea(Long id, Tarea tareaDetalles) {
        // Buscar la tarea actual por su ID
        Optional<Tarea> tareaOptional = tareaRepository.findById(id);

        // Si no existe, lanzar excepción
        if (!tareaOptional.isPresent()) {
            throw new RuntimeException("Tarea no encontrada");
        }

        // Obtener la tarea que se va a actualizar
        Tarea tarea = tareaOptional.get();

        // Actualizar los campos de la tarea con los nuevos valores
        tarea.setTitulo(tareaDetalles.getTitulo());
        tarea.setDescripcion(tareaDetalles.getDescripcion());
        tarea.setFechaInicio(tareaDetalles.getFechaInicio());
        tarea.setFechaFin(tareaDetalles.getFechaFin());
        tarea.setEstado(tareaDetalles.getEstado());

        // Verificar si el miembro asignado existe
        Optional<Miembro> miembroOptional = miembroRepository.findById(
            tareaDetalles.getAsignadoA().getIdMiembro()
        );

        // Si no existe, lanzar excepción
        if (!miembroOptional.isPresent()) {
            throw new RuntimeException("Miembro no encontrado");
        }

        // Asignar el nuevo miembro a la tarea
        Miembro nuevoMiembro = miembroOptional.get();
        tarea.setAsignadoA(nuevoMiembro);

        // Guardar la tarea actualizada
        return tareaRepository.save(tarea);
    }

    // Método para eliminar una tarea por su ID
    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id); // Se elimina directamente del repositorio
    }
}
