package com.tfg.gestionproyectos.services;

import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.models.MiembroProyecto;
import com.tfg.gestionproyectos.models.MiembroProyecto.RolProyecto;
import com.tfg.gestionproyectos.models.Proyecto;
import com.tfg.gestionproyectos.repositories.MiembroRepository;
import com.tfg.gestionproyectos.repositories.MiembroProyectoRepository;
import com.tfg.gestionproyectos.repositories.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProyectoService {
    
    @Autowired
    private ProyectoRepository proyectoRepository;
    
    @Autowired
    private MiembroRepository miembroRepository;
    
    @Autowired
    private MiembroProyectoRepository miembroProyectoRepository;

    // Obtener todos los proyectos
    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoRepository.findAll();
    }

    // Obtener un proyecto por ID
    public Optional<Proyecto> obtenerProyectoPorId(Long id) {
        return proyectoRepository.findById(id);
    }

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

    // Crear Proyecto con miembros
    public Proyecto crearProyectoConMiembros(Proyecto proyectoDetalles, Set<Long> miembroIds, Long creadorId) {
        // Crear el proyecto con los detalles proporcionados
        Proyecto proyectoNuevo = new Proyecto();
        proyectoNuevo.setNombre(proyectoDetalles.getNombre());
        proyectoNuevo.setDescripcion(proyectoDetalles.getDescripcion());
        proyectoNuevo.setFechaInicio(proyectoDetalles.getFechaInicio());
        proyectoNuevo.setFechaFin(proyectoDetalles.getFechaFin());
        
        // Guardar el proyecto primero
        Proyecto proyectoGuardado = proyectoRepository.save(proyectoNuevo);
        
        // Crear la relación MiembroProyecto para el creador como ADMINISTRADOR
        Optional<Miembro> creadorOptional = miembroRepository.findById(creadorId);
        if (!creadorOptional.isPresent()) {
            throw new RuntimeException("Creador con ID " + creadorId + " no encontrado");
        }
        Miembro creador = creadorOptional.get();
        
        MiembroProyecto miembroProyectoCreador = new MiembroProyecto(creador, proyectoGuardado, RolProyecto.ADMINISTRADOR);
        miembroProyectoRepository.save(miembroProyectoCreador);
        
        // Agregar los demás miembros como MIEMBRO
        if (miembroIds != null && !miembroIds.isEmpty()) {
            for (Long miembroId : miembroIds) {
                // Evitar duplicar al creador
                if (!miembroId.equals(creadorId)) {
                    Optional<Miembro> miembroOptional = miembroRepository.findById(miembroId);
                    if (miembroOptional.isPresent()) {
                        Miembro miembro = miembroOptional.get();
                        MiembroProyecto miembroProyecto = new MiembroProyecto(miembro, proyectoGuardado, RolProyecto.MIEMBRO);
                        miembroProyectoRepository.save(miembroProyecto);
                    } else {
                        throw new RuntimeException("Miembro con ID " + miembroId + " no encontrado");
                    }
                }
            }
        }
        
        return proyectoGuardado;
    }

    // Método para agregar miembros a un proyecto ya existente
    public Proyecto asignarMiembrosAProyecto(Long proyectoId, Set<Long> miembroIds) {
        // Obtener el proyecto existente
        Optional<Proyecto> proyectoOptional = proyectoRepository.findById(proyectoId);
        if (!proyectoOptional.isPresent()) {
            throw new RuntimeException("Proyecto con ID " + proyectoId + " no encontrado");
        }
        Proyecto proyecto = proyectoOptional.get();
        
        // Agregar los nuevos miembros
        for (Long miembroId : miembroIds) {
            Optional<Miembro> miembroOptional = miembroRepository.findById(miembroId);
            if (miembroOptional.isPresent()) {
                Miembro miembro = miembroOptional.get();
                
                // Verificar si el miembro ya está en el proyecto
                boolean yaEsMiembro = miembroProyectoRepository.existsByMiembroAndProyecto(miembro, proyecto);
                
                if (!yaEsMiembro) {
                    MiembroProyecto miembroProyecto = new MiembroProyecto(miembro, proyecto, RolProyecto.MIEMBRO);
                    miembroProyectoRepository.save(miembroProyecto);
                }
            } else {
                throw new RuntimeException("Miembro con ID " + miembroId + " no encontrado");
            }
        }
        
        return proyecto;
    }

    // Método para eliminar a un miembro de un proyecto
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

        // Buscar y eliminar la relación MiembroProyecto
        Optional<MiembroProyecto> miembroProyectoOptional = 
            miembroProyectoRepository.findByMiembroAndProyecto(miembro, proyecto);
        
        if (miembroProyectoOptional.isPresent()) {
            miembroProyectoRepository.delete(miembroProyectoOptional.get());
        } else {
            throw new RuntimeException("El miembro no está asignado a este proyecto");
        }

        return proyecto;
    }
    
    // Método para cambiar el rol de un miembro en un proyecto
    public void cambiarRolMiembroEnProyecto(Long proyectoId, Long miembroId, RolProyecto nuevoRol) {
        Optional<Proyecto> proyectoOptional = proyectoRepository.findById(proyectoId);
        Optional<Miembro> miembroOptional = miembroRepository.findById(miembroId);
        
        if (!proyectoOptional.isPresent()) {
            throw new RuntimeException("Proyecto no encontrado");
        }
        if (!miembroOptional.isPresent()) {
            throw new RuntimeException("Miembro no encontrado");
        }
        
        Proyecto proyecto = proyectoOptional.get();
        Miembro miembro = miembroOptional.get();
        
        Optional<MiembroProyecto> miembroProyectoOptional = 
            miembroProyectoRepository.findByMiembroAndProyecto(miembro, proyecto);
        
        if (miembroProyectoOptional.isPresent()) {
            MiembroProyecto miembroProyecto = miembroProyectoOptional.get();
            miembroProyecto.setRol(nuevoRol);
            miembroProyectoRepository.save(miembroProyecto);
        } else {
            throw new RuntimeException("El miembro no está asignado a este proyecto");
        }
    }
    
    // Método para verificar si un miembro es administrador de un proyecto 
    public boolean esAdministradorDelProyecto(Long miembroId, Long proyectoId) {
        Optional<Proyecto> proyectoOptional = proyectoRepository.findById(proyectoId);
        Optional<Miembro> miembroOptional = miembroRepository.findById(miembroId);
        
        if (!proyectoOptional.isPresent() || !miembroOptional.isPresent()) {
            return false;
        }
        
        Proyecto proyecto = proyectoOptional.get();
        Miembro miembro = miembroOptional.get();
        
        Optional<MiembroProyecto> miembroProyectoOptional = 
            miembroProyectoRepository.findByMiembroAndProyecto(miembro, proyecto);
        
        return miembroProyectoOptional.isPresent() && 
               miembroProyectoOptional.get().getRol() == RolProyecto.ADMINISTRADOR;
    }
    
    // Método para obtener todos los proyectos donde un miembro es administrador
    public List<Proyecto> obtenerProyectosAdministradosPor(Long miembroId) {
        Optional<Miembro> miembroOptional = miembroRepository.findById(miembroId);
        if (!miembroOptional.isPresent()) {
            throw new RuntimeException("Miembro no encontrado");
        }
        
        Miembro miembro = miembroOptional.get();
        return miembroProyectoRepository.findProyectosByMiembroAndRol(miembro, RolProyecto.ADMINISTRADOR);
    }

    public Long getMiembroIdByUsername(String username) {
    Miembro miembro = miembroRepository.findByNombreUsuario(username);
    if (miembro == null) {
        throw new RuntimeException("Miembro no encontrado");
    }
    return miembro.getIdMiembro();
    }

    public List<Proyecto> obtenerProyectosPorMiembroId(Long miembroId) {
        Miembro miembro = miembroRepository.findById(miembroId)
            .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        List<MiembroProyecto> relaciones = miembroProyectoRepository.findByMiembro(miembro);
        List<Proyecto> proyectos = new ArrayList<>();

        for (MiembroProyecto mp : relaciones) {
            proyectos.add(mp.getProyecto());
        }

        return proyectos;
    }


    public boolean esAdministradorDeAlgunProyectoCompartido(Long solicitanteId, Long objetivoId) {
        List<Proyecto> proyectosDelObjetivo = obtenerProyectosPorMiembroId(objetivoId);

        for (Proyecto proyecto : proyectosDelObjetivo) {
            if (esAdministradorDelProyecto(solicitanteId, proyecto.getIdProyecto())) {
                return true;
            }
        }

        return false;
    }


}