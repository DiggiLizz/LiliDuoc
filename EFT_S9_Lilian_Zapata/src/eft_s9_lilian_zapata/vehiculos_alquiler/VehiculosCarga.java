
package eft_s9_lilian_zapata.vehiculos_alquiler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;



public class VehiculosCarga extends Vehiculos implements CalculosDinero{
    Scanner teclado = new Scanner(System.in);

    //atributo
    private double capacidadCargaToneladas;
    private double costoFijoPorKm;
    private double costoVariablePorKm;
    
    
    //contructor
    public VehiculosCarga(int numeroRuedas, String color, int numeroPuertas, String marca, String modelo, String patente, String tipoVehiculo, double capacidadCargaToneladas, double costoFijoPorKm, double costoVariablePorKm) {
        super(numeroRuedas, color, numeroPuertas, marca, modelo, patente, tipoVehiculo);
        this.capacidadCargaToneladas = capacidadCargaToneladas;
        this.costoFijoPorKm = costoFijoPorKm;
        this.costoVariablePorKm = costoVariablePorKm;
    }

    public double getCapacidadCargaToneladas(){
        return capacidadCargaToneladas;
    }
    
    public void setCapacidadCargaToneladas(double capacidadCargaToneladas){
        this.capacidadCargaToneladas = capacidadCargaToneladas;
    }
    
    public double getCostoFijoPorKm(){
        return costoFijoPorKm;
    }
    
    public void setCostoFijoPorKm(double costoFijoPorKm){
        this.costoFijoPorKm = costoFijoPorKm;
    }
    
    public double getCostoVariablePorKm(){
        return costoVariablePorKm;
    }
    
    public void setCostoVariablePorKm(double costoVariablePorKm){
        this.costoVariablePorKm = costoVariablePorKm;
    }
    
    
    //sobre escritura del calculo de base para carga
    @Override
    public double calcularPrecioBase(double distancia) {
        double precioBase = costoFijoPorKm + (costoVariablePorKm * distancia);
        return precioBase;
    }
    
    
    
    @Override
    public String toString() {
        return "Vehículo de carga: \n" +
            "- Patente: " + patente + "\n" +
            "- Marca: " + marca + "\n" +
            "- Modelo: " + modelo + "\n" +
            "- Numero de ruedas: " + numeroRuedas + "\n" +
            "- Color: " + color + "\n" +
            "- Numero de puertas: " + numeroPuertas + "\n" +
            "- Capacidad de carga: " + String.format("%.2f", capacidadCargaToneladas) + " toneladas\n" +
            "- Costo fijo por km: $" + String.format("%.2f", costoFijoPorKm) + "\n" +
            "- Costo variable por km: $" + String.format("%.2f", costoVariablePorKm);
    }

    @Override
    public double calcularIva(double precioBase) {
        return precioBase * 0.19;
    }

    @Override
    public double descuento(double precioBase) {
        double descuento = 0;
        return descuento = precioBase * (5 / 100);
    }
    
    @Override
    public void mostrarDetalles() {
        System.out.println("Patente: " + patente);
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Tipo: " + tipoVehiculo);
        System.out.println("Capacidad de carga: " + capacidadCargaToneladas + " toneladas");
        System.out.println("Costo fijo por km: $" + costoFijoPorKm);
        System.out.println("Costo variable por km: $" + costoVariablePorKm);
    }

    @Override
    public String mostrarBoleta(int duracion, double distancia) {
        DecimalFormat df = new DecimalFormat("0.00"); // Formato con 2 decimales
        
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CL")); // Locale para Chile
        
        StringBuilder boleta = new StringBuilder();
        System.out.println("          AUTOFLEX RENTALS");
        System.out.println("       Sucursal virtual Santiago");
        System.out.println("");
        boleta.append("===== BOLETA DE ALQUILER DE VEHICULO DE CARGA =====\n");
        boleta.append("Nombre Cliente: \n");
        boleta.append("Numero Contacto: \n");
        boleta.append("---------------------------------------------------\n");
        boleta.append("Patente: ").append(this.patente).append("\n");
        boleta.append("Marca: ").append(this.marca).append("\n");
        boleta.append("Modelo: ").append(this.modelo).append("\n");
        boleta.append("Capacidad de carga: ").append(String.format("%.2f", capacidadCargaToneladas)).append(" toneladas\n");
        boleta.append("---------------------------------------------------\n");
        boleta.append("Duracion (dias): ").append(duracion).append("\n");
        boleta.append("Distancia (km): ").append(distancia).append("\n");

        // Calcular los valores
        double precioBase = calcularPrecioBase(distancia);
        double iva = calcularIva(precioBase);
        double porcentajeDescuento = 5; // Descuento del 5%
        double descuento = precioBase * (porcentajeDescuento / 100); // Descuento sobre el precio base
        double costoTotal = (precioBase - descuento) + iva; // Primero se resta el descuento al precio base, luego se suma el IVA
        
        String costoTotalFormateado = formatoMoneda.format(costoTotal);System.out.println("");
        boleta.append("Costo base: $").append(df.format(precioBase)).append("\n");
        boleta.append("IVA: 19% ($").append(df.format(precioBase * 0.19)).append(")\n");
        boleta.append("Descuento: $").append(df.format(descuento)).append("\n");
        boleta.append("Costo total: $").append(df.format(costoTotal)).append("\n");
        boleta.append("================================================");
        return boleta.toString();
    }
    
   
}
