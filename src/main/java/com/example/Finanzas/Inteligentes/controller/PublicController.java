package com.example.Finanzas.Inteligentes.controller;

import com.example.Finanzas.Inteligentes.modelo.Carga;
import com.example.Finanzas.Inteligentes.modelo.Cliente;
import com.example.Finanzas.Inteligentes.modelo.Usuario;
import com.example.Finanzas.Inteligentes.service.CargaService;
import com.example.Finanzas.Inteligentes.service.ClienteService;
import com.example.Finanzas.Inteligentes.service.ResourceService;
import com.example.Finanzas.Inteligentes.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private CargaService cargaService;
    @Autowired
    private ResourceService resourceService;

    @GetMapping("/cliente/getClientes")
    public ResponseEntity<?> getClientes() {
        List<Cliente> clienteList = clienteService.getClientes();
        return new ResponseEntity<>(clienteList, HttpStatus.OK);
    }

    @GetMapping("/usuario/getActive")
    public ResponseEntity<?> getActive() {
        List<Usuario> usuarioList = usuarioService.getActive();
        for (Usuario usuario : usuarioList) {
            usuario.setNocifrado("");
            usuario.setContrasena("");
            usuario.setNombre_c("");
            usuario.setFecha_edicion(null);
            usuario.setFecha_edicion(null);
            usuario.setRolList(null);
        }
        return new ResponseEntity<>(usuarioList, HttpStatus.OK);
    }

    @GetMapping("/carga/getArchivos/{idusuario}")
    public ResponseEntity<?> getArchivos(@PathVariable Long idusuario) {
        List<Carga> cargas = cargaService.getByUsuario(idusuario);
        return new ResponseEntity<>(cargas, HttpStatus.OK);
    }

    @GetMapping("/usuario/getById/{idusuario}")
    public ResponseEntity<?> getById(@PathVariable(name = "idusuario") Long idusuario) {
        Usuario usuario = usuarioService.getById(idusuario);
        usuario.setContrasena("");
        usuario.setNocifrado("");
        usuario.setCorreo("");
        usuario.setFecha_registro(null);
        usuario.setFecha_edicion(null);
        usuario.getCliente().setDescripcion("");
        usuario.setRolList(null);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    /*Nueva implementación de recursos*/
    @GetMapping("/resource/bajar/**")
    public ResponseEntity<Resource> nuevo(HttpServletRequest request) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        String p = request.getRequestURI().split("/bajar/")[1];
        p = p.replace("%20", " ");
        Resource resource = null;
        try {
            resource = resourceService.cargar(p);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(resource.getFile().toPath()));
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"");
          return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/resource/descarga/**")
    public ResponseEntity<Resource> descarga(HttpServletRequest request) throws IOException{
        String p = request.getRequestURI().split("/descarga/")[1];
        Resource r = null;
        InputStreamResource i = null;
        try{
            r = resourceService.cargar(p);
            i = new InputStreamResource(r.getInputStream());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + r.getFilename() +"\"");
        return ResponseEntity.ok().headers(cabecera).contentLength(r.contentLength()).contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(i);
    }
    @GetMapping("/getResource/**")
    public ResponseEntity<Resource> getResource(HttpServletRequest request) throws IOException{
        String path = request.getRequestURI().split("/getResource/")[1];
        Resource resource = null;
        try{
            resource = resourceService.cargar(path);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(resource.getFile().toPath()));
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() +"\"");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

}