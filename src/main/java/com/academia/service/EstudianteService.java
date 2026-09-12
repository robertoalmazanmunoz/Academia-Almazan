package com.academia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.academia.model.Estudiante;
import com.academia.repository.EstudianteRepository;

@Service
public class EstudianteService {

	private final EstudianteRepository estudianteRepository;

	public EstudianteService(EstudianteRepository estudianteRepository) {
		this.estudianteRepository = estudianteRepository;
	}

	// Listar todos los estudiantes
	public List<Estudiante> listarTodos() {
		return estudianteRepository.findAll();
	}

	// BUSCAR POR ID
	public Optional<Estudiante> buscarPorId(Long id) {
		return estudianteRepository.findById(id);
	}

	// BUSCAR POR CRITERIO PARA NOMBRE, RUT, TELÉFONO, GÉNERO, CARRERA o NOMBRE DEL CURSO
	public List<Estudiante> buscar(String criterio) {
		return estudianteRepository
					.findByNombreContainingIgnoreCaseOrRutContainingIgnoreCaseOrTelefonoContainingIgnoreCaseOrGeneroContainingIgnoreCaseOrCarreraContainingIgnoreCaseOrCursoNombreContainingIgnoreCase(
							criterio, criterio, criterio, criterio, criterio, criterio);

	}

	// GUARDAR
	public Estudiante guardar(Estudiante estudiante) {
		return estudianteRepository.save(estudiante);
	}

	// ELIMINAR
	public void eliminar(Long id) {
		estudianteRepository.deleteById(id);
	}

}