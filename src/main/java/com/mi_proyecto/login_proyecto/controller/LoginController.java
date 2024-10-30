package com.mi_proyecto.login_proyecto.controller;

import com.mi_proyecto.login_proyecto.model.Accion;
import com.mi_proyecto.login_proyecto.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mi_proyecto.login_proyecto.model.Usuario;
import com.mi_proyecto.login_proyecto.repository.UsuarioRepository;
import com.mi_proyecto.login_proyecto.services.UsuarioServices;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private ActionRepository actionRepository;
    private UsuarioServices usuarioServices;

    public LoginController(UsuarioServices usuarioServices) {
        this.usuarioServices = usuarioServices;
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
            return "Index";
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
    public String hacerTrabajo(@RequestParam String Usuario, @RequestParam String Action, @RequestParam String fecha,
                               @RequestParam String hora, Model model) {
        Usuario user = usuarioRepository.findByUsername(Usuario);
        System.out.println("Usuario: " + Usuario + ", Action: " + Action + ", Fecha: " + fecha + ", Hora: " + hora);
        if (user != null && Action.isEmpty() && hora.isEmpty() && fecha.isEmpty()) {
            Accion accion = new Accion();
            accion.setUsuario(Usuario);
            accion.setFecha(fecha);
            accion.setHora(hora);
            accion.setAction(Action);
            actionRepository.save(accion);
            model.addAttribute("message", "Registro exitoso.");
            return "/";
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

    @GetMapping("/reporte")
    public String reporte(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "reporte";
    }

    @GetMapping("/configuracion")
    public String configuracion() {
        return "/contrasenia";
    }

}