package com.tfg.gestionproyectos.dtos;

import com.tfg.gestionproyectos.models.MensajeChat;
import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.models.Miembro.RolMiembro;
import com.tfg.gestionproyectos.models.Tarea;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class MiembroDTO {

    private Long idMiembro;

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres.")
    private String nombreUsuario;

    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "Debe proporcionar un correo electrónico válido.")
    private String correo;

    @NotNull(message = "El rol del miembro es obligatorio.")
    private RolMiembro rol;

    @NotNull(message = "La lista de tareas asignadas no puede ser nula.")
    private List<Long> tareasAsignadas;

    @NotNull(message = "La lista de mensajes enviados no puede ser nula.")
    private List<Long> mensajesEnviados;

    // Constructor que recibe una entidad Miembro
    public MiembroDTO(Miembro miembro) {
        this.idMiembro = miembro.getIdMiembro();
        this.nombreUsuario = miembro.getNombreUsuario();
        this.correo = miembro.getCorreo();
        this.rol = miembro.getRol();

        this.tareasAsignadas = new ArrayList<>();
        this.mensajesEnviados = new ArrayList<>();

        for (Tarea tarea : miembro.getTareasAsignadas()) {
            this.tareasAsignadas.add(tarea.getIdTarea());
        }

        for (MensajeChat mensaje : miembro.getMensajesEnviados()) {
            this.mensajesEnviados.add(mensaje.getIdMensaje());
        }
    }

    //constructor vacío necesario para Swagger
    public MiembroDTO(){
        
    }

    // Getters y Setters
    public Long getIdMiembro() { return idMiembro; }
    public void setIdMiembro(Long idMiembro) { this.idMiembro = idMiembro; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public RolMiembro getRol() { return rol; }
    public void setRol(RolMiembro rol) { this.rol = rol; }

    public List<Long> getTareasAsignadas() { return tareasAsignadas; }
    public void setTareasAsignadas(List<Long> tareasAsignadas) { this.tareasAsignadas = tareasAsignadas; }

    public List<Long> getMensajesEnviados() { return mensajesEnviados; }
    public void setMensajesEnviados(List<Long> mensajesEnviados) { this.mensajesEnviados = mensajesEnviados; }
}
