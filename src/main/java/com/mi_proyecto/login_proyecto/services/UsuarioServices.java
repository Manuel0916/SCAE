package com.mi_proyecto.login_proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mi_proyecto.login_proyecto.modelo.Usuario;
import com.mi_proyecto.login_proyecto.repository.UsuarioRepository;

@Service
public class UsuarioServices {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findByUserOrEmail(String userOrEmail) {
        Usuario user = usuarioRepository.findByUsername(userOrEmail);
        if (user == null) {
            user = usuarioRepository.findByEmail(userOrEmail);
        }
        return user;
    }

    public Usuario getUserById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void olvidarContrasenna(Long id, String password) {
        Usuario user = getUserById(id);
        if (user != null) {
            user.setPassword(password);
            usuarioRepository.save(user);
        } else {
            System.err.println("Usuario no encontrado con ID: " + id);
        }
    }

    public Usuario getUserByUsername(String userOrEmail) {
        return findByUserOrEmail(userOrEmail);
    }

}