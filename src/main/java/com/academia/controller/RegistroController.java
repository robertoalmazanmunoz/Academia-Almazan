package com.academia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.academia.dto.RegistroUsuarioDTO;
import com.academia.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
public class RegistroController {

	private final UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("registroUsuario",new RegistroUsuarioDTO());
        return "registro";
    }
    
    @GetMapping("/registro-admin")
    public String mostrarFormularioAdmin(Model model) {
        model.addAttribute("registroUsuario",new RegistroUsuarioDTO());
        return "registro-admin";
    }

    @PostMapping("/registro")
    public String registrarUsuario(
    		@Valid @ModelAttribute RegistroUsuarioDTO registroUsuario,
            BindingResult result,
            Model model) {
        // Validaciones de Bean Validation
        if (result.hasErrors()) {
            return "registro";
        }

        // Comprobar que las contraseñas coincidan
        if (!registroUsuario.getPassword()
                .equals(registroUsuario.getConfirmPassword())) {
            model.addAttribute("error","Las contraseñas no coinciden.");
            return "registro";
        }

        // Comprobar si el usuario ya existe
        if (usuarioService.existeUsuario(registroUsuario.getEmail())) {
            model.addAttribute("error","Ya existe un usuario registrado con ese email.");
            return "registro";
        }

        // Registrar usuario
        usuarioService.registrarUsuario(registroUsuario.getEmail(),registroUsuario.getPassword());
        return "redirect:/login?registro=exitoso";
    }
    
    @PostMapping("/registro-admin")
    public String registrarUsuarioAdmin(
    		@Valid @ModelAttribute RegistroUsuarioDTO registroUsuario,
            BindingResult result,
            Model model) {
        // Validaciones de Bean Validation
        if (result.hasErrors()) {
            return "registro-admin";
        }

        // Comprobar que las contraseñas coincidan
        if (!registroUsuario.getPassword()
                .equals(registroUsuario.getConfirmPassword())) {
            model.addAttribute("error","Las contraseñas no coinciden.");
            return "registro-admin";
        }

        // Comprobar si el usuario ya existe
        if (usuarioService.existeUsuario(registroUsuario.getEmail())) {
            model.addAttribute("error","Ya existe un usuario registrado con ese email.");
            return "registro-admin";
        }

        // Registrar usuario
        usuarioService.registrarUsuarioAdmin(registroUsuario.getEmail(),registroUsuario.getPassword());
        return "redirect:/login?registro=exitoso";
    }
}