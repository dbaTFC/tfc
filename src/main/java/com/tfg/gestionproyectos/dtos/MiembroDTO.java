package com.tfg.gestionproyectos.dtos;

import com.tfg.gestionproyectos.models.MensajeChat;
import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.models.MiembroProyecto;
import com.tfg.gestionproyectos.models.Tarea;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class MiembroDTO {

    private Long idMiembro;

    // Validación: nombre de usuario obligatorio y tamaño entre 3 y 50 caracteres
    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres.")
    private String nombreUsuario;

    // Validación: correo obligatorio y formato email válido
    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "Debe proporcionar un correo electrónico válido.")
    private String correo;

    // Lista de IDs de tareas asignadas, no puede ser nula
    @NotNull(message = "La lista de tareas asignadas no puede ser nula.")
    private List<Long> tareasAsignadas;

    // Lista de IDs de mensajes enviados, no puede ser nula
    @NotNull(message = "La lista de mensajes enviados no puede ser nula.")
    private List<Long> mensajesEnviados;

    // Lista de proyectos en los que participa el miembro, representados con su DTO
    private List<MiembroProyectoDTO> proyectos = new ArrayList<>();

    /**
     * Constructor que recibe una entidad Miembro
     * Convierte las relaciones a listas de IDs o DTOs para evitar enviar objetos completos
     */
    public MiembroDTO(Miembro miembro) {
        this.idMiembro = miembro.getIdMiembro();
        this.nombreUsuario = miembro.getNombreUsuario();
        this.correo = miembro.getCorreo();

        this.tareasAsignadas = new ArrayList<>();
        if (miembro.getTareasAsignadas() != null) {
            for (Tarea tarea : miembro.getTareasAsignadas()) {
                this.tareasAsignadas.add(tarea.getIdTarea());
            }
        }

        this.mensajesEnviados = new ArrayList<>();
        if (miembro.getMensajesEnviados() != null) {
            for (MensajeChat mensaje : miembro.getMensajesEnviados()) {
                this.mensajesEnviados.add(mensaje.getIdMensaje());
            }
        }

        this.proyectos = new ArrayList<>();
        if (miembro.getProyectosMiembro() != null) {
            for (MiembroProyecto mp : miembro.getProyectosMiembro()) {
                this.proyectos.add(new MiembroProyectoDTO(mp)); // Convertimos cada MiembroProyecto a su DTO
            }
        }
    }

    // Constructor vacío necesario para Swagger y frameworks de deserialización
    public MiembroDTO(){
        
    }

    // Getters y Setters de todos los campos
    public Long getIdMiembro() { return idMiembro; }
    public void setIdMiembro(Long idMiembro) { this.idMiembro = idMiembro; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public List<MiembroProyectoDTO> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<MiembroProyectoDTO> proyectos) {
        this.proyectos = proyectos;
    }

    public List<Long> getTareasAsignadas() { return tareasAsignadas; }
    public void setTareasAsignadas(List<Long> tareasAsignadas) { this.tareasAsignadas = tareasAsignadas; }

    public List<Long> getMensajesEnviados() { return mensajesEnviados; }
    public void setMensajesEnviados(List<Long> mensajesEnviados) { this.mensajesEnviados = mensajesEnviados; }
}
