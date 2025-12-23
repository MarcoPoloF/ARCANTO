package com.example.Finanzas.Inteligentes.service;

import com.example.Finanzas.Inteligentes.modelo.Cliente;
import com.example.Finanzas.Inteligentes.modelo.Usuario;

import java.util.List;

public interface ClienteService {
    Usuario nuevoCliente(Usuario usuario);
    void setLogo(Long idusuario, String logo);
    List<Cliente> getClientes();
    Usuario updateCliente(Usuario usuario);
    void actualizarStatus( Usuario usuario);
    Usuario updateLogo(Usuario usuario);
}
