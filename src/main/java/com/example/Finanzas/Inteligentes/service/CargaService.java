package com.example.Finanzas.Inteligentes.service;

import com.example.Finanzas.Inteligentes.modelo.Carga;
import com.example.Finanzas.Inteligentes.utils.CargaPageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CargaService {
    Carga nuevo(Carga carga);
    List<Carga> getAll();
    Carga getById(Long id);
    List<Carga> getByUsuario(Long idUsuario);
    void cargaArchivo(Long id, String archivo);
    void deleteCarga(Long idcarga);
    Carga updateCarga(Carga carga);
    Page<Carga> findAllPage(PageRequest pageRequest);
    CargaPageable getPage(int page, Carga filtros);
}
