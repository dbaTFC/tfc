package com.tfg.gestionproyectos.services;

import com.tfg.gestionproyectos.models.EventoCalendario;
import com.tfg.gestionproyectos.models.Proyecto;
import com.tfg.gestionproyectos.repositories.EventoCalendarioRepository;
import com.tfg.gestionproyectos.repositories.ProyectoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoCalendarioService {

    @Autowired
    private EventoCalendarioRepository eventoCalendarioRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    // Obtener todos los eventos existentes en la base de datos
    public List<EventoCalendario> obtenerTodosLosEventos() {
        return eventoCalendarioRepository.findAll();
    }

    // Obtener los eventos asociados a un proyecto específico por su ID
    public List<EventoCalendario> obtenerEventosPorProyecto(Long idProyecto) {
        // Buscar los eventos asociados al proyecto en el repositorio
        return eventoCalendarioRepository.findByProyecto_IdProyecto(idProyecto);
    }

    // Obtener un evento específico por su ID
    public EventoCalendario obtenerEventoPorId(Long idEvento) {
        // Buscar el evento por su ID en el repositorio
        Optional<EventoCalendario> eventoOptional = eventoCalendarioRepository.findById(idEvento);

        // Verificar si el evento fue encontrado
        if (eventoOptional.isPresent()) {
            return eventoOptional.get(); // Devolver el evento encontrado
        } else {
            // Si no se encuentra el evento, lanzar una excepción
            throw new RuntimeException("Evento no encontrado");
        }
    }

    // Crear o guardar un nuevo evento en la base de datos
    public EventoCalendario guardarEvento(EventoCalendario evento) {
        // Verificamos si el evento tiene proyecto asignado con ID válido
        if (evento.getProyecto() == null || evento.getProyecto().getIdProyecto() == null) {
            throw new RuntimeException("El evento debe tener un proyecto asociado con ID válido");
        }

        // Buscamos el proyecto completo desde la base de datos para asociarlo correctamente
        Long idProyecto = evento.getProyecto().getIdProyecto();
        Optional<Proyecto> proyectoOptional = proyectoRepository.findById(idProyecto);

        if (!proyectoOptional.isPresent()) {
            throw new RuntimeException("Proyecto no encontrado con ID: " + idProyecto);
        }

        // Asociamos el proyecto persistido al evento antes de guardarlo
        evento.setProyecto(proyectoOptional.get());

        // Guardamos el evento en el repositorio y devolvemos la entidad persistida
        return eventoCalendarioRepository.save(evento);
    }

    // Eliminar un evento por su ID
    public void eliminarEvento(Long idEvento) {
        eventoCalendarioRepository.deleteById(idEvento);
    }

    // Actualizar un evento existente identificándolo por su ID
    public EventoCalendario actualizarEvento(Long idEvento, EventoCalendario eventoDetalles) {
        // Buscar el evento por su ID en la base de datos
        Optional<EventoCalendario> eventoOptional = eventoCalendarioRepository.findById(idEvento);

        // Verificar si el evento existe en la base de datos
        if (eventoOptional.isPresent()) {
            // Si el evento existe, obtenemos el objeto evento
            EventoCalendario evento = eventoOptional.get();

            // Actualizamos los detalles del evento con los nuevos datos
            evento.setTitulo(eventoDetalles.getTitulo());
            evento.setDescripcion(eventoDetalles.getDescripcion());
            evento.setFecha(eventoDetalles.getFecha());

            // Guardamos el evento actualizado en la base de datos y lo devolvemos
            return eventoCalendarioRepository.save(evento);
        } else {
            // Si no encontramos el evento con el ID proporcionado, lanzamos una excepción
            throw new RuntimeException("Evento no encontrado");
        }
    }
}
