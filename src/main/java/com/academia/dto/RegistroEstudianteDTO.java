package com.academia.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.academia.util.ValidRut;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;	

public class RegistroEstudianteDTO {
	
	private Long id;
	
	@NotBlank(message = "El rut es obligatorio.")
	@ValidRut
	private String rut;
	
	@NotBlank(message = "EL nombre es obligatorio.")
	private String nombre;
	
	@NotNull(message = "La fecha de nacimiento es obligatoria.")
	@PastOrPresent(message = "La fecha de nacimiento debe ser menor o igual a la fecha actual")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fecha_nac;
	
	@NotBlank(message = "El género es obligatorio.")
	private String genero;
	
	private String telefono;
	
	@NotNull(message = "El curso es obligatorio")
	@Positive(message = "El ID del curso debe ser mayor a cero")
	private Long cursoId;
	
	@NotBlank(message = "La carrera es obligatoria.")
	@Size(max = 150, message = "Cantidad máxima de 150 caracteres." )
	private String carrera;
	
	@NotNull(message = "El año es obligatorio.")
	@Positive(message = "El año debe ser mayor que cero.")
	private int año;
	
	//Constructor
	public RegistroEstudianteDTO() {
		
	}

	//Getters y Setters
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

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public int getAño() {
		return año;
	}

	public void setAño(int año) {
		this.año = año;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public LocalDate getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(LocalDate fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Long getCursoId() {
		return cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}

}