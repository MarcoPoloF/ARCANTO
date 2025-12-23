package com.example.Finanzas.Inteligentes.dao;

import com.example.Finanzas.Inteligentes.modelo.Usuario;
import com.example.Finanzas.Inteligentes.utils.UsuarioPageable;

public interface IUsuarioRepository {
    UsuarioPageable getUsuarioPageable(int page, Usuario filtros);
}
