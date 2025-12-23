package com.example.Finanzas.Inteligentes.service;

import com.example.Finanzas.Inteligentes.modelo.Usuario;
import com.example.Finanzas.Inteligentes.utils.UsuarioPageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UsuarioService {
    Usuario findByEmail(String correo);
    Usuario getById(Long idUsuario);
    List<Usuario> getAll();

    List<Usuario> getGestores();
    List<Usuario> getAnalistas();
    Usuario updatePassword(Usuario usuario);
    void deleteById(Long idusuario);
    List<Usuario> getActive();
    Page<Usuario> findAllPage(PageRequest pageRequest);
    UsuarioPageable getPage(int page, Usuario filtros);
    void saveGestor(Usuario usuario);
    void updateGestor(Usuario usuario);
}
