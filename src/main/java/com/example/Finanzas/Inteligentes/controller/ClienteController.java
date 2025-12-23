package com.example.Finanzas.Inteligentes.controller;

import com.example.Finanzas.Inteligentes.modelo.Cliente;
import com.example.Finanzas.Inteligentes.modelo.Usuario;
import com.example.Finanzas.Inteligentes.service.ClienteService;
import com.example.Finanzas.Inteligentes.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ResourceService resourceService;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevoCliente(@RequestBody Usuario usuario){
        Usuario response = clienteService.nuevoCliente(usuario);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }
    /*agregar update despues de revisión*/
    @PostMapping("/update")
    public ResponseEntity<?>updateCliente(@RequestBody Usuario usuario){
        Usuario response = clienteService.updateCliente(usuario);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/logo")
    public ResponseEntity<?> updateLogo(@RequestBody Usuario usuario){
        Usuario response = clienteService.updateLogo(usuario);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/agregarLogo")
    public ResponseEntity<?> asignarLogo(HttpServletRequest request) throws IOException{
        Long idUsuario = Long.parseLong(request.getParameter("idusuario"));
        MultipartHttpServletRequest multipartHttpServletRequest =(MultipartHttpServletRequest) request;
        Map map = multipartHttpServletRequest.getFileMap();
        MultipartFile logo =(MultipartFile) map.get("file");
        String archivo ="";
        if(!logo.isEmpty()){
            archivo = resourceService.cargaLogo(logo, idUsuario);
        }
        clienteService.setLogo(idUsuario,archivo);
        Usuario response = new Usuario();
        Cliente cliente = new Cliente();
        cliente.setLogo(archivo);
        response.setCliente(cliente);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getClientes")
    public ResponseEntity<?> getClientes(){
        List<Cliente> clienteList = clienteService.getClientes();
        return new ResponseEntity<>(clienteList,HttpStatus.OK);
    }

}
