package com.academia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.academia.model.Curso;
import com.academia.repository.CursoRepository;

@Service
public class CursoService {
	
	private final CursoRepository cursoRepository;

	public CursoService(CursoRepository cursoRepository) {
		this.cursoRepository = cursoRepository;
	}
	
	//Listar todos los cursos
	public List<Curso> listarTodos() {
		return cursoRepository.findAll();
	}
	
	//BUSCAR POR ID
	public Optional<Curso> buscarPorId(Long id){
		return cursoRepository.findById(id);
	}
	
	//BUSCAR POR CRITERIO PARA NOMBRE O SEMESTRE
	public List<Curso> buscar(String criterio) {
		return cursoRepository.findByNombreContainingIgnoreCaseOrSemestreContainingIgnoreCase(criterio, criterio);
	}
	
	//GUARDAR
	public Curso guardar(Curso curso) {
		return cursoRepository.save(curso);
	}
	
	
	//ELIMINAR
	public void eliminar(Long id) {
		cursoRepository.deleteById(id);
	}

}