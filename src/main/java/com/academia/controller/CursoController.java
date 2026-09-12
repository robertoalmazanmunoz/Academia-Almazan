package com.academia.controller;

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

import com.academia.dto.RegistroCursoDTO;
import com.academia.exception.RecursoNoEncontradoException;
import com.academia.model.Curso;
import com.academia.service.CursoService;
import com.academia.util.SemestreUtil;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/cursos")
public class CursoController {

	private final CursoService cursoService;

	public CursoController(CursoService cursoService) {
		this.cursoService = cursoService;
	}

	// Listar
	@GetMapping
	public String listar(@RequestParam(required = false) String buscar, Model model) {
		List<Curso> cursos;

		if (buscar == null || buscar.isBlank()) {
			cursos = cursoService.listarTodos();
		} else {
			cursos = cursoService.buscar(buscar);
		}

		model.addAttribute("cursos", cursos);
		model.addAttribute("buscar", buscar);

		return "cursos/listar";
	}

	// LLAMAR AL FORMULARIO PARA NUEVO CURSO
	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("cursoDTO", new RegistroCursoDTO());
		model.addAttribute("semestres", SemestreUtil.obtenerSemestres());
		return "cursos/formulario";
	}

	// VER DETALLE CURSO
	@GetMapping("/ver/{id}")
	public String verCurso(@PathVariable Long id, Model model) {
		Curso curso = cursoService.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado para el ID: " + id+"."));
		if (curso != null) {
			model.addAttribute("curso", curso);
			return "cursos/detalle";
		} else {
			return "redirect:/cursos";
		}
	}

	// ACTUALIZACION CURSO - LLAMADA AL FORMULARIO
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		Curso curso = cursoService.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado para el ID: " + id+"."));
		RegistroCursoDTO cursoDTO = new RegistroCursoDTO();
		cursoDTO.setId(curso.getId());
		cursoDTO.setNombre(curso.getNombre());
		cursoDTO.setSemestre(curso.getSemestre());
		model.addAttribute("cursoDTO", cursoDTO);
		model.addAttribute("semestres", SemestreUtil.obtenerSemestres());
		return "cursos/formulario";
	}

	// REGISTRO NUEVO o ACTUALIZAR CURSO - GUARDAR DATOS
	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute("cursoDTO") RegistroCursoDTO cursoDTO, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("semestres", SemestreUtil.obtenerSemestres());
			return "cursos/formulario";
		}

		Curso curso;
		if (cursoDTO.getId() == null) {
			curso = new Curso();
			curso.setNombre(cursoDTO.getNombre());
			curso.setSemestre(cursoDTO.getSemestre());
			cursoService.guardar(curso);
			redirectAttributes.addFlashAttribute("mensaje", "Curso registrado correctamente.");
		} else {
			curso = cursoService.buscarPorId(cursoDTO.getId())
					.orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado para el ID: " + cursoDTO.getId()+"."));
			curso.setNombre(cursoDTO.getNombre());
			curso.setSemestre(cursoDTO.getSemestre());
			cursoService.guardar(curso);
			redirectAttributes.addFlashAttribute("mensaje", "Curso editado correctamente.");
		}

		return "redirect:/cursos";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarGet(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("error","No se puede realizar el proceso de eliminación tal como lo deseas hacer.");
		return "redirect:/cursos";
	}
	
	@PostMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Curso curso = cursoService.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado para el ID: " + id+"."));

		if (curso.getEstudiantes().size() > 0) {
			redirectAttributes.addFlashAttribute("error","No se puede eliminar el Curso, debido a que tiene Estudiantes registrados.");
			return "redirect:/cursos";
		}
		
		if (curso != null) {
			cursoService.eliminar(id);
			redirectAttributes.addFlashAttribute("mensaje", "Curso eliminado correctamente.");
		}
		
		return "redirect:/cursos";
	}

}