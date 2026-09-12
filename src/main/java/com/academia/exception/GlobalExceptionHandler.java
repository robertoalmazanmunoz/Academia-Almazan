package com.academia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	// RECURSO NO ENCONTRADO
	@ExceptionHandler(RecursoNoEncontradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String recursoNoEncontrado(RecursoNoEncontradoException ex, Model model) {
		model.addAttribute("mensaje", ex.getMessage());
		return "404";
	}

	// URL NO EXISTENTE
	@ExceptionHandler(NoResourceFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String paginaNoEncontrada(NoResourceFoundException ex, Model model) {
		model.addAttribute("mensaje", "La página solicitada no existe.");
		return "404";
	}
}