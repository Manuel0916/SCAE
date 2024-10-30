package com.mi_proyecto.login_proyecto.model;

import jakarta.persistence.*;


@Entity
@Table(name = "Reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    
}
