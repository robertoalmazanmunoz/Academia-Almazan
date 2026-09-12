package com.academia.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.academia.dto.ApiResponse;

@RestControllerAdvice(basePackages = "com.academia.rest")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

	@ExceptionHandler(RecursoNoEncontradoException.class)
	public ResponseEntity<ApiResponse<Void>> recursoNoEncontrado(RecursoNoEncontradoException ex) {

		ApiResponse<Void> respuesta = new ApiResponse<>(false, ex.getMessage(), 0, "1.0", null);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	}
}