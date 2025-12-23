package com.example.Finanzas.Inteligentes.dao;

import com.example.Finanzas.Inteligentes.modelo.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface IRolDao extends JpaRepository<Rol, Long> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("select r from Rol r where r.idRol=?1")
    Rol getRolById(Long idRol);
}
