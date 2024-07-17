package com.example.javafx.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "socios")
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_socio")
    public int id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "apellido")
    private String lastname;

    @Column(name = "dni")
    private String dni;
    @Column(name = "fecha_pago")
    private LocalDate fechaPago;
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVenc;

    @Column(name = "cuota_vencida")
    private boolean membershipExpired;
    public void setFechaPagoYVencimiento(){
        this.fechaPago = LocalDateTime.now().toLocalDate();
        this.fechaVenc = this.fechaPago.plusDays(30);
    }
}
