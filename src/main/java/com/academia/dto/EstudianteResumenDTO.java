package com.academia.dto;

import java.time.LocalDate;

public class EstudianteResumenDTO {

	private Long id;
	private String rut;
	private String nombre;
	private LocalDate fecha_nac;
	private int edad;
	private String genero;
	private String telefono;
	private String carrera;
	private Integer año;

	//Constructores
    public EstudianteResumenDTO() {
    }

    public EstudianteResumenDTO(Long id, String rut, String nombre, LocalDate fecha_nac, int edad, 
			String genero, String telefono, String carrera, Integer año) {
		this.id = id;
		this.rut = rut;
		this.nombre = nombre;
		this.fecha_nac = fecha_nac;
		this.edad = edad;
		this.genero = genero;
		this.telefono = telefono;
		this.carrera = carrera;
		this.año = año;
	}

    //Getters y Setters
  	public Long getId() {
  		return id;
  	}

  	public void setId(Long id) {
  		this.id = id;
  	}

  	public String getRut() {
  		return rut;
  	}

  	public void setRut(String rut) {
  		this.rut = rut;
  	}

  	public String getNombre() {
  		return nombre;
  	}

  	public void setNombre(String nombre) {
  		this.nombre = nombre;
  	}

  	public LocalDate getFecha_nac() {
  		return fecha_nac;
  	}

  	public void setFecha_nac(LocalDate fecha_nac) {
  		this.fecha_nac = fecha_nac;
  	}

  	public int getEdad() {
  		return edad;
  	}

  	public void setEdad(int edad) {
  		this.edad = edad;
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

  	public String getCarrera() {
  		return carrera;
  	}

  	public void setCarrera(String carrera) {
  		this.carrera = carrera;
  	}

  	public Integer getAño() {
  		return año;
  	}

  	public void setAño(Integer año) {
  		this.año = año;
  	}
}