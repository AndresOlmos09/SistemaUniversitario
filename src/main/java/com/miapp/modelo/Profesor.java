/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.modelo;

/**
 *
 * @author Estudiante
 */
public class Profesor extends Persona {

    private final double salarioBase;
    private int clasesImpartidas;
    private static final double BONIFICACION_POR_CLASE = 50000.0;

   public Profesor(String nombre, int id, String apellido, double salarioBase) {
        super(nombre, id, apellido);
        this.salarioBase = salarioBase;
        this.clasesImpartidas = 0;
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public double getSalarioBase() {
        return salarioBase;
    }

    public int getClasesImpartidas() {
        return clasesImpartidas;
    }
    
    public void impartirClase() {
        clasesImpartidas++;
        System.out.println("El profesor " + getNombre() + " " + getApellido()
                + " está impartiendo una clase. Total de clases: " + clasesImpartidas);
    }

    
    @Override
    public double calcularPago() {
        return salarioBase + (clasesImpartidas * BONIFICACION_POR_CLASE);
    }

    
    
   
    
   
    
}
