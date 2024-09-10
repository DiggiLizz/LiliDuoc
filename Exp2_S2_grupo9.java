/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/*
Integrantes: Lilian Zapata -  Vania Maluenda
fecha entrega: 09-09-2024
*/

package com.mycompany.exp2_s2_grupo9;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Entrada {
    int numero;
    int fila;
    int columna;
    String tipo;  // Estudiante, Tercera Edad, etc.
    double precioFinal;

    public Entrada(int numero, int fila, int columna, String tipo, double precioFinal) {
        this.numero = numero;
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
        this.precioFinal = precioFinal;
    }
}

public class Exp2_S2_grupo9 {
    // Variables estáticas para estadísticas
    private static int totalEntradasVendidas = 0;
    private static double totalIngresos = 0.0;
    
    // Variables de instancia
    private static List<Entrada> entradasVendidas = new ArrayList<>(); //arreglo para ver si los asientos estan ocpuados o no
    private static final double PRECIO_BASE = 10000; // Precio base de las entradas
    private static final double DESCUENTO_ESTUDIANTE = 0.10;
    private static final double DESCUENTO_TERCERA_EDAD = 0.15;
    private static final double DESCUENTO_CANTIDAD = 0.05; // 5% de descuento si compra más de 5 entradas

    private static final int FILAS = 6; //tamaño de la sala
    private static final int COLUMNAS = 20; // tamaño de la sala
    private static char[][] sala = new char[FILAS][COLUMNAS]; // Matriz de la sala de teatro
    
    // Variables locales
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarSala();
        
        
        System.out.println();
        boolean salir = false;
        while (!salir) {
            System.out.println("BIENVENIDO A TEATRO MORO");
            System.out.println("Obra: El Quijote de la Mancha");
            System.out.println("Funcion de hoy 19:00 horas");
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            switch (opcion) {
                case 1:
                    mostrarPromociones(); //acceso a promociones
                    break;
                case 2:
                    venderEntrada(); // acceso a venta
                    break;
                case 3:
                    buscarEntrada(); // buscar entrada seleccionada
                    break;
                case 4:
                    eliminarEntrada(); //eliminacion de entrada
                    break;
                case 5:
                    finalizarCompra(); // finalizar la compra o ingresar al menu para nueva compra
                    //saber si se quiere comprar mas entradas luego del pago
                    while (true){
                        System.out.println("¿Desea ingresar el sistema de venta del Teatro Moro? S/N");
                        char continuar = Character.toUpperCase(scanner.next().charAt(0));
                        
                        //validacion de respuesta
                        if (continuar == 'S'){
                            inicializarSala();
                            break; // seguir comprando
                        }else if (continuar == 'N'){
                            salir = true; //terminar de comprar
                            break;
                        }else{
                            System.out.println("Ingrese respuesta Válida. S o N");
                            
                            
                        }

                    }
                    
                    System.out.println("Gracias por Visitar TEATRO MORO");
                    System.out.println("---------------------------------------------------------------");
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
                    System.out.println(""); // salto de linea, para que al correr se diferencien los procesos
            }
        }
    }

    private static void inicializarSala() { // icnio de sala sin compras
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                sala[i][j] = 'O'; // Asiento disponible
            }
        }
    }

    private static void mostrarSala() { //se muestra la ubicacion de los asientos
        System.out.println("   Escenario");
        for (int i = 0; i < FILAS; i++) {
            System.out.printf("Fila %2d: ", (i + 1));
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print(sala[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("O: Disponible, X: Ocupado");
    }

    private static void mostrarMenu() {
        System.out.println("--- Menú Principal ---");
        System.out.println("1. Ver Promociones");
        System.out.println("2. Vender Entradas");
        System.out.println("3. Buscar Entrada");
        System.out.println("4. Eliminar Entrada");
        System.out.println("5. Finalizar y Mostrar Compras");
        System.out.print("Seleccione una opción: ");
        
    }

    private static void venderEntrada() {
        System.out.println("");
        System.out.println("¿Cuántas entradas desea comprar?"); //saber la cantidad de entradas que comprará
        int numeroEntradas = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        //validacion de asientos
        while (numeroEntradas < 0 || numeroEntradas >120){
            System.out.println("Numero entradas invalido o supera la capacidad maxima del teatro");
            System.out.println("Ingrese nuevamente la cantidad de entradas a comprar");
            numeroEntradas = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            break;
        }
        for (int i = 0; i < numeroEntradas; i++) {
            System.out.printf("Entrada %d de %d\n", (i + 1), numeroEntradas);
            mostrarSala();
            //seleccion de fila
            System.out.println("Seleccione la fila del asiento (1-6):");
            int fila = scanner.nextInt()-1;
            while (fila < 0 || fila > 6){
                System.out.println("Numero de fila no valido");
                System.out.println("Seleccione la fila del asiento (1-6):");
                fila = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
            }
            // seleccion de columna
            System.out.println("Seleccione la columna del asiento (1-20):");
            int columna = scanner.nextInt() -1;
            //validacion de eleccion
            if (fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS) {
                if (sala[fila][columna] == 'O') {
                    sala[fila][columna] = 'X'; // Marcar el asiento como ocupado
                    String tipoCliente = seleccionarTipoCliente();
                    double precioFinal = calcularPrecioFinal(tipoCliente);

                    // Almacenar la entrada
                    totalEntradasVendidas++;
                    Entrada nuevaEntrada = new Entrada(totalEntradasVendidas, fila + 1, columna + 1, tipoCliente, precioFinal);
                    entradasVendidas.add(nuevaEntrada);

                    totalIngresos += precioFinal;

                    System.out.printf("Entrada reservada con Exito" ,totalEntradasVendidas, fila + 1, columna + 1, precioFinal);
                    System.out.println("");
                    System.out.println(""); // salto de linea, para que al correr se diferencien los procesos
                } else {
                    System.out.println("Ese asiento ya está ocupado. Elija otro.");
                    i--; // Permitir reintentar el mismo número de asiento
                    System.out.println("");
                    System.out.println(""); // salto de linea, para que al correr se diferencien los procesos
                    
                }
            } else {
                System.out.println("Asiento inválido. Intente nuevamente.");
                i--; // Reintentar si los valores están fuera del rango
                System.out.println("");
                    System.out.println(""); // salto de linea, para que al correr se diferencien los procesos
            }
        }
    }

    private static String seleccionarTipoCliente() {
        //ingreso de tipo de descuento
        System.out.println("Seleccione el tipo de cliente:");
        System.out.println("1. Estudiante");
        System.out.println("2. Tercera Edad");
        System.out.println("3. Normal");
        System.out.print("Opción: ");
        int opcionTipo = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        switch (opcionTipo) {
            case 1:
                return "Estudiante";
            case 2:
                return "Tercera Edad";
            case 3:
                return "Normal";
            default:
                System.out.println("Opción inválida. Se seleccionará 'Normal' por defecto.");
                return "Normal";
        }
    }

    private static double calcularPrecioFinal(String tipo) {
        double precioFinal = PRECIO_BASE;

        if (tipo.equalsIgnoreCase("Estudiante")) {
            precioFinal -= (PRECIO_BASE * DESCUENTO_ESTUDIANTE);
        } else if (tipo.equalsIgnoreCase("Tercera Edad")) {
            precioFinal -= (PRECIO_BASE * DESCUENTO_TERCERA_EDAD);
        }

        return precioFinal;
    }

    private static void mostrarPromociones() {
        System.out.println(""); //separa del menu anterior al leer
        System.out.println(" --- Promociones ---");
        System.out.println("1. Descuento del 10% para estudiantes.");
        System.out.println("2. Descuento del 15% para personas de la tercera edad.");
        System.out.println("3. Compra 5 entradas o más y obtén un 5% de descuento adicional en cada una.");
        System.out.println(""); // salto de linea, para que al correr se diferencien los procesos
    }

    private static void buscarEntrada() {
        System.out.println(""); //separa del menu anterior al leer
        System.out.print("Ingrese el número de la entrada a buscar: ");
        int numero = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        boolean encontrada = false;
        for (Entrada entrada : entradasVendidas) {
            if (entrada.numero == numero) {
                System.out.println("Entrada encontrada:");
                System.out.printf("Número: %d, Fila: %d, Columna: %d, Tipo: %s, Precio Final: $%.2f\n", 
                entrada.numero, entrada.fila, entrada.columna, entrada.tipo, entrada.precioFinal);
                encontrada = true;
                break;
            }
        }
        if (!encontrada) {
            System.out.println("Entrada no encontrada.");
        }
    }

    private static void eliminarEntrada() {
        System.out.println(""); //separa del menu anterior al leer
        System.out.print("Ingrese el número de la entrada a eliminar: ");
        int numero = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        //verificar si realmente quiere eliminar la entrada, para evitar borrar por error
        System.out.println("¿Está realmente seguro que desea eliminar la entrada? (S/N)");
        char confirmacion;
        
        //validacion de la respuesta
        do{
            confirmacion = Character.toUpperCase(scanner.next().charAt(0));
            if (confirmacion != 'S' && confirmacion != 'N'){
                System.out.println("Respuesta inválida. Por favor, ingrese S para confirmar o N para cancelar.");
            }
        }while (confirmacion != 'S' && confirmacion != 'N');{
        
        // Si el usuario confirma la eliminación
        if (confirmacion == 'S'){
            boolean eliminada = false;
            for (Entrada entrada : entradasVendidas){
                if (entrada.numero == numero){
                    // Liberar el asiento, eliminar la entrada de la lista y actualizar totales
                    sala[entrada.fila - 1][entrada.columna - 1] = 'O'; // Liberar el asiento 
                    entradasVendidas.remove(entrada);
                    totalIngresos -= entrada.precioFinal;
                    totalEntradasVendidas--; //Elimina la entrada del total comprado
                    System.out.println("Entrada eliminada correctamente.");
                    eliminada = true;
                    break;
                }else{
                    System.out.println("No se eliminó la entrada");
                    return;        
            }
        }
        // si no esta la entrada seleccionada
        if (!eliminada){
            System.out.println("Entrada seleccionada no se encontró");
        }
        }else{
            System.out.println("Se canceló la eliminación de entrada");
        }           
        }
        System.out.println(" ");//espacio para separar del codigo al momento de leer
        
    }

    private static void finalizarCompra() {
        System.out.println(""); //separa del menu anterior al leer
        System.out.println("Proceso de pago");
        System.out.printf("Total de entradas compradas: %d\n", totalEntradasVendidas);
        if (totalEntradasVendidas > 0) {
            if (totalEntradasVendidas >= 5) {
                double descuentoAdicional = totalIngresos * DESCUENTO_CANTIDAD;
                System.out.printf("Descuento adicional por comprar más de 5 entradas: $%.2f\n", descuentoAdicional);
                totalIngresos -= descuentoAdicional;
            }
            System.out.printf("Total a pagar: $%.2f\n", totalIngresos);
            
            System.out.println("Ingrese su Pago");
            int pago = 0;
            pago = scanner.nextInt();
            
            //validacion del pago
            while (pago < totalIngresos){
                System.out.println("El pago debe ser mayor o igual al Total a pagar");
                System.out.println("Reingrese su pago");
                pago = scanner.nextInt();
            }
            //Vuelto
            int vuelto = (int) (pago - totalIngresos);
            System.out.println("Su vuelto es: $ " + vuelto);
            
            //impresion del ticket
            System.out.println("=====================================================================================");
            System.out.println("                 COMPROBANTE DE COMPRA TEATRO MORO");
            System.out.println("======================================================================================");
            System.out.println("Función: El Quijote de la Mancha");
            System.out.println("Hora de funcion: 19:00 horas");
            System.out.println(" ");
            System.out.println("Total entradas compradas: " + totalEntradasVendidas);
            System.out.println("Resumen de entradas " );
            System.out.println("_______________________________________________");
            for (Entrada entrada : entradasVendidas){
                System.out.printf("Entrada %d: -Fila %d, -Columna %d, -Tipo de descuento: %s, -Precio Final: $%.2f\n", 
                                  entrada.numero, entrada.fila, entrada.columna, entrada.tipo, entrada.precioFinal);
            }
            System.out.println("_______________________________________________");
            System.out.println("Total a pagar, incluido descuentos: $ " + totalIngresos);
            System.out.println("Pago: $ " + pago);
            System.out.println("Vuelto: $ " + vuelto);
            System.out.println("Disfrute su Función");
            System.out.println("=====================================================================================");
            
            
            
        } else {
            // terminar el programa sin compras
            System.out.println("No se han realizado compras.");
            System.out.println("Gracias Por visitar Teatro Moro");
        }
    }   
}