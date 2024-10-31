package com.mi_proyecto.login_proyecto.services;

import com.mi_proyecto.login_proyecto.model.Reporte;
import com.mi_proyecto.login_proyecto.model.Usuario;
import com.mi_proyecto.login_proyecto.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteServices {
    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private UsuarioServices usuarioServices;

    public String registrarReporte(String usuarioNombre, String contenido) {
        Usuario user = usuarioServices.findByUserOrEmail(usuarioNombre);

        if (user != null && !contenido.isEmpty()) {
            Reporte nuevoReporte = new Reporte();
            nuevoReporte.setUsuario(user);
            nuevoReporte.setContenido(contenido);
            reporteRepository.save(nuevoReporte);
            return "Reporte registrado exitosamente";
        } else {
            return "Usuario no encontrado o datos incompletos.";
        }
    }
}