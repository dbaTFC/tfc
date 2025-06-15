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

// Indica que esta clase es un servicio de Spring
@Service
public class ProyectoService {

    // Inyecta el repositorio de proyectos para operaciones CRUD
    @Autowired
    private ProyectoRepository proyectoRepository;

    // Inyecta el repositorio de miembros
    @Autowired
    private MiembroRepository miembroRepository;

    // Inyecta el repositorio que gestiona la relación entre miembros y proyectos
    @Autowired
    private MiembroProyectoRepository miembroProyectoRepository;

    // Método que retorna todos los proyectos almacenados en la base de datos
    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoRepository.findAll(); // Devuelve todos los proyectos
    }

    // Método que busca un proyecto por su ID
    public Optional<Proyecto> obtenerProyectoPorId(Long id) {
        return proyectoRepository.findById(id); // Devuelve el proyecto si existe
    }

    // Método para actualizar un proyecto existente
    public Proyecto actualizarProyecto(Long id, Proyecto proyectoDetalles) {
        Optional<Proyecto> proyecto = proyectoRepository.findById(id); // Busca el proyecto por ID
        if (proyecto.isPresent()) { // Si el proyecto existe
            Proyecto p = proyecto.get(); // Lo obtenemos
            p.setNombre(proyectoDetalles.getNombre()); // Actualiza el nombre
            p.setDescripcion(proyectoDetalles.getDescripcion()); // Actualiza la descripción
            p.setFechaInicio(proyectoDetalles.getFechaInicio()); // Actualiza la fecha de inicio
            p.setFechaFin(proyectoDetalles.getFechaFin()); // Actualiza la fecha de fin
            return proyectoRepository.save(p); // Guarda los cambios
        } else {
            throw new RuntimeException("Proyecto no encontrado"); // Si no existe, lanza excepción
        }
    }

    // Método para eliminar un proyecto por su ID
    public void eliminarProyecto(Long id) {
        proyectoRepository.deleteById(id); // Elimina el proyecto
    }

    // Método para crear un proyecto y asignar miembros, incluyendo al creador como administrador
    public Proyecto crearProyectoConMiembros(Proyecto proyectoDetalles, Set<Long> miembroIds, Long creadorId) {
        Proyecto proyectoNuevo = new Proyecto(); // Crea un nuevo objeto proyecto
        proyectoNuevo.setNombre(proyectoDetalles.getNombre()); // Asigna nombre
        proyectoNuevo.setDescripcion(proyectoDetalles.getDescripcion()); // Asigna descripción
        proyectoNuevo.setFechaInicio(proyectoDetalles.getFechaInicio()); // Asigna fecha inicio
        proyectoNuevo.setFechaFin(proyectoDetalles.getFechaFin()); // Asigna fecha fin

        Proyecto proyectoGuardado = proyectoRepository.save(proyectoNuevo); // Guarda el nuevo proyecto

        Optional<Miembro> creadorOptional = miembroRepository.findById(creadorId); // Busca al creador por ID
        if (!creadorOptional.isPresent()) {
            throw new RuntimeException("Creador con ID " + creadorId + " no encontrado"); // Si no existe, lanza excepción
        }

        Miembro creador = creadorOptional.get(); // Obtiene el objeto Miembro
        MiembroProyecto miembroProyectoCreador = new MiembroProyecto(creador, proyectoGuardado, RolProyecto.ADMINISTRADOR); // Relación con rol ADMIN
        miembroProyectoRepository.save(miembroProyectoCreador); // Guarda la relación

        // Añade los demás miembros como MIEMBRO
        if (miembroIds != null && !miembroIds.isEmpty()) {
            for (Long miembroId : miembroIds) {
                if (!miembroId.equals(creadorId)) { // Evita duplicar al creador
                    Optional<Miembro> miembroOptional = miembroRepository.findById(miembroId); // Busca miembro
                    if (miembroOptional.isPresent()) {
                        Miembro miembro = miembroOptional.get();
                        MiembroProyecto miembroProyecto = new MiembroProyecto(miembro, proyectoGuardado, RolProyecto.MIEMBRO); // Relación con rol MIEMBRO
                        miembroProyectoRepository.save(miembroProyecto); // Guarda la relación
                    } else {
                        throw new RuntimeException("Miembro con ID " + miembroId + " no encontrado"); // Excepción si no existe
                    }
                }
            }
        }

        return proyectoGuardado; // Devuelve el proyecto guardado
    }

    // Método para asignar nuevos miembros a un proyecto ya existente
    public Proyecto asignarMiembrosAProyecto(Long proyectoId, Set<Long> miembroIds) {
        Optional<Proyecto> proyectoOptional = proyectoRepository.findById(proyectoId); // Busca proyecto
        if (!proyectoOptional.isPresent()) {
            throw new RuntimeException("Proyecto con ID " + proyectoId + " no encontrado");
        }
        Proyecto proyecto = proyectoOptional.get(); // Obtiene proyecto

        for (Long miembroId : miembroIds) {
            Optional<Miembro> miembroOptional = miembroRepository.findById(miembroId); // Busca miembro
            if (miembroOptional.isPresent()) {
                Miembro miembro = miembroOptional.get();
                boolean yaEsMiembro = miembroProyectoRepository.existsByMiembroAndProyecto(miembro, proyecto); // Comprueba si ya está asignado
                if (!yaEsMiembro) {
                    MiembroProyecto miembroProyecto = new MiembroProyecto(miembro, proyecto, RolProyecto.MIEMBRO); // Crea relación
                    miembroProyectoRepository.save(miembroProyecto); // Guarda relación
                }
            } else {
                throw new RuntimeException("Miembro con ID " + miembroId + " no encontrado");
            }
        }

        return proyecto; // Devuelve el proyecto actualizado
    }

    // Método para eliminar un miembro de un proyecto
    public Proyecto eliminarMiembroDeProyecto(Long proyectoId, Long miembroId) {
        Optional<Proyecto> proyectoOptional = proyectoRepository.findById(proyectoId);
        if (!proyectoOptional.isPresent()) {
            throw new RuntimeException("Proyecto no encontrado");
        }
        Proyecto proyecto = proyectoOptional.get();

        Optional<Miembro> miembroOptional = miembroRepository.findById(miembroId);
        if (!miembroOptional.isPresent()) {
            throw new RuntimeException("Miembro no encontrado");
        }
        Miembro miembro = miembroOptional.get();

        Optional<MiembroProyecto> miembroProyectoOptional = miembroProyectoRepository.findByMiembroAndProyecto(miembro, proyecto);
        if (miembroProyectoOptional.isPresent()) {
            miembroProyectoRepository.delete(miembroProyectoOptional.get()); // Elimina la relación
        } else {
            throw new RuntimeException("El miembro no está asignado a este proyecto");
        }

        return proyecto;
    }

    // Cambiar el rol de un miembro dentro de un proyecto
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

        Optional<MiembroProyecto> miembroProyectoOptional = miembroProyectoRepository.findByMiembroAndProyecto(miembro, proyecto);
        if (miembroProyectoOptional.isPresent()) {
            MiembroProyecto miembroProyecto = miembroProyectoOptional.get();
            miembroProyecto.setRol(nuevoRol); // Asigna nuevo rol
            miembroProyectoRepository.save(miembroProyecto); // Guarda cambios
        } else {
            throw new RuntimeException("El miembro no está asignado a este proyecto");
        }
    }

    // Verifica si un miembro es administrador de un proyecto
    public boolean esAdministradorDelProyecto(Long miembroId, Long proyectoId) {
        Optional<Proyecto> proyectoOptional = proyectoRepository.findById(proyectoId);
        Optional<Miembro> miembroOptional = miembroRepository.findById(miembroId);

        if (!proyectoOptional.isPresent() || !miembroOptional.isPresent()) {
            return false;
        }

        Proyecto proyecto = proyectoOptional.get();
        Miembro miembro = miembroOptional.get();

        Optional<MiembroProyecto> miembroProyectoOptional = miembroProyectoRepository.findByMiembroAndProyecto(miembro, proyecto);
        return miembroProyectoOptional.isPresent() &&
               miembroProyectoOptional.get().getRol() == RolProyecto.ADMINISTRADOR;
    }

    // Devuelve todos los proyectos donde un miembro actúa como administrador
    public List<Proyecto> obtenerProyectosAdministradosPor(Long miembroId) {
        Optional<Miembro> miembroOptional = miembroRepository.findById(miembroId);
        if (!miembroOptional.isPresent()) {
            throw new RuntimeException("Miembro no encontrado");
        }

        Miembro miembro = miembroOptional.get();
        return miembroProyectoRepository.findProyectosByMiembroAndRol(miembro, RolProyecto.ADMINISTRADOR);
    }

    // Devuelve el ID del miembro a partir de su nombre de usuario
    public Long getMiembroIdByUsername(String username) {
        Miembro miembro = miembroRepository.findByNombreUsuario(username);
        if (miembro == null) {
            throw new RuntimeException("Miembro no encontrado");
        }
        return miembro.getIdMiembro();
    }

    // Devuelve todos los proyectos a los que pertenece un miembro
    public List<Proyecto> obtenerProyectosPorMiembroId(Long miembroId) {
        Miembro miembro = miembroRepository.findById(miembroId)
            .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        List<MiembroProyecto> relaciones = miembroProyectoRepository.findByMiembro(miembro); // Relaciones del miembro
        List<Proyecto> proyectos = new ArrayList<>(); // Lista de proyectos

        for (MiembroProyecto mp : relaciones) {
            proyectos.add(mp.getProyecto()); // Añade el proyecto relacionado
        }

        return proyectos;
    }

    // Verifica si un miembro es administrador de algún proyecto en común con otro miembro
    public boolean esAdministradorDeAlgunProyectoCompartido(Long solicitanteId, Long objetivoId) {
        List<Proyecto> proyectosDelObjetivo = obtenerProyectosPorMiembroId(objetivoId); // Proyectos del objetivo

        for (Proyecto proyecto : proyectosDelObjetivo) {
            if (esAdministradorDelProyecto(solicitanteId, proyecto.getIdProyecto())) {
                return true; // Si el solicitante es admin de alguno, devuelve true
            }
        }

        return false; // Si no es admin en ningún proyecto compartido
    }
}
