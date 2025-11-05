package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Historial {
    private List <Operacion> operaciones = new ArrayList<>();

    public void agregarOperacion (Operacion operacion) {
        operaciones.add(operacion);
    }

    public void mostrarHistorial () {
        if (operaciones.isEmpty()) {
            System.out.println("No hay operaciones registradas aún");
        } else {
            for (Operacion op : operaciones){
                System.out.println(op);
            }
        }
    }

    public void guardarEnCSV (String nombreArchivo) {
        try(FileWriter writer = new FileWriter (nombreArchivo)) {
            writer.write("Dato1,Dato2,Operacion,Resultado \n");
            for (Operacion op : operaciones){
                writer.write(op.toCSV() + "\n");
            }
            System.out.println("Historial guardado en " + nombreArchivo);
        }catch (IOException e){
            System.out.println("Error al guardar el archivo "+ e.getMessage());
        }
        
    }
    
}
