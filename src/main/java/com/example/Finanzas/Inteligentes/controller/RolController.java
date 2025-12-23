package com.example.Finanzas.Inteligentes.controller;

import com.example.Finanzas.Inteligentes.modelo.Rol;
import com.example.Finanzas.Inteligentes.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin (origins = "*")
@RequestMapping("/rol")
public class RolController {
    @Autowired
    private RolService rolService;
    @GetMapping("/getAll")
    public ResponseEntity<?>getAll(){
        List<Rol> rols = rolService.getAll();
        return new ResponseEntity<>(rols, HttpStatus.OK);
    }
}
