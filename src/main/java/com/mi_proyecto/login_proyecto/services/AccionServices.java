package com.mi_proyecto.login_proyecto.services;

import com.mi_proyecto.login_proyecto.model.Accion;
import com.mi_proyecto.login_proyecto.model.Usuario;
import com.mi_proyecto.login_proyecto.repository.AccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccionServices {
    @Autowired
    private AccionRepository accionRepository;

    @Autowired
    private UsuarioServices usuarioServices;

    public String registrarAccion(String usuarioNombre, String accion, String fecha, String hora) {
        Usuario user = usuarioServices.findByUserOrEmail(usuarioNombre);

        if (user != null && !accion.isEmpty() && !hora.isEmpty() && !fecha.isEmpty()) {
            Accion nuevaAccion = new Accion();
            nuevaAccion.setUsuario(user);
            nuevaAccion.setFecha(fecha);
            nuevaAccion.setHora(hora);
            nuevaAccion.setAction(accion);
            accionRepository.save(nuevaAccion);
            return "Registro exitoso.";
        } else {
            return "Usuario no encontrado o datos incompletos.";
        }
    }
}