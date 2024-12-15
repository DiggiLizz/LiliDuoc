
package eft_s9_lilian_zapata.vehiculos_alquiler;

import static eft_s9_lilian_zapata.vehiculos_alquiler.Auxiliar.calcularCostoAdicionalPorDuracion;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;


public class Arriendo {
    //permite la escritura
    public static Scanner teclado = new Scanner(System.in);
    
    //lista de autos arrendados
    public static List<Arriendo> vehiculosArrendados = new ArrayList<>();
    
//atributos
    private Vehiculos vehiculo;
    private int duracion;
    private double distancia;
    private double costoTotal;
    private String nombreCliente;
    private String numeroContacto;
    
    //constructor
    public Arriendo(Vehiculos vehiculoSeleccionado, int duracion, double distancia, double costoTotal, String nombreCliente, String numeroContacto) {
        this.vehiculo = vehiculoSeleccionado;
        this.duracion = duracion;
        this.distancia = distancia;
        this.costoTotal = costoTotal;
        this.nombreCliente = nombreCliente;
        this.numeroContacto = numeroContacto;

    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }
    
    //getter y setter

    public Vehiculos getVehiculo() {
        return vehiculo;
    }

    public int getDuracion() {
        return duracion;
    }

    public double getDistancia() {
        return distancia;
    }

    public double getCostoTotal() {
        return costoTotal;
    }
    
    public void setVehiculo(Vehiculos vehiculo) {
        this.vehiculo = vehiculo;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }
    
    private static final Map<String, Double> descuentosPorTipo = new HashMap<>();
    static {
        descuentosPorTipo.put("carga", 0.05); // 5% de descuento
        descuentosPorTipo.put("pasajero", 0.10); // 10% de descuento
    }
    
    public static void arrendarVehiculo(ConcurrentLinkedQueue<Vehiculos> vehiculos, List<Arriendo> vehiculosArrendados) {
        System.out.println("");
        String nombreCliente = "";
        System.out.print("Ingrese su Nombre y Apeliido: ");
        boolean nombreValido = false;
        while (!nombreValido) {
            try {
                nombreCliente = teclado.nextLine();
                if (!nombreCliente.matches("[a-zA-Z áéíóúñÑ'ü-]+")) {
                    throw new Exception("El nombre solo puede contener letras, espacios, guiones y dieresis.");
                }
                nombreValido = true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.print("Ingrese nuevamente su Nombre y Apellido: ");
            }
        }
        
        System.out.print("Ingrese su numero de Contacto (123456789): ");
        String numeroContacto = "";
        boolean numeroValido = false;
        while (!numeroValido) {
            try {
                numeroContacto = teclado.nextLine();
                if (!numeroContacto.matches("\\d+")) {
                    throw new Exception("El numero de contacto debe contener solo numeros.");
                }
                numeroValido = true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.print("Ingrese nuevamente su numero de Contacto: ");
            }
        }

        // Mostrar vehículos disponibles
        System.out.println("Vehiculos disponibles para arrendar:");
        int contador = 1;
        // Crear una nueva lista para los vehículos disponibles (copia defensiva)
        List<Vehiculos> vehiculosDisponibles = new ArrayList<>(vehiculos);
        vehiculosDisponibles.removeIf(vehiculo -> vehiculo.isArrendado());
        for (Vehiculos vehiculo : vehiculosDisponibles) {
            System.out.println(contador + ". " + vehiculo.toString());
            contador++;
        }
        
        
        // Solicitar al usuario el número del vehiculo a alquilar
        System.out.print("Ingrese el numero del vehiculo que desea arrendar ");
        int opcionVehiculo = teclado.nextInt();

        //validacion y obtener el vehiculo seleccionado
        if (opcionVehiculo > 0 && opcionVehiculo <= vehiculosDisponibles.size()) {
            synchronized (vehiculosDisponibles){
                Vehiculos vehiculoSeleccionado = (Vehiculos) vehiculos.toArray()[opcionVehiculo - 1];
                if (!vehiculoSeleccionado.isArrendado()){
                    // Solicitar datos del alquiler
                    System.out.print("Ingrese la cantidad de dias del arriendo: ");
                    int duracion = teclado.nextInt();
                    System.out.print("Ingrese los kilometros estimado: ");
                    double distancia = teclado.nextDouble();

                    // Calcular el costo total
                    double costoTotal = vehiculoSeleccionado.calcularPrecioBase(distancia);

                    // Crear un contrato de alquiler
                    Arriendo contrato = new Arriendo(vehiculoSeleccionado, duracion, distancia, costoTotal, nombreCliente, numeroContacto);

                    // Marcar el vehiculo como no disponible
                    vehiculoSeleccionado.setArrendado(true);

                    // Agregar el contrato a la lista de vehículos arrendados
                    vehiculosArrendados.add(contrato);

                    // Generar y mostrar la boleta
                    contrato.mostrarBoleta(); 

                    // Mostrar un mensaje de confirmación
                    System.out.println("Vehiculo arrendado exitosamente!");
                }else {
                    System.out.println("El vehiculo seleccionado ya esta arrendado.");
                }
            }
        } else {
            System.out.println("Opcion de vehiculo invsalida.");
        }
    }
    
    public void mostrarBoleta() {
        System.out.println("");
        System.out.println("===== COMPROBANTE DE ARRIENDO =====");
        System.out.println("Nombre Cliente: " + nombreCliente);
        System.out.println("Numero de Contacto: " + numeroContacto);
        System.out.println("-----------------------------------");
        vehiculo.mostrarDetalles(); // Delegamos la visualización de los detalles del vehículo al vehículo
        System.out.println("Duracion (dias): " + duracion);
        System.out.println("Distancia (km): " + distancia);

        // Obtener el tipo de vehículo
        String tipoVehiculo = vehiculo.getTipoVehiculo();
        
        // Calcular los valores
        double precioBase = vehiculo.calcularPrecioBase(distancia);
        double iva = precioBase * 0.19;
        
         // Obtener el descuento correspondiente
        double descuentoPorcentaje = descuentosPorTipo(tipoVehiculo);
        double descuento = precioBase * descuentoPorcentaje;
        double costoAdicional = calcularCostoAdicionalPorDuracion(duracion);
        double costoTotal = (precioBase - descuento) + iva + costoAdicional;

        // Formatear los valores monetarios
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String precioBaseFormateado = formatter.format(precioBase);
        String ivaFormateado = formatter.format(iva);
        String descuentoFormateado = formatter.format(descuento);
        String costoTotalFormateado = formatter.format(costoTotal);

        System.out.println("Precio base: " + precioBaseFormateado);
        System.out.println("IVA: " + ivaFormateado);
        System.out.println("Descuento: " + descuentoFormateado); // Asegúrate de que se imprima aquí
        System.out.println("Costo total: " + costoTotalFormateado);
        System.out.println("===== FIN COMPROBANTE =====");
    }

    private double descuentosPorTipo(String tipoVehiculo) {
        if (tipoVehiculo.equalsIgnoreCase("pasajeros")) {
            // Aplicar un descuento del 10%
            return 0.10;
        } else {
            // Aplicar un descuento del 5%
            return 0.05;
        }
    }
}
