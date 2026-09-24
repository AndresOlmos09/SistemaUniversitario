/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.miapp.servicios;

/**
 *
 * @author taidy
 */

//Interfaz logica
public interface IBuscador {
    void buscarEstudiante(String criterio);
    void buscarEstudiantePorCarrera(String carrera);
    void buscarEstudiantePorCurso(String codigoCurso);
    void buscarEstudiantePorEstado(String estadoMatricula);
    void cargarDatos();
}
