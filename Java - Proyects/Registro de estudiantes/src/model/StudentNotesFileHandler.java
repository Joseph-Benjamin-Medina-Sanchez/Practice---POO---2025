package model;

import model.Estudiante;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StudentNotesFileHandler {
    public static void main (String [], args){
        

        String nombreArchivo = "gradesStudents.csv";

        List<Estudiante> listaEstudiantes = new ArrayList<>();

            listaEstudiantes.add(new Estudiante("Juan", "Pérez", "ST001", 8.5, 9.0, 7.5));
            listaEstudiantes.add(new Estudiante("María", "García", "ST002", 9.0, 8.8, 9.5));
        listaEstudiantes.add(new Estudiante("Carlos", "López", "ST003", 7.0, 8.0, 7.5));

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("nameStudent,lastNameStudent,studentId,nota1,nota2,nota3,promedio\n");
            for (Estudiante e : listaEstudiantes) {
                writer.write(e.toCSV() + "\n");
        }
    System.out.println("Archivo CSV creado correctamente.");
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}


    
}