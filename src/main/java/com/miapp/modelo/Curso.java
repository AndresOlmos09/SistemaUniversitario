/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Estudiante
 */
public class Curso {
    private String codigo;
    private String nombre;
    private int creditos;
    
    private List<Estudiante> estudiantesInscritos;
    
    //Constructor
    public Curso(String codigo, String nombre, int creditos, List<Estudiante> estudiantesInscritos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.estudiantesInscritos = new ArrayList<>();
    }
    
    //Setter y Getters

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public List<Estudiante> getEstudiantesInscritos() {
        return estudiantesInscritos;
    }

    
   public boolean agregarEstudiante(Estudiante estudiante) {
        if (estudiante != null && !estudiantesInscritos.contains(estudiante)) {
            return estudiantesInscritos.add(estudiante);
        }
        return false;
    }
    
   public boolean removerEstudiante(Estudiante estudiante) {
        return estudiantesInscritos.remove(estudiante);
    }

    @Override
    public String toString() {
        return "Curso{" + "codigo=" + codigo + ", nombre=" + nombre
                + ", creditos=" + creditos
                + ", inscritos=" + estudiantesInscritos.size() + "}";
    }
    
    
}
