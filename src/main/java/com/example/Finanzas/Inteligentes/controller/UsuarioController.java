package com.example.Finanzas.Inteligentes.controller;

import com.example.Finanzas.Inteligentes.modelo.Usuario;
import com.example.Finanzas.Inteligentes.service.UsuarioService;
import com.example.Finanzas.Inteligentes.utils.UsuarioPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/getById/{idusuario}")
    public ResponseEntity<?> getById(@PathVariable(name="idusuario") Long idusuario){
        Usuario usuario= usuarioService.getById(idusuario);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> findAll(){
        List<Usuario> usuarioList = usuarioService.getAll();
        return new ResponseEntity<>(usuarioList, HttpStatus.OK);
    }
    @GetMapping("/getActive")
    public ResponseEntity<?> getActive(){
        List<Usuario> usuarioList = usuarioService.getActive();
        return new ResponseEntity<>(usuarioList,HttpStatus.OK);
    }
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody Usuario usuario){
        Usuario response = usuarioService.updatePassword(usuario);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getGestors")
    public ResponseEntity<?> getGestors(){
        List<Usuario> response = usuarioService.getGestores();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getAnalistas")
    public ResponseEntity<?> getAnalistas(){
        List<Usuario> response = usuarioService.getAnalistas();
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/saveGestors")
    public ResponseEntity<?> saveGestors(@RequestBody Usuario usuario){
        usuarioService.saveGestor(usuario);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    @DeleteMapping("/eliminarUsuario/{idusuario}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long idusuario){
        usuarioService.deleteById(idusuario);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }
    /*Filter*/
    @GetMapping("/page/{page}")
    public Page<Usuario> usuarioPage(@PathVariable Integer page){
    return usuarioService.findAllPage(PageRequest.of(page,10));
    }
    @RequestMapping("/byPage/{page}")
    public ResponseEntity<?> usuarioByPage(@PathVariable Integer page, @RequestBody Usuario filtros){
        UsuarioPageable usuarioPageable= usuarioService.getPage(page, filtros);
        return new ResponseEntity<>(usuarioPageable, HttpStatus.OK);
    }
}
