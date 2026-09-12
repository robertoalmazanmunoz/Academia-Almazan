package com.academia.dto;

import jakarta.validation.constraints.NotBlank;

public class RegistroCursoDTO {
	
	private Long id;
	
	@NotBlank(message = "El nombre del curso es obligatorio")
	private String nombre;
	
	@NotBlank(message = "El semestre del curso es obligatorio")
	private String semestre;
	
	public RegistroCursoDTO() {
		
	}

	public RegistroCursoDTO(Long id, String nombre, String semestre) {
		this.id = id;
		this.nombre = nombre;
		this.semestre = semestre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	
}