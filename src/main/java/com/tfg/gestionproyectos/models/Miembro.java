package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

/**
 * Entidad Miembro que representa un usuario del sistema (administrador o miembro).
 * Se mapea a la tabla "miembros" en la base de datos.
 */
@Entity
@Table(name = "miembros")
public class Miembro {

    // Identificador único autogenerado para cada miembro
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_miembro")
    private Long idMiembro;

    // Nombre de usuario único, obligatorio, con validación de longitud
    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres.")
    @Column(unique = true, nullable = false)
    private String nombreUsuario;

    // Correo electrónico único y obligatorio con validación de formato
    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "Debe ser un correo electrónico válido.")
    @Column(nullable = false, unique = true)
    private String correo;

    // Contraseña obligatoria con validación de longitud mínima
    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    @Column(nullable = false)
    private String contraseña;

    // Relación OneToMany con MiembroProyecto (proyectos en los que participa)
    // Un miembro puede pertenecer a varios proyectos
    @OneToMany(mappedBy = "miembro", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MiembroProyecto> proyectosMiembro;

    // Relación OneToMany con Tarea (tareas asignadas al miembro)
    @OneToMany(mappedBy = "asignadoA", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareasAsignadas;

    // Relación OneToMany con MensajeChat (mensajes enviados por el miembro)
    @OneToMany(mappedBy = "miembro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MensajeChat> mensajesEnviados;

    // Constructor vacío necesario para JPA
    public Miembro() {
    }

    /**
     * Constructor con parámetros para crear un miembro con datos básicos.
     * @param nombreUsuario Nombre de usuario.
     * @param correo Correo electrónico.
     * @param contraseña Contraseña (ya cifrada).
     */
    public Miembro(String nombreUsuario, String correo, String contraseña ) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    // Getters y setters para los atributos, permitiendo acceso y modificación

    public Long getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(Long id) {
        this.idMiembro = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Set<MiembroProyecto> getProyectosMiembro() {
        return proyectosMiembro;
    }

    public void setProyectosMiembro(Set<MiembroProyecto> proyectosMiembro) {
        this.proyectosMiembro = proyectosMiembro;
    }

    public List<Tarea> getTareasAsignadas() {
        return tareasAsignadas;
    }

    public void setTareasAsignadas(List<Tarea> tareasAsignadas) {
        this.tareasAsignadas = tareasAsignadas;
    }

    public List<MensajeChat> getMensajesEnviados() {
        return mensajesEnviados;
    }

    public void setMensajesEnviados(List<MensajeChat> mensajesEnviados) {
        this.mensajesEnviados = mensajesEnviados;
    }
}
