package model;

public class Animal {
    private String tipo;
    private String nombre;
    private int edad;
    private float pesoEnKilogramos;
    private int energia;
    private int hambre;
    

    public Animal(String tipo, String nombre, int edad, float pesoEnKilogramos, float energia, float hambre) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.edad = edad;
        this.pesoEnKilogramos = pesoEnKilogramos;
        this.energia = 50;
        this.hambre = 50;
    }


    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return this.edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public float getPesoEnKilogramos() {
        return this.pesoEnKilogramos;
    }

    public void setPesoEnKilogramos(float pesoEnKilogramos) {
        this.pesoEnKilogramos = pesoEnKilogramos;
    }

    public float getEnergia() {
        return this.energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public float getHambre() {
        return this.hambre;
    }

    public void setHambre(int hambre) {
        this.hambre = hambre;
    }

    
}