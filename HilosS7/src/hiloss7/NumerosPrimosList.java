
package hiloss7;

import java.util.ArrayList;
import java.util.List;


public class NumerosPrimosList extends  ArrayList<Integer> {

    //metodo para saber si es numero primo o no
    public static boolean isPrime(int numero) {
        if (numero <= 1) {  //por definicion lo numero menores a uno, no son primos
            return false;
        }
        for (int i = 2; i * i <= numero; i++) { //se divide por otro numero que no sea el mismo, por lo tanto no es primo
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }
}
