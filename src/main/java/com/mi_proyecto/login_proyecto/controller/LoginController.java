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
import com.mi_proyecto.login_proyecto.services.UsuarioServices;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioServices usuarioServices;

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
        Usuario usuario = usuarioServices.findByUserOrEmail(userOrEmail);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return "redirect:/";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "/login";
        }
    }

    @GetMapping("/contrasenia")
    public String Contrasenia(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "contrasenia";
    }

    @PostMapping("/contrasenia")
    public String cambiarcontrasenia(@RequestParam String userOrEmail, @RequestParam String newpassword,
            @RequestParam String password, Model model) {
        if (!newpassword.equals(password)) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "/OlvidoContraseña";
        }
        Usuario user = usuarioServices.getUserByUsername(userOrEmail);
        if (user != null) {
            usuarioServices.olvidarContrasenna(user.getId(), newpassword);
            model.addAttribute("message", "Contraseña cambiada con éxito.");
            return "redirect:/";
        } else {
            model.addAttribute("error", "Usuario no encontrado.");
            return "/contrasenia";
        }
    }

    @GetMapping({ " ", "/", "/trabajo" })
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