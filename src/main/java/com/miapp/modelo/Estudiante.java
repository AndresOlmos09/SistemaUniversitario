package com.miapp.modelo;

import com.miapp.servicios.Inscribible;
import com.miapp.utilidades.EstadoMatricula;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo: representa la entidad Estudiante.
 */
public class Estudiante extends Persona implements Inscribible{  

    private static int totalEstudiantes = 0;
    public static final int PROMEDIO_MINIMO = 0;
    public static final int PROMEDIO_MAXIMO = 5;
    public static final String CARRERA_PREDETERMINADA = "Sin especificar";
    public static final int MAX_MATERIAS = 6;

    // ── Atributos de instancia ────────────────────────────────────────────────
    private String carrera;
    private double promedio;
    private EstadoMatricula estado;
    
    private List<Curso> cursosInscritos;

    // ── Constructor ───────────────────────────────────────────────────────────

    public Estudiante(String carrera, double promedio, String nombre, int id, String apellido) {
        super(nombre, id, apellido);
        this.carrera = carrera;
        this.promedio = promedio;
        this.cursosInscritos = new ArrayList<>();
        this.estado = EstadoMatricula.ACTIVO;

        
        if (promedio >= PROMEDIO_MINIMO && promedio <= PROMEDIO_MAXIMO) {
            this.promedio = promedio;
        } else {
            this.promedio = 0.0;  // Por defecto si está fuera de rango
        }
        
        // nuevo: Incrementa el contador estático de estudiantes
        totalEstudiantes++;

    }

    

    // ── Métodos estáticos (de clase) ──────────────────────────────────────────

    public static int getTotalEstudiantes() {
        return totalEstudiantes;
    }

    public static void reiniciarContador() {
        totalEstudiantes = 0;
    }

    public static int getProximoId() {  
        return totalEstudiantes + 1;
    
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public String getCarrera() { 
        return carrera; 
    }

    public double getPromedio() { 
        return promedio; 
    }

    // ── Setters ──────────────────────────────────────────────────────────────

    public void setCarrera(String carrera) { 
        this.carrera = carrera; 
    }

    /**
     Valida el promedio antes de asignarlo usando constantes finales
     * @param p promedio a validar (debe estar entre PROMEDIO_MINIMO y PROMEDIO_MAXIMO)
     */
    public void setPromedio(double p) {
        // nuevo: Uso de constantes finales para validación
        
        if (p >= PROMEDIO_MINIMO && p <= PROMEDIO_MAXIMO) {
            this.promedio = p;
        }
    }
    
    public EstadoMatricula getEstado() {
        return estado;
    }

    public void setEstado(EstadoMatricula estado) {
        this.estado = estado;
    }

    public List<Curso> getCursosInscritos() {
        return cursosInscritos;
    }

    // ── Implementación de la interfaz Inscribible ───────────────────────────

    /**
     * Inscribe al estudiante en el curso indicado, siempre que:
     *  - el curso no sea nulo,
     *  - el estudiante no esté ya inscrito en ese curso,
     *  - el estudiante no haya alcanzado el máximo de materias (MAX_MATERIAS),
     *  - el estudiante se encuentre en estado ACTIVO.
     *
     * Mantiene consistente la asociación N:M agregando también el estudiante
     * a la lista de inscritos del curso.
     *
     * @param curso curso en el que se desea inscribir al estudiante
     * @return true si la inscripción fue exitosa, false en caso contrario
     */
    @Override
    public boolean inscribir(Curso curso) {
        if (curso == null) {
            return false;
        }
        if (estado != EstadoMatricula.ACTIVO) {
            return false;
        }
        if (cursosInscritos.contains(curso)) {
            return false;
        }
        if (cursosInscritos.size() >= MAX_MATERIAS) {
            return false;
        }

        cursosInscritos.add(curso);
        curso.agregarEstudiante(this); // mantiene el otro lado de la relación N:M

        return true;
    }

    /**
     Método final: no puede ser sobrescrito por subclases
     */
    @Override
    public String toString() {
        return "ID: " + id
             + " | Nombre: " + getNombre()
             + " | Apellido: " + getApellido()  
             + " | Carrera: " + carrera
             + " | Promedio: " + String.format("%.2f", promedio)
             + " | Estado:  " + estado;
    }
    
    @Override
    public double calcularPago() {
    // Escribe aquí la lógica para calcular el pago específico del estudiante
    return 0.0; 
    }
    
    
}