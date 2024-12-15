
package eft_s9_lilian_zapata.vehiculos_alquiler;

import java.text.DecimalFormat;
import java.util.Scanner;


public class VehiculosPasajeros extends Vehiculos implements CalculosDinero {
    Scanner teclado = new Scanner(System.in);
    
    //atributos
    private int numeroAsientos;
    private String nombreCliente;
    private String numeroContacto;
    
    //constructor
    public VehiculosPasajeros(int numeroRuedas, String color, int numeroPuertas, String marca, String modelo, String patente, String tipoVehiculo, int numeroAsientos){
        super(numeroRuedas, color, numeroPuertas, marca, modelo, patente, tipoVehiculo);
        this.numeroAsientos = numeroAsientos;
    }


    @Override
    public String toString() {
        return "Vehiculo de pasajeros: \n" + 
                "  - Patente: " + patente + "\n" + 
                "  - Marca: " + marca + "\n" + 
                "  - Modelo: " + modelo + "\n" +  
                "  - Numero de asientos: " + numeroAsientos;
    }

    

    @Override
    public double calcularIva(double precioBase) {
        return precioBase * 0.19;
    }

    @Override
    public double descuento(double precioBase) {
        double descuento = 0;
        return descuento = precioBase * (10 / 100);
    }

    @Override
    public double calcularPrecioBase(double distancia) {
        double precioBase = 2000 * distancia;
        return precioBase;
    }
    
    @Override
    public void mostrarDetalles() {
        System.out.println("Patente: " + patente);
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Tipo: " + tipoVehiculo);
        System.out.println("Numero de asientos: " + numeroAsientos);
    }

    
    @Override
    public String mostrarBoleta(int duracion, double distancia) {
        System.out.println("");
        DecimalFormat df = new DecimalFormat("0.00"); // Formato con 2 decimales
        StringBuilder boleta = new StringBuilder();
        System.out.println("      AUTOFLEX RENTALS");
        System.out.println("  Sucursal virtual Santiago");
        System.out.println("");
        boleta.append("===== COMPROBANTE DE ARRIENDO =====\n");
        boleta.append("Nombre Cliente: ").append(nombreCliente).append("\n");
        boleta.append("Número Contacto: ").append(numeroContacto).append("\n");
        boleta.append("-----------------------------------\n");
        boleta.append("Patente: ").append(this.patente).append("\n");
        boleta.append("Marca: ").append(this.marca).append("\n");
        boleta.append("Modelo: ").append(this.modelo).append("\n");
        boleta.append("Tipo: ").append(this.tipoVehiculo).append("\n");
        boleta.append("Numero de asientos: ").append(this.numeroAsientos).append("\n");
        boleta.append("-----------------------------------\n");
        boleta.append("Duracion (dias): ").append(duracion).append("\n");
        boleta.append("Distancia (km): ").append(distancia).append("\n");

        // Calcular los valores
        double precioBase = calcularPrecioBase(distancia);
        double iva = calcularIva(precioBase);
        double porcentajeDescuento = 10; // Descuento del 5%
        double descuento = precioBase * (porcentajeDescuento / 100); // Descuento sobre el precio base
        double costoTotal = (precioBase - descuento) + iva; // Primero se resta el descuento al precio base, luego se suma el IVA

        boleta.append("Costo base: $").append(df.format(precioBase)).append("\n");
        boleta.append("IVA: 19% ($").append(df.format(precioBase * 0.19)).append(")\n");
        boleta.append("Descuento: $").append(df.format(descuento)).append("\n");
        boleta.append("Costo total: $").append(df.format(costoTotal)).append("\n");
        boleta.append("=============================");
        return boleta.toString();
    }
}
   
    

