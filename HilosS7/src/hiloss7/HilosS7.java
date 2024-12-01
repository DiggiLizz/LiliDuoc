
package hiloss7;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class HilosS7 {

    
    public static void main(String[] args) {
        //creacion de instancia para manejar los numeros primos
        PrimesList manejo = new PrimesList();
        Scanner teclado = new Scanner(System.in);
        boolean salir = false;
        
        System.out.println("     NUMEROS PRIMOS PARA ENCRIPTACION");
        System.out.println("");
        
        while(!salir){
            try{
                System.out.println("");
                System.out.println("   Elija una opcion para ver numero primos");
                System.out.println("");
                System.out.println("1.- Buscar numero primos segun un rango");
                System.out.println("2.- Ingresar un numero y ver si es primo");
                System.out.println("3.- Buscar un numero primo");
                System.out.println("4.- Eliminar un numero primo");
                System.out.println("5.- Mostar lista de numero primos");
                System.out.println("0.- Salir del sistema");
                int opcion = teclado.nextInt();

                switch (opcion){
                    case 1:
                        System.out.println("Para ver los numeros primos que hay en un rango, ingrese la siguiente informacion");
                        System.out.print("Ingrese el numero de inicio del rango: ");
                        int inicio = teclado.nextInt();
                        System.out.print("Ingrese el numero de fin de rango: ");
                        int fin = teclado.nextInt();


                        //creacion de los hilos para que se realicen de manera simultanea
                        Thread hilo1 = new Thread(new GeneradorNumeroPrimos(manejo, inicio, (inicio + fin) / 2));  //se toma el inico de rango hasta la mitad
                        Thread hilo2 = new Thread(new GeneradorNumeroPrimos(manejo, (inicio + fin) /2 +1, fin));   //se toma de la mitas + 1 hasta el numero de fin de rango

                        //inicializacion de los hilos
                        hilo1.start();
                        hilo2.start();

                        //manejo de caida si los hilos se llegan a detener
                        try{
                            hilo1.join();
                            hilo2.join();
                        }catch (InterruptedException e){
                            System.out.println("Los hilos se han interrumpido: " + e.getMessage());
                        }
                        //Mostrar los numero primos generados de los 2 hilos, ordenados
                        manejo.imprimirNumerosOrdenados();
                        break;
                    case 2:
                        System.out.print("Ingrese el numero para ver si es primo y agregra a la lista: ");
                        int numero = teclado.nextInt();
                        if (manejo.agregarSiEsPrimo(numero)){
                            System.out.println(numero + " es un numero primo y se agreo a la lista");
                        }else {
                            throw new IllegalArgumentException("El numero ingresado no es Primo");
                        }
                        break;
                    case 3:
                        try {
                            System.out.print("Ingrese el numero primo que desea buscar: ");
                            int numeroABuscar = teclado.nextInt();

                            if (manejo.contienePrimo(numeroABuscar)) {
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
                            if (manejo.eliminarPrimo(primoAEliminar)) {
                                System.out.println("El numero ingresado: " + primoAEliminar + ", fue eliminado.");
                            } else {
                                System.out.println("El numero ingresado: " + primoAEliminar + ", no se encontró en la lista.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Error: Debe ingresar un número entero válido.");
                        }
                        break;
                    case 5:
                        //Mostrar los numero primos generados de los 2 hilos, ordenados
                        manejo.imprimirNumerosOrdenados();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema de numero primos");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opcion ingresada no valida");
                }
            }catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Error: Ingrese un valor numérico válido.");
            }
        }
        teclado.close();
    }
}
