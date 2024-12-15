
package eft_s9_lilian_zapata.vehiculos_alquiler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;




public class Auxiliar {
    // Utilizar ConcurrentHashMap para garantizar la seguridad en entornos multihilo
    public static Map<String, Vehiculos> vehiculosPorPatente = new ConcurrentHashMap<>();

    //metodo de unicidad de patentes
    public static boolean unicidadPatentes(String patente) {
        return !vehiculosPorPatente.containsKey(patente);
    }

    //metofo para ver duracion y coste extra
    public static double calcularCostoAdicionalPorDuracion(int duracion){
        double costoPorDiaAdicional = 50.0; 
        return Math.max(0, duracion - 7) * costoPorDiaAdicional;
        
    }
}
    
    
    

