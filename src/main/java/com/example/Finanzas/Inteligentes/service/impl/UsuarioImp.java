package com.example.Finanzas.Inteligentes.service.impl;

import com.example.Finanzas.Inteligentes.dao.IRolDao;
import com.example.Finanzas.Inteligentes.dao.IUsuario;
import com.example.Finanzas.Inteligentes.dao.IUsuarioRepository;
import com.example.Finanzas.Inteligentes.modelo.Rol;
import com.example.Finanzas.Inteligentes.modelo.Usuario;
import com.example.Finanzas.Inteligentes.service.MailService;
import com.example.Finanzas.Inteligentes.service.UsuarioService;
import com.example.Finanzas.Inteligentes.utils.UsuarioPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UsuarioImp implements UsuarioService {

    @Autowired
    private IUsuario iUsuario;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private IUsuarioRepository iUsuarioRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private IRolDao rolDao;

    @Override
    @Transactional (readOnly = true)
    public Usuario findByEmail(String correo) {
        return iUsuario.findByCorreo(correo);
    }

    @Override
    public Usuario getById(Long idUsuario) {
        return iUsuario.getById(idUsuario);
    }

    @Override
    public List<Usuario> getAll() {
        return iUsuario.findAll();
    }

    @Override
    public List<Usuario> getGestores() {
        return iUsuario.getByRol(3L,1L);
    }

    @Override
    public List<Usuario> getAnalistas() {
        return iUsuario.getAnalistas();
    }

    @Transactional
    @Override
    public Usuario updatePassword(Usuario usuario) {
      iUsuario.updatePassword(usuario.getIdusuario(), passwordEncoder.encode(usuario.getContrasena()));
        return iUsuario.getById(usuario.getIdusuario());
    }

    @Transactional
    @Override
    public void deleteById(Long idusuario) {
            iUsuario.deleteByid(idusuario);
    }

    @Override
    public List<Usuario> getActive() {
        return iUsuario.getActive();
    }

    @Override
    public Page<Usuario> findAllPage(PageRequest pageRequest) {
        return iUsuario.getAllPage(pageRequest);
    }

    @Override
    public UsuarioPageable getPage(int page, Usuario filtros) {
        return iUsuarioRepository.getUsuarioPageable(page, filtros);
    }

    @Override
    public void saveGestor(Usuario usuario) {
        List<Rol> role = new ArrayList<>();
        role.add(rolDao.getRolById(3L));
        //role.add(rolDao.getRolById(usuario.getRolList().get(0).getIdRol()));
        usuario.setRolList(role);
        String passwordBcrypt = passwordEncoder.encode(usuario.getContrasena());
        usuario.setNombre_c(usuario.getNombre_c());
        usuario.setContrasena(passwordBcrypt);
        usuario.setNocifrado(usuario.getNocifrado());
        usuario.setFecha_registro(new Date());
        usuario.setFecha_edicion(null);
        usuario.setEstado(usuario.isEstado());
        usuario.setCliente(null);
        iUsuario.save(usuario);
        try{
            String mensaje ="<h3 style='color:#31497D;'>Bienvenida(o) a nuestra plataforma Finanzas Inteligentes</h3><br><br>"+
                    "<p style='color:#31497D;'>Buen día " +usuario.getNombre_c()+"</p>"+
                    "<p>Deseamos que te encuentres bien y aprovechamos para darte una cordial bienvenida a la plataforma de Finanzas Inteligentes.\n" +
                    "Una forma inteligente y productiva de revisar tus reportes para la toma de decisiones de negocio.</p>"+
                    "<p>A continuación, te compartimos tus datos de acceso a la plataforma</p>"+
                    "<p style='text-align: center !important;'>Datos de acceso </p>"+
                    "<p style='color:#31497D;'>Usuario: " +usuario.getCorreo()+"</p>"+
                    "<p style='color:#31497D;'>Contraseña: "+usuario.getNocifrado()+"</p>"+
                    "<p style='color:#31497D;'>Accediendo desde el siguiente enlace: <br> <a href='https://finanzasinteligente.com:99' target='_blank'>Finanzas Inteligentes</a> " +
                    "<br></p>" +
                    "<br><p>Nos encontramos a sus ordenes para cualquier duda que presente.<br>" +
                    " Saludos.</p>";
            mailService.sendMail(usuario.getCorreo(),"Mensaje de bienvenida", mensaje);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateGestor(Usuario usuario) {
            iUsuario.updateGestor(usuario.getIdusuario(), usuario.getNombre_c(), usuario.getCorreo(),usuario.isEstado());
            iUsuario.updateRol(usuario.getIdusuario(), usuario.getRolList().get(0).getIdRol());
                   if(usuario.getContrasena() != null && usuario.getContrasena() != ""){
                       String passwordBcrypt = passwordEncoder.encode(usuario.getContrasena());
                       iUsuario.updatePassword(usuario.getIdusuario(), passwordBcrypt);
                   }
    }

}
