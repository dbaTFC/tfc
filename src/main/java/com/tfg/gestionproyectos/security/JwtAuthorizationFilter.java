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

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MiembroDetailsService miembroDetailsService;

   @Override
   protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain) throws ServletException, IOException {
       String header = request.getHeader("Authorization");

       if (header == null || !header.startsWith("Bearer ")) {
           filterChain.doFilter(request, response);
           return;
       }

       String token = header.substring(7);

       if (tokenProvider.isTokenValid(token)) {
           String username = tokenProvider.getUsernameFromToken(token);
           UserDetails userDetails = miembroDetailsService.loadUserByUsername(username);
           UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                   userDetails, null, userDetails.getAuthorities());

           SecurityContextHolder.getContext().setAuthentication(authToken);
       }

       filterChain.doFilter(request, response);
   }
}

