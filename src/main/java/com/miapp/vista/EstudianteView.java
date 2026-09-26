package com.miapp.vista;

import com.miapp.controlador.EstudianteController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class EstudianteView extends JFrame {

    // ── Constantes finales para dimensiones ────────────────────────────────────
    private static final int ANCHO_VENTANA = 1300;
    private static final int ALTO_VENTANA = 900;
    private static final int ANCHO_CAMPO_BUSQUEDA = 18;
    private static final int ANCHO_CAMPO_AGREGAR = 12;
    private static final int ALTO_FILA_TABLA = 24;

    // ── Constantes finales para textos ─────────────────────────────────────────
    private static final String TITULO_VENTANA = "Gestión de Estudiantes — MVC (Búsqueda + Agregar + Cursos + Profesores )";
    private static final String TITULO_PANEL_BUSQUEDA = "Buscar estudiante por nombre";
    private static final String TITULO_PANEL_CARRERA = "Buscar por carrera";
    private static final String TITULO_PANEL_AGREGAR = "Agregar nuevo estudiante";
    private static final String TITULO_PANEL_RESULTADOS = "Resultados";
    private static final String LABEL_NOMBRE = "Nombre:";
    private static final String LABEL_APELLIDO = "Apellido:";
    private static final String LABEL_CARRERA = "Carrera:";
    private static final String LABEL_PROMEDIO = "Promedio:";
    private static final String BOTON_BUSCAR = "Buscar";
    private static final String BOTON_BUSCAR_CARRERA = "Buscar por Carrera";
    private static final String BOTON_LIMPIAR = "Limpiar";
    private static final String BOTON_AGREGAR = "Agregar Estudiante";
    private static final String OPCION_SELECCIONAR = "Seleccionar...";
    private static final String MENSAJE_INICIAL = "Ingrese un nombre o seleccione una carrera y presione Buscar.";
    private static final String MENSAJE_ENCONTRADO_UNO = "Se encontró 1 estudiante.";
    private static final String MENSAJE_ENCONTRADOS_VARIOS = "Se encontraron {0} estudiante(s).";
    private static final String MENSAJE_SIN_RESULTADOS = "No se encontraron estudiantes con ese criterio.";

    // ── Constantes finales para colores ────────────────────────────────────────
    private static final Color COLOR_BOTON_FONDO = new Color(59, 139, 212);
    private static final Color COLOR_BOTON_CARRERA = new Color(76, 175, 80);
    private static final Color COLOR_BOTON_LIMPIAR = new Color(244, 67, 54);
    private static final Color COLOR_BOTON_AGREGAR = new Color(103, 58, 183);
    private static final Color COLOR_BOTON_TEXTO = Color.WHITE;
    private static final Color COLOR_ESTADO_TEXTO = Color.GRAY;

    // ── Columnas de la tabla (constante final) ─────────────────────────────────
    private static final String[] COLUMNAS_TABLA = {"ID", "Nombre", "Apellido", "Carrera", "Promedio"};
    private static final int INDICE_PROMEDIO = 4;

    // ── Componentes UI - Búsqueda por nombre ────────────────────────────────────
    private JTextField             txtNombre;
    private JButton                btnBuscar;

    // ── Componentes UI - Búsqueda por carrera ──────────────────────────────────
    private JComboBox<String>      cmbCarrera;
    private JButton                btnBuscarCarrera;
    private JButton                btnLimpiar;

    // ── Componentes UI - Agregar estudiante ────────────────────────────────────
    private JTextField             txtAgregarNombre;
    private JTextField             txtAgregarApellido;
    private JComboBox<String>      cmbAgregarCarrera;
    private JSpinner               spinPromedio;
    private JButton                btnAgregar;

    // ── Componentes UI - Resultados y Estado ────────────────────────────────────
    private JTable                 tblResultados;
    private DefaultTableModel      modeloTabla;
    private JLabel                 lblEstado;
    private JLabel                 lblTotalEstudiantes;
    
    // ── Componentes UI - Cursos ─────────────────────────────────────────────────
    private JComboBox<String> cmbCurso;
    private JButton btnVerEstudiantesCurso;
    private JButton btnInscribirCurso;

    // ── Componentes UI - Profesores ─────────────────────────────────────────────
    private JTextField txtProfesorNombre;
    private JSpinner spinSalarioBase;
    private JButton btnAgregarProfesor;
    private JComboBox<String> cmbProfesor;
    private JButton btnVerCursosProfesor;
    private JComboBox<String> cmbCursoAsignar;
    private JButton btnAsignarACurso;

    // ── Componentes UI - Estado de Matrícula ────────────────────────────────────
    private JComboBox<String> cmbNuevoEstado;
    private JButton btnBuscarPorEstado;
    private JButton btnCambiarEstado;

    // ── Controlador ───────────────────────────────────────────────────────────
    private EstudianteController controlador;

    // ── Constructor ───────────────────────────────────────────────────────────

    public EstudianteView() {
        initComponentes();
        initEventos();
    }

    // ── Inicialización de componentes ─────────────────────────────────────────

   
    private void initComponentes() {
        setTitle(TITULO_VENTANA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ────────────────────────────────────────────────────────────────────────
        // PANEL SUPERIOR: Búsqueda y Agregar (con GridLayout)
        // ────────────────────────────────────────────────────────────────────────

        // Panel búsqueda por nombre (Fila 1)
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_BUSQUEDA));

        JLabel lblNombre = new JLabel(LABEL_NOMBRE);
        txtNombre = new JTextField(ANCHO_CAMPO_BUSQUEDA);
        btnBuscar = new JButton(BOTON_BUSCAR);
        btnBuscar.setBackground(COLOR_BOTON_FONDO);
        btnBuscar.setForeground(COLOR_BOTON_TEXTO);
        btnBuscar.setFocusPainted(false);

        panelBusqueda.add(lblNombre);
        panelBusqueda.add(txtNombre);
        panelBusqueda.add(btnBuscar);

        // Panel búsqueda por carrera (Fila 2)
        JPanel panelCarrera = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelCarrera.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_CARRERA));

        JLabel lblCarrera = new JLabel(LABEL_CARRERA);
        cmbCarrera = new JComboBox<>();
        cmbCarrera.addItem(OPCION_SELECCIONAR);
        // Se carga después, cuando el controlador esté disponible

        btnBuscarCarrera = new JButton(BOTON_BUSCAR_CARRERA);
        btnBuscarCarrera.setBackground(COLOR_BOTON_CARRERA);
        btnBuscarCarrera.setForeground(COLOR_BOTON_TEXTO);
        btnBuscarCarrera.setFocusPainted(false);

        btnLimpiar = new JButton(BOTON_LIMPIAR);
        btnLimpiar.setBackground(COLOR_BOTON_LIMPIAR);
        btnLimpiar.setForeground(COLOR_BOTON_TEXTO);
        btnLimpiar.setFocusPainted(false);

        panelCarrera.add(lblCarrera);
        panelCarrera.add(cmbCarrera);
        panelCarrera.add(btnBuscarCarrera);
        panelCarrera.add(btnLimpiar);

        // Panel agregar estudiante (Fila 3)
        JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelAgregar.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_AGREGAR));

        JLabel lblAgregarNombre = new JLabel(LABEL_NOMBRE);
        txtAgregarNombre = new JTextField(ANCHO_CAMPO_AGREGAR);

        JLabel lblAgregarApellido = new JLabel(LABEL_APELLIDO);
        txtAgregarApellido = new JTextField(ANCHO_CAMPO_AGREGAR);

        JLabel lblAgregarCarrera = new JLabel(LABEL_CARRERA);
        cmbAgregarCarrera = new JComboBox<>();
        cmbAgregarCarrera.addItem(OPCION_SELECCIONAR);
        // Se carga después, cuando el controlador esté disponible

        JLabel lblAgregarPromedio = new JLabel(LABEL_PROMEDIO);
        spinPromedio = new JSpinner(new SpinnerNumberModel(3.0, 0.0, 5.0, 0.1));
        spinPromedio.setPreferredSize(new Dimension(60, 25));

        btnAgregar = new JButton(BOTON_AGREGAR);
        btnAgregar.setBackground(COLOR_BOTON_AGREGAR);
        btnAgregar.setForeground(COLOR_BOTON_TEXTO);
        btnAgregar.setFocusPainted(false);

        panelAgregar.add(lblAgregarNombre);
        panelAgregar.add(txtAgregarNombre);
        panelAgregar.add(lblAgregarApellido);
        panelAgregar.add(txtAgregarApellido);
        panelAgregar.add(lblAgregarCarrera);
        panelAgregar.add(cmbAgregarCarrera);
        panelAgregar.add(lblAgregarPromedio);
        panelAgregar.add(spinPromedio);
        panelAgregar.add(btnAgregar);
        
        // ────────────────────────────────────────────────────────────────────────
        // Panel Cursos: inscripción y consulta (Fila 4)
        // ────────────────────────────────────────────────────────────────────────
        JPanel panelCursos = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelCursos.setBorder(BorderFactory.createTitledBorder("Cursos: inscripción y consulta"));
        
        JLabel lblCurso = new JLabel("Curso:");
        cmbCurso = new JComboBox<>();
        cmbCurso.addItem(OPCION_SELECCIONAR);
        
        btnVerEstudiantesCurso = new JButton("Ver estudiantes del curso");
        btnVerEstudiantesCurso.setBackground(new Color(0, 150, 136)); 
        btnVerEstudiantesCurso.setForeground(Color.WHITE);
        
        btnInscribirCurso = new JButton("Inscribir en curso");
        btnInscribirCurso.setBackground(new Color(255, 152, 0)); 
        btnInscribirCurso.setForeground(Color.WHITE);
        
        JLabel lblInstruccionInscribir = new JLabel("(primero busque y seleccione un estudiante en la tabla)");
        lblInstruccionInscribir.setForeground(Color.GRAY);
        
        JLabel lblProfesorAsignado = new JLabel("Profesor asignado: (ninguno)");
        lblProfesorAsignado.setForeground(Color.BLUE);
        lblProfesorAsignado.setFont(lblProfesorAsignado.getFont().deriveFont(Font.BOLD));

        panelCursos.add(lblCurso);
        panelCursos.add(cmbCurso);
        panelCursos.add(btnVerEstudiantesCurso);
        panelCursos.add(btnInscribirCurso);
        panelCursos.add(lblInstruccionInscribir);
        panelCursos.add(lblProfesorAsignado);

        // ────────────────────────────────────────────────────────────────────────
        // Panel Profesores: agregar y asignar a curso (Fila 5)
        // ────────────────────────────────────────────────────────────────────────
        JPanel panelProfesores = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelProfesores.setBorder(BorderFactory.createTitledBorder("Profesores: agregar y asignar a curso"));
        
        JLabel lblProfNombre = new JLabel("Nombre:");
        txtProfesorNombre = new JTextField(12);
        
        JLabel lblSalario = new JLabel("Salario base:");
        spinSalarioBase = new JSpinner(new SpinnerNumberModel(3000000, 0, 100000000, 100000));
        
        btnAgregarProfesor = new JButton("Agregar Profesor");
        btnAgregarProfesor.setBackground(new Color(63, 81, 181)); 
        btnAgregarProfesor.setForeground(Color.WHITE);
        
        JLabel lblProfesor = new JLabel("Profesor:");
        cmbProfesor = new JComboBox<>();
        cmbProfesor.addItem(OPCION_SELECCIONAR);
        
        btnVerCursosProfesor = new JButton("Ver cursos del profesor");
        btnVerCursosProfesor.setBackground(new Color(0, 150, 136)); 
        btnVerCursosProfesor.setForeground(Color.WHITE);
        
        JLabel lblCursoAsignar = new JLabel("Curso a asignar:");
        cmbCursoAsignar = new JComboBox<>();
        cmbCursoAsignar.addItem(OPCION_SELECCIONAR);
        
        btnAsignarACurso = new JButton("Asignar a curso");
        btnAsignarACurso.setBackground(new Color(63, 81, 181));
        btnAsignarACurso.setForeground(Color.WHITE);

        panelProfesores.add(lblProfNombre);
        panelProfesores.add(txtProfesorNombre);
        panelProfesores.add(lblSalario);
        panelProfesores.add(spinSalarioBase);
        panelProfesores.add(btnAgregarProfesor);
        panelProfesores.add(lblProfesor);
        panelProfesores.add(cmbProfesor);
        panelProfesores.add(btnVerCursosProfesor);
        panelProfesores.add(lblCursoAsignar);
        panelProfesores.add(cmbCursoAsignar);
        panelProfesores.add(btnAsignarACurso);

        // ────────────────────────────────────────────────────────────────────────
        // Panel Estado de matrícula (Fila 6)
        // ────────────────────────────────────────────────────────────────────────
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelEstado.setBorder(BorderFactory.createTitledBorder("Estado de matrícula: buscar y cambiar"));
        
        JLabel lblNuevoEstado = new JLabel("Nuevo estado:");
        cmbNuevoEstado = new JComboBox<>();
        cmbNuevoEstado.addItem(OPCION_SELECCIONAR);
        
        btnBuscarPorEstado = new JButton("Buscar por estado");
        btnBuscarPorEstado.setBackground(new Color(76, 175, 80)); 
        btnBuscarPorEstado.setForeground(Color.WHITE);
        
        btnCambiarEstado = new JButton("Cambiar estado");
        btnCambiarEstado.setBackground(new Color(0, 150, 136));
        btnCambiarEstado.setForeground(Color.WHITE);
        
        JLabel lblInstruccionEstado = new JLabel("(\"Cambiar estado\" requiere seleccionar un estudiante en la tabla)");
        lblInstruccionEstado.setForeground(Color.GRAY);

        panelEstado.add(lblNuevoEstado);
        panelEstado.add(cmbNuevoEstado);
        panelEstado.add(btnBuscarPorEstado);
        panelEstado.add(btnCambiarEstado);
        panelEstado.add(lblInstruccionEstado);
        
        // Panel superior con GridLayout (6 filas, 1 columna)
        JPanel panelSuperior = new JPanel(new GridLayout(6, 1, 5, 5));
        panelSuperior.add(panelBusqueda);
        panelSuperior.add(panelCarrera);
        panelSuperior.add(panelAgregar);
        panelSuperior.add(panelCursos);       
        panelSuperior.add(panelProfesores);  
        panelSuperior.add(panelEstado);
        

        // ────────────────────────────────────────────────────────────────────────
        // PANEL CENTRAL: Tabla de resultados
        // ────────────────────────────────────────────────────────────────────────

        modeloTabla = new DefaultTableModel(COLUMNAS_TABLA, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblResultados = new JTable(modeloTabla);
        tblResultados.setRowHeight(ALTO_FILA_TABLA);
        tblResultados.getTableHeader().setReorderingAllowed(false);
        tblResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tblResultados);
        scroll.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_RESULTADOS));

        // ────────────────────────────────────────────────────────────────────────
        // PANEL INFERIOR: Estado y Total de estudiantes
        // ────────────────────────────────────────────────────────────────────────

        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));

        lblEstado = new JLabel(MENSAJE_INICIAL);
        lblEstado.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        lblEstado.setForeground(COLOR_ESTADO_TEXTO);

        lblTotalEstudiantes = new JLabel();
        lblTotalEstudiantes.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        lblTotalEstudiantes.setForeground(Color.BLUE);
        actualizarTotalEstudiantes();

        panelInferior.add(lblEstado, BorderLayout.WEST);
        panelInferior.add(lblTotalEstudiantes, BorderLayout.EAST);

        // ────────────────────────────────────────────────────────────────────────
        // Agregar todo al JFrame
        // ────────────────────────────────────────────────────────────────────────

        add(panelSuperior,    BorderLayout.NORTH);
        add(scroll,           BorderLayout.CENTER);
        add(panelInferior,    BorderLayout.SOUTH);
    }

    // ── Métodos de inicialización ─────────────────────────────────────────────

    /**
     * Carga las carreras disponibles desde el controlador al combo de búsqueda.
     */
    private void cargarCarreras() {
        if (controlador != null) {
            String[] carreras = controlador.obtenerCarrerasUnicas();
            for (String carrera : carreras) {
                cmbCarrera.addItem(carrera);
            }
        }
    }

    /**
     * Carga las carreras disponibles desde el controlador al combo de agregar.
     */
    private void cargarCarrerasAgregar() {
        if (controlador != null) {
            String[] carreras = controlador.obtenerCarrerasUnicas();
            for (String carrera : carreras) {
                cmbAgregarCarrera.addItem(carrera);
            }
        }
    }
    
    private void cargarCombosExtra() {
        if (controlador != null) {
            // Llenar Cursos
            for (String curso : controlador.obtenerNombresCursos()) {
                cmbCurso.addItem(curso);
                cmbCursoAsignar.addItem(curso);
            }
            
            // Llenar Estados de matrícula
            for (String estado : controlador.obtenerEstadosMatricula()) {
                cmbNuevoEstado.addItem(estado);
            }
            
            // Llenar Profesores (usa el método que también sirve para actualizar)
            actualizarComboProfesores();
        }
    }

    public void actualizarComboProfesores() {
        if (controlador != null) {
            cmbProfesor.removeAllItems();
            cmbProfesor.addItem(OPCION_SELECCIONAR);
            for (String prof : controlador.obtenerNombresProfesores()) {
                cmbProfesor.addItem(prof);
            }
        }
    }

    // ── Eventos ───────────────────────────────────────────────────────────────

    /**
     * Método que encapsula la inicialización de eventos.
     */
    private void initEventos() {
        // Evento: buscar por nombre
        btnBuscar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                controlador.buscarEstudiante(txtNombre.getText().trim());
            }
        });

        txtNombre.addActionListener((ActionEvent e) -> btnBuscar.doClick());

        // Evento: buscar por carrera
        btnBuscarCarrera.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String carriSelected = (String) cmbCarrera.getSelectedItem();
                if (carriSelected != null && !carriSelected.equals(OPCION_SELECCIONAR)) {
                    controlador.buscarEstudiantePorCarrera(carriSelected);
                } else {
                    mostrarError("Seleccione una carrera válida.");
                }
            }
        });

        // Evento: limpiar búsqueda
        btnLimpiar.addActionListener((ActionEvent e) -> {
            limpiarBusqueda();
        });

        // Evento: agregar nuevo estudiante
        btnAgregar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String nombre = txtAgregarNombre.getText().trim();
                String apellido = txtAgregarApellido.getText().trim();
                String carrera = (String) cmbAgregarCarrera.getSelectedItem();
                double promedio = (double) spinPromedio.getValue();

                if (controlador.agregarEstudiante(nombre, apellido, carrera, promedio)) {
                    // Limpiar formulario
                    txtAgregarNombre.setText("");
                    txtAgregarApellido.setText("");
                    cmbAgregarCarrera.setSelectedIndex(0);
                    spinPromedio.setValue(3.0);
                    actualizarTotalEstudiantes();
                }
            }
        });
        
        btnAgregarProfesor.addActionListener(e -> {
            if (controlador != null && !txtProfesorNombre.getText().trim().isEmpty()) {
                String nombre = txtProfesorNombre.getText().trim();
                double salario = ((Number) spinSalarioBase.getValue()).doubleValue();
                
                
                controlador.agregarProfesor(nombre, salario);
  
                txtProfesorNombre.setText(""); 
            } else {
                mostrarError("Por favor ingrese el nombre del profesor.");
            }
        });
    }

    public void mostrarEstudiante(Object[] fila) {
        limpiarTabla();
        modeloTabla.addRow(fila);
        setEstado(MENSAJE_ENCONTRADO_UNO);
    }

    public void mostrarEstudiantes(List<Object[]> filas) {
        limpiarTabla();
        if (filas == null || filas.isEmpty()) {
            setEstado(MENSAJE_SIN_RESULTADOS);
            return;
        }
        for (Object[] fila : filas) {
            modeloTabla.addRow(fila);
        }
        setEstado(String.format(MENSAJE_ENCONTRADOS_VARIOS, filas.size()));
    }

    /**
     * Muestra un mensaje de error en la barra de estado.
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        setEstado("Error: " + mensaje);
    }

    /**
     * Muestra un mensaje de información/éxito en la barra de estado.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
        setEstado(mensaje);
    }

    /**
     * Devuelve el texto ingresado en el campo de nombre.
     */
    public String getNombreBuscado() {
        return txtNombre.getText().trim();
    }

   
    public void setControlador(EstudianteController controlador) {
        this.controlador = controlador;
        cargarCarreras();
        cargarCarrerasAgregar();
        cargarCombosExtra();
        actualizarTotalEstudiantes();
    }


    private void actualizarTotalEstudiantes() {
        int total = (controlador != null) ? controlador.obtenerTotalEstudiantes() : 0;
        lblTotalEstudiantes.setText("Total de estudiantes: " + total);
    }

    /**
     * Limpia todos los campos de búsqueda y la tabla.
     */
    private void limpiarBusqueda() {
        txtNombre.setText("");
        cmbCarrera.setSelectedIndex(0);
        limpiarTabla();
        setEstado(MENSAJE_INICIAL);
    }

    /**
     * Limpia todas las filas de la tabla.
     */
    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    /**
     * Actualiza el texto del label de estado.
     */
    private void setEstado(String texto) {
        lblEstado.setText(texto);
    }
}