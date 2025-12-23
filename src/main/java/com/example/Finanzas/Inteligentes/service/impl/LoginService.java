package com.example.Finanzas.Inteligentes.service.impl;

import com.example.Finanzas.Inteligentes.dao.IUsuario;
import com.example.Finanzas.Inteligentes.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private IUsuario iUsuario;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = iUsuario.findByCorreo(s);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        List<GrantedAuthority> authorities =
                usuario.getRolList().stream()
                        .map(role-> new SimpleGrantedAuthority(role.getRol().toString())).collect(Collectors.toList());
        return new User(usuario.getCorreo(), usuario.getContrasena(), usuario.isEstado(),true,true,true,authorities);
    }
}
