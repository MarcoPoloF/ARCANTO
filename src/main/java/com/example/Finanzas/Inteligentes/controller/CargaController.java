package com.example.Finanzas.Inteligentes.controller;

import com.example.Finanzas.Inteligentes.modelo.Carga;
import com.example.Finanzas.Inteligentes.modelo.Cliente;
import com.example.Finanzas.Inteligentes.modelo.Usuario;
import com.example.Finanzas.Inteligentes.service.CargaService;
import com.example.Finanzas.Inteligentes.service.ResourceService;
import com.example.Finanzas.Inteligentes.utils.CargaPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping(value = "/carga")
@CrossOrigin(origins = "*")
public class CargaController {
    @Autowired
private CargaService cargaService;
    @Autowired
    private ResourceService resourceService;
    @PostMapping("/nuevo")
public ResponseEntity<?> nuevaCarga(@RequestBody Carga carga){
    Carga response = cargaService.nuevo(carga);
    return new ResponseEntity<>(response, HttpStatus.OK);
}
@PostMapping("/cargarArchivo")
public ResponseEntity<?> cargarArchivo(HttpServletRequest request) throws IOException {
    Long idcarga = Long.parseLong(request.getParameter("id"));
    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
    Map map = multipartHttpServletRequest.getFileMap();
    MultipartFile archivo = (MultipartFile) map.get("file");
    String nuevoarchivo = "";
    if (!archivo.isEmpty()) {
        nuevoarchivo = resourceService.cargaArchivo(archivo, idcarga);
    }
    cargaService.cargaArchivo(idcarga, nuevoarchivo);

    Carga carga = new Carga();
    Usuario response = new Usuario();
    carga.setRuta(nuevoarchivo);
    carga.setUsuario(response);
    return new ResponseEntity<>(carga, HttpStatus.OK);
    }
    @GetMapping("/getCargas")
    public ResponseEntity<?> getReportes(){
        List<Carga> cargas = cargaService.getAll();
        return new ResponseEntity<>(cargas,HttpStatus.OK);
    }
    @GetMapping("/getArchivos/{idusuario}")
    public ResponseEntity<?>getArchivos(@PathVariable Long idusuario){
        List<Carga> cargas = cargaService.getByUsuario(idusuario);
        return  new ResponseEntity<>(cargas, HttpStatus.OK);
    }
    @GetMapping("/getByCarga/{id}")
    public ResponseEntity<?>getArchivosByCarga(@PathVariable Long id){
        Carga carga = cargaService.getById(id);
        return  new ResponseEntity<>(carga,HttpStatus.OK);
    }
    @DeleteMapping("/quitarCarga/{id}")
    public ResponseEntity<?> quitarCarga(@PathVariable Long id){
        cargaService.deleteCarga(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/updateCarga")
    public ResponseEntity<?> updateCarga(@RequestBody Carga carga){
    Carga response = cargaService.updateCarga(carga);
    return new ResponseEntity<>(response,HttpStatus.OK);
    }
    /*filter*/
    @GetMapping("/cargapage/{page}")
    public Page<Carga> cargaPage(@PathVariable Integer page){
        return cargaService.findAllPage(PageRequest.of(page,10));
    }

    @RequestMapping("/byCargaPage/{page}")
    public ResponseEntity<?> cargaByPage(@PathVariable Integer page, @RequestBody Carga filtros){
        CargaPageable cargaPageable = cargaService.getPage(page, filtros);
        return new ResponseEntity<>(cargaPageable,HttpStatus.OK);
    }

}
