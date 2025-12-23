package com.example.Finanzas.Inteligentes.dao;

import com.example.Finanzas.Inteligentes.modelo.Carga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICarga extends JpaRepository<Carga, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Carga c set c.usuario.idusuario=?2 where c.id=?1")
    void setCarga(Long id, Long idcliente);//se cambia por Usuario para relacionar
    @Modifying
    @Query("update Carga c set c.ruta=?2 where c.id=?1")
    void cargaArchivo(Long id, String archivo);

    @Query("Select c from Carga c  where c.usuario.idusuario=?1")
    List<Carga> getByUsuario(Long IdUSuario);

    @Modifying
    @Query("delete from Carga c where c.id = ?1")
    void deleteCarga(Long idcarga);

    @Modifying(clearAutomatically = true)
    @Query("update Carga c set c.nombre =?2 where c.id=?1")
    void updateCarga(Long id, String nombre);

    @Query("select c from Carga c order by c.nombre desc")
    Page<Carga> getAllPage(PageRequest pageRequest);
}
