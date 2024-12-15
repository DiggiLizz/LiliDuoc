
package eft_s9_lilian_zapata.vehiculos_alquiler;

import java.util.stream.Stream;

public abstract class  Vehiculos implements CalculosDinero {
    //atributos
    int numeroRuedas;
    String color;
    int numeroPuertas;
    String marca;
    String modelo;
    String patente;
    String tipoVehiculo;
    private boolean arrendado;
    
    //constructor
    public Vehiculos(int numeroRuedas, String color, int numeroPuertas, String marca, String modelo, String patente, String tipoVehiculo){
        this.numeroRuedas = numeroRuedas;
        this.color = color;
        this.numeroPuertas = numeroPuertas;
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.tipoVehiculo = tipoVehiculo;
        this.arrendado = arrendado;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    //getter y setter
    public int getNumeroRuedas(){
        return numeroRuedas;
    }
    public void setNumeroRuedas(int numeroRuedas){
        this.numeroRuedas = numeroRuedas;
    }
    public String getColor(){
        return color;
    }
    public void setColors(String color){
        this.color = color;
    }
    public int getNumeroPuertas(){
        return numeroPuertas;
    }
    public void setNumeroPuertas(int numeroPuertas){
        this.numeroPuertas = numeroPuertas;
    }
    public String getMarca(){
        return marca;
    }
    public void setMarca(String marca){
        this.marca = marca;
    }
    public String getModelo(){
        return modelo;
    }
    public void setModelo(String modelo){
        this.modelo = modelo;
    }
    
    public String getPatente(){
        return patente;
    }
    public void setPatente(String patente){
        this.patente = patente;
    }
    
    public String getTipoVehiculo(){
        return modelo;
    }
    public void setTipoVehiculo(String tipoVehiculo){
        this.tipoVehiculo = tipoVehiculo;
    }
    
    
    //Metodos heredables
    public abstract double calcularPrecioBase(double distancia);
    public abstract void mostrarDetalles();
    

    public String mostrarBoleta(int duracion, double distancia) {
        // Implementación por defecto (si es necesario)
        return "Boleta no disponible para este tipo de vehiculo";
    }

    public boolean isArrendado() {
        return arrendado;
    }

    public void setArrendado(boolean arrendado) {
        this.arrendado = arrendado;
    }

    public boolean isDisponible() {
        return arrendado;
    }
    
}
