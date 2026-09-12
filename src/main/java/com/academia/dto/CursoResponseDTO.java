package com.academia.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CursoResponseDTO {

    private Long id;
    private String nombre;
    private String semestre;
    private int cantidadEstudiantes;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<EstudianteResumenDTO> estudiantes;

    public CursoResponseDTO() {
    }

    public CursoResponseDTO(Long id, String nombre, String semestre, int cantidadEstudiantes, List<EstudianteResumenDTO> estudiantes) {
        this.id = id;
        this.nombre = nombre;
        this.semestre = semestre;
        this.cantidadEstudiantes = cantidadEstudiantes;
        this.estudiantes = estudiantes;
    }

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

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public int getCantidadEstudiantes() {
        return cantidadEstudiantes;
    }

    public void setCantidadEstudiantes(int cantidadEstudiantes) {
        this.cantidadEstudiantes = cantidadEstudiantes;
    }

    public List<EstudianteResumenDTO> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<EstudianteResumenDTO> estudiantes) {
        this.estudiantes = estudiantes;
    }
}