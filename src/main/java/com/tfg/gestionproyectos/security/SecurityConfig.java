package com.tfg.gestionproyectos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
                    "/mensajes/**"
                ).permitAll()
                .anyRequest().permitAll() // Permitir el resto también (temporal)
            )
            .formLogin(form -> form.disable()); // Desactiva el login por formulario

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/h2-console/**");
    }
}
