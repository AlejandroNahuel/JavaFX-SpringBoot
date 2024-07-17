package com.example.javafx.service;

import com.example.javafx.dao.SocioDAO;
import com.example.javafx.model.Socio;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SocioServiceImpl implements SocioService{

    @Autowired
    private SocioDAO socioDAO;

    @Override
    public List<Socio> getAllSocios() {
        return socioDAO.findAll();
    }

    @Override
    public Socio getSocioById(int id) {
        return socioDAO.findById(id).orElse(null);
    }

    @Override
    public void addSocio(Socio socio) {

        socio.setFechaPagoYVencimiento();

        socioDAO.save(socio);
    }

    @Override
    public void updateSocio(int id, Socio socio) {
        if(socioDAO.findById(id).isPresent()){
            socio.setId(id);
        }
    }

    @Override
    public void deleteSocio(int id) {
        if(socioDAO.findById(id).isPresent())
            socioDAO.deleteById(id);
    }

    @Override
    public Socio getSocioByDNI(String dni) {

        return socioDAO.findSocioByDni(dni);
    }
}
