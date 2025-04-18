package com.tfg.gestionproyectos.dtos;

import com.tfg.gestionproyectos.models.MensajeChat;
import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.models.Miembro.RolMiembro;
import com.tfg.gestionproyectos.models.Tarea;

import java.util.ArrayList;
import java.util.List;

public class MiembroDTO {

    private Long idMiembro;
    private String nombreUsuario;
    private String correo;
    private RolMiembro rol;
    private List<Long> tareasAsignadas;
    private List<Long> mensajesEnviados;

    // Constructor que recibe una entidad Miembro
    public MiembroDTO(Miembro miembro) {
        this.idMiembro = miembro.getIdMiembro();
        this.nombreUsuario = miembro.getNombreUsuario();
        this.correo = miembro.getCorreo();
        this.rol = miembro.getRol();
        // Inicializamos las listas vacías
        this.tareasAsignadas = new ArrayList<>();
        this.mensajesEnviados = new ArrayList<>();

        // Convertimos cada Tarea en un ID de Tarea y lo agregamos a la lista
        for (Tarea tarea : miembro.getTareasAsignadas()) {
            this.tareasAsignadas.add(tarea.getIdTarea());  // Añadimos el ID de la tarea a la lista
        }

        // Convertimos cada MensajeChat en un MensajeChatDTO y lo agregamos a la lista
        for (MensajeChat mensaje : miembro.getMensajesEnviados()) {
            this.mensajesEnviados.add(mensaje.getIdMensaje());  // Añadimos el MensajeChatDTO a la lista
        }

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
