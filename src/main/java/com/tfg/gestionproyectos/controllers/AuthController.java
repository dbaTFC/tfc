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

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtUtil;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(token, userDetails.getUsername()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }
}

class AuthRequest {
    private String username;
    private String password;

    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return username; }

    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }
}

class AuthResponse {
    private String token;
    private String username;

    public AuthResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() { return token; }
    public String getUsername() { return username; }
}
