package com.academia.rest;

import java.time.Period;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.academia.dto.ApiResponse;
import com.academia.dto.CursoResumenDTO;
import com.academia.dto.RegistroEstudianteDTO;
import com.academia.dto.EstudianteResponseDTO;
import com.academia.exception.RecursoNoEncontradoException;
import com.academia.model.Curso;
import com.academia.model.Estudiante;
import com.academia.service.CursoService;
import com.academia.service.EstudianteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteRestController {

	private final EstudianteService estudianteService;
	private final CursoService cursoService;

	public EstudianteRestController(EstudianteService estudianteService, CursoService cursoService) {
		this.estudianteService = estudianteService;
		this.cursoService = cursoService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<EstudianteResponseDTO>>> listar() {
		List<EstudianteResponseDTO> estudiantes = estudianteService.listarTodos().stream().map(this::convertirDTO).toList();
		int results = estudiantes.size();
		ApiResponse<List<EstudianteResponseDTO>> respuesta = new ApiResponse<>(true, "Estudiantes obtenidos correctamente.",
				results, "1.0", estudiantes);

		return ResponseEntity.ok(respuesta);
	}
	
	
	@GetMapping("/buscar")
	public ResponseEntity<ApiResponse<List<EstudianteResponseDTO>>> buscar(
	        @RequestParam String q) {

	    List<EstudianteResponseDTO> estudiantes =
	    		estudianteService.buscar(q)
	                    .stream()
	                    .map(this::convertirDTO)
	                    .toList();

	    int results = estudiantes.size();

	    ApiResponse<List<EstudianteResponseDTO>> respuesta =
	            new ApiResponse<>(
	                    true,
	                    "Búsqueda de estudiantes realizada correctamente.",
	                    results,
	                    "1.0",
	                    estudiantes
	            );

	    return ResponseEntity.ok(respuesta);
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<EstudianteResponseDTO>> buscarPorId(@PathVariable Long id) {
		Estudiante estudiante = estudianteService.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado para el ID: " + id+"."));

		EstudianteResponseDTO estudianteDTO = convertirDTO(estudiante);

		ApiResponse<EstudianteResponseDTO> respuesta = new ApiResponse<>(true, "Estudiante encontrado correctamente.", 1, "1.0",
				estudianteDTO);

		return ResponseEntity.ok(respuesta);
	}

	
	@PostMapping
	public ResponseEntity<ApiResponse<EstudianteResponseDTO>> crear(@Valid @RequestBody RegistroEstudianteDTO estudianteDTO) {

		Curso curso = cursoService.buscarPorId(estudianteDTO.getCursoId()).orElseThrow(
				() -> new RecursoNoEncontradoException("Curso no encontrado para el ID: " + estudianteDTO.getCursoId()+"."));

		Estudiante estudiante = new Estudiante();

		estudiante.setRut(estudianteDTO.getRut());
		estudiante.setFecha_nac(estudianteDTO.getFecha_nac());

		//Calculamos la edad
		if (estudianteDTO.getFecha_nac() != null) {
	        int edadCalculada = Period.between(estudianteDTO.getFecha_nac(), java.time.LocalDate.now()).getYears();
	        estudiante.setEdad(edadCalculada);
	    }
		
		estudiante.setCurso(curso);
		estudiante.setGenero(estudianteDTO.getGenero());
		estudiante.setNombre(estudianteDTO.getNombre());
		estudiante.setCarrera(estudianteDTO.getCarrera());
		estudiante.setAño(estudianteDTO.getAño());
		estudiante.setTelefono(estudianteDTO.getTelefono());

		Estudiante nuevoEstudiante = estudianteService.guardar(estudiante);

		EstudianteResponseDTO respuestaDTO = convertirDTO(nuevoEstudiante);

		ApiResponse<EstudianteResponseDTO> respuesta = new ApiResponse<>(true, "Estudiante creado correctamente.", 1, "1.0",
				respuestaDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<EstudianteResponseDTO>> actualizar(@PathVariable Long id,
			@Valid @RequestBody RegistroEstudianteDTO estudianteDTO) {

		Estudiante estudiante = estudianteService.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado para el ID: " + id+"."));

		Curso curso = cursoService.buscarPorId(estudianteDTO.getCursoId()).orElseThrow(
				() -> new RecursoNoEncontradoException("Curso no encontrado para el ID: " + estudianteDTO.getCursoId()));

		estudiante.setRut(estudianteDTO.getRut());
		estudiante.setFecha_nac(estudianteDTO.getFecha_nac());
		
		//Calculamos la edad
				if (estudianteDTO.getFecha_nac() != null) {
			        int edadCalculada = Period.between(estudianteDTO.getFecha_nac(), java.time.LocalDate.now()).getYears();
			        estudiante.setEdad(edadCalculada);
			    }
				
		estudiante.setCurso(curso);
		estudiante.setGenero(estudianteDTO.getGenero());
		estudiante.setNombre(estudianteDTO.getNombre());
		estudiante.setCarrera(estudianteDTO.getCarrera());
		estudiante.setAño(estudianteDTO.getAño());
		estudiante.setTelefono(estudianteDTO.getTelefono());

		Estudiante actualizado = estudianteService.guardar(estudiante);

		EstudianteResponseDTO respuestaDTO = convertirDTO(actualizado);

		ApiResponse<EstudianteResponseDTO> respuesta = new ApiResponse<>(true, "Estudiante actualizado correctamente.", 1, "1.0",
				respuestaDTO);

		return ResponseEntity.ok(respuesta);
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

		Estudiante estudiante = estudianteService.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + id+"."));

		estudianteService.eliminar(estudiante.getId());

		ApiResponse<Void> respuesta = new ApiResponse<>(true, "Estudiante eliminado correctamente.", 1, "1.0", null);

		return ResponseEntity.ok(respuesta);
	}

	
	private EstudianteResponseDTO convertirDTO(Estudiante estudiante) {

		Curso curso = estudiante.getCurso();

		CursoResumenDTO cursoDTO = new CursoResumenDTO(curso.getId(), curso.getNombre(), curso.getSemestre());

		return new EstudianteResponseDTO(estudiante.getId(), estudiante.getRut(), estudiante.getNombre(), estudiante.getFecha_nac(), estudiante.getEdad(), estudiante.getGenero(), estudiante.getTelefono(), cursoDTO, estudiante.getCarrera(), estudiante.getAño());
	}

}