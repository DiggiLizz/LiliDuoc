/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hiloss7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author lilia
 */
public class Encriptador {
    public static void encriptarMensaje(String mensaje, PrimesList listaPrimos) throws IOException {
        // Validar si hay números primos en la lista
        if (listaPrimos.getPrimesCount() == 0) {
            System.out.println("La lista de números primos está vacía. No se puede encriptar el mensaje.");
            return;
        }

        //nombre del archivo donde se guarda el mensaje encriptado
        String archivoSalida = "mensajesEncriptados.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSalida, true))) { // Modo append
            StringBuilder mensajeEncriptado = new StringBuilder();
            List<Integer> primos = listaPrimos.getNumerosPrimosOrdenados(); // Obtener lista ordenada de números primos

            for (int i = 0; i < mensaje.length(); i++) {
                char caracter = mensaje.charAt(i);
                int primo = primos.get(i % primos.size()); // Usar números primos de forma cíclica
                char encriptado = (char) (caracter + primo); // Encriptar carácter
                mensajeEncriptado.append(encriptado);
            }

            // Escribir el mensaje encriptado en el archivo
            writer.write(mensajeEncriptado.toString());
            writer.newLine(); // Agregar una nueva línea para separar mensajes
            
            System.out.println("Mensaje encriptado: " + mensajeEncriptado);
            System.out.println("Mensaje encriptado guardado en el archivo: " + archivoSalida);

        } catch (IOException e) {
            System.err.println("Error al guardar el mensaje en el archivo: " + e.getMessage());
        }
    }
    
    // Método para desencriptar el último mensaje del archivo
    public static String desencriptarUltimoMensaje(String archivoEntrada, PrimesList listaPrimos) {
        // Validar si hay números primos en la lista
        if (listaPrimos.getPrimesCount() == 0) {
            System.out.println("La lista de números primos está vacía. No se puede desencriptar el mensaje.");
            return null;
        }

        String mensajeEncriptado = null;

        // Leer solo la última línea del archivo
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoEntrada))) {
            String line;
            while ((line = reader.readLine()) != null) {
                mensajeEncriptado = line; // Guardar la última línea
            }

            if (mensajeEncriptado == null) {
                System.out.println("El archivo está vacío.");
                return null;
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return null;
        }

        // Desencriptar el mensaje usando los números primos
        StringBuilder mensajeDesencriptado = new StringBuilder();
        List<Integer> primos = listaPrimos.getNumerosPrimosOrdenados(); // Obtener lista ordenada de números primos

        for (int i = 0; i < mensajeEncriptado.length(); i++) {
            char caracterEncriptado = mensajeEncriptado.charAt(i);
            int primo = primos.get(i % primos.size()); // Usar números primos de forma cíclica
            char caracterOriginal = (char) (caracterEncriptado - primo); // Revertir la transformación
            mensajeDesencriptado.append(caracterOriginal);
        }

        return mensajeDesencriptado.toString();
    }
    
    // Sobrecarga para usar el archivo predeterminado
    public static String desencriptarUltimoMensaje(PrimesList listaPrimos) {
        return desencriptarUltimoMensaje("mensajesEncriptados.txt", listaPrimos);
    }
    
}
    
   
