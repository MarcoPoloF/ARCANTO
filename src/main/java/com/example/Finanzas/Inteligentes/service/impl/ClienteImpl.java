package com.example.Finanzas.Inteligentes.service.impl;

import com.example.Finanzas.Inteligentes.dao.ICliente;
import com.example.Finanzas.Inteligentes.dao.IRolDao;
import com.example.Finanzas.Inteligentes.dao.IUsuario;
import com.example.Finanzas.Inteligentes.modelo.Cliente;
import com.example.Finanzas.Inteligentes.modelo.Rol;
import com.example.Finanzas.Inteligentes.modelo.Usuario;
import com.example.Finanzas.Inteligentes.service.ClienteService;
import com.example.Finanzas.Inteligentes.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClienteImpl implements ClienteService {
    @Autowired
    private ICliente iCliente;
    @Autowired
    private IUsuario iUsuario;
    @Autowired
    private IRolDao rolDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;

    @Override
    @Transactional
    public Usuario nuevoCliente(Usuario usuario) {
        Usuario usual1 = new Usuario();
        Cliente cliente = new Cliente();
        usual1.setNombre_c(usuario.getNombre_c());
        usual1.setCorreo(usuario.getCorreo());
        usual1.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usual1.setNocifrado(usuario.getNocifrado());
        usual1.setFecha_registro(new Date());
        usual1.setFecha_edicion(new Date());
        usual1.setEstado(usuario.isEstado());
        List<Rol> role =new ArrayList<>();
        role.add(rolDao.getRolById(2L));
        usual1.setRolList(role);
        usual1 = iUsuario.save(usual1);
        cliente.setNombre(usuario.getCliente().getNombre());
        cliente.setDescripcion(usuario.getCliente().getDescripcion());
        cliente.setUrl(usuario.getCliente().getUrl());
        cliente.setColorP(usuario.getCliente().getColorP());
        cliente.setColorS(usuario.getCliente().getColorS());
        cliente = iCliente.save(cliente);
        iUsuario.asignarCliente(usual1.getIdusuario(),cliente.getId());
        try {
            String mensaje ="<h3 style='color:#31497D;'>Bienvenida(o) a nuestra plataforma Finanzas Inteligentes</h3><br><br>"+
                    "<p style='color:#31497D;'>Buen día " +usual1.getNombre_c()+"</p>"+
                    "<p>Deseamos que te encuentres bien y aprovechamos para darte una cordial bienvenida a la plataforma de reportes y dashboards de Finanzas Inteligentes.\n" +
                    "Una forma inteligente y productiva de revisar tus reportes para la toma de decisiones de negocio.</p>"+
                    "<p>A continuación, te compartimos tus datos de acceso a la plataforma</p>"+
                    "<p style='text-align: center !important;'>Datos de acceso </p>"+
                    "<p style='color:#31497D;'>Usuario: " +usual1.getCorreo()+"</p>"+
                    "<p style='color:#31497D;'>Razón social: " +cliente.getNombre()+"</p>"+
                    "<p style='color:#31497D;'>Contraseña: "+usual1.getNocifrado()+"</p>"+
                    "<p style='color:#31497D;'>Accediendo desde el siguiente enlace: <br> <a href='https://finanzasinteligente.com/login' target='_blank'>Finanzas Inteligentes</a> " +
                    "<br></p>" +
                    "<br><p>Nos encontramos a sus ordenes para cualquier duda que presente.<br>" +
                    " Saludos.</p>";
            mailService.sendMail(usual1.getCorreo(),"Mensaje de bienvenida", mensaje);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
       return iUsuario.getById(usual1.getIdusuario());
    }

    @Override
    @Transactional
    public void setLogo(Long idCliente, String logo) {
        iCliente.actualizaLogo(idCliente,logo);
    }

    @Override
    public List<Cliente> getClientes() {
        return iCliente.findAll();
    }

    @Override
    @Transactional
    public Usuario updateCliente(Usuario usuario) {
        iUsuario.UpdateUsuario(usuario.getIdusuario(),
                usuario.getNombre_c(),
                usuario.getCorreo(),
                passwordEncoder.encode(usuario.getContrasena()),
                usuario.getNocifrado(), usuario.isEstado(), new Date());
        iUsuario.updateCliente(usuario.getCliente().getId(),usuario.getCliente().getNombre(),
                usuario.getCliente().getDescripcion(),usuario.getCliente().getLogo(),usuario.getCliente().getUrl(),
                usuario.getCliente().getColorP(),usuario.getCliente().getColorS());
        return iUsuario.getById(usuario.getIdusuario());
    }
    @Transactional
    @Override
    public Usuario updateLogo(Usuario usuario) {
        iCliente.actualizaLogo(usuario.getCliente().getId(),usuario.getCliente().getLogo());
        return iUsuario.getById(usuario.getIdusuario());
    }

    @Override
    public void actualizarStatus(Usuario usuario) {
        iUsuario.actualizarStatus(usuario.getIdusuario(), usuario.isEstado());
    }

}
