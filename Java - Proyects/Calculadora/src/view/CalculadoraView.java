package view;

import model.*;
import java.util.Scanner;

public class CalculadoraView {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculadora calc = new Calculadora();
        Historial historial = new Historial();

        double a = 0, b = 0;
        boolean datosIngresados = false;
        int opcion;

        do {
            System.out.println("\n=== MENÚ CALCULADORA ===");
            System.out.println("1. Ingresar datos");
            System.out.println("2. Cambiar datos");
            System.out.println("3. Sumar");
            System.out.println("4. Restar");
            System.out.println("5. Multiplicar");
            System.out.println("6. Dividir");
            System.out.println("7. Ver historial");
            System.out.println("8. Guardar historial en CSV");
            System.out.println("9. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                case 2:
                    System.out.print("Ingresa el primer número: ");
                    a = sc.nextDouble();
                    System.out.print("Ingresa el segundo número: ");
                    b = sc.nextDouble();
                    datosIngresados = true;
                    break;
                case 3:
                    if (datosIngresados) {
                        double r = calc.sumar(a, b);
                        historial.agregarOperacion(new Operacion(a, b, "suma", r));
                        System.out.println("Resultado: " + r);
                    } else System.out.println("Primero ingresa los datos.");
                    break;
                case 4:
                    if (datosIngresados) {
                        double r = calc.restar(a, b);
                        historial.agregarOperacion(new Operacion(a, b, "resta", r));
                        System.out.println("Resultado: " + r);
                    } else System.out.println("Primero ingresa los datos.");
                    break;
                case 5:
                    if (datosIngresados) {
                        double r = calc.multiplicar(a, b);
                        historial.agregarOperacion(new Operacion(a, b, "multiplicacion", r));
                        System.out.println("Resultado: " + r);
                    } else System.out.println("Primero ingresa los datos.");
                    break;
                case 6:
                    if (datosIngresados) {
                        try {
                            double r = calc.dividir(a, b);
                            historial.agregarOperacion(new Operacion(a, b, "division", r));
                            System.out.println("Resultado: " + r);
                        } catch (ArithmeticException e) {
                            System.out.println(e.getMessage());
                        }
                    } else System.out.println("Primero ingresa los datos.");
                    break;
                case 7:
                    historial.mostrarHistorial();
                    break;
                case 8:
                    historial.guardarEnCSV("historial.csv");
                    break;
                case 9:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 9);

        sc.close();
    }
}
