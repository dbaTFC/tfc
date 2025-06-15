package com.tfg.gestionproyectos.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.tfg.gestionproyectos.services.MiembroDetailsService;

/**
 * Configuración principal de seguridad para la aplicación.
 * Define filtros, reglas de acceso, autenticación y manejo de CORS.
 */
@Configuration // Marca esta clase como configuración Spring
@EnableWebSecurity // Habilita la configuración de seguridad web
public class SecurityConfig {

    @Autowired
    private MiembroDetailsService miembroDetailsService; // Servicio personalizado para cargar datos de usuario

    @Autowired
    private JwtAuthorizationFilter jwtAuthenticationFilter; // Filtro que valida el JWT en cada request

    /**
     * Configuración CORS para permitir peticiones desde distintos orígenes.
     * En desarrollo se suele permitir todo, pero en producción hay que restringir.
     */
   @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // Solo se permite Angular en local
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        corsConfig.setExposedHeaders(Arrays.asList("Authorization")); // Exponemos el token si fuera necesario
        corsConfig.setAllowCredentials(false); // No se usan cookies ni credenciales en JWT
        corsConfig.setMaxAge(3600L); // Cache de preflight por 1h

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }


    /**
     * Configura la cadena de filtros de seguridad para peticiones HTTP.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Aplica la configuración CORS
            .csrf(csrf -> csrf.disable()) // Deshabilita protección CSRF (se suele hacer en APIs REST)
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // Permite uso de consola H2 embebida
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No se mantiene sesión (JWT)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/h2-console/**",       // Consola H2 accesible sin autenticación
                    "/v3/api-docs/**",      // Documentación Swagger accesible sin autenticación
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/login"                // Endpoint login público para autenticarse
                ).permitAll()
                .requestMatchers(HttpMethod.POST, "/miembros/**").permitAll() // Permite crear usuarios sin autenticarse
                .requestMatchers(HttpMethod.GET, "/miembros/**").permitAll()  // TEMPORAL: permite listar miembros sin login
                .anyRequest().authenticated() // Resto de endpoints requieren autenticación
            )
            // Añade el filtro para validar JWT antes del filtro de autenticación por usuario/contraseña
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    /**
     * Proveedor de autenticación que usa el servicio de usuario personalizado y codifica la contraseña.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // Indica cómo cargar usuario y cómo verificar la contraseña
        authProvider.setUserDetailsService(miembroDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Provee el AuthenticationManager, que procesa las solicitudes de autenticación.
     * Se usa principalmente para el login.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Bean que provee el encoder de contraseñas usando BCrypt (hash seguro).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
