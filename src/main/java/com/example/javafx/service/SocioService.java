package com.example.javafx.service;

import com.example.javafx.model.Socio;

import java.util.List;

public interface SocioService {

    List<Socio> getAllSocios();
    Socio getSocioById(int id);
    void addSocio(Socio socio);
    void updateSocio(int id, Socio socio);
    void deleteSocio(int id);
    Socio getSocioByDNI(String dni);

}
