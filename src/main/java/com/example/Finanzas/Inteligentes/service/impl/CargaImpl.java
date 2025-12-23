package com.example.Finanzas.Inteligentes.service.impl;

import com.example.Finanzas.Inteligentes.dao.ICarga;
import com.example.Finanzas.Inteligentes.dao.ICargaRepository;
import com.example.Finanzas.Inteligentes.modelo.Carga;
import com.example.Finanzas.Inteligentes.modelo.Cliente;
import com.example.Finanzas.Inteligentes.modelo.Usuario;
import com.example.Finanzas.Inteligentes.service.CargaService;
import com.example.Finanzas.Inteligentes.utils.CargaPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargaImpl implements CargaService {
    @Autowired
    private ICarga iCarga;
    @Autowired
    private ICargaRepository iCargaRepository;
    @Transactional
    @Override
    public Carga nuevo(Carga carga) {
        Carga carga1 = new Carga();
         carga1.setNombre(carga.getNombre());
        //carga1.setRuta(carga.getRuta());
        carga1 = iCarga.save(carga1);
        iCarga.setCarga(carga1.getId(), carga.getUsuario().getIdusuario());
        return iCarga.getById(carga1.getId());
    }

    @Override
    public List<Carga> getAll() {
        return iCarga.findAll();
    }

    @Override
    public Carga getById(Long id) {
        return iCarga.getById(id);
    }

    @Override
    public List<Carga> getByUsuario(Long idUsuario) {
        return iCarga.getByUsuario(idUsuario);
    }

    @Transactional
    @Override
    public void cargaArchivo(Long id, String archivo) {
        iCarga.cargaArchivo(id, archivo);
    }

    @Override
    @Transactional
    public void deleteCarga(Long idcarga) {
        iCarga.deleteCarga(idcarga);
    }

    @Transactional
    @Override
    public Carga updateCarga(Carga carga) {
        iCarga.updateCarga(carga.getId(), carga.getNombre());
        return iCarga.getById(carga.getId());
    }

    @Override
    public Page<Carga> findAllPage(PageRequest pageRequest) {
        return iCarga.getAllPage(pageRequest);
    }

    @Override
    public CargaPageable getPage(int page, Carga filtros) {
        return iCargaRepository.getCargaPageable(page,filtros);
    }
}
