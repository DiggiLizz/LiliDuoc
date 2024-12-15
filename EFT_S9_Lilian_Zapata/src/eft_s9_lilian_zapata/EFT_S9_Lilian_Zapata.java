
package eft_s9_lilian_zapata;

//importaciones
import eft_s9_lilian_zapata.vehiculos_alquiler.Arriendo;
import static eft_s9_lilian_zapata.vehiculos_alquiler.Arriendo.vehiculosArrendados;
import eft_s9_lilian_zapata.vehiculos_alquiler.Auxiliar;
import static eft_s9_lilian_zapata.vehiculos_alquiler.Auxiliar.vehiculosPorPatente;
import eft_s9_lilian_zapata.vehiculos_alquiler.Vehiculos;
import eft_s9_lilian_zapata.vehiculos_alquiler.VehiculosCarga;
import eft_s9_lilian_zapata.vehiculos_alquiler.VehiculosPasajeros;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;


public class EFT_S9_Lilian_Zapata {
    //lista
    private static final ConcurrentLinkedQueue<Vehiculos> vehiculos = new ConcurrentLinkedQueue<>();
    private static final ConcurrentMap<String, Vehiculos> vehiculosPorPatente = new ConcurrentHashMap<>();
    public static Scanner teclado = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        
        System.out.println(" BIENVENIDOS A 'AUTOFLEX RENTALS'");
        System.out.println("");                                         //espacio en blanco
        System.out.println("Tenemos disponibles ");
        System.out.println("* Vehiculos Transporte de pasajeros");
        System.out.println("* Vehiculos Carga");
        
