package com.tfg.gestionproyectos.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/test")
@Tag(name = "TestController", description = "Controlador expuesto en Swagger")
public class TestController {

    @GetMapping
    public String hello() {
        return "Hola Swagger";
    }
}
