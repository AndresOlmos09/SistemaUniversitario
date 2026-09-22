package com.miapp.modelo;

/**
 * Modelo: representa la entidad Estudiante.
 */
public final class Estudiante extends Persona {  

    private static int totalEstudiantes = 0;
    public static final int PROMEDIO_MINIMO = 0;
    public static final int PROMEDIO_MAXIMO = 5;
    public static final String CARRERA_PREDETERMINADA = "Sin especificar";

    // ── Atributos de instancia ────────────────────────────────────────────────
    private int    id;
    private String nombre;
    private String apellido;
    private String carrera;
    private double promedio;

    // ── Constructor ───────────────────────────────────────────────────────────

    public Estudiante(int id, String nombre, String apellido, String carrera, double promedio) {
        this.id       = id;
        this.nombre   = nombre;
        this.apellido = apellido;
        this.carrera  = carrera;
   
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

    public int getId() { 
        return id; 
    }

    public String getNombre() { 
        return nombre; 
    }

    public String getApellido() {
        return apellido;
    }

    public String getCarrera() { 
        return carrera; 
    }

    public double getPromedio() { 
        return promedio; 
    }

    // ── Setters ──────────────────────────────────────────────────────────────

    public void setId(int id) { 
        this.id = id; 
    }

    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public void setApellido(String apellido) { 
        this.apellido = apellido; 
    }

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

    /**
     Método final: no puede ser sobrescrito por subclases
     */
    @Override
    public final String toString() {
        return "ID: " + id
             + " | Nombre: " + nombre
             + " | Apellido: " + apellido   
             + " | Carrera: " + carrera
             + " | Promedio: " + String.format("%.2f", promedio);
    }
}