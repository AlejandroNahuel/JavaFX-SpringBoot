package com.example.javafx.dao;

import com.example.javafx.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocioDAO extends JpaRepository<Socio, Integer> {

    Socio findSocioByDni(String dni);
}
