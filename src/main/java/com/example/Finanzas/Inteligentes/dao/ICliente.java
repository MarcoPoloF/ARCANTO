package com.example.Finanzas.Inteligentes.dao;

import com.example.Finanzas.Inteligentes.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ICliente extends JpaRepository<Cliente,Long> {

    @Modifying
    @Query("update Cliente c set c.logo=?2 where c.id= ?1")
    void actualizaLogo(Long idCliente, String logo);

}
