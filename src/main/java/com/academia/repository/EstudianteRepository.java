package com.academia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.academia.model.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

	List<Estudiante> findByNombreContainingIgnoreCaseOrRutContainingIgnoreCaseOrTelefonoContainingIgnoreCaseOrGeneroContainingIgnoreCaseOrCarreraContainingIgnoreCaseOrCursoNombreContainingIgnoreCase(
			String nombre, String rut, String telefono, String genero, String carrera, String nombreCurso);

}