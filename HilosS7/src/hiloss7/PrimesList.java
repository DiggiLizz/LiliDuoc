
package hiloss7;

import static hiloss7.NumerosPrimosList.isPrime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class PrimesList extends ArrayList<Integer> implements PrimesListInterface {
    //lista que almacena los numeros primos
    private final List<Integer> numerosPrimos;

    //constructor
    public PrimesList() {
        // se utulizan la coleccion de sincornizacion para que corran los hilos simultaneos
        this.numerosPrimos = Collections.synchronizedList(new NumerosPrimosList());
    }
    
    //muestra la lista de numeros primos
    public List<Integer> getPrimesList(){
        return numerosPrimos;
    }
    
    //metodo para agregar los numero primos a la lista, un hilo a la vez
    public synchronized void agregar(int numero) {
        if (isPrime(numero)) {
            numerosPrimos.add(numero);
        }
    }
    
    // Método para eliminar un número primo de la lista
    @Override
    public synchronized boolean eliminarPrimo(int primoAEliminar) {
        boolean eliminado = numerosPrimos.remove(Integer.valueOf(primoAEliminar));

        return eliminado;
    }
    
    // Método para imprimir los números primos ordenados
    public synchronized void imprimirNumerosOrdenados() {
        List<Integer> copiaOrdenada = new ArrayList<>(numerosPrimos);
        Collections.sort(copiaOrdenada);                                //ordena de menos amayor
        System.out.println("Los numeros primos ordenados son: " + copiaOrdenada);
    }
    
    //metodo para agregar un solo numero y ver si es primo para guardarlo en la lista
    public synchronized boolean agregarSiEsPrimo(int numero) {
        if (isPrime(numero)) {
            numerosPrimos.add(numero);
            return true;
        } else {
            return false;
        }
    }
    
    //metodo para buscar un numero primo especifico
    public synchronized boolean contienePrimo(int primoABuscar) {
        return numerosPrimos.contains(primoABuscar);
    }
}
