package com.example.Finanzas.Inteligentes.dao.impl;

import com.example.Finanzas.Inteligentes.dao.IUsuarioRepository;
import com.example.Finanzas.Inteligentes.modelo.Usuario;
import com.example.Finanzas.Inteligentes.utils.UsuarioPageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class IUsuarioImpl implements IUsuarioRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public UsuarioPageable getUsuarioPageable(int page, Usuario filtros) {
    String buildfilter = buildfilter(filtros);
        int firstResult =page * 10;
        String stringQueryCount ="select count(u) from Usuario u  " + buildfilter;
        String stringQuery = "SELECT u from Usuario u  " + buildfilter;
        Query queryCount = entityManager.createQuery(stringQueryCount, Long.class);
        Query query = entityManager.createQuery(stringQuery, Usuario.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(10);

        UsuarioPageable response = new UsuarioPageable();
        response.setUsuarioList(query.getResultList());
        response.setSize(10);
        response.setNumber(page);
        response.setTotalElements(Math.toIntExact((Long) queryCount.getResultList().get(0)));
        response.setTotalPage((response.getTotalElements() / 10) + 1);
        response.setNumberOfElements(response.getUsuarioList().size());
        return response;
    }

    private String buildfilter(Usuario filtros) {
        String response = "u.estado = true";
        if (filtros.getNombre_c() != "") {
            if(response != ""){
                response = response +" AND ";
            }
            response = response +"(lower(u.nombre_c) like '%" + filtros.getNombre_c().toLowerCase() +"%' OR lower(u.cliente.nombre) like '%"+filtros.getNombre_c().toLowerCase()+"%'" +
                    " OR lower(u.correo) like '%"+filtros.getNombre_c().toLowerCase()+"%')";
        }
            if(filtros.getCliente().getId() != 0 ){
            if(response != ""){
            response = response + " AND ";
            }
            response = response + "u.cliente.id='" + filtros.getCliente().getId() +"'";
            }
        if (response != "") {
            response = " WHERE " + response;
        }
            if(filtros.getCliente().getNombre() != ""){
                response = response + "ORDER BY u.cliente.nombre ASC";
            }
            return response;
    }
}