        boolean salir = false;
        while(!salir){
            try{
                System.out.println("");
                System.out.println("--------MENU PRINCIPAL---------");
                System.out.println("1.- Agregar vehiculo");
                System.out.println("2.- Cargar Archivo de vehiculos");
                System.out.println("3.- Listado de vehiculos");
                System.out.println("4.- Arriendo de Vehiculo");
                System.out.println("5.- Boleta de Arriendo");
                System.out.println("6.- vehiculos en arriendo");
                System.out.println("0.- Salir del sistema");
                System.out.print("Ingrese una opcion: ");
                int opcion = teclado.nextInt();

                switch (opcion){
                    case 1:
                        System.out.println("");                                              //espacio en blanco
                        System.out.println("MENU INGRESO DE VEHICULOS");
                        System.out.println("1.- Vehiculo de Pasajeros");
                        System.out.println("2.- Vehiculo de Carga");
                        System.out.println("3.- Volver al menu principal");
                        System.out.print("Elija una opcion: ");

                        try {
                            int vehiculoAElegir = teclado.nextInt();
                            teclado.nextLine(); // Limpiar el buffer del teclado

                            switch (vehiculoAElegir) {
                                case 1:
                                    System.out.println("");                                           //espacio en blanco
                                    System.out.println("Bienvenido al Ingreso de Vehiculo de Pasajeros");
                                    System.out.println("");
                                    System.out.println("Ingrese los datos del vehiculo de pasajeros:");
                                    
                                    // Crear un vehículo de pasajeros
                                    int numeroRuedas = numeroRuedas();
                                    String color = color();
                                    int numeroPuertas = numeroPuertas();
                                    String marca = marca();
                                    String modelo = modelo();
                                    String patente = patente();
                                    // Verificar la unicidad de la patente antes de crear el vehículo
                                    if (!Auxiliar.unicidadPatentes(patente)) {
                                        System.err.println("La patente " + patente + " ya esta registrada.");
                                        break;
                                    }
                                    
                                    System.out.print("Numero de asientos: ");
                                    int numeroAsientos = 0;
                                    while (true) {
                                        try {
                                            numeroAsientos = teclado.nextInt();
                                            if (numeroAsientos > 0) {
                                                
                                                if (numeroAsientos <= 40) { //no hay buses mas grande
                                                    break; 
                                                } else {
                                                    System.out.println("El numero maximo de asientos es 40");
                                                }
                                            } else {
                                                System.out.println("Ingrese un numero mayor a cero");
                                            }
                                        } catch (InputMismatchException e) {
                                            System.out.println("Entrada invalida. Ingrese un numero mayor a ccero");
                                            teclado.nextLine(); // Limpia el buffer del teclado
                                        }
                                    }
                                    // Crear el objeto VehiculoPasajeros
                                    Vehiculos nuevoVehiculo = new VehiculosPasajeros(numeroRuedas, color, numeroPuertas, marca, modelo, patente, "pasajeros", numeroAsientos);
                                    vehiculos.add(nuevoVehiculo);
                                    System.out.println("Vehiculo ingresado Exitosamente");
                                    System.out.println("------------------------------------------------------");
                                    break;
                                case 2:
                                    System.out.println(""); //espacio en blanco
                                    System.out.println("Bienvenido al Ingreso de Vehiculo de Carga");
                                    System.out.println("Ingrerse los datos del vehiculo");
                                    
                                    //crear un vehiculo de carga
                                    
                                    do{
                                        System.out.print("Numero de Ruedas: ");
                                        numeroRuedas = teclado.nextInt();
                                    }while(numeroRuedas < 4);             //minimo de ruedas 
                                    
                                    
                                    System.out.print("Color: ");
                                    color = teclado.nextLine();

                                    while (!color.matches("[a-zA-Z-]+")) {
                                        System.out.println("El color debe contener solo letras y guiones. Ingrese el color nuevamente");
                                        color = teclado.nextLine();
                                    }
                                    
                                    
                                    do{
                                        System.out.print("Numero de Puertas: ");
                                        numeroPuertas = teclado.nextInt();
                                    }while(numeroPuertas < 2);   //minimo de puertas
                                    
                                    do {
                                        System.out.print("Marca: ");
                                        marca = teclado.nextLine();
                                    } while (!marca.matches("[a-zA-Z0-9\\s]+"));

                                    // Bucle para validar modelo
                                    do {
                                        System.out.print("Modelo: ");
                                        modelo = teclado.nextLine();
                                    } while (!modelo.matches("[a-zA-Z0-9\\s\\-\\.]+"));

                                    // Bucle para validar patente
                                    do {
                                        System.out.print("Patente (AAAA12): ");
                                        patente = teclado.nextLine().toUpperCase();
                                    } while (!patente.matches("[A-Z]{4}\\d{2}"));
                                    // Verificar la unicidad de la patente antes de crear el vehículo
                                    if (!Auxiliar.unicidadPatentes(patente)) {
                                        System.err.println("La patente " + patente + " ya esta registrada.");
                                        break;
                                    }
                                    
                                    System.out.print("Capacidad de carga (toneladas): ");
                                    double capacidadCargaTonelada = 0;
                                    while (true) {
                                        try {
                                            capacidadCargaTonelada = teclado.nextDouble();
                                            if (capacidadCargaTonelada > 0) {
                                                break; // Sale del bucle si el valor es positivo
                                            } else {
                                                System.out.println("Solo se permiten numeros positivos");
                                            }
                                        } catch (InputMismatchException e) {
                                            System.out.println("Solo se permiten numeros positivos");
                                            teclado.nextLine(); // Limpia el buffer del teclado
                                        }
                                    }
                                    
                                    System.out.print("Costo fijo: ");
                                    double costoFijoPorKm = 0;
                                    while (true) {
                                        try {
                                            costoFijoPorKm = teclado.nextDouble();
                                            if (costoFijoPorKm > 0) {
                                                break; // Sale del bucle si el valor es positivo
                                            } else {
                                                System.out.println("Solo se permiten numeros positivos");
                                            }
                                        } catch (InputMismatchException e) {
                                            System.out.println("Solo se permiten numeros positivos");
                                            teclado.nextLine(); // Limpia el buffer del teclado
                                        }
                                    }
                                    
                                    System.out.print("Costo variable: ");
                                    double costoVariablePorKm = 0;
                                    while (true) {
                                        try {
                                            costoVariablePorKm = teclado.nextDouble();
                                            if (costoVariablePorKm > 0) {
                                                break; 
                                            } else {
                                                System.out.println("Solo se permiten numeros positivos");
                                            }
                                        } catch (InputMismatchException e) {
                                            System.out.println("Solo se permiten numeros positivos");
                                            teclado.nextLine(); // Limpia el buffer del teclado
                                        }
                                    }

                                    // Crear el objeto VehiculosCarga
                                    nuevoVehiculo = new VehiculosCarga(numeroRuedas, color, numeroPuertas, marca, modelo, patente, "carga", capacidadCargaTonelada, costoFijoPorKm, costoVariablePorKm);
                                    vehiculos.add(nuevoVehiculo);
                                    System.out.println("Vehiculo ingresado Exitosamente");
                                    System.out.println("------------------------------------------------------");
                                    break;
                                case 3:
                                    System.out.println("Volviendo a Menu Principal");
                                    break;
                                default:
                                    System.out.println("Opcion de vehiculo invalida");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Error: Debe ingresar un numero valido para el tipo de vehiculo.");
                            teclado.nextLine(); // Limpiar el buffer del teclado
                        }
                        break;
                    case 2:
                        System.out.print("Carga de archivo Vehiculos: ");
                        String nombreArchivo = "vehiculos.csv";
                        cargarArchivo(nombreArchivo);
                        
                        break;
                    case 3:
                        System.out.println("Mostrando lista de vehiculos ingresados");
                        mostrarVehiculos();
                        break;
                    case 4:
                        System.out.println("Bienvenido al arriendo de vehiculos");
                        Arriendo.arrendarVehiculo(vehiculos, vehiculosArrendados);
                        break;
                    case 5:
                        System.out.println("Bienvenido al sistema de boletas");

                        // Mostrar la lista de arriendos
                        System.out.println("Arrendamientos disponibles:");
                        for (int i = 0; i < vehiculosArrendados.size(); i++) {
                            System.out.println((i + 1) + ". " + vehiculosArrendados.get(i).getVehiculo().getPatente() + " - Cliente: " + vehiculosArrendados.get(i).getNombreCliente());
                        }

                        // Solicitar al usuario el numero de arriendo
                        System.out.print("Ingrese el numero del arriendo: ");
                        int indiceArriendo = teclado.nextInt() - 1; // Restamos 1 para ajustar al indice de la lista

                        // Validar el índice del arriendo
                        if (indiceArriendo >= 0 && indiceArriendo < vehiculosArrendados.size()) {
                            Arriendo arriendoSeleccionado = vehiculosArrendados.get(indiceArriendo);

                            
                            arriendoSeleccionado.mostrarBoleta();
                        } else {
                            System.out.println("Numero de arriendo invalido.");
                        }
                        break;
                    case 6:
                        System.out.println("Mostrando vehiculos en arriendo");
                        if (vehiculosArrendados.isEmpty()) {
                            System.out.println("No hay vehiculos actualmente arrendados.");
                        } else {
                            int contador = 1;
                            for (Arriendo arriendo : vehiculosArrendados) {
                                System.out.println("Arriendo " + contador++);
                                System.out.println(arriendo.getVehiculo().toString());
                                System.out.println("Duracion: " + arriendo.getDuracion() + " dias");
                                System.out.println("Distancia estimada: " + arriendo.getDistancia() + " km");
                                System.out.println("Costo total: $" + arriendo.getCostoTotal());
                                System.out.println("---------------------------------------");
                            }

                            // Preguntar si desea guardar la lista en un archivo
                            System.out.print("Desea guardar la lista de arriendos en un archivo? (s/n): ");
                            Scanner respuesta = new Scanner(System.in);
                            String guardar = respuesta.nextLine().toLowerCase();

                            if (guardar.equals("s")) {
                                // Llamar a un método para guardar la lista en un archivo
                                guardarListaArrendadosEnArchivo(vehiculosArrendados);
                                System.out.println("Lista de arriendos guardada exitosamente.");
                            }
                        }
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema 'AUTOFLEX RENTALS' ");
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

    private static void cargarArchivo(String nombreArchivo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            String[] datos;

            // Saltar la primera línea si es un encabezado
            lector.readLine();

            while ((linea = lector.readLine()) != null) {
                datos = linea.split(";");

                // Crear un objeto Vehiculo a partir de los datos
                Vehiculos nuevoVehiculo = crearVehiculo(datos);

                //se agregan a la losta si se leyo bien
                if (nuevoVehiculo != null) {
                    vehiculos.add(nuevoVehiculo);
                }
            }

            System.out.println("Archivo cargado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    private static Vehiculos crearVehiculoCarga(String[] datos) {
        if (datos.length <11 ) {
        System.err.println("Linea invalida (Vehiculo Carga): Datos insuficientes");
        return null;
        }

        try {
            int numeroRuedas = Integer.parseInt(datos[0]);
            String color = datos[1];
            int numeroPuertas = Integer.parseInt(datos[2]);
            String marca = datos[3];
            String modelo = datos[4];
            String patente = datos[5];
            
            // Verificar la unicidad de la patente antes de crear el vehículo
            if (!Auxiliar.unicidadPatentes(patente)) {
                System.err.println("La patente " + patente + " ya esta registrada.");
                return null;
            }
            
            double capacidadCargaToneladas = Double.parseDouble(datos[9]); // Asegurarse de que se convierte a double
            double costoFijoPorKm = Double.parseDouble(datos[10]);
            double costoVariablePorKm = Double.parseDouble(datos[11]);

            // Validación adicional
            if (capacidadCargaToneladas <= 0 || costoFijoPorKm < 0 || costoVariablePorKm < 0) {
                System.err.println("Valores inválidos para el vehículo de carga.");
                return null;
            }
            Vehiculos nuevoVehiculo = new VehiculosCarga(numeroRuedas, color, numeroPuertas, marca, modelo, patente, "carga", capacidadCargaToneladas, costoFijoPorKm, costoVariablePorKm);
            
            // Bloquear el acceso a ambas estructuras
            synchronized (vehiculos) {
                vehiculos.add(nuevoVehiculo);
                vehiculosPorPatente.put(patente, nuevoVehiculo);

                // Utilizar putIfAbsent directamente sin verificación adicional
                if (vehiculosPorPatente.putIfAbsent(patente, nuevoVehiculo) != null) {
                    System.err.println("Error: Se produjo un conflicto al agregar el vehiculo. La patente ya existe.");
                    return null;
                }
            }
            
            return nuevoVehiculo;
        } catch (NumberFormatException e) {
            System.err.println("Error: Datos invalidos para Vehiculo Carga (formato)");
            return null;
        }
    }

    private static Vehiculos crearVehiculoPasajeros(String[] datos) {
        if (datos.length < 11) {
        System.err.println("Linea invalida (Vehiculo Pasajeros): Datos insuficientes");
        return null;
        }

        try {
            int numeroRuedas = Integer.parseInt(datos[0]);
            String color = datos[1];
            int numeroPuertas = Integer.parseInt(datos[2]);
            String marca = datos[3];
            String modelo = datos[4];
            String patente = datos[5];
            // Verificar la unicidad de la patente antes de crear el vehículo
            if (!Auxiliar.unicidadPatentes(patente)) {
                System.err.println("La patente " + patente + " ya está registrada.");
                return null;
            }
            int numeroAsientos = Integer.parseInt(datos[6]);
            
            // Crear el objeto VehiculosPasajeros
            Vehiculos nuevoVehiculo = new VehiculosPasajeros(numeroRuedas, color, numeroPuertas, marca, modelo, patente, "pasajeros", numeroAsientos);

            // Bloquear el acceso a ambas estructuras
            synchronized (vehiculos) {
                vehiculos.add(nuevoVehiculo);
                vehiculosPorPatente.put(patente, nuevoVehiculo);

                // Utilizar putIfAbsent directamente sin verificación adicional
                if (vehiculosPorPatente.putIfAbsent(patente, nuevoVehiculo) != null) {
                    System.err.println("Error: Se produjo un conflicto al agregar el vehiculo. La patente ya existe.");
                    return null;
                }
            }
            return nuevoVehiculo;
        } catch (NumberFormatException e) {
            System.err.println("Error: Datos invalidos para Vehiculo Pasajeros (formato)");
            return null;
        }
    }

    public static String color(){
        System.out.print("Color: ");           //color y valudacion
        String color = teclado.nextLine();
        while (true) {
            color = teclado.nextLine();
            if (color.matches("[a-zA-Z-]+")) {
                break;
            } else {
                System.out.println("El color debe contener solo letras y guiones. Ingrese el color nuevamente");
            }
        }
        return color;
    }

    private static int numeroRuedas() {
        System.out.print("Numero de ruedas: ");  //reudas y validacion
        int numeroRuedas = 0;
        while (true) {                          //validacion, mientras se inrgese4 bien el n° de reudas
            try {
                numeroRuedas = teclado.nextInt();
                if (numeroRuedas >= 4) {  //si el numero es mas greanded que cero sale del ciclo
                    break; 
                } else {
                    System.out.println("El numero de ruedas debe ser un entero positivo. Ingrese el numero correcto de ruedas");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Ingrese un numero mayor a cero");
                teclado.nextLine(); // Limpia el buffer del teclado
            }
        }
        return numeroRuedas;
    }

    private static int numeroPuertas() {
        System.out.print("Numero de puertas: ");
        int numeroPuertas = 0;

        while (true) {
            try {
                numeroPuertas = teclado.nextInt();
                if (numeroPuertas >= 2) {
                    break; 
                } else {
                    System.out.println("El numero de puertas debe ser un entero positivo. Ingrse el numero nuevamente");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Ingrese un numero mayor a cero");
                teclado.nextLine(); // Limpia el buffer del teclado
            }
        }
        return numeroPuertas;
        
    }

    private static String marca() {
        System.out.print("Marca: ");
        String marca = "";

        while (true) {
            marca = teclado.nextLine();
            // validacion letras y simbolos
            if (marca.matches("[a-zA-Z0-9\\s]+")) {
                break; 
            } else {
                System.out.println("La marca debe contener solo letras, numeros y espacios. Intente nuevamente.");
            }
        }
        return marca;
    }

    private static String modelo() {
        System.out.print("Modelo: ");
        String modelo = "";

        while (true) {
            modelo = teclado.nextLine();
            // validacion de letras y simbolos
            if (modelo.matches("[a-zA-Z0-9\\s\\-\\.]+")) {
                break; 
            } else {
                System.out.println("Se aceptan solo letras y simbolos. Ingrese el modelo nuevamente");
            }
        }
        return modelo;
    }

    private static String patente() {
        System.out.print("Patente (AAAA12): ");
        String patente;

        do {
            patente = teclado.nextLine().toUpperCase();
            if (!patente.matches("[A-Z]{4}\\d{2}")) {
                System.out.println("La patente debe tener el formato AAAA12 (ejemplo: ABCD12). Intente nuevamente");
            }
        } while (!patente.matches("[A-Z]{4}\\d{2}"));
        
        return patente;
    }
    
    private static void mostrarVehiculos() {
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehiculos registrados.");
        } else {
            System.out.println("Lista de vehiculos:");
            int contador = 1;
            for (Vehiculos vehiculo : vehiculos) {
                System.out.println("      Vehiculo " + contador++ + ":");
                System.out.println(vehiculo.toString()); 
                System.out.println("---------------------------------------");
            }
        }
    }

    private static Vehiculos crearVehiculo(String[] datos) {
        // Validar la cantidad minima de datos
        if (datos.length < 8) {
            System.err.println("Linea invilida: Datos insuficientes. Se esperan al menos 8 campos.");
            return null;
        }

        try {
            int numeroRuedas = Integer.parseInt(datos[0]);
            String color = datos[1];
            int numeroPuertas = Integer.parseInt(datos[2]);
            String marca = datos[3];
            String modelo = datos[4];
            String patente = datos[5];
            // Verificar la unicidad de la patente antes de crear el vehículo
            if (!Auxiliar.unicidadPatentes(patente)) {
                System.err.println("La patente " + patente + " ya esta registrada.");
                return null;
            }
            String tipoVehiculo = datos[6].toLowerCase();

            // Crear el objeto Vehiculo según el tipo
            switch (tipoVehiculo) {
                case "pasajeros":
                    int numeroAsientos = Integer.parseInt(datos[7]);
                    return new VehiculosPasajeros(numeroRuedas, color, numeroPuertas, marca, modelo, patente, "pasajeros", numeroAsientos);
                case "carga":
                    double capacidadCargaToneladas = Double.parseDouble(datos[datos.length - 3]);
                    double costoFijo = Double.parseDouble(datos[datos.length - 2]);
                    double costoVariable = Double.parseDouble(datos[datos.length - 1]);
                    return new VehiculosCarga(numeroRuedas, color, numeroPuertas, marca, modelo, patente, "carga", capacidadCargaToneladas, costoFijo, costoVariable);
                default:
                    System.err.println("Tipo de vehiculo desconocido: " + tipoVehiculo);
                    return null;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir datos a numeros en la linea: " + Arrays.toString(datos));
            return null;
        }
    }

    private static void guardarListaArrendadosEnArchivo(List<Arriendo> vehiculosArrendados) {
        try (FileWriter writer = new FileWriter("arriendos.csv")) {
            // Escribimos el encabezado (opcional)
            writer.write("vehiculo,duracion,distancia,costoTotal\n");

            for (Arriendo arriendo : vehiculosArrendados) {
                Vehiculos vehiculo = arriendo.getVehiculo();
                String linea = vehiculo.getPatente() + "," +
                               arriendo.getDuracion() + "," +
                               arriendo.getDistancia() + "," +
                               arriendo.getCostoTotal() + "\n";
                writer.write(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar la lista de arriendos: " + e.getMessage());
        }
    }
    
    
}
