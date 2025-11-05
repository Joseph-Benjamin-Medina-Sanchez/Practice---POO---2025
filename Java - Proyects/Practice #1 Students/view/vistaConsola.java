package view;

import java.util.Scanner;
import model.Estudiante;
import controller.ControladorCurso;

public class VistaConsola {
    private ControladorCurso controlador;
    private Scanner sc;

    public VistaConsola() {
        controlador = new ControladorCurso();
        sc = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Crear curso");
            System.out.println("2. Agregar estudiante a curso");
            System.out.println("3. Mostrar cursos");
            System.out.println("4. Mostrar estudiantes de un curso");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> crearCurso();
                case 2 -> agregarEstudiante();
                case 3 -> controlador.mostrarCursos();
                case 4 -> mostrarEstudiantes();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private void crearCurso() {
        System.out.print("Nombre del curso: ");
        String nombre = sc.nextLine();
        controlador.crearCurso(nombre);
        System.out.println("Curso creado con éxito.");
    }

    private void agregarEstudiante() {
        System.out.print("Nombre del curso: ");
        String curso = sc.nextLine();

        System.out.print("Nombre del estudiante: ");
        String nombre = sc.nextLine();
        System.out.print("Apellido: ");
        String apellido = sc.nextLine();
        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Materias reprobadas: ");
        int reprobadas = sc.nextInt();
        System.out.print("Materias aprobadas: ");
        int aprobadas = sc.nextInt();
        sc.nextLine();

        Estudiante e = new Estudiante(nombre, apellido, id, reprobadas, aprobadas);
        controlador.agregarEstudianteACurso(curso, e);
        System.out.println("Estudiante agregado correctamente.");
    }

    private void mostrarEstudiantes() {
        System.out.print("Nombre del curso: ");
        String curso = sc.nextLine();
        var c = controlador.buscarCurso(curso);
        if (c != null) {
            c.mostrarEstudiantes();
        } else {
            System.out.println("Curso no encontrado.");
        }
    }
}
