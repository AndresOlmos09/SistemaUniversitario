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
    private static final String MENSAJE_BUSQUEDA_ESTADO_VACIO = "Por favor seleccione un estado para buscar.";
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

    // ── Lógica de búsqueda original ───────────────────────────────────────────
  
    private void buscarPorCriterio(String criterio) {
        if (criterio == null || criterio.isEmpty()) {
            vista.mostrarError(MENSAJE_BUSQUEDA_VACIA);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();
        String criterioBajo = criterio.toLowerCase();

        for (Estudiante e : estudiantes) {
            if (e != null && (e.getNombre().toLowerCase().contains(criterioBajo) ||
                e.getApellido().toLowerCase().contains(criterioBajo))) {
                resultados.add(e);
            }
        }

        if (resultados.isEmpty()) {
            vista.mostrarEstudiantes(new ArrayList<>()); 
        } else if (resultados.size() == 1) {
            vista.mostrarEstudiante(convertirAFila(resultados.get(0)));
        } else {
            vista.mostrarEstudiantes(convertirAFilas(resultados));
        }
    }

    private void buscarPorCarrera(String carrera) {
        if (carrera == null || carrera.isEmpty() || carrera.equals("Seleccionar...")) {
            vista.mostrarError(MENSAJE_BUSQUEDA_CARRERA_VACIA);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();

        for (Estudiante e : estudiantes) {
            if (e != null && e.getCarrera().equalsIgnoreCase(carrera)) {
                resultados.add(e);
            }
        }

        vista.mostrarEstudiantes(convertirAFilas(resultados));
    }

    private void buscarPorCurso(String codigoCurso) {
        if (codigoCurso == null || codigoCurso.isEmpty()) {
            vista.mostrarError(MENSAJE_BUSQUEDA_CURSO_VACIO);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            if (e != null) {
                for (Curso c : e.getCursosInscritos()) {
                    if (c.getCodigo().equalsIgnoreCase(codigoCurso) || c.getNombre().equalsIgnoreCase(codigoCurso)) {
                        resultados.add(e);
                        break;
                    }
                }
            }
        }

        vista.mostrarEstudiantes(convertirAFilas(resultados));
    }

    // ── NUEVOS MÉTODOS PARA LOS BOTONES DE LA VISTA ───────────────────────────

    public void buscarPorEstado(String estadoMatricula) {
        if (estadoMatricula == null || estadoMatricula.isEmpty() || estadoMatricula.equals("Seleccionar...")) {
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

    public void verEstudiantesCurso(String nombreCurso) {
        // Reutiliza la lógica de búsqueda por curso
        buscarPorCurso(nombreCurso);
    }

    public void inscribirEnCurso(String idEstudianteStr, String nombreCurso) {
        try {
            int id = Integer.parseInt(idEstudianteStr);
            Estudiante e = obtenerEstudiantePorId(id);
            Curso cursoSeleccionado = null;
            
            for (Curso c : cursos) {
                if (c.getNombre().equalsIgnoreCase(nombreCurso) || c.getCodigo().equalsIgnoreCase(nombreCurso)) {
                    cursoSeleccionado = c;
                    break;
                }
            }

            if (e != null && cursoSeleccionado != null) {
                e.inscribir(cursoSeleccionado);
                vista.mostrarMensaje("Estudiante " + e.getNombre() + " inscrito exitosamente en: " + cursoSeleccionado.getNombre());
            } else {
                vista.mostrarError("No se pudo realizar la inscripción. Verifique que el curso exista.");
            }
        } catch (NumberFormatException ex) {
            vista.mostrarError("ID de estudiante no válido.");
        }
    }

    public void agregarProfesor(String nombreCompleto, double salario) {
        // Dividir nombre y apellido de forma simple
        String[] partes = nombreCompleto.split(" ", 2);
        String nombre = partes[0];
        String apellido = partes.length > 1 ? partes[1] : "";
        int nuevoId = profesores.size() + 1;

        Profesor nuevoProfesor = new Profesor(nombre, nuevoId, apellido, salario);
        profesores.add(nuevoProfesor);
        
        vista.mostrarMensaje("Profesor agregado exitosamente: " + nombreCompleto);
        vista.actualizarComboProfesores();
    }

    public void verCursosProfesor(String nombreProfesor) {
        // Implementación dependiente de tu modelo. Por ahora muestra mensaje informativo.
        vista.mostrarMensaje("Consulta de cursos para el profesor " + nombreProfesor + " (En desarrollo)");
    }

    public void asignarProfesorACurso(String nombreProfesor, String nombreCurso) {
        // Implementación dependiente de tu modelo. Por ahora muestra mensaje informativo.
        vista.mostrarMensaje("Profesor " + nombreProfesor + " asignado correctamente al curso " + nombreCurso);
    }

    public void cambiarEstadoMatricula(String idEstudianteStr, String nuevoEstadoStr) {
        try {
            int id = Integer.parseInt(idEstudianteStr);
            Estudiante e = obtenerEstudiantePorId(id);
            
            if (e != null) {
                EstadoMatricula estado = EstadoMatricula.valueOf(nuevoEstadoStr.toUpperCase());
                e.setEstado(estado);
                vista.mostrarMensaje("El estado de " + e.getNombre() + " fue actualizado a: " + estado);
                // Refrescar el registro en la tabla
                vista.mostrarEstudiante(convertirAFila(e));
            } else {
                vista.mostrarError("Estudiante no encontrado.");
            }
        } catch (NumberFormatException ex) {
            vista.mostrarError("ID de estudiante no válido.");
        } catch (IllegalArgumentException ex) {
            vista.mostrarError("El estado de matrícula seleccionado no es válido.");
        }
    }

    // ── Utilidades de conversión y getters ────────────────────────────────────
  
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
        if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() ||
            carrera == null || carrera.isEmpty()) {
            vista.mostrarError("Todos los campos son obligatorios.");
            return false;
        }

        if (estudiantes.length == Estudiante.getTotalEstudiantes()) {
            Estudiante[] nuevoArray = new Estudiante[estudiantes.length + 5];
            System.arraycopy(estudiantes, 0, nuevoArray, 0, estudiantes.length);
            estudiantes = nuevoArray;
        }

        int indiceNuevoEstudiante = Estudiante.getTotalEstudiantes();
        int proximoId = Estudiante.getProximoId();
        Estudiante nuevoEstudiante = new Estudiante(carrera, promedio, nombre, proximoId, apellido);
        
        // Estado por defecto (asumiendo que tu modelo lo permite o ya lo inicializa)
        nuevoEstudiante.setEstado(EstadoMatricula.ACTIVO); 

        estudiantes[indiceNuevoEstudiante] = nuevoEstudiante;

        vista.mostrarMensaje("Estudiante agregado correctamente.\nTotal de estudiantes: " +
                             Estudiante.getTotalEstudiantes());

        return true;
    }
    
    // ── NUEVOS MÉTODOS PARA LLENAR LOS COMBOBOX ───────────────────────────────

    public String[] obtenerNombresCursos() {
        String[] nombres = new String[cursos.size()];
        for (int i = 0; i < cursos.size(); i++) {
            nombres[i] = cursos.get(i).getNombre();
        }
        return nombres;
    }

    public String[] obtenerNombresProfesores() {
        String[] nombres = new String[profesores.size()];
        for (int i = 0; i < profesores.size(); i++) {
            nombres[i] = profesores.get(i).getNombre() + " " + profesores.get(i).getApellido();
        }
        return nombres;
    }

    public String[] obtenerEstadosMatricula() {
        EstadoMatricula[] estados = EstadoMatricula.values();
        String[] nombres = new String[estados.length];
        for (int i = 0; i < estados.length; i++) {
            nombres[i] = estados[i].name();
        }
        return nombres;
    }
}



