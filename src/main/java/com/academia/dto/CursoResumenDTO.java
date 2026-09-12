package com.academia.dto;

public class CursoResumenDTO {

    private Long id;
    private String nombre;
    private String semestre;

    public CursoResumenDTO() {
    }

    public CursoResumenDTO(Long id, String nombre, String semestre) {
        this.id = id;
        this.nombre = nombre;
        this.semestre = semestre;
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
}