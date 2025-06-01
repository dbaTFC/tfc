package com.tfg.gestionproyectos.controllers;

import com.tfg.gestionproyectos.dtos.MiembroDTO;
import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.services.MiembroService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/miembros")
public class MiembroController {

    @Autowired
    private MiembroService miembroService;

    // Obtener todos los miembros (versión DTO)
    @GetMapping
    public ResponseEntity<List<MiembroDTO>> obtenerTodosLosMiembros() {
        List<Miembro> miembros = miembroService.obtenerTodosLosMiembros();

        // Convertimos a DTOs para no exponer información sensible como contraseñas
        List<MiembroDTO> miembroDTOs = new ArrayList<>();
        for (Miembro miembro : miembros) {
            miembroDTOs.add(new MiembroDTO(miembro));
        }

        return ResponseEntity.ok(miembroDTOs);
    }

    // Obtener todos los miembros (incluyendo contraseña) ELIMINAR O PROTEGER
    @GetMapping("/contraseña")
    public ResponseEntity<List<Miembro>> obtenerTodosLosMiembrosConContraseña() {
        List<Miembro> miembros = miembroService.obtenerTodosLosMiembros();

        // Retornamos la lista de miembros con todas sus propiedades, incluyendo la contraseña
        return ResponseEntity.ok(miembros);
    }


    // Obtener miembro por ID (versión DTO)
    @GetMapping("/{idMiembro}")
    public ResponseEntity<MiembroDTO> obtenerMiembroPorId(@PathVariable Long idMiembro) {
        // Buscar el miembro por su ID utilizando el servicio
        Miembro miembro = miembroService.obtenerMiembroPorId(idMiembro);

        // Si no lanza excepción, lo convertimos a DTO
        MiembroDTO dto = new MiembroDTO(miembro);
        return ResponseEntity.ok(dto);
    }

    // Crear un nuevo miembro (acepta entidad completa)
    @PostMapping
    public ResponseEntity<Miembro> crearMiembro(@Valid @RequestBody Miembro miembro) {
        Miembro nuevoMiembro = miembroService.crearMiembro(miembro);
        return ResponseEntity.status(201).body(nuevoMiembro);
    }

    // Actualizar un miembro existente (acepta entidad completa)
    @PutMapping("/{idMiembro}")
    public ResponseEntity<Miembro> actualizarMiembro(
            @PathVariable Long idMiembro,
            @RequestBody Miembro miembroDetalles) {

        Miembro miembroActualizado = miembroService.actualizarMiembro(idMiembro, miembroDetalles);
        return ResponseEntity.ok(miembroActualizado);
    }

    // Eliminar miembro por ID
    @DeleteMapping("/{idMiembro}")
    public ResponseEntity<Void> eliminarMiembro(@PathVariable Long idMiembro) {
        miembroService.eliminarMiembro(idMiembro);
        return ResponseEntity.noContent().build();
    }
}
