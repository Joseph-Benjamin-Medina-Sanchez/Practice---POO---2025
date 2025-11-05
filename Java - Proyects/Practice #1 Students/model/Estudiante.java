package model;

public class Estudiante {
    private String nombre;
    private String apellido;
    private String id;
    private int materiasReprobadas;
    private int materiasAprobadas;

    public Estudiante(String nombre, String apellido, String id, int materiasReprobadas, int materiasAprobadas) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.materiasReprobadas = materiasReprobadas;
        this.materiasAprobadas = materiasAprobadas;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMateriasReprobadas() {
        return this.materiasReprobadas;
    }

    public void setMateriasReprobadas(int materiasReprobadas) {
        this.materiasReprobadas = materiasReprobadas;
    }

    public int getMateriasAprobadas() {
        return this.materiasAprobadas;
    }

    public void setMateriasAprobadas(int materiasAprobadas) {
        this.materiasAprobadas = materiasAprobadas;
    }

    @Override
        public String toString() {
        return "Estudiante{" +
           "nombre='" + nombre + '\'' +
           ", apellido='" + apellido + '\'' +
           ", id='" + id + '\'' +
           ", materiasReprobadas=" + materiasReprobadas +
           ", materiasAprobadas=" + materiasAprobadas +
           '}';
        }
}
