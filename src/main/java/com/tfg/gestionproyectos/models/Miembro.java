package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;


@Entity
@Table(name = "miembros")
public class Miembro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_miembro")
    private Long idMiembro;

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres.")
    @Column(unique = true, nullable = false)
    private String nombreUsuario;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "Debe ser un correo electrónico válido.")
    @Column(nullable = false, unique = true)
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    @Column(nullable = false)
    private String contraseña;

    @OneToMany(mappedBy = "miembro", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MiembroProyecto> proyectosMiembro;

    @OneToMany(mappedBy = "asignadoA", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareasAsignadas;

    @OneToMany(mappedBy = "miembro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MensajeChat> mensajesEnviados;

    // Constructores
    public Miembro() {

    }

    public Miembro(String nombreUsuario, String correo, String contraseña ) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
     
    }

    // Getters y Setters
    public Long getIdMiembro() { return idMiembro; }
    public void setIdMiembro(Long id) { this.idMiembro = id; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

       public Set<MiembroProyecto> getProyectosMiembro() {
        return proyectosMiembro;
    }

    public void setProyectosMiembro(Set<MiembroProyecto> proyectosMiembro) {
        this.proyectosMiembro = proyectosMiembro;
    }
    public List<Tarea> getTareasAsignadas() { return tareasAsignadas; }
    public void setTareasAsignadas(List<Tarea> tareasAsignadas) { this.tareasAsignadas = tareasAsignadas; }

    public List<MensajeChat> getMensajesEnviados() { return mensajesEnviados; }
    public void setMensajesEnviados(List<MensajeChat> mensajesEnviados) { this.mensajesEnviados = mensajesEnviados; }
}
