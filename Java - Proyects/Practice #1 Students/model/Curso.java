package model;

import java.util.ArrayList;

public class Curso {
    private String nombreCurso;
    private ArrayList <Estudiante> estudiantes;
    

    public Curso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
        this.estudiantes = new ArrayList<>();
    }
    
    public void agregarEstudiante(Estudiante e) {
        estudiantes.add(e);
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public String getNombreCurso(){
        return nombreCurso;
    }

    public void mostrarEstudiantes(){
        System.out.println("Curso: " + nombreCurso + " ");
        for (Estudiante e : estudiantes){
        System.out.println(e);
        }
    }

    public Estudiante mejorEstudiante(){
        if (estudiantes.isEmpty()) return null;

        Estudiante mejor = estudiantes.get(0);
        for(Estudiante e : estudiantes){
            if (e.getMateriasAprobadas() > mejor.getMateriasAprobadas()) {
                mejor = e;                
            } 
        }
        return mejor;
    }    
}
