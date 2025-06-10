package com.tfg.gestionproyectos.security; 


public class Constants {
    // Clave estándar del encabezado HTTP que transporta el token JWT.
    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";

    // Prefijo usado en el token para indicar el esquema de autenticación.
    public static final CharSequence TOKEN_BEARER_PREFIX = "Bearer ";

    // Tiempo de expiración del token JWT (1 hora en milisegundos).
    public static final long TOKEN_EXPIRATION_TIME = 1000 * 60 * 60;

    // Ruta que se expone públicamente para autenticarse y obtener el token JWT.
    public static final String LOGIN_URL = "/login";
}
