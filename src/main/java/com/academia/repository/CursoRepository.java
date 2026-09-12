package com.academia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.academia.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
	
	List<Curso> findByNombreContainingIgnoreCaseOrSemestreContainingIgnoreCase(String nombre, String semestre);

}