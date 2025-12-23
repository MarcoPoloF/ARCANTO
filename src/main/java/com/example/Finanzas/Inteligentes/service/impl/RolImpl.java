package com.example.Finanzas.Inteligentes.service.impl;

import com.example.Finanzas.Inteligentes.dao.IRolDao;
import com.example.Finanzas.Inteligentes.modelo.Rol;
import com.example.Finanzas.Inteligentes.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RolImpl implements RolService {
    @Autowired
    private IRolDao iRolDao;

    @Override
    public List<Rol> getAll() {
        return iRolDao.findAll();
    }
}
