package com.example.Finanzas.Inteligentes.dao;

import com.example.Finanzas.Inteligentes.modelo.Carga;
import com.example.Finanzas.Inteligentes.utils.CargaPageable;

public interface ICargaRepository {
    CargaPageable getCargaPageable(int page, Carga filtros);
}
