package com.mi_proyecto.login_proyecto.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccionDto {
    private String Fecha;
    private String Hora;
    private String Action;
    private Long usuarioId;
}