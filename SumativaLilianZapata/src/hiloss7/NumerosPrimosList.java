
package hiloss7;

import java.util.concurrent.CopyOnWriteArrayList;


public class NumerosPrimosList extends  CopyOnWriteArrayList<Integer> {
    
    //metodo para saber si es numero primo o no
    public static boolean isPrime(int numero) {
        if (numero <= 1) return false;                          //numeros menos o igual a uno, no son primos
        if (numero <= 3) return true;                           //2 y 3 es primo
        if (numero % 2 == 0 || numero % 3 == 0) return false;   //si se divide en 2 o 3 no es primo

        for (int i = 5; i * i <= numero; i += 6) {              //se revisa si el numero tiene un divisor mayor que si raiz cuadrada
            if (numero % i == 0 || numero % (i + 2) == 0) return false; //si el numero se divide i o se le suma 2, no es primo
        }
        
        return true;                                            //sino se encontro divisor, es un numero primo
    }
}
