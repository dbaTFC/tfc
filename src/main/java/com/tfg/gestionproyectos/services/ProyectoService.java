package com.tfg.gestionproyectos.services;

import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.models.Proyecto;
import com.tfg.gestionproyectos.repositories.MiembroRepository;
import com.tfg.gestionproyectos.repositories.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private MiembroRepository miembroRepository;

    // Obtener todos los proyectos
    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoRepository.findAll();
    }

    // Obtener un proyecto por ID
    public Optional<Proyecto> obtenerProyectoPorId(Long id) {
        return proyectoRepository.findById(id);
    }

    // Crear un nuevo proyecto
    // public Proyecto guardarProyecto(Proyecto proyecto) {
    //     return proyectoRepository.save(proyecto);
    // }

    // Actualizar un proyecto existente
    public Proyecto actualizarProyecto(Long id, Proyecto proyectoDetalles) {
        Optional<Proyecto> proyecto = proyectoRepository.findById(id);
        if (proyecto.isPresent()) {
            Proyecto p = proyecto.get();
            p.setNombre(proyectoDetalles.getNombre());
            p.setDescripcion(proyectoDetalles.getDescripcion());
            p.setFechaInicio(proyectoDetalles.getFechaInicio());
            p.setFechaFin(proyectoDetalles.getFechaFin());
            return proyectoRepository.save(p);
        } else {
            throw new RuntimeException("Proyecto no encontrado");
        }
    }

    // Eliminar un proyecto
    public void eliminarProyecto(Long id) {
        proyectoRepository.deleteById(id);
    }

    //crear Proyecto con miembros
    public Proyecto crearProyectoConMiembros(Proyecto proyectoDetalles, Set<Long> miembroIds) {
    //Crear el proyecto con los detalles proporcionados
    Proyecto proyectoNuevo = new Proyecto();
    proyectoNuevo.setNombre(proyectoDetalles.getNombre());
    proyectoNuevo.setDescripcion(proyectoDetalles.getDescripcion());
    proyectoNuevo.setFechaInicio(proyectoDetalles.getFechaInicio());
    proyectoNuevo.setFechaFin(proyectoDetalles.getFechaFin());

    //Obtener los miembros desde la base de datos
    Set<Miembro> miembrosAsignados = new HashSet<>();
    for (Long miembroId : miembroIds) {
        Optional<Miembro> miembroOptional = miembroRepository.findById(miembroId);
        if (miembroOptional.isPresent()) {
            miembrosAsignados.add(miembroOptional.get());
        } else {
            throw new RuntimeException("Miembro con ID " + miembroId + " no encontrado");
        }
    }

    //Asociar los miembros al proyecto
    proyectoNuevo.setMiembros(miembrosAsignados);

    //Guardar el proyecto (y por cascada, los miembros serán automáticamente guardados en la tabla intermedia)
    return proyectoRepository.save(proyectoNuevo);
    }

    //metodo para agregar un miembro a un proyecto ya existente
    public Proyecto asignarMiembrosAProyecto(Long proyectoId, Set<Long> miembroIds) {
        // Obtener el proyecto existente
        Optional<Proyecto> proyectoOptional = proyectoRepository.findById(proyectoId);
        if (!proyectoOptional.isPresent()) {
            throw new RuntimeException("Proyecto con ID " + proyectoId + " no encontrado");
        }
        Proyecto proyecto = proyectoOptional.get();
    
        // Obtener los miembros desde la base de datos
        Set<Miembro> miembrosAsignados = proyecto.getMiembros();  // Obtener los miembros ya asignados
        for (Long miembroId : miembroIds) {
            Optional<Miembro> miembroOptional = miembroRepository.findById(miembroId);
            if (miembroOptional.isPresent()) {
                miembrosAsignados.add(miembroOptional.get());
            } else {
                throw new RuntimeException("Miembro con ID " + miembroId + " no encontrado");
            }
        }
    
        //Actualizar la relación de miembros en el proyecto
        proyecto.setMiembros(miembrosAsignados);
    
        //Guardar los cambios en el proyecto (los miembros serán actualizados automáticamente)
        return proyectoRepository.save(proyecto);
    }
    

    // metodo para eliminar a un miembro de un proyecto
    public Proyecto eliminarMiembroDeProyecto(Long proyectoId, Long miembroId) {
        // Obtener el proyecto por su ID
        Optional<Proyecto> proyectoOptional = proyectoRepository.findById(proyectoId);
        if (!proyectoOptional.isPresent()) {
            throw new RuntimeException("Proyecto no encontrado");
        }

        Proyecto proyecto = proyectoOptional.get();

        // Obtener el miembro por su ID
        Optional<Miembro> miembroOptional = miembroRepository.findById(miembroId);
        if (!miembroOptional.isPresent()) {
            throw new RuntimeException("Miembro no encontrado");
        }

        Miembro miembro = miembroOptional.get();

        // Eliminar al miembro de la lista de miembros del proyecto
        proyecto.getMiembros().remove(miembro);

        // Guardar el proyecto actualizado
        return proyectoRepository.save(proyecto);
    }

}
