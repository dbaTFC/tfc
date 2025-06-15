package com.tfg.gestionproyectos.controllers;

import com.tfg.gestionproyectos.dtos.MiembroContraseñaDTO;
import com.tfg.gestionproyectos.dtos.MiembroDTO;
import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.models.MiembroProyecto;
import com.tfg.gestionproyectos.services.MiembroService;
import com.tfg.gestionproyectos.services.ProyectoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// Definición del controlador REST para manejar solicitudes HTTP relacionadas con miembros
@RestController
// Prefijo común para todas las rutas en este controlador
@RequestMapping("/miembros")
public class MiembroController {

    // Inyección automática del servicio que maneja la lógica de miembros
    @Autowired
    private MiembroService miembroService;

    // Inyección automática del servicio que maneja la lógica de proyectos
    @Autowired
    private ProyectoService proyectoService;

    // Inyección del encoder para codificar contraseñas
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Obtener todos los miembros (sin incluir la contraseña)
     * @return lista de miembros convertidos a DTO para no exponer datos sensibles
     */
    @GetMapping
    public ResponseEntity<List<MiembroDTO>> obtenerTodosLosMiembros() {
        // Obtener todos los miembros desde el servicio
        List<Miembro> miembros = miembroService.obtenerTodosLosMiembros();

        // Convertir cada miembro a su DTO correspondiente
        List<MiembroDTO> dtos = new ArrayList<>();
        for (Miembro miembro : miembros) {
            dtos.add(new MiembroDTO(miembro));
        }

        // Devolver la lista de DTOs con código HTTP 200 OK
        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtener todos los miembros incluyendo sus contraseñas
     * IMPORTANTE: Este endpoint debe protegerse o eliminarse en producción
     * @return lista de miembros con todas sus propiedades, incluida la contraseña
     */
    @GetMapping("/contraseña")
    public ResponseEntity<List<MiembroContraseñaDTO>> obtenerTodosLosMiembrosConContraseña() {
        // Obtener todos los miembros
        List<Miembro> miembros = miembroService.obtenerTodosLosMiembros();

        // Convertir cada miembro a DTO que incluye la contraseña
        List<MiembroContraseñaDTO> dtos = new ArrayList<>();
        for (Miembro miembro : miembros) {
            dtos.add(new MiembroContraseñaDTO(miembro));
        }

        // Devolver la lista con código HTTP 200 OK
        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtener un miembro específico por su ID
     * @param idMiembro ID del miembro a buscar
     * @return miembro convertido a DTO
     */
    @GetMapping("/{idMiembro}")
    public ResponseEntity<MiembroDTO> obtenerMiembroPorId(@PathVariable Long idMiembro) {
        // Obtener miembro desde el servicio por ID
        Miembro miembro = miembroService.obtenerMiembroPorId(idMiembro);

        // Convertir el miembro a DTO para devolverlo
        MiembroDTO dto = new MiembroDTO(miembro);

        // Devolver DTO con código HTTP 200 OK
        return ResponseEntity.ok(dto);
    }

    /**
     * Crear un nuevo miembro, codificando la contraseña antes de guardar
     * @param miembro objeto miembro recibido en el body de la solicitud
     * @return miembro creado convertido a DTO con código HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<MiembroDTO> crearMiembro(@Valid @RequestBody Miembro miembro) {
        // Codificar la contraseña para seguridad antes de guardar
        miembro.setContraseña(passwordEncoder.encode(miembro.getContraseña()));

        // Guardar nuevo miembro a través del servicio
        Miembro nuevoMiembro = miembroService.crearMiembro(miembro);

        // Convertir a DTO para devolverlo sin datos sensibles
        MiembroDTO dto = new MiembroDTO(nuevoMiembro);

        // Devolver DTO con código HTTP 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    /**
     * Actualizar un miembro existente
     * @param idMiembro ID del miembro a actualizar
     * @param miembroDetalles detalles para actualizar recibidos en el body
     * @return miembro actualizado convertido a DTO
     */
    @PutMapping("/{idMiembro}")
    public ResponseEntity<MiembroDTO> actualizarMiembro(
            @PathVariable Long idMiembro,
            @Valid @RequestBody Miembro miembroDetalles) {

        // Actualizar miembro mediante el servicio
        Miembro miembroActualizado = miembroService.actualizarMiembro(idMiembro, miembroDetalles);

        // Convertir a DTO para devolverlo
        MiembroDTO dto = new MiembroDTO(miembroActualizado);

        // Devolver DTO con código HTTP 200 OK
        return ResponseEntity.ok(dto);
    }

    /**
     * Eliminar un miembro, solo si el solicitante es administrador de algún proyecto del miembro
     * @param idMiembro ID del miembro a eliminar
     * @param userDetails detalles del usuario autenticado (solicitante)
     * @return respuesta vacía con código 204 No Content o 403 Forbidden si no tiene permisos
     */
    @DeleteMapping("/{idMiembro}")
    public ResponseEntity<Void> eliminarMiembro(
            @PathVariable Long idMiembro,
            @AuthenticationPrincipal UserDetails userDetails) {

        // Obtener ID del solicitante por su username
        Long solicitanteId = proyectoService.getMiembroIdByUsername(userDetails.getUsername());

        // Obtener el miembro que se desea eliminar junto con sus proyectos
        Miembro miembroAEliminar = miembroService.obtenerMiembroPorId(idMiembro);
        Set<MiembroProyecto> proyectosMiembro = miembroAEliminar.getProyectosMiembro();

        // Verificar si el solicitante es administrador de al menos uno de esos proyectos
        boolean esAdmin = proyectosMiembro.stream()
                .anyMatch(mp -> proyectoService.esAdministradorDelProyecto(solicitanteId, mp.getProyecto().getIdProyecto()));

        // Si no es administrador, devolver código 403 Forbidden
        if (!esAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Si tiene permisos, eliminar el miembro
        miembroService.eliminarMiembro(idMiembro);

        // Devolver código 204 No Content para indicar éxito sin contenido en respuesta
        return ResponseEntity.noContent().build();
    }
}
