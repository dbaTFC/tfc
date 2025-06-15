package com.tfg.gestionproyectos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.tfg.gestionproyectos.security.TokenProvider;

/**
 * Controlador REST para el proceso de autenticación (login).
 * Permite a los usuarios autenticarse y recibir un token JWT.
 */
@RestController
@RequestMapping("/login")  // Define la ruta base para este controlador
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager; // Maneja el proceso de autenticación

    @Autowired
    private TokenProvider jwtUtil; // Utilidad para generar y validar tokens JWT

    /**
     * Endpoint para login de usuarios.
     * Recibe usuario y contraseña, intenta autenticar y devuelve un token JWT si es correcto.
     */
    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // Se intenta autenticar con las credenciales recibidas
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            // Si la autenticación es exitosa, obtenemos detalles del usuario autenticado
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generamos un token JWT para ese usuario
            String token = jwtUtil.generateToken(userDetails);

            // Devolvemos el token y el nombre de usuario en la respuesta
            return ResponseEntity.ok(new AuthResponse(token, userDetails.getUsername()));
        } catch (BadCredentialsException e) {
            // Si la autenticación falla (usuario o contraseña incorrectos), devolvemos 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }
}

/**
 * Clase para mapear la solicitud de login.
 * Contiene el username y password enviados desde el cliente.
 */
class AuthRequest {
    private String username;
    private String password;

    // Getters y setters para serialización/deserialización JSON
    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return username; }

    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }
}

/**
 * Clase para devolver la respuesta de login.
 * Contiene el token JWT y el username.
 */
class AuthResponse {
    private String token;
    private String username;

    public AuthResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    // Getters para serializar la respuesta a JSON
    public String getToken() { return token; }
    public String getUsername() { return username; }
}
