package com.example.Finanzas.Inteligentes.dao;

import com.example.Finanzas.Inteligentes.modelo.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface IUsuario extends JpaRepository<Usuario,Long> {
    @Transactional(propagation = Propagation.REQUIRES_NEW) //Propagación de usuario
    Usuario findByCorreo(String correo);

    @Modifying(clearAutomatically = true)
    @Query("update Usuario  u set u.cliente.id= ?2 where u.idusuario= ?1")
    void asignarCliente(Long idusuario, Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Usuario  u set u.nombre_c=?2, u.correo=?3, u.contrasena=?4, u.nocifrado=?5, u.estado=?6, u.fecha_edicion=?7 where u.idusuario= ?1")
    void UpdateUsuario(Long idusuario, String nombre_c, String correo, String contrasena, String nocifrado, boolean estado, Date date);
    @Modifying(clearAutomatically = true)
    @Query("update Cliente  c set c.nombre=?2, c.descripcion=?3, c.logo=?4, c.url=?5, c.colorP=?6,c.colorS=?7 where c.id=?1")
    void updateCliente(Long id, String nombre, String descripcion, String logo, String url, String colorP, String colorS);
    @Modifying(clearAutomatically = true)
    @Query("update Cliente  c set c.logo=?2 where c.id=?1")
    void updateLogo(Long id, String logo);

    @Modifying(clearAutomatically = true)
    @Query("update Usuario  u set u.contrasena=?2 where u.idusuario=?1")
    void updatePassword(Long id, String contrasena);
    @Modifying
    @Query("delete from Usuario u where u.idusuario = ?1")
    void deleteByid(Long idusuario);
    //@Query(value = "select * from usuario u where u.estado=1", nativeQuery = true)
    @Query(value = "SELECT * FROM usuario u INNER join usuario_rol ur" +
            " on u.id_usuario = ur.id_usuario inner JOIN rol r on" +
            " ur.id_rol = r.id_rol WHERE " +
            "r.rol = 'ROLE_CLIENTE' and u.estado = 1", nativeQuery = true)
    List<Usuario> getActive();

    @Query(value = "SELECT * FROM usuario u INNER join usuario_rol ur" +
            " on u.id_usuario = ur.id_usuario inner JOIN rol r on" +
            " ur.id_rol = r.id_rol WHERE " +
            "r.rol = 'ROLE_ANALISTA' and u.estado = 1", nativeQuery = true)
    List<Usuario> getAnalistas();

    @Query("select u from Usuario u order by u.nombre_c desc")
    Page<Usuario> getAllPage(PageRequest pageRequest);

    /*Opción variante eliminar estado*/
    @Modifying
    @Query("update Usuario  u set u.estado=?2 where  u.idusuario=?1")
    void actualizarStatus(Long idusuario, boolean estado);

    List<Usuario> getAllByEstadoIsTrue();
    /*Metodos Rol*/
    @Query("select u from Usuario u join u.rolList r where r.idRol = ?1 or r.idRol= ?2")
    List<Usuario> getByRol(Long idRol, long idRolAdmin);
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update usuario_rol set id_rol=?2 where id_usuario=?1")
    void updateRol(Long id , Long idRol);
    @Modifying(clearAutomatically = true)
    @Query("update Usuario u set u.nombre_c=?2, u.correo = ?3, u.estado=?4 where u.idusuario=?1")
    void updateGestor(Long id, String nombre_c,String correo, boolean estado );

}
