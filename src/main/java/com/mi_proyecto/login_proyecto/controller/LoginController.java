package com.mi_proyecto.login_proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mi_proyecto.login_proyecto.modelo.Usuario;
import com.mi_proyecto.login_proyecto.repository.UsuarioRepository;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("Users", new Usuario());
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "crear_cuenta";
    }

    @PostMapping("/registro")
    public String registerUser(@ModelAttribute("Usuario") Usuario usuario, RedirectAttributes redirect,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "/registro";
        }
        usuarioRepository.save(usuario);
        redirect.addFlashAttribute("mensaje", "Usuario registrado con éxito!");
        return "redirect:/";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String userOrEmail, @RequestParam String password, Model model) {
        Usuario usuario = findByUserOrEmail(userOrEmail);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return "redirect:/";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "/login";
        }
    }

    public Usuario findByUserOrEmail(String userOrEmail) {
        Usuario user = usuarioRepository.findByUsername(userOrEmail);
        if (user == null) {
            user = usuarioRepository.findByEmail(userOrEmail);
        }
        return user;
    }

    @GetMapping("/contrasenia")
    public String Contraseña() {
        return "contrasenia";
    }

    @GetMapping({" ", "/", "/trabajo"})
    public String trabajo() {
        return "Index";
    }

    @GetMapping("/reporte")
    public String reporte() {
        return "reporte";
    }

    @GetMapping("/configuracion")
    public String configuracion() {
        return "configuracion";
    }




}
