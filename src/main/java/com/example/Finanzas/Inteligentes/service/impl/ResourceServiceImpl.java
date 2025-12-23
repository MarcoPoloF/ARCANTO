package com.example.Finanzas.Inteligentes.service.impl;

import com.example.Finanzas.Inteligentes.service.ResourceService;
import com.example.Finanzas.Inteligentes.utils.Constants;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

@Service
public class ResourceServiceImpl implements ResourceService {
    private final static String DIRECTORIO_UPLOAD = Constants.DIRECTORIO_UPLOAD;

    @Override
    public String cargaLogo(MultipartFile logo, Long idUsuario) throws IOException {
       String extension = logo.getOriginalFilename().split("\\.")[1];
        //Asigna un nombre de manera aleatoria al archivo y carga en ruta configurada
       String nombreArchivo = UUID.randomUUID()+"_"+ idUsuario+"."+extension;
       Path destinationFile = Paths.get(DIRECTORIO_UPLOAD, nombreArchivo);
       destinationFile.getParent().toFile().mkdir();
       Files.copy(logo.getInputStream(),destinationFile, StandardCopyOption.REPLACE_EXISTING);
       return nombreArchivo;
    }

    @Override
    public Resource cargar(String archivo) throws MalformedURLException {
       Path rutaArchivo = getPath(archivo);
       Resource resource = new UrlResource(rutaArchivo.toUri());
       if(!resource.exists()){
           throw new MalformedURLException("No se pudo cargar el logo :c");
       }
        return resource;
    }

    @Override
    public String cargaArchivo(MultipartFile archivo, Long idCliente) throws IOException {
        String arrayExtension = archivo.getOriginalFilename().split("\\.")[1];
    //String extension = arrayExtension[arrayExtension.length - 1];
    //        String nombreArchivo = UUID.randomUUID() + "." + arrayExtension;
    String nombreArchivo = archivo.getOriginalFilename()+"."+arrayExtension;
    Path destino = Paths.get(DIRECTORIO_UPLOAD, nombreArchivo);
    destino.getParent().toFile().mkdir();
    Files.copy(archivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
        return nombreArchivo;
    }

    @Override
    public Resource carga(String archivo) throws MalformedURLException  {
       Path ruta = getPath(archivo);
       Resource resource = new UrlResource(ruta.toUri());
       if(!resource.exists()){
           throw  new MalformedURLException("El archivo no ha sido cargado");
       }
        return resource;
    }

    /*Metodo base 64 convierte archivo a cadena de texto*/
    @Override
    public String copiarBase64Folder(String base64, String folder) throws IOException {
       int extensionStartIndex = base64.indexOf('/');
       int extensionEndIndex = base64.indexOf(';');
       String fileExtension = base64.substring(extensionStartIndex +1, extensionEndIndex);
        byte[] decodeImg = Base64.getDecoder().decode(base64.split(",")[1].getBytes(StandardCharsets.UTF_8));
       String nombreArchivo = UUID.randomUUID() + "." + fileExtension;
       Path destino = Paths.get(DIRECTORIO_UPLOAD + "/" + folder, nombreArchivo);
        destino.getParent().toFile().mkdir();
        File result = Files.write(destino, decodeImg).toFile();
        result.setWritable(true);
        result.setReadable(true);
        result.setExecutable(true);
        return folder + "/" + nombreArchivo;
    }

    public Path getPath(String archivo){
        return Paths.get(DIRECTORIO_UPLOAD).resolve(archivo).toAbsolutePath();
    }
}
