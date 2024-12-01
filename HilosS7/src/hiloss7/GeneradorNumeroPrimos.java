/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hiloss7;

import static hiloss7.NumerosPrimosList.isPrime;

/**
 *
 * @author lilia
 */
public class GeneradorNumeroPrimos implements Runnable {
    //atributos
    private final PrimesList manejo;    //manejo de los numero primos
    private final int inicio;           //rango de inicio de los hilos
    private final int fin;              //rango de termino de los hilos

    //constructor
    public GeneradorNumeroPrimos(PrimesList manejo, int inicio, int fin) {
        this.manejo = manejo;
        this.inicio = inicio;
        this.fin = fin;
    }
    
    //metodo sobre escrito, que permite que corran lo shilos
    @Override
    public void run(){
        // Generar números primos dentro del rango especificado
        for (int i = inicio; i <= fin; i++) {
            if (isPrime(i)) { // Verifica si el número es primo
                manejo.agregar(i); // Agrega el número primo al gestor
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.out.println("Hilo interrumpido: " + e.getMessage());
                }
            }
        }
    }
}
