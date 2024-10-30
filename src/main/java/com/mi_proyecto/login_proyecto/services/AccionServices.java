package com.mi_proyecto.login_proyecto.services;

import com.mi_proyecto.login_proyecto.model.Accion;
import com.mi_proyecto.login_proyecto.model.Dto.AccionDto;
import com.mi_proyecto.login_proyecto.model.Usuario;
import com.mi_proyecto.login_proyecto.repository.AccionRepository;
import com.mi_proyecto.login_proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class AccionServices {
    @Autowired
    private AccionRepository accionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void saveAccion(AccionDto accionDto) {
        Accion accion = new Accion();
        Usuario user = usuarioRepository.findById(accionDto.getUsuarioId()).orElse(null);
        if (user != null) {
            accion.setUsuario(user);
            accion.setAction(accionDto.getAction());
            accion.setFecha(accionDto.getFecha());
            accion.setHora(accionDto.getHora());
            accionRepository.save(accion);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

}