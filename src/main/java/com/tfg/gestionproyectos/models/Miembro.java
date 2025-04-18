package com.tfg.gestionproyectos.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "miembros")
public class Miembro {

    public enum RolMiembro {
        ADMINISTRADOR,
        MIEMBRO
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    @Column(name = "id_miembro")
    private Long idMiembro;

    @Column(unique = true, nullable = false)
    private String nombreUsuario;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String contraseña;

    @Enumerated(EnumType.STRING) // Almacena el rol como texto en la base de datos
    private RolMiembro rol;

    // Relación Muchos a Muchos con Proyectos
    @ManyToMany(mappedBy = "miembros")
    private Set<Proyecto> proyectos;

    // Relación con Tareas (Un Miembro puede tener muchas tareas asignadas)
    @OneToMany(mappedBy = "asignadoA", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareasAsignadas;

    // Relación con Mensajes del Chat (Un Miembro puede enviar muchos mensajes)
    @OneToMany(mappedBy = "miembro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MensajeChat> mensajesEnviados;

    // Constructores
    public Miembro() {}

    public Miembro(String nombreUsuario, String correo, String contraseña, RolMiembro rol) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
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

    public RolMiembro getRol() { return rol; }
    public void setRol(RolMiembro rol) { this.rol = rol; }

    public List<Tarea> getTareasAsignadas() { return tareasAsignadas; }
    public void setTareasAsignadas(List<Tarea> tareasAsignadas) { this.tareasAsignadas = tareasAsignadas; }

    public List<MensajeChat> getMensajesEnviados() { return mensajesEnviados; }
    public void setMensajesEnviados(List<MensajeChat> mensajesEnviados) { this.mensajesEnviados = mensajesEnviados; }
}


