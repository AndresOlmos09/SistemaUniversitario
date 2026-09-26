
package com.miapp.controlador;

import com.miapp.modelo.Curso;
import com.miapp.modelo.Estudiante;
import com.miapp.modelo.Profesor;
import com.miapp.servicios.IBuscador;
import com.miapp.utilidades.EstadoMatricula;
import com.miapp.vista.EstudianteView;

import java.util.ArrayList;
import java.util.List;


public class EstudianteController implements IBuscador {

    // ── Constantes finales ────────────────────────────────────────────────────
    private static final int CANTIDAD_ESTUDIANTES_INICIALES = 12;
    private static final String MENSAJE_BUSQUEDA_VACIA = "Por favor ingrese un nombre para buscar.";
    private static final String MENSAJE_BUSQUEDA_CARRERA_VACIA = "Por favor seleccione una carrera para buscar.";
    // agregar mensaje de busqueda curso y estado vacia
    private static final String MENSAJE_BUSQUEDA_ESTADO_VACIO = "Por favor seleccione un estado para buscar. ";
    private static final String MENSAJE_BUSQUEDA_CURSO_VACIO = "Por favor seleccione un curso para buscar.";
    private static final String MENSAJE_SIN_RESULTADOS = "No se encontraron estudiantes con ese criterio.";

    // ── Vista ─────────────────────────────────────────────────────────────────
    private EstudianteView vista;

    // ── Array de estudiantes (fuente de datos) ────────────────────────────────
    private Estudiante[] estudiantes;
    private List<Curso> cursos;
    private List<Profesor> profesores;

    // ── Constructor ───────────────────────────────────────────────────────────

    public EstudianteController(EstudianteView vista) {
        this.vista = vista;
        // Primero cargar datos (inicializar estudiantes[])
        cargarDatos();
        // Luego asignar controlador a la vista (ahora es seguro acceder a estudiantes[])
        this.vista.setControlador(this);
    }

    // ── Implementación de la interfaz IBuscador ───────────────────────────────

    @Override
    public void cargarDatos() {
        inicializarEstudiantes();
    }

    @Override
    public void buscarEstudiante(String criterio) {
        buscarPorCriterio(criterio);
    }

    @Override
    public void buscarEstudiantePorCarrera(String carrera) {
        buscarPorCarrera(carrera);
    }

    @Override
    public void buscarEstudiantePorCurso(String codigoCurso) {
        buscarPorCurso(codigoCurso);
    }

    @Override
    public void buscarEstudiantePorEstado(String estadoMatricula) {
        buscarPorEstado(estadoMatricula);
    }


    // ── Carga de datos iniciales ──────────────────────────────────────────────

    private void inicializarEstudiantes() {
        estudiantes = new Estudiante[CANTIDAD_ESTUDIANTES_INICIALES];

        // Reinicia el contador estático de Estudiante antes de cargar nuevos datos
        Estudiante.reiniciarContador();

        // ── Cursos de ejemplo ───────────────────────────────────────────────
        cursos = new ArrayList<>();
        cursos.add(new Curso("SIS101", "Programación Orientada a Objetos", 4));
        cursos.add(new Curso("MAT201", "Cálculo Diferencial", 3));
        cursos.add(new Curso("BDA150", "Bases de Datos", 4));

        // ── Profesores de ejemplo (usan impartirClase() y calcularPago()) ────
        profesores = new ArrayList<>();
        Profesor profesor1 = new Profesor("Laura", 1, "Gómez", 2500000.0);
        profesor1.impartirClase();
        profesores.add(profesor1);

       // ── Estudiantes de ejemplo (demuestran Inscribible y N:M) ───────────
        Estudiante e1 = new Estudiante("Ingeniería de Sistemas", 4.2, "Camila", 1, "Torres");
        Estudiante e2 = new Estudiante("Ingeniería Industrial", 3.8, "Andrés", 2, "Ramírez");
        Estudiante e3 = new Estudiante("Ingeniería de Sistemas", 4.5, "Sofía", 3, "López");

        // Inscripciones de ejemplo (relación N:M Estudiante–Curso)
        e1.inscribir(cursos.get(0));
        e1.inscribir(cursos.get(2));
        e2.inscribir(cursos.get(0));
        e2.inscribir(cursos.get(1));
        e3.inscribir(cursos.get(1));
        e3.inscribir(cursos.get(2));

        estudiantes[0] = e1;
        estudiantes[1] = e2;
        estudiantes[2] = e3;

        // Log: informa cuántos estudiantes se cargaron usando static getTotalEstudiantes()
        System.out.println("Total de estudiantes cargados: " + Estudiante.getTotalEstudiantes());
    }

