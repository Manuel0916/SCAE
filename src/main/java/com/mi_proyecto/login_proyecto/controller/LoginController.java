package com.mi_proyecto.login_proyecto.controller;

import com.mi_proyecto.login_proyecto.model.Accion;
import com.mi_proyecto.login_proyecto.repository.ActionRepository;
import com.mi_proyecto.login_proyecto.model.Reporte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mi_proyecto.login_proyecto.model.Usuario;
import com.mi_proyecto.login_proyecto.repository.ReporteRepository;
import com.mi_proyecto.login_proyecto.repository.UsuarioRepository;
import com.mi_proyecto.login_proyecto.services.UsuarioServices;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private ActionRepository actionRepository;
    private UsuarioServices usuarioServices;
    private ReporteRepository reporteRepository;

    @Autowired
    public LoginController(ActionRepository actionRepository, UsuarioRepository usuarioRepository, UsuarioServices usuarioServices, ReporteRepository reporteRepository) {
        this.actionRepository = actionRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioServices = usuarioServices;
        this.reporteRepository = reporteRepository;
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
    public String hacerTrabajo(@RequestParam String usuarioNombre, @RequestParam String accion,
                               @RequestParam String fecha, @RequestParam String hora, Model model) {
        Usuario user = usuarioServices.findByUserOrEmail(usuarioNombre);

        if (user != null && !accion.isEmpty() && !hora.isEmpty() && !fecha.isEmpty()) {
            Accion nuevaAccion = new Accion();
            nuevaAccion.setUsuario(user);
            nuevaAccion.setFecha(fecha);
            nuevaAccion.setHora(hora);
            nuevaAccion.setAction(accion);
            actionRepository.save(nuevaAccion);

            model.addAttribute("message", "Registro exitoso.");
            return "redirect:/";
        } else {
            model.addAttribute("error", "Usuario no encontrado o datos incompletos.");
            return "trabajo";
        }
    }

    
    @GetMapping("/reporte")
    public String reporte(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "reporte";
    }
    
    @PostMapping("/reporte")
    public String hacerReporte(@RequestParam String usuarioNombre, @RequestParam String reporte, Model model) {
    Usuario user = usuarioServices.findByUserOrEmail(usuarioNombre);

    if (user != null && !reporte.isEmpty()) {
        Reporte nuevoReporte = new Reporte(); 
        nuevoReporte.setUsuario(user); 
        nuevoReporte.setContenido(reporte);
        
        reporteRepository.save(nuevoReporte);

        model.addAttribute("message", "Reporte registrado exitosamente");
        return "redirect:/";
    } else {
        model.addAttribute("error", "Usuario no encontrado o datos incompletos.");
        return "reporte";
    }
    }

    

    @GetMapping("/configuracion")
    public String configuracion() {
        return "/contrasenia";
    }

}