package com.tfg.gestionproyectos.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Clase responsable de gestionar la creación, validación y análisis de tokens JWT.
 */
@Component // Marca esta clase como un bean de Spring que puede ser inyectado donde se necesite
public class TokenProvider {

    // Se inyecta el valor del secreto JWT desde application.properties o application.yml
    @Value("${jwt.secret}")
    private String secretBase64;

    /**
     * Convierte la clave secreta en base64 a una clave secreta binaria válida para firmar el token.
     * 
     * @return clave secreta HMAC
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretBase64));
    }

    /**
     * Genera un token JWT para un usuario dado.
     *
     * @param userDetails los detalles del usuario (Spring Security)
     * @return el token JWT como String
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // El nombre de usuario será el "subject" del token
                .setIssuedAt(new Date())               // Fecha de emisión del token
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Expira en 1 hora
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) // Firma con clave secreta y algoritmo HS512
                .compact(); // Genera el token como una cadena compacta (String)
    }

    /**
     * Valida si un token es correcto (formato, firma y expiración).
     *
     * @param token el token JWT a validar
     * @return true si es válido, false si no lo es
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                .setSigningKey(getSigningKey()) // Usa la misma clave con la que se firmó el token
                .build()
                .parseClaimsJws(token); // Si el token es válido y no está expirado, no lanza excepción
            return true;
        } catch (JwtException e) {
            // Cualquier excepción indica que el token no es válido: mal formado, expirado o alterado
            return false;
        }
    }

    /**
     * Extrae el nombre de usuario del token JWT (que fue guardado como 'subject').
     *
     * @param token el token del cual extraer el nombre de usuario
     * @return el nombre de usuario
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey()) // Usamos la misma clave para decodificar
                .build()
                .parseClaimsJws(token)          // Parseamos el token firmado
                .getBody()
                .getSubject();                  // Obtenemos el "subject", que es el nombre de usuario
    }
}
