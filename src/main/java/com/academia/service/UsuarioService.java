package com.academia.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.academia.model.Rol;
import com.academia.model.Usuario;
import com.academia.repository.UsuarioRepository;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,PasswordEncoder passwordEncoder) {    	
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public boolean existeUsuario(String email) {
        return usuarioRepository.existsById(email);
    }

    
    public Usuario registrarUsuario(String email,String password) {
        String passwordHasheada = passwordEncoder.encode(password);
        Usuario usuario = new Usuario(email,passwordHasheada,Rol.Usuario);
        return usuarioRepository.save(usuario);
    }
    
    public Usuario registrarUsuarioAdmin(String email,String password) {
        String passwordHasheada = passwordEncoder.encode(password);
        Usuario usuario = new Usuario(email,passwordHasheada,Rol.Administrador);
        return usuarioRepository.save(usuario);
    }
    
}