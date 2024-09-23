/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.exp3_s7_grupo9;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Lilian Zapata - Vania Maluenda
 * Experiencia 3, semana 7
 * fecha entrega: Lunes 23 de sept. 2024
 */

public class Exp3_S7_grupo9 {
    // Datos del teatro y precios, variables constantes, todas son final, porque su valor no cambia
    private static final String NOMBRE_TEATRO = "Teatro Moro";
    private static final int CAPACIDAD_SALA = 120;
    private static final int FILAS_VIP = 3;
    private static final int FILAS_PLATEA = 5;
    private static final int FILAS_BALCON = 4;
    private static final int FILAS_TOTALES = FILAS_VIP + FILAS_PLATEA + FILAS_BALCON;
    private static final int ASIENTOS_POR_FILA = 10;
    private static final double PRECIO_VIP = 15000;
    private static final double PRECIO_PLATEA = 13500;
    private static final double PRECIO_BALCON = 11900;
    private static final double DESCUENTO_ESTUDIANTE = 0.10;
    private static final double DESCUENTO_TERCERA_EDAD = 0.15;
   
    // Definición de la clase Asiento
    static class Asiento {
        private boolean ocupado;
        private double precio;

        public Asiento(double precio) {
            this.ocupado = false;
            this.precio = precio;
        }

        // Getters y setters
        public boolean isOcupado() {
            return ocupado;
        }

        public void setOcupado(boolean ocupado) {
            this.ocupado = ocupado;
        }
        public double getPrecio(){
            return precio;
        }
    }
    // Definición de la clase Venta
    static class Venta {
        private String nombreCompleto;
        private String ubicacion;
        private int fila;
        private int columna;
        private double precioBase;
        private double descuento;
        private double precioFinal;

        public Venta(String nombreCompleto, String ubicacion, int fila, int columna, double precioBase, double descuento, double precioFinal) {
            this.nombreCompleto = nombreCompleto;
            this.ubicacion = ubicacion;
            this.fila = fila;
            this.columna = columna;
            this.precioBase = precioBase;
            this.descuento = descuento;
            this.precioFinal = precioFinal;
        }
    }
    //arreglo bidimensional para representar la sala
    private static Asiento[][] asientos = new Asiento[FILAS_TOTALES][ASIENTOS_POR_FILA];
    
    //inicar la sala
    static {
        asientos = new Asiento[FILAS_TOTALES][ASIENTOS_POR_FILA];
        for (int i = 0; i < asientos.length; i++) {
            for (int j = 0; j < asientos[i].length; j++) {
                asientos[i][j] = new Asiento(calcularPrecioAsiento(i));
            }
        }
    } 
    
    // Lista para almacenar las ventas
    private static List<Venta> ventas = new ArrayList<>();

    // Contadores
    private static int entradasVendidas = 0;
    private static double ingresosTotales = 0;
    
