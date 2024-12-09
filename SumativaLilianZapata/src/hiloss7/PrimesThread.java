/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hiloss7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author lilia
 */

// Clase que implementa Runnable para procesar números de la lista
public class PrimesThread implements Runnable {
    //atributos
    private final PrimesList listaPrimos;                           //manejo de los numero primos
    private final ConcurrentLinkedQueue<Integer> queue;        // Cola de números a procesar
    private final Object lock;                                 // Objeto para sincronización
    
    //constructor
    public PrimesThread(PrimesList listaPrimos, ConcurrentLinkedQueue<Integer> queue, Object lock) {
        this.listaPrimos = listaPrimos;
        this.queue = queue;
        this.lock = lock;
        
    }
    
    //metodo sobre escrito, que permite que corran lo shilos
    @Override
    public void run() {
        while (true) {
            Integer numero;
            synchronized (queue) {
                while (queue.isEmpty()) {       //se espera qque la cola este vacia
                    try {
                        queue.wait(); // Espera a que haya datos en la cola
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();  // si la espera se interrumpe, se sale del metodo al inicio
                        return;
                    }
                }
                //se abren las colas para que trabajen
                numero = queue.poll();
                queue.notifyAll(); // Notifica que la cola ha cambiado
            }

            if (numero == null) break;         //si no hay numero se sale del ciclo

            //verificacion si es primo llandno al metodo
            if (NumerosPrimosList.isPrime(numero)) {
                synchronized (lock) {
                    if (listaPrimos.agregarPrimo(numero)) {   //se agrega el numero a la lista sino esta ingresado
                        System.out.println("Número primo agregado: " + numero + ", por hilo: " + Thread.currentThread().getName());
                    }
                }
            }
        }
    }
    
    // Carga numeros desde un archivo CSV y los agrega a una cola
    public static ConcurrentLinkedQueue<Integer> loadToQueue(String fileName) {
        //se crea una cola nueva para guadrar lo primos
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
       
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;         //se leen todas alas lineas del archivo
            while ((line = reader.readLine()) != null) {
                try {
                    queue.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException e) {
                    System.err.println("Error al extraer el numero: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return queue;
    }
}