    // ── Lógica de búsqueda ────────────────────────────────────────────────────

  
    private void buscarPorCriterio(String criterio) {

        // Validación básica usando constante final
        if (criterio == null || criterio.isEmpty()) {
            vista.mostrarError(MENSAJE_BUSQUEDA_VACIA);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();
        String criterioBajo = criterio.toLowerCase();

        for (Estudiante e : estudiantes) {
            // Validar que el elemento no sea null
            if (e != null && (e.getNombre().toLowerCase().contains(criterioBajo) ||
                e.getApellido().toLowerCase().contains(criterioBajo))) {
                resultados.add(e);
            }
        }

        if (resultados.isEmpty()) {
            vista.mostrarEstudiantes(new ArrayList<>()); // mostrará mensaje vacío
        } else if (resultados.size() == 1) {
            // Un solo resultado: usar vista.mostrarEstudiante(fila)
            vista.mostrarEstudiante(convertirAFila(resultados.get(0)));
        } else {
            // Varios resultados: mostrar lista completa ya convertida a filas
            vista.mostrarEstudiantes(convertirAFilas(resultados));
        }
    }

   
    private void buscarPorCarrera(String carrera) {
        // Validación básica usando constante final
        if (carrera == null || carrera.isEmpty() || carrera.equals("Seleccionar...")) {
            vista.mostrarError(MENSAJE_BUSQUEDA_CARRERA_VACIA);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();

        // Búsqueda exacta por carrera
        for (Estudiante e : estudiantes) {
            // Validar que el elemento no sea null
            if (e != null && e.getCarrera().equalsIgnoreCase(carrera)) {
                resultados.add(e);
            }
        }

        // Mostrar resultados (ya convertidos a filas, no como Estudiante)
        vista.mostrarEstudiantes(convertirAFilas(resultados));
    }

    /**
     * Busca estudiantes inscritos en un curso específico, recorriendo
     * la relación N:M Estudiante–Curso.
     */
    private void buscarPorCurso(String codigoCurso) {
        if (codigoCurso == null || codigoCurso.isEmpty()) {
            vista.mostrarError(MENSAJE_BUSQUEDA_CURSO_VACIO);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            if (e != null) {
                for (Curso c : e.getCursosInscritos()) {
                    if (c.getCodigo().equalsIgnoreCase(codigoCurso)) {
                        resultados.add(e);
                        break;
                    }
                }
            }
        }

        vista.mostrarEstudiantes(convertirAFilas(resultados));
    }

    /**
     * Busca estudiantes según su EstadoMatricula (ACTIVO, EGRESADO, RETIRADO).
     */
    private void buscarPorEstado(String estadoMatricula) {
        if (estadoMatricula == null || estadoMatricula.isEmpty()) {
            vista.mostrarError(MENSAJE_BUSQUEDA_ESTADO_VACIO);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();
        try {
            EstadoMatricula estadoBuscado = EstadoMatricula.valueOf(estadoMatricula.toUpperCase());
            for (Estudiante e : estudiantes) {
                if (e != null && e.getEstado() == estadoBuscado) {
                    resultados.add(e);
                }
            }
        } catch (IllegalArgumentException ex) {
            vista.mostrarError("Estado de matrícula no válido: " + estadoMatricula);
            return;
        }

        vista.mostrarEstudiantes(convertirAFilas(resultados));
    }

    
    private Object[] convertirAFila(Estudiante e) {
        return new Object[]{
            e.getId(),
            e.getNombre(),
            e.getApellido(),
            e.getCarrera(),
            String.format("%.2f", e.getPromedio())
        };
    }

  
    private List<Object[]> convertirAFilas(List<Estudiante> lista) {
        List<Object[]> filas = new ArrayList<>();
        for (Estudiante e : lista) {
            filas.add(convertirAFila(e));
        }
        return filas;
    }

    public Estudiante obtenerEstudiantePorId(int id) {
        for (Estudiante e : estudiantes) {
            if (e != null && e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public String[] obtenerCarrerasUnicas() {
        List<String> carreras = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            // Validar que el elemento no sea null
            if (e != null) {
                String carrera = e.getCarrera();
                if (!carreras.contains(carrera)) {
                    carreras.add(carrera);
                }
            }
        }
        return carreras.toArray(new String[0]);
    }

 
    public final int obtenerTotalEstudiantes() {
        return Estudiante.getTotalEstudiantes();
    }

   
    public boolean agregarEstudiante(String nombre, String apellido, String carrera, double promedio) {
        // Validación de datos
        if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() ||
            carrera == null || carrera.isEmpty()) {
            vista.mostrarError("Todos los campos son obligatorios.");
            return false;
        }

        // Expandir el array si es necesario antes de agregar
        if (estudiantes.length == Estudiante.getTotalEstudiantes()) {
            // El array está lleno, crear uno más grande
            Estudiante[] nuevoArray = new Estudiante[estudiantes.length + 5];
            System.arraycopy(estudiantes, 0, nuevoArray, 0, estudiantes.length);
            estudiantes = nuevoArray;
        }

        // Obtener el índice donde se guardará el nuevo estudiante
        int indiceNuevoEstudiante = Estudiante.getTotalEstudiantes();

        // Crear nuevo estudiante con ID automático basado en el contador static
        int proximoId = Estudiante.getProximoId();
        Estudiante nuevoEstudiante = new Estudiante(carrera, promedio, nombre, proximoId, apellido);

        // Agregar el nuevo estudiante en la posición correcta
        estudiantes[indiceNuevoEstudiante] = nuevoEstudiante;

        // Mostrar mensaje de éxito
        vista.mostrarMensaje("Estudiante agregado correctamente.\nTotal de estudiantes: " +
                            Estudiante.getTotalEstudiantes());

        return true;
    }
}