package model;

public class Operacion {
    private double dato1;
    private double dato2;
    private String tipo;
    private double resultado;


    public Operacion(double dato1, double dato2, String tipo, double resultado) {
        this.dato1 = dato1;
        this.dato2 = dato2;
        this.tipo = tipo;
        this.resultado = resultado;
    }


    public double getDato1() {
        return this.dato1;
    }

    public double getDato2() {
        return this.dato2;
    }

    public String getTipo() {
        return this.tipo;
    }

    public double getResultado() {
        return this.resultado;
    }

    public String toCSV(){
        return dato1 + ", " + dato2 + ", " + tipo + ", " + resultado;
    }

    @Override
    public String toString(){
        return tipo + " (" + dato1 + ", " + dato2 + " ) = " + resultado;
    }


    
}
