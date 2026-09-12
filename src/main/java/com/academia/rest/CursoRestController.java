package com.academia.rest;

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
import com.academia.dto.RegistroCursoDTO;
import com.academia.dto.CursoResponseDTO;
import com.academia.dto.EstudianteResumenDTO;
import com.academia.exception.RecursoNoEncontradoException;
import com.academia.model.Curso;
import com.academia.service.CursoService;

@RestController
@RequestMapping("/api/cursos")
public class CursoRestController {

	private final CursoService cursoService;

	public CursoRestController(CursoService cursoService) {
		this.cursoService = cursoService;
	}

	// Listar todos los cursos
	@GetMapping
	public ResponseEntity<ApiResponse<List<CursoResponseDTO>>> listar() {
		List<CursoResponseDTO> cursos = cursoService.listarTodos().stream().map(this::convertirResponseDTO).toList();
		int results = cursos.size();
		ApiResponse<List<CursoResponseDTO>> respuesta = new ApiResponse<>(true, "Cursos obtenidos correctamente.",
				results, "1.0", cursos);
		return ResponseEntity.ok(respuesta);
	}

	// Buscar cursos
	@GetMapping("/buscar")
	public ResponseEntity<ApiResponse<List<CursoResponseDTO>>> buscar(@RequestParam String q) {
		List<CursoResponseDTO> cursos = cursoService.buscar(q).stream().map(this::convertirResponseDTO).toList();
		int results = cursos.size();
		ApiResponse<List<CursoResponseDTO>> respuesta = new ApiResponse<>(true,
				"Búsqueda de cursos realizada correctamente.", results, "1.0", cursos);
		return ResponseEntity.ok(respuesta);
	}

	// Ver detalle de un curso y sus estudiantes
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<CursoResponseDTO>> obtenerCursoPorId(@PathVariable Long id) {
		Curso curso = cursoService.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado para el ID: " + id+"."));
		CursoResponseDTO cursoResponse = converirDetalleResponseDTO(curso);
		ApiResponse<CursoResponseDTO> respuesta = new ApiResponse<>(true, "Curso encontrado correctamente.", 1, "1.0",
				cursoResponse);
		return ResponseEntity.ok(respuesta);
	}

	// Guardar un nuevo curso
	@PostMapping
	public ResponseEntity<ApiResponse<CursoResponseDTO>> crear(@RequestBody RegistroCursoDTO cursoDTO) {
		Curso curso = convertidEntidad(cursoDTO);
		Curso cursoGuardado = cursoService.guardar(curso);
		CursoResponseDTO response = convertirResponseDTO(cursoGuardado);

		ApiResponse<CursoResponseDTO> respuesta = new ApiResponse<>(true, "Curso creado correctamente.", 1, "1.0",
				response);

		return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
	}

	// Editar un curso
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<CursoResponseDTO>> actualizar(@PathVariable Long id,
			@RequestBody RegistroCursoDTO cursoDTO) {

		Curso curso = cursoService.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado para el ID: " + id+"."));

		curso.setNombre(cursoDTO.getNombre());
		curso.setSemestre(cursoDTO.getSemestre());
		Curso cursoActualizado = cursoService.guardar(curso);

		CursoResponseDTO response = convertirResponseDTO(cursoActualizado);

		ApiResponse<CursoResponseDTO> respuesta = new ApiResponse<>(true, "Curso actualizado correctamente.", 1, "1.0",
				response);

		return ResponseEntity.ok(respuesta);
	}

	// Eliminar un curso (siempre que no tenga estudiantes asociados)
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
		Curso curso = cursoService.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado para el ID: " + id+"."));

		if (!curso.getEstudiantes().isEmpty()) {
			ApiResponse<Void> respuesta = new ApiResponse<>(false,
					"No se puede eliminar el curso porque tiene estudiantes asociados.", 0, "1.0", null);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
		}

		cursoService.eliminar(id);

		ApiResponse<Void> respuesta = new ApiResponse<>(true, "Curso eliminado correctamente.", 1, "1.0", null);
		return ResponseEntity.ok(respuesta);
	}
	
	

	private CursoResponseDTO convertirResponseDTO(Curso curso) {
		return new CursoResponseDTO(curso.getId(), curso.getNombre(), curso.getSemestre(), curso.getEstudiantes().size(), null);
	}

	private CursoResponseDTO converirDetalleResponseDTO(Curso curso) {
		List<EstudianteResumenDTO> estudiantes = curso.getEstudiantes().stream()
				.map(estudiante -> new EstudianteResumenDTO(estudiante.getId(), estudiante.getRut(), estudiante.getNombre(), estudiante.getFecha_nac(), estudiante.getEdad(), estudiante.getGenero(), estudiante.getTelefono(), estudiante.getCarrera(), estudiante.getAño()))
				.toList();

		return new CursoResponseDTO(curso.getId(), curso.getNombre(), curso.getSemestre(), estudiantes.size(), estudiantes);
	}

	private Curso convertidEntidad(RegistroCursoDTO cursoDTO) {
		Curso curso = new Curso();
		curso.setNombre(cursoDTO.getNombre());
		curso.setSemestre(cursoDTO.getSemestre());
		return curso;
	}

}