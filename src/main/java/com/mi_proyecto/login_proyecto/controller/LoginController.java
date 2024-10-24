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

import com.mi_proyecto.login_proyecto.model.Usuario;
import com.mi_proyecto.login_proyecto.repository.UsuarioRepository;
import com.mi_proyecto.login_proyecto.services.UsuarioServices;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioServices usuarioServices;

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

    @GetMapping({ "/", "/login" })
    public String login(Model model) {
        model.addAttribute("Users", new Usuario());
        return "login";
    }

    @PostMapping({ "/", "/login" })
    public String authenticate(@RequestParam String userOrEmail, @RequestParam String password,
            RedirectAttributes redirect) {
        Usuario usuario = usuarioServices.findByUserOrEmail(userOrEmail);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return "redirect:/trabajo";
        } else {
            redirect.addFlashAttribute("error", "Usuario o contraseña incorrectos");
            return "redirect:/";
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
        Usuario user = usuarioServices.findByUserOrEmail(userOrEmail);
        if (user != null) {
            usuarioServices.olvidarContrasenna(user.getId(), newpassword);
            model.addAttribute("message", "Contraseña cambiada con éxito.");
            return "redirect:/";
        } else {
            model.addAttribute("error", "Usuario no encontrado.");
            return "/contrasenia";
        }
    }

    @GetMapping("/trabajo")
    public String trabajo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "Index";
    }

    @PostMapping("/trabajo")
    public String hacerTrabajo(@RequestParam String usuarioTb, @RequestParam String action, Model model) {
        Usuario user = usuarioServices.findByUserOrEmail(usuarioTb);
        if (user != null) {
            model.addAttribute("message", "Trabajando...");
            return "/fecha_hora";
        } else {
            model.addAttribute("error", "Usuario no encontrado.");
            return "/trabajo";
        }
    }

    @GetMapping("/fecha_hora")
    public String fecha_hora(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "fecha_hora";
    }

    @PostMapping("/fecha_hora")
    public String fechaEntradaSalida(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "fecha_hora";
    }

    @GetMapping("/reporte")
    public String reporte(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "reporte";
    }

    @GetMapping("/configuracion")
    public String configuracion() {
        return "configuracion";
    }

}
