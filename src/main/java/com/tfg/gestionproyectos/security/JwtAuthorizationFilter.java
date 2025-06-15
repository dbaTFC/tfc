package com.tfg.gestionproyectos.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tfg.gestionproyectos.services.MiembroDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Filtro personalizado de autorización JWT.
 * Se ejecuta una vez por solicitud y verifica si el token JWT es válido.
 */
@Component // Marca esta clase como un componente gestionado por Spring (detectado automáticamente)
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider; // Clase que gestiona operaciones sobre el token JWT (validación, extracción de usuario, etc.)

    @Autowired
    private MiembroDetailsService miembroDetailsService; // Servicio que carga detalles del usuario (implementa UserDetailsService)

    /**
     * Este método se ejecuta en cada solicitud HTTP.
     * Comprueba si hay un token JWT válido en la cabecera Authorization y, si lo hay,
     * establece la autenticación en el contexto de seguridad de Spring.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Extraemos el header "Authorization" de la solicitud
        String header = request.getHeader("Authorization");

        // Si no existe el header o no empieza por "Bearer ", no se procesa ningún token
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Continuamos sin autenticar
            return;
        }

        // Extraemos el token JWT quitando el prefijo "Bearer "
        String token = header.substring(7);

        // Validamos el token
        if (tokenProvider.isTokenValid(token)) {
            // Si es válido, extraemos el nombre de usuario del token
            String username = tokenProvider.getUsernameFromToken(token);

            // Cargamos los detalles del usuario desde la base de datos
            UserDetails userDetails = miembroDetailsService.loadUserByUsername(username);

            // Creamos un objeto de autenticación con los detalles del usuario
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            // Establecemos la autenticación en el contexto de seguridad de Spring
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // Continuamos la ejecución del siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }
}