package com.academia.controller;

import java.io.IOException;
import java.time.Period;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.academia.dto.RegistroEstudianteDTO;
import com.academia.exception.RecursoNoEncontradoException;
import com.academia.model.Curso;
import com.academia.model.Estudiante;
import com.academia.service.CursoService;
import com.academia.service.EstudianteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

	private final EstudianteService estudianteService;
	private final CursoService cursoService;

	public EstudianteController(EstudianteService estudianteService, CursoService cursoService) {
		this.estudianteService = estudianteService;
		this.cursoService = cursoService;
	}

	// Listar
	@GetMapping
	public String listar(@RequestParam(required = false) String buscar, Model model) {
		List<Estudiante> estudiantes;

		if (buscar == null || buscar.isBlank()) {
			estudiantes = estudianteService.listarTodos();
		} else {
			estudiantes = estudianteService.buscar(buscar);
		}

		model.addAttribute("estudiantes", estudiantes);
		model.addAttribute("buscar", buscar);

		return "estudiantes/listar";
	}

	// LLAMAR AL FORMULARIO PARA NUEVO ESTUDIANTE
	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("estudianteDTO", new RegistroEstudianteDTO());
		model.addAttribute("cursos", cursoService.listarTodos());
		return "estudiantes/formulario";
	}

	// LLAMAR AL FORMULARIO PARA EDITAR ESTUDIANTE
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		Estudiante estudiante = estudianteService.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado para el ID: " + id+"."));
		RegistroEstudianteDTO estudianteDTO = new RegistroEstudianteDTO();
		estudianteDTO.setId(estudiante.getId());
		estudianteDTO.setRut(estudiante.getRut());
		estudianteDTO.setNombre(estudiante.getNombre());
		estudianteDTO.setFecha_nac(estudiante.getFecha_nac());
		estudianteDTO.setGenero(estudiante.getGenero());
		estudianteDTO.setTelefono(estudiante.getTelefono());
		if (estudiante.getCurso() != null) {
			estudianteDTO.setCursoId(estudiante.getCurso().getId());
		}
		estudianteDTO.setCarrera(estudiante.getCarrera());
		estudianteDTO.setAño(estudiante.getAño());
		model.addAttribute("estudianteDTO", estudianteDTO);
		model.addAttribute("cursos", cursoService.listarTodos());
		return "estudiantes/formulario";
	}

	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute("estudianteDTO") RegistroEstudianteDTO estudianteDTO, BindingResult result,
			Model model,RedirectAttributes redirectAttributes) throws IOException {

		// VALIDACIÓN DEL FORMULARIO
		if (result.hasErrors()) {
			//Borrar
			result.getAllErrors().forEach(System.out::println);
			model.addAttribute("cursos", cursoService.listarTodos());
			return "estudiantes/formulario";
		}
		
		// BUSCAR AUTOR
				Curso curso = cursoService.buscarPorId(estudianteDTO.getCursoId()).orElseThrow(
						() -> new RecursoNoEncontradoException("Curso no encontrado para el ID: " + estudianteDTO.getCursoId()+"."));

		Estudiante estudiante;

		// CREAR NUEVO ESTUDIANTE
		if (estudianteDTO.getId() == null) {
			estudiante = new Estudiante();
			estudiante.setRut(estudianteDTO.getRut());
			estudiante.setNombre(estudianteDTO.getNombre());
			estudiante.setFecha_nac(estudianteDTO.getFecha_nac());

			//Calculamos la edad
			if (estudianteDTO.getFecha_nac() != null) {
		        int edadCalculada = Period.between(estudianteDTO.getFecha_nac(), java.time.LocalDate.now()).getYears();
		        estudiante.setEdad(edadCalculada);
		    }
			
			estudiante.setGenero(estudianteDTO.getGenero());
			estudiante.setTelefono(estudianteDTO.getTelefono());
			estudiante.setCurso(curso);
			estudiante.setCarrera(estudianteDTO.getCarrera());
			estudiante.setAño(estudianteDTO.getAño());
			estudianteService.guardar(estudiante);
			redirectAttributes.addFlashAttribute("mensaje", "Estudiante registrado correctamente.");
		}

		// EDITAR ESTUDIANTE EXISTENTE
		else {
			estudiante = estudianteService.buscarPorId(estudianteDTO.getId()).orElseThrow(
					() -> new RecursoNoEncontradoException("Estudiante no encontrado para el ID: " + estudianteDTO.getId()+"."));
			estudiante.setRut(estudianteDTO.getRut());
			estudiante.setNombre(estudianteDTO.getNombre());
			estudiante.setFecha_nac(estudianteDTO.getFecha_nac());

			//Calculamos la edad
			if (estudianteDTO.getFecha_nac() != null) {
		        int edadCalculada = Period.between(estudianteDTO.getFecha_nac(), java.time.LocalDate.now()).getYears();
		        estudiante.setEdad(edadCalculada);
		    }
						
			estudiante.setGenero(estudianteDTO.getGenero());
			estudiante.setTelefono(estudianteDTO.getTelefono());
			estudiante.setCurso(curso);
			estudiante.setCarrera(estudianteDTO.getCarrera());
			estudiante.setAño(estudianteDTO.getAño());
			estudianteService.guardar(estudiante);
			redirectAttributes.addFlashAttribute("mensaje", "Estudiante actualizado correctamente.");
		}
		
		return "redirect:/estudiantes";
	}

	@GetMapping("/ver/{id}")
	public String detalle(@PathVariable Long id, Model model) {
		Estudiante estudiante = estudianteService.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado para el ID: " + id+"."));
		model.addAttribute("estudiante", estudiante);
		return "estudiantes/detalle";
	}
	
	
	@GetMapping("/eliminar/{id}")
	public String eliminarGet(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("error","No se puede realizar el proceso de eliminación tal como lo deseas hacer.");
		return "redirect:/estudiantes";
	}

	@PostMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Estudiante estudiante = estudianteService.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado para el ID: " + id+"."));
		
		if (estudiante != null) {
			estudianteService.eliminar(id);
			redirectAttributes.addFlashAttribute("mensaje", "Estudiante eliminado correctamente.");
		}
		
		return "redirect:/estudiantes";
	}

}