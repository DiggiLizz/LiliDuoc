
package hiloss7;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HilosS7 {

    
    public static void main(String[] args) throws IOException {
        
        // Inicialización de la lista al inicio del método principal
        PrimesList listaPrimos = new PrimesList();
        Object lock = new Object(); // Objeto para sincronización

        // Cargar numeros desde el archivo
        ConcurrentLinkedQueue<Integer> queue = PrimesThread.loadToQueue("numeros1.csv");
        
        Scanner teclado = new Scanner(System.in);
        boolean salir = false;
        
        System.out.println("     NUMEROS PRIMOS PARA ENCRIPTACION");
        System.out.println("");
        
        while(!salir){
            try{
                System.out.println(""); //espacio en blanco
                System.out.println("            MENU PRINCIPAL");
                System.out.println("");
                System.out.println("1.- Carga de Archivos");
                System.out.println("2.- Ingresar un numero y ver si es primo");
                System.out.println("3.- Buscar un numero primo");
                System.out.println("4.- Eliminar un numero primo");
                System.out.println("5.- Mostar lista de numero primos");
                System.out.println("6.- Contar los numeros Primos");
                System.out.println("7.- Encriptar mensaje");
                System.out.println("8.- Desencriptar ultimo mesnsaje");
                System.out.println("0.- Salir del sistema");
                System.out.print("   Elija una opcion: ");
                int opcion = teclado.nextInt();

                switch (opcion){
                    case 1:
                        
                        
                        System.out.println("Cargar de archivos");
                        
                        

                        // Crear un pool de hilos
                        ExecutorService executor = Executors.newFixedThreadPool(3); // Tres hilos
                        for (int i = 0; i < 3; i++) {
                            executor.execute(new PrimesThread(listaPrimos, queue, lock));
                        }

                        executor.shutdown(); // Finalizar el pool de hilos

                        // Esperar a que todos los hilos terminen
                        while (!executor.isTerminated()) {
                        }

                        // Ahora todos los números primos deberían estar en la lista listaPrimos
                        System.out.println("Todos los hilos han terminado. Los numeros primos son:");
                        listaPrimos.imprimirNumerosOrdenados();

                        // Mostrar la cantidad de números impares
                        //System.out.println("Total de numeros impares: " + listaPrimos.getPrimesCount());
                        break;
                    case 2:
                        System.out.print("Ingrese el numero para ver si es primo y agregra a la lista: ");
                        int numero = teclado.nextInt();
                        if (listaPrimos.agregarPrimo(numero)){
                            System.out.println(numero + " es un numero primo y se agreo a la lista");
                        }else {
                            throw new IllegalArgumentException("El numero ingresado no es Primo");
                        }
                        break;
                    case 3:
                        try {
                            System.out.print("Ingrese el numero primo que desea buscar: ");
                            int numeroABuscar = teclado.nextInt();

                            if (listaPrimos.contienePrimo(numeroABuscar)) {
                                System.out.println("El numero primo " + numeroABuscar + " se encontro en la lista");
                            } else {
                                System.out.println("El numero ingresado no se encontro en la lista");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Error: Debes ingresar un numero entero");
                            teclado.next(); // Limpia el buffer del scanner
                        }
                        break;
                    case 4:
                        try {
                            System.out.print("Ingrese el numero primo que desea eliminar: ");
                            int primoAEliminar = teclado.nextInt();
                            if (listaPrimos.eliminarPrimo(primoAEliminar)) {
                                System.out.println("El numero ingresado: " + primoAEliminar + ", fue eliminado.");
                            } else {
                                System.out.println("El numero ingresado: " + primoAEliminar + ", no se encontró en la lista.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Error: Debe ingresar un número entero valido.");
                        }
                        break;
                    case 5:
                        //Mostrar los numero primos generados de los 2 hilos, ordenados
                        listaPrimos.imprimirNumerosOrdenados();
                        break;
                    case 6:
                        //contar los numero primos que hay en la lista
                        System.out.print("Cantidad de numeros primos: " + listaPrimos.getPrimesCount());
                        break;
                    case 7: 
                        System.out.print("Ingrese el mensaje que desea encriptar: ");
                        teclado.nextLine(); // Consumir el salto de línea pendiente
                        String mensaje = teclado.nextLine();

                        // Llamar al método de encriptación
                        Encriptador.encriptarMensaje(mensaje, listaPrimos);
                        break;
                    case 8:
                        // Llamar al metodo para desencriptar el ultimo mensaje
                        String mensajeDesencriptado = Encriptador.desencriptarUltimoMensaje(listaPrimos);
                        if (mensajeDesencriptado != null) {
                            System.out.println("El ultimo mensaje desencriptado es: " + mensajeDesencriptado);
                        }
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema de numero primos");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opcion ingresada no valida");
                }
            }catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Error: Ingrese un valor numerico valido.");
            }
        }
        teclado.close();
    }
    
}
