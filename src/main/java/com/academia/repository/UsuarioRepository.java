package com.academia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.academia.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

	/**
	 * save(), findById(), findAll(), deleteById(), delete(), existsById()
	 */
	
	Optional<Usuario> findByEmail(String email);
	
}