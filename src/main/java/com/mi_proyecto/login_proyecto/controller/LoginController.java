package com.mi_proyecto.login_proyecto.controller;

import com.mi_proyecto.login_proyecto.model.Dto.AccionDto;
import com.mi_proyecto.login_proyecto.repository.AccionRepository;
import com.mi_proyecto.login_proyecto.services.AccionServices;
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
    private AccionRepository accionRepository;
    private AccionServices accionService;
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
    public String showLoginPage(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping({ "/", "/login" })
    public String authenticate(@RequestParam String userOrEmail, @RequestParam String password,
                               RedirectAttributes redirect, BindingResult result) {
        if (result.hasErrors()) {
            return "formulario";
        }
        Usuario usuario = usuarioServices.findByUserOrEmail(userOrEmail);

        System.out.println( " Aqui estuve ... Hp ");

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
        if (user != null && !Action.isEmpty() && !hora.isEmpty() && !fecha.isEmpty()) {
            AccionDto accionDto = new AccionDto();
            accionDto.setUsuarioId(user.getId());
            accionDto.setAction(Action);
            accionDto.setFecha(fecha);
            accionDto.setHora(hora);
            accionService.saveAccion(accionDto);
            model.addAttribute("message", "Registro exitoso.");
            return "redirect:/trabajo";
        } else {
            model.addAttribute("error", "Datos incompletos o usuario no encontrado.");
            return "trabajo";
        }
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