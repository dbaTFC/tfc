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

@Service
public class TareaService {

    // Inyectamos el repositorio de tareas
    @Autowired
    private TareaRepository tareaRepository;

    // Inyectamos el repositorio de proyectos
    @Autowired
    private ProyectoRepository proyectoRepository;

    // Inyectamos el repositorio de miembros
    @Autowired
    private MiembroRepository miembroRepository;

    // Obtener todas las tareas desde la base de datos
    public List<Tarea> obtenerTodasLasTareas() {
        return tareaRepository.findAll();
    }

    // Obtener una tarea por su ID
    public Tarea obtenerTareaPorId(Long id) {
        // Buscar la tarea por ID
        Optional<Tarea> tareaOptional = tareaRepository.findById(id);

        // Si se encuentra, la devolvemos
        if (tareaOptional.isPresent()) {
            return tareaOptional.get();
        } else {
            // Si no se encuentra, lanzamos una excepción
            throw new RuntimeException("Tarea no encontrada");
        }
    }

    // Crear una nueva tarea
    public Tarea crearTarea(Tarea tarea) {
        // --- ASIGNAR EL PROYECTO CORRECTAMENTE ---

        // Obtener el ID del proyecto que viene en la tarea
        Long idProyecto = tarea.getProyecto().getIdProyecto();

        // Buscar el proyecto en la base de datos
        Optional<Proyecto> proyectoOptional = proyectoRepository.findById(idProyecto);

        // Si no se encuentra el proyecto, lanzamos una excepción
        if (!proyectoOptional.isPresent()) {
            throw new RuntimeException("Proyecto no encontrado con ID: " + idProyecto);
        }

        // Asignamos el proyecto real a la tarea (el que está en la base de datos)
        tarea.setProyecto(proyectoOptional.get());

        // --- ASIGNAR EL MIEMBRO (SI EXISTE) CORRECTAMENTE ---

        // Verificamos si se ha asignado un miembro a la tarea
        if (tarea.getAsignadoA() != null) {
            // Obtenemos el ID del miembro
            Long idMiembro = tarea.getAsignadoA().getIdMiembro();

            // Buscamos el miembro en la base de datos
            Optional<Miembro> miembroOptional = miembroRepository.findById(idMiembro);

            // Si no se encuentra, lanzamos una excepción
            if (!miembroOptional.isPresent()) {
                throw new RuntimeException("Miembro no encontrado con ID: " + idMiembro);
            }

            // Asignamos el miembro real a la tarea
            tarea.setAsignadoA(miembroOptional.get());
        }

        // Guardamos la tarea en la base de datos
        return tareaRepository.save(tarea);
    }

    // Actualizar una tarea existente
    public Tarea actualizarTarea(Long id, Tarea tareaDetalles) {
        // Buscar la tarea original por ID
        Optional<Tarea> tareaOptional = tareaRepository.findById(id);
    
        // Si no existe, lanzamos una excepción
        if (!tareaOptional.isPresent()) {
            throw new RuntimeException("Tarea no encontrada");
        }
    
        // Obtenemos la tarea existente
        Tarea tarea = tareaOptional.get();
    
        // Actualizamos los detalles de la tarea (nombre, descripción, fechas, estado)
        tarea.setTitulo(tareaDetalles.getTitulo());
        tarea.setDescripcion(tareaDetalles.getDescripcion());
        tarea.setFechaInicio(tareaDetalles.getFechaInicio());
        tarea.setFechaFin(tareaDetalles.getFechaFin());
        tarea.setEstado(tareaDetalles.getEstado());
    
        // Buscar el nuevo miembro por su ID (si existe)
        Optional<Miembro> miembroOptional = miembroRepository.findById(tareaDetalles.getAsignadoA().getIdMiembro());
    
        // Si no se encuentra el miembro, lanzamos una excepción
        if (!miembroOptional.isPresent()) {
            throw new RuntimeException("Miembro no encontrado");
        }
    
        // Asignar el nuevo miembro a la tarea
        Miembro nuevoMiembro = miembroOptional.get();
        tarea.setAsignadoA(nuevoMiembro);
    
        // Guardamos los cambios en la tarea
        return tareaRepository.save(tarea);
    }
    
    // Eliminar una tarea por ID
    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }
}
