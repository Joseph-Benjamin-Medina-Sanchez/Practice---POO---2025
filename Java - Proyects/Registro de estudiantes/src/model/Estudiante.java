package model;

import javax.print.DocFlavor.STRING;

public class Estudiante {
    private String nameStudent;
    private String lastNameStudent;
    private String studentId;
    private double nota1;
    private double nota2;
    private double nota3;


    public Estudiante(String nameStudent, String lastNameStudent, String studentId, double nota1, double nota2, double nota3) {
        this.nameStudent = nameStudent;
        this.lastNameStudent = lastNameStudent;
        this.studentId = studentId;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
    }


    public String getNameStudent() {
        return this.nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getLastNameStudent() {
        return this.lastNameStudent;
    }

    public void setLastNameStudent(String lastNameStudent) {
        this.lastNameStudent = lastNameStudent;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public double getNota1() {
        return this.nota1;
    }

    public void setNota1(double nota1) {
        this.nota1 = nota1;
    }

    public double getNota2() {
        return this.nota2;
    }

    public void setNota2(double nota2) {
        this.nota2 = nota2;
    }

    public double getNota3() {
        return this.nota3;
    }

    public void setNota3(double nota3) {
        this.nota3 = nota3;
    }

    public double calcularPromedio (){
        double promedioFinal = (nota1 + nota2 + nota3) / 3;
        return promedioFinal;
    }

    public String toCSV() {
    return nameStudent + "," + lastNameStudent + "," + studentId + "," +
           nota1 + "," + nota2 + "," + nota3 + "," + calcularPromedio();
}

    @Override
    public String toString() {
        return "{" +
            " nameStudent='" + getNameStudent() + "'" +
            ", lastNameStudent='" + getLastNameStudent() + "'" +
            ", studentId='" + getStudentId() + "'" +
            ", nota1='" + getNota1() + "'" +
            ", nota2='" + getNota2() + "'" +
            ", nota3='" + getNota3() + "'" +
            "}";
    }


}
