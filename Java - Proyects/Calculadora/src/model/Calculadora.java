package model;

public class Calculadora {

    public double sumar (double valor1, double valor2){
        return valor1 + valor2;
    }

    public double restar (double valor1, double valor2){
        return valor1 - valor2;
    }

    public double multiplicar (double valor1, double valor2){
        return valor1 * valor2;
    }

    public double dividir (double valor1, double valor2){
        if (valor2 == 0) {
            throw new ArithmeticException ("No se puede dividir entre 0");
        }
        return valor1 / valor2;
    }



}