package com.example.Finanzas.Inteligentes.dao.impl;

import com.example.Finanzas.Inteligentes.dao.ICargaRepository;
import com.example.Finanzas.Inteligentes.modelo.Carga;
import com.example.Finanzas.Inteligentes.utils.CargaPageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class ICargaImpl implements ICargaRepository {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public CargaPageable getCargaPageable(int page, Carga filtros) {
     String builderfilter = builderfilter(filtros);
     int firstResult = page * 10;
     String stringQueryCount ="select count(c) from Carga c " + builderfilter;
     String stringQuery = "Select c from Carga c " + builderfilter;
        Query queryCount = entityManager.createQuery(stringQueryCount, Long.class);
        Query query = entityManager.createQuery(stringQuery, Carga.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(10);

        CargaPageable response = new CargaPageable();
        response.setCargaList(query.getResultList());
        response.setSize(10);
        response.setNumber(page);
        response.setTotalElements(Math.toIntExact((Long) queryCount.getResultList().get(0)));
        response.setTotalPage((response.getTotalElements() / 10) + 1);
        response.setNumberOfElements(response.getCargaList().size());
        return response;
    }

    private String builderfilter(Carga filtros) {
        String response = "c.usuario.estado = true";
        if( filtros.getNombre() != ""){
            if(response != ""){
                response = response +" AND ";
            }
            response = response +"(lower(c.nombre) like '%"+ filtros.getNombre().toLowerCase() + "%'" +
                    " OR lower(c.usuario.nombre_c) like '%"+ filtros.getNombre().toLowerCase() + "%'" +
                    " OR lower(c.usuario.cliente.nombre) like '%"+ filtros.getNombre().toLowerCase() + "%')";
        }
        if (response !=""){
            response = " WHERE " + response;
        }
        return response;
    }
}
