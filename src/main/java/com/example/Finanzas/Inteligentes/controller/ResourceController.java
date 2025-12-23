package com.example.Finanzas.Inteligentes.controller;

import com.example.Finanzas.Inteligentes.service.ResourceService;
import org.codehaus.jackson.map.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/resources")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;
    @GetMapping("/download/{nombre:.+}")
    public ResponseEntity<Resource> download(@PathVariable String nombre) throws IOException{
        Resource resource = null;
        InputStreamResource inputStreamResource = null;
        try{
            resource = resourceService.cargar(nombre);
            inputStreamResource = new InputStreamResource(resource.getInputStream());
        }catch (MalformedURLException e){
            System.out.println(e.getMessage());
         e.printStackTrace();
        }
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+resource.getFilename()+"\"");
        return  ResponseEntity.ok().headers(cabecera).contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(inputStreamResource);
    }
    /*Nueva implementación de recursos*/
    @GetMapping("/descarga/**")
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

    @GetMapping("/viewtoken/**")
    public ResponseEntity<Resource> viewtoken(HttpServletRequest request) throws IOException{
        String token = request.getParameter("token");
        if(token == null){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        try{
            Jwt jwt = JwtHelper.decode(token);
            JSONObject jsonObject = new JSONObject(jwt.getClaims());
            JSONArray authorities = (JSONArray) jsonObject.get("authorities");
            if(authorities.toList().size() > 0){
                String path = request.getRequestURI().split("/viewtoken/")[1];
                Resource resource = null;
                try{
                    resource = resourceService.cargar(path);
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(resource.getFile().toPath()));
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() +"\"");
                return  new ResponseEntity<>(resource, headers, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/descarga/{name:.+}")
    public ResponseEntity<Resource> descarga(@PathVariable String name) throws IOException{
        Resource resource = null;
        InputStreamResource inputStreamResource = null;
        try {
            resource = resourceService.carga(name);
            inputStreamResource = new InputStreamResource(resource.getInputStream());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
    }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+resource.getFilename()+"\"");
        return ResponseEntity.ok().headers(headers).contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(inputStreamResource);
}

    @GetMapping("/bajar/{name:.+}")
    public ResponseEntity<Resource> bajar(@PathVariable String name) throws IOException{
        Resource resource = null;
        InputStreamResource inputStreamResource = null;
        try {
            resource = resourceService.carga(name);
            inputStreamResource = new InputStreamResource(resource.getInputStream());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"inline; filename=\""+resource.getFilename()+"\"");
        return ResponseEntity.ok().headers(headers).contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(inputStreamResource);
    }
    @GetMapping("/view/{name:.+}")
    public ResponseEntity<Resource> baja(@PathVariable String name) throws IOException {
        Resource resource = null;
        try {
            resource = resourceService.carga(name);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(resource.getFile().toPath()));
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
