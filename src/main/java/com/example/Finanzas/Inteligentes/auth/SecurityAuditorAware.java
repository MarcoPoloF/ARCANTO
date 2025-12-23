package com.example.Finanzas.Inteligentes.auth;

import com.example.Finanzas.Inteligentes.dao.IUsuario;
import com.example.Finanzas.Inteligentes.modelo.Usuario;
import com.example.Finanzas.Inteligentes.service.UsuarioService;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityAuditorAware implements AuditorAware<Long> {
    private final UsuarioService usuarioService;

    public SecurityAuditorAware( UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")){
            return Optional.empty();
        }
        Usuario usuario = usuarioService.findByEmail(authentication.getName());
        return Optional.of(usuario.getIdusuario());
    }
}
