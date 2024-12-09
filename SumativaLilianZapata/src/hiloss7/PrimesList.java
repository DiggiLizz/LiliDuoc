
package hiloss7;

import static hiloss7.NumerosPrimosList.isPrime;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

class PrimesList extends ArrayList<Integer> implements PrimesListInterface {
    private  final List<Integer> numerosPrimos = Collections.synchronizedList(new ArrayList<>()); // Declarar como final para evitar modificaciones accidentales
    
    // Carga numeros desde un archivo CSV y los agrega a una cola
    public static ConcurrentLinkedQueue<Integer> loadToQueue(String fileName) {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                queue.add(Integer.parseInt(line)); // Añade cada número a la cola
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar desde archivo: " + e.getMessage());
        }
        return queue;
    }
    
    
    //metodo para agregar los numero primos a la lista, un hilo a la vez
    public boolean agregarPrimo(int numero) {
        if (!NumerosPrimosList.isPrime(numero)) {
            throw new IllegalArgumentException("El numero ingresado no es primo.");
        }
        synchronized (this) {
            if (!numerosPrimos.contains(numero)) {
                numerosPrimos.add(numero);
                return true;
            }
        }
        return false; // Número duplicado, no se agregó.
    }
    
    //metodo para eliminar un numero primo
    @Override
    public boolean eliminarPrimo(int primoAEliminar) {
        synchronized (numerosPrimos) {
            if (!numerosPrimos.contains(primoAEliminar)) {
                throw new IllegalArgumentException("El número no está en la lista.");
            }
            return numerosPrimos.remove(Integer.valueOf(primoAEliminar));
        }
    }
    
    //metodo para imprimir los números primos ordenados
    public void imprimirNumerosOrdenados() {
        synchronized (numerosPrimos){
            List<Integer> copiaOrdenada = new ArrayList<>(numerosPrimos);
            Collections.sort(copiaOrdenada);                                //ordena de menos amayor
            System.out.println("Los numeros primos ordenados son: " + copiaOrdenada);
        }
    }
    
    //metodo para buscar un numero primo especifico
    public boolean contienePrimo(int primoABuscar) {
        synchronized (numerosPrimos){
            return numerosPrimos.contains(primoABuscar);
        }
    }
    
    //metodo para contar los numero primos
    public int getPrimesCount(){
        synchronized (numerosPrimos){
            return numerosPrimos.size();
        }
    }

    //metodo sincronizado para tener los numero ordenados
    public synchronized List<Integer> getNumerosPrimosOrdenados() {
        List<Integer> copiaOrdenada = new ArrayList<>(numerosPrimos);
        Collections.sort(copiaOrdenada);  //se ordenan de menos a mayor los numeros
        return copiaOrdenada;
    }
}
