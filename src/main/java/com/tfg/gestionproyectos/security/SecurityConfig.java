package com.tfg.gestionproyectos.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        // Configuración ultra-permisiva (solo para desarrollo)
        corsConfig.setAllowedOrigins(Arrays.asList("*"));  // Permite cualquier origen
        corsConfig.setAllowedMethods(Arrays.asList("*"));  // Permite cualquier método
        corsConfig.setAllowedHeaders(Arrays.asList("*"));  // Permite cualquier header
        corsConfig.setExposedHeaders(Arrays.asList("*"));  // Expone todos los headers
        corsConfig.setAllowCredentials(false);  // Importante cuando se usa "*" en origins
        corsConfig.setMaxAge(3600L);  // Cache de opciones CORS por 1 hora

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactivar CSRF (importante para H2 y pruebas sin frontend)
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // Permitir cargar H2 en iframe
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/h2-console/**",
                    "/proyectos/**",
                    "/tareas/**",
                    "/miembros/**",
                    "/documentos/**",
                    "/eventos/**",
                    "/mensajes/**",
                     "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
                ).permitAll()
                .anyRequest().authenticated() // Permitir el resto también (temporal)
            )
            .csrf(csrf -> csrf.disable());
            //.formLogin(form -> form.disable()); // Desactiva el login por formulario

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/h2-console/**");
    }
}
