package com.example.Finanzas.Inteligentes.controller;

import com.example.Finanzas.Inteligentes.service.MailService;
import com.example.Finanzas.Inteligentes.utils.Constants;
import com.example.Finanzas.Inteligentes.utils.dto.SoporteDTO;
import com.example.Finanzas.Inteligentes.utils.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;
    @PostMapping("/sendMail")
    public ResponseEntity<?> FormContacto(@RequestBody UsuarioDTO dto){
        String mensaje ="<p>Nuevo contacto desde formulario web</p>";
        mensaje +="<p style='color: #31497D;'>Nombre: "+ dto.getNombre() +"</p>";
        mensaje +="<p style='color: #31497D;'>Correo: "+ dto.getCorreo() +"</p>";
        mensaje +="<p style='color: #31497D;'>Mensaje: "+ dto.getMensaje() +"</p>";
        mailService.sendMail(Constants.MAIL_ADMIN,"Solicitud de información desde formulario web", mensaje);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/sendMailSupport")
    public ResponseEntity<?> FormSupport(@RequestBody SoporteDTO dto){
        String mensajeS ="<p>Información recabada desde menu ayuda</p>";
        mensajeS +="<p style='color: #31497D;'>Nombre: "+ dto.getNombre() +"</p>";
        mensajeS +="<p style='color: #31497D;'>Correo: "+ dto.getCorreo() +"</p>";
        mensajeS +="<p style='color: #31497D;'>Mensaje: "+ dto.getMensaje() +"</p>";
        mensajeS +="<p style='color: #31497D;'>Describe tu problema: "+ dto.getDescribe() +"</p>";
        mailService.sendMail(Constants.MAIL_ADMIN,"Solicitud de información desde formulario web", mensajeS);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
