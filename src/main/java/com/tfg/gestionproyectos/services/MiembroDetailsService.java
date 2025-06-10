package com.tfg.gestionproyectos.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tfg.gestionproyectos.models.Miembro;
import com.tfg.gestionproyectos.repositories.MiembroRepository;

@Service
public class MiembroDetailsService implements UserDetailsService {

    @Autowired
    private MiembroRepository miembroRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Miembro miembro = miembroRepository.findByNombreUsuario(username);

        if (miembro == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        // Aquí solo se asigna un rol genérico ROLE_USER para Spring Security.
        // La lógica fina de administración por proyecto se maneja en ProyectoService.
        return new User(
            miembro.getNombreUsuario(),
            miembro.getContraseña(),
            getAuthorities()
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER")); // toddos tienen un rol genérico
}

}