    //metodo principal
    public static void main(String[] args) {
        System.out.println("BIENVENIDOS AL MEJOR TEATRO DE LA CIUDAD");
        System.out.println("HOY:  EL QUIJOTE DE LA MANCHA");
        System.out.println("Hora: 20:00 hrs");
        System.out.println("Funcion para mayores de 15 años");
        
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        
        while (!salir){
            mostrarMenu();
            int opcion = scanner.nextInt();
            
            switch(opcion){
                case 1: 
                    venderEntrada(scanner);
                    break;
                case 2:   
                    mostrarResumenVenta();
                    break;
                case 3:
                    generarBoleta(scanner);
                    break;
                case 0:
                    System.out.println("Gracias por Visitar TEATRO MORO");
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }
    //metodo para calcular el precio de los asientos
    private static double calcularPrecioAsiento(int fila) {
        if (fila < FILAS_VIP) {
            return PRECIO_VIP;
        } else if (fila < FILAS_VIP + FILAS_PLATEA) {
            return PRECIO_PLATEA;
        } else {
            return PRECIO_BALCON;
        }
    }
    
    private static void mostrarMenu() {
        System.out.println("");
        System.out.println("Bienvenido al menu de " + NOMBRE_TEATRO);
        System.out.println("1. Vender entrada");
        System.out.println("2. Ver resumen de ventas");
        System.out.println("3. Generar boleta");
        System.out.println("0. Salir");
    }
    
     private static double calcularDescuento(int edad) {
        if (edad <= 25) {
            return DESCUENTO_ESTUDIANTE;
        } else if (edad >= 65) {
            return DESCUENTO_TERCERA_EDAD;
        } else {
            return 0; // No hay descuento para otras edades
        }
    }
     
    //metodo que muestra la sala completa sin dividirla por secciones
    private static void mostrarSalaCompleta() {
        System.out.println("Mapa de la Sala Completa");
        System.out.println("     Escenario ");

        for (int i = 0; i < FILAS_TOTALES; i++) {
            System.out.printf("Fila %2d: ", (i + 1));
            for (int j = 0; j < ASIENTOS_POR_FILA; j++) {
                System.out.print(asientos[i][j].isOcupado() ? "X " : "O ");
            }
            System.out.println();
        }
    }
    
    //metodo que muestra la sala segun la seccion de seleccion
    private static void mostrarSala(String seccion, boolean mostrarNumeroFilas) {
        System.out.println("Mapa de la Sala - Sección " + seccion);
        System.out.println("            Escenario ");

        int inicioFila = 0;
        int finFila = 0;

        switch (seccion) {
            case "VIP":
                finFila = FILAS_VIP;
                break;
            case "PLATEA":
                inicioFila = FILAS_VIP;
                finFila = FILAS_VIP + FILAS_PLATEA;
                break;
            case "BALCON":
                inicioFila = FILAS_VIP + FILAS_PLATEA;
                finFila = FILAS_TOTALES;
                break;
            default:
                System.out.println("Sección inválida.");
                return;
        }

        for (int i = inicioFila; i < finFila; i++) {
            if (mostrarNumeroFilas){
                System.out.printf("Fila %2d: ", (i + 1));
            }
            for (int j = 0; j < ASIENTOS_POR_FILA; j++) {
                System.out.print(asientos[i][j].isOcupado() ? "X " : "O ");
            }
            System.out.println();
        }
    }
    
    //metodo patra la venta de entradas
    private static void venderEntrada(Scanner scanner) {
        //mostra la sala completa
        System.out.println("Sala del Teatro");
        mostrarSalaCompleta();
        System.out.println("-----------------------");
        System.out.println("Seleccione la sección:");
        System.out.println("1. VIP    (fila 1 a 3)");
        System.out.println("2. Platea (fila 4 a 8)");
        System.out.println("3. Balcón (fila 9 a 12)");
        int opcionSeccion = scanner.nextInt();
        String ubicacion = "";
        double precioBase = 0;
        
        switch (opcionSeccion) {
            case 1:
                ubicacion =  "VIP";
                precioBase = PRECIO_VIP;
                break;
            case 2:
                ubicacion = "PLATEA";
                precioBase = PRECIO_PLATEA;
                break;
            case 3:
                ubicacion = "BALCON";
                precioBase = PRECIO_BALCON;
                break;
            default:
                System.out.println("Opción de sección inválida.");
                return; //salir del metodo si es invalida la opcion
        }
        mostrarSala(ubicacion, true);
        
        System.out.println("Ingrese el número de fila:");
        int fila = scanner.nextInt();
        //validacion segun seccion elegida
        int maxFila = maximoFilasPorSeccion(ubicacion);
        if (fila > maxFila){
            System.out.println("Fila seleccionada no corresponde a Seccion " + ubicacion);
            return;
        }
        
        System.out.println("Ingrese el número de columna:");
        int columna = scanner.nextInt();
        
        //ingresar la edad para el descuento y valiadacion de rango
        int edad;
        do{
           System.out.print("Ingrese su edad para ver si accede algun descuento: ");
           while (!scanner.hasNextInt()){
               System.out.println("Ingrese edad en numero entero por favor");
               scanner.next();
           }
           edad = scanner.nextInt();
           if (edad < 15){
               System.out.println("funcion para mayores de 15 años");
           }
        }while (edad < 15 || edad > 125);
        
        //mostrar precio base, segun la seleccion del sector
        System.out.println("Valor base: $" + precioBase);
        
        //calcular descuento y precio final
        double descuento = calcularDescuento(edad);
        double precioFinal = precioBase - (precioBase * descuento);
        System.out.println("Valor a cancelar: $" + precioFinal);
        
         // Reservar el asiento y continuar con el proceso de venta si está disponible
        if (reservarAsiento(ubicacion, fila, columna)) {
             // ingreso del nombre y validacion
            scanner.nextLine();// salto de linea, que permite ingresar el nombre
            String nombreCompleto;
            do{
               System.out.println("Ingrese su nombre y apellido:");
               nombreCompleto = scanner.nextLine();
               if (!nombreCompleto.matches("[a-zA-Z\\s]+")){
                   System.out.println("Se permiten solo letras y espacios");
               }
            }while (!nombreCompleto.matches("[a-zA-Z\\s]+"));
            //si esta disponible se va al pago
            generarPago(scanner, precioFinal);
            // Si el asiento está disponible, creamos una nueva venta
            Venta nuevaVenta = new Venta(nombreCompleto, ubicacion, fila, columna, precioBase, descuento, precioFinal);

            // Agregamos la nueva venta a la lista de ventas
            ventas.add(nuevaVenta);

            //Actualiza los valores
            entradasVendidas++;
            ingresosTotales += precioFinal;

            // Imprimimos un mensaje de confirmación
            System.out.println("¡GRACIAS POR SU COMPRA!");
        } else {
            System.out.println("Asiento no disponible. Por favor, intente nuevamente.");
        }
    }
    
    private static boolean reservarAsiento(String ubicacion, int fila, int columna) {
        // Validar sección, fila y columna según las variables de configuración
         if (fila < 1 || columna < 1) {
            System.out.println("Fila o columna inválida.");
            return false;
        }
        int maxFilas = switch (ubicacion) {
            case "VIP" -> FILAS_VIP;
            case "PLATEA" -> FILAS_PLATEA;
            case "BALCON" -> FILAS_BALCON;
            default -> throw new IllegalArgumentException("Sección inválida");
        };

        if (fila > maxFilas || columna > ASIENTOS_POR_FILA) {
            System.out.println("Asiento fuera de rango.");
            return false;
        }

        // Marcar el asiento como ocupado
        if (!asientos[fila - 1][columna - 1].isOcupado()) { // Verificar si el asiento está libre
            asientos[fila - 1][columna - 1].setOcupado(true);
            return true;
        } else {
            System.out.println("El asiento ya está ocupado.");
            return false;
        }
    }
    
    private static int maximoFilasPorSeccion(String ubicacion){
        //metodo que valida que se elija fila correctas segun la seccion elegida
        switch(ubicacion){
            case "VIP":
                return FILAS_VIP;
            case "PLATEA":
                return FILAS_VIP + FILAS_PLATEA;
            case "BALCON":
                return FILAS_TOTALES;
            default:
                throw new IllegalArgumentException("Seccion elegida invalida");
        }
    }
    
    private static void generarPago(Scanner scanner, double precioFinal){
        System.out.println("Ingrese su pago: ");
        while (true){
            try{
                double montoPagado = scanner.nextDouble();
                if (montoPagado >= precioFinal){
                    double vuelto = montoPagado - precioFinal;
                    System.out.println("Su vuelto es: $ " + vuelto);
                    break;
                }else{
                    System.out.println("El pago ingresado es insuficiente");
                }
            }catch (InputMismatchException e){
                System.out.println("Por favor, ingrese un valor numérico.");
                scanner.next(); // Limpiar el buffer del scanner
            }
        }
    }
    
    private static void mostrarResumenVenta() {
        System.out.println("Resumen de Ventas:");
        System.out.println("------------------");
        for (Venta venta : ventas) {
            System.out.println("Nombre: " + venta.nombreCompleto);
            System.out.println("Ubicación: " + venta.ubicacion);
            System.out.println("Fila: " + venta.fila);
            System.out.println("Columna: " + venta.columna);
            System.out.println("Precio base: $" + venta.precioBase);
            System.out.println("Descuento: " + venta.descuento * 100 + "%");
            System.out.println("Precio final: $" + venta.precioFinal);
            System.out.println("------------------");
        }
    }
    
    private static void generarBoleta(Scanner scanner) {
        System.out.println("Ingrese el número de la venta (1-" + ventas.size() + "):");
        int numeroVenta = scanner.nextInt() - 1; // Ajustamos el índice a 0-based

        //valiadacion para generar la boleta, sea dontro del total de las boletas vendidas
        if (numeroVenta >= 0 && numeroVenta < ventas.size()) {
            Venta venta = ventas.get(numeroVenta);

            System.out.println("\nBoleta de Venta");
            System.out.println("------------------");
            System.out.println("Nombre: " + venta.nombreCompleto);
            System.out.println("Ubicación: " + venta.ubicacion);
            System.out.println("Fila: " + venta.fila);
            System.out.println("Columna: " + venta.columna);
            System.out.println("Precio base: $" + venta.precioBase);
            System.out.println("Descuento: " + venta.descuento * 100 + "%");
            System.out.println("Precio final: $" + venta.precioFinal);
            System.out.println("------------------");
            System.out.println("¡Gracias por su compra!");
        } else {
            System.out.println("Número de venta inválido.");
        }
    }
    
}
        