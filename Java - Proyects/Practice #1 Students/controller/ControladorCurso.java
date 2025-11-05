package controller;

import model.Curso;
import model.Estudiante;
import java.util.ArrayList;

public class ControladorCurso {
    private ArrayList <Curso> cursos;


    public ControladorCurso() {
        this.cursos = new ArrayList<>();
    }

    public void CrearCurso (String nombre){
        cursos.add(new Curso(nombre));
    }

    public Curso buscarCurso (String nombre){
        for(Curso c : cursos){
            if (c.getNombreCurso().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    public void agregarEstudianteACurso (String nombreCurso, Estudiante e){
        Curso curso = buscarCurso(nombreCurso);
        if (curso != null) {
            curso.agregarEstudiante(e);            
        } else {
            System.out.println("El curso seleccionado no existe");
        }
    }

    public void mostrarCurso (){
        for(Curso c : cursos) {
            System.out.println("- " + c.getNombreCurso() );
        }
    }
}