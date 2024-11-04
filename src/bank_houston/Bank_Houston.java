
package bank_houston;

import bank_houston.banco.*;
import bank_houston.banco.CuentaBancaria;
import bank_houston.banco.Cliente;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Bank_Houston {

    //creamos el scanner que nos deja ingresar datos a la consola
    public static Scanner teclado = new Scanner(System.in);
    
    //lista para almacenar los clientes creados
    public static List<Cliente> listaClientes = new ArrayList<>();
    
    public static void main(String[] args) {
        Cliente cliente = null;
        CuentaBancaria cuenta = null;
        
        System.out.println("BIENVENIDOS A SISTEMA BANK HOUSTON");
        
        boolean continuar = true;
        
        while (continuar){
            mostrarMenu();
            int opcion = teclado.nextInt();
            
            switch(opcion){
                case 1:
                    cliente = registrarCliente(teclado);
                    break;
                case 2:
                    cuenta = crearCuenta(teclado, cliente);
                    break;
                case 3:
                    if (cuenta != null){
                        cliente.mostrarInformacionCliente();
                    }else{
                        System.out.println("Debe crear una cuenta primero, en opcion 2");
                    }
                    break;
                case 4:
                    if (cuenta != null){
                        despositarDinero(teclado, cuenta);
                    }else{
                        System.out.println("Debe crear una cuenta primero, en opcion 2");
                    }
                    break;
                case 5:
                    if (cuenta !=null){
                        girarDinero(teclado, cuenta);
                    }else{
                        System.out.println("Debe crear una cuenta primero, en opcion 2");
                    }
                    break;
                case 6:
                    if (cuenta != null){
                        DecimalFormat formatter = new DecimalFormat("#,###,###,###,###,###,###.##");
                        String saldoFormateado = formatter.format(cuenta.getSaldo());
                        System.out.println("Su saldo actual es: $ " + saldoFormateado);
                    }else{
                        System.out.println("Debe crear una cuenta primero, en opcion 2");
                    }
                    break;
                case 0:
                    System.out.println("Gracias por vistira Bank Houston");
                    System.out.println("Saliendo del sistema bancario");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opcion ingresada invalida, ingrese opcion de 0 a 6.");
            }
        }
    }

    //metodo que muestra como es el menu
    private static void mostrarMenu() {
        System.out.println("");
        System.out.println("   Menu principal"); 
        System.out.println("1.- Registrar Cliente");
        System.out.println("2.- Crear nueva cuenta");
        System.out.println("3.- Ver datos");
        System.out.println("4.- Depositar Dinero");
        System.out.println("5.- Girar Dinero");
        System.out.println("6.- Consultar saldo");
        System.out.println("0.- Salir del sistema");
        System.out.print("Ingrese una opcion: ");
    }

    private static Cliente registrarCliente(Scanner teclado) {
        String nombreUsuario, run, telefono, domicilio;
        
        System.out.println("");
        System.out.println("BIENVENIDO AL REGISTRO DE CLIENTE DE BANH HOUSTON");
        System.out.println("");
        System.out.println("Para completar el registro, ingrese la siguiente informacion: ");
        System.out.println("");
        
        boolean nombreValido = false;
        //ingreso de nombre y validacion
        do {
            System.out.println("1.- Ingrese su nombre y apellido: ");
            teclado.nextLine();
            nombreUsuario = teclado.nextLine();
            
            // Validar el nombre
            if (nombreUsuario.matches("^\\s*[a-zA-Z]+\\s+[a-zA-Z]+\\s*$")) {
                nombreValido = true;
            } else {
                System.out.println("El nombre ingresado no es valido. Por favor, ingrese solo letras y un espacio entre el nombre y el apellido.");
            }
        } while (!nombreValido);
        
        //ingreso de run y validacion
        do{
            System.out.print("2.- Ingrese su RUN (XX.XXX.XXX-X): ");
            run = teclado.nextLine();
        }while (!(run.length() == 11 || run.length()== 12)); // valiadacion de largo del run de la persona
        
        //ingreso del telefono y validacion 
        do{
            System.out.print("3.- Ingrese su numero de telefono (X XXXX XXXX): ");
            telefono = teclado.nextLine();
            //si agrega +, se elimina
            telefono = telefono.replaceAll("\\D","");
        }while (telefono.length()<9 || telefono.length()>11);  //validacion del largo del numero de telefono
        
        //ingreso del domicilio de la persona y valiadcion
        do{
            System.out.print("4.- Ingrese su domicilio: ");
            domicilio = teclado.nextLine();
            
            //validacion del largo de la direccion
            if(domicilio.matches("[a-zA-Z0-0\\s,-]+")){ //solo se permiten letras y numeros
                continue;
            }
        }while (domicilio.length()< 5 || domicilio.length()>1000); // se permiten entre 5 y 1000 caracteres
        
        //crear un objto cliente con los daros ingresados
        Cliente cliente = new Cliente(nombreUsuario, run, telefono, domicilio);
        
        //se agrega a la lista
        listaClientes.add(cliente);
        
        System.out.println("Se a ingresado exitosamente el cliente");
        System.out.println("Volviendo a menu principal");
        System.out.println("");
        
        return cliente;
    }
    
    //lista para almacenar una cuenta creada
    private static List<CuentaBancaria> listaCuentas = new ArrayList<>();

    // metodo para crear una cuenta nueva en el banco
    private static CuentaBancaria crearCuenta(Scanner teclado, Cliente cliente) {
        String numeroCuenta;
        double saldoInicial;
        
        System.out.println("");
        System.out.println("BIENVENIDO A LA CREACION DE CUENTAS DE BANK HOUSTON");
        System.out.println("Tipo de cuentas: ");
        System.out.println("1.- Cuenta Corriente");
        System.out.println("2.- Cuenta de Ahorros");
        System.out.println("3.- Cuenta de Inversion");
        System.out.print("Seleccione tipo de cuenta (1 a 3): ");
        int opcionCuenta = teclado.nextInt();
        teclado.nextLine(); // se limpia buffer
        
        CuentaBancaria cuenta = null;
        switch(opcionCuenta){
            case 1:
                numeroCuenta = generarNumeroCuenta(); // se llama al metodo que genera numero cuenta
                saldoInicial = 1000000;
                cuenta = new CuentaCorriente(saldoInicial, numeroCuenta, "Corriente", cliente);
                System.out.println("Se ha creado con exito la Cuenta Corriente");
                break;
            case 2:
                numeroCuenta = generarNumeroCuenta();
                System.out.print("Ingrese su primer deposito por favor: $ ");
                double deposito = teclado.nextLong();
                System.out.print("Ingrese la Tasa de Interes anual (solo numero): ");
                double tasaInteres = teclado.nextDouble();
                cuenta = new CuentaAhorro(deposito, numeroCuenta, " Ahorros", cliente);
                System.out.println("Se ha creado con exito la Cuenta de Ahorro");
                break;
            case 3:
                numeroCuenta = generarNumeroCuenta();
                System.out.print("Ingrese el dinero par su primera inversion: $ ");
                double depositoInversion = teclado.nextDouble();
                System.out.println("La tasa de interes anual es 0.4%.");
                tasaInteres = 0.4;
                cuenta = new CuentaInversion(depositoInversion, numeroCuenta, "Inversion", tasaInteres, cliente);
                System.out.println("Se ha creado con exito su cuenta de Inversiones");
                break;
            default:
                System.out.println("Opcion ingresada invalida");
                break;
        }
        if (cuenta != null){
            //se agrega la cuenta al cliente
            cliente.getCuentas().add(cuenta);
        }
        return cuenta;
    }

    private static void despositarDinero(Scanner teclado, CuentaBancaria cuenta) {
        String run;
        Cliente cliente;
        
        //pedir el run para saber que cliente est
        do{
            System.out.print("Ingrese su RUN (XX.XXX.XXX-9) : ");
            run = teclado.nextLine();
            
            //se busca el cliente por el run
            cliente = buscarClientePorRun(listaClientes, run);
            
            if (cliente == null){
                System.out.println("No se ha encontrado el run ingresado");
            }
        }while (cliente == null);
        
        //mostrar las cuentas que tenga asociadaas
        System.out.println("Cuentas asociadas al cliente " + cliente.getNombreUsuario() + ": ");
        for (int i = 0; i <cliente.getCuentas().size();i++){
            cuenta = cliente.getCuentas().get(i);
            DecimalFormat formatter = new DecimalFormat("##,###,###,###,###,###.##");
            String saldoFormateado = formatter.format(cuenta.getSaldo());
            System.out.println((i+1)+"."+cuenta.getNumeroCuenta()+ "saldo : $ " + saldoFormateado);
        }
        
        // solicitar numero cuenta
        int numeroCuenta;
        do{
            System.out.println("Ingrese el numero de cuenta (o 0 para salir)");
            numeroCuenta = teclado.nextInt();
            
            if (numeroCuenta <0 || numeroCuenta> cliente.getCuentas().size()){ // se busca entre 0 y las cuentas que se hayan creado
                System.out.println("Numero ingresado incorrecto, intente nuevamente");
            }
        }while (numeroCuenta <0 || numeroCuenta> cliente.getCuentas().size());
        
        if (numeroCuenta !=0){
            CuentaBancaria cuentaSeleccionada = cliente.getCuentas().get(numeroCuenta-1); // se resta, ya que parte de cero
            
            //depositar dinero
            System.out.println("Ingrese la cantidad de dinero a depositar");
            double monto = teclado.nextDouble();

            // se actualcia el saldo
            cuentaSeleccionada.setSaldo(cuentaSeleccionada.getSaldo()+ monto);

            // se formatea para que no aoarezca ocmo expresion
            DecimalFormat formatter = new DecimalFormat("##,###,###,###,###,###.##");
            String saldoFormateado = formatter.format(cuenta.getSaldo());
            System.out.println("Deposito realizado con exito");
        }
        
        
    }

    private static void girarDinero(Scanner teclado, CuentaBancaria cuenta) {
        String run;
        Cliente cliente;
        
        // Pedir el RUN del cliente
        do {
            System.out.print("Ingrese su RUN: ");
            run = teclado.nextLine();

            // Buscar al cliente
            cliente = buscarClientePorRun(listaClientes, run);

            if (cliente == null) {
                System.out.println("Cliente no encontrado. Por favor, ingrese un RUN válido.");
            }
        } while (cliente == null);

        // Mostrar las cuentas del cliente y permitir la selección
        System.out.println("Cuentas asociadas al cliente " + cliente.getNombreUsuario() + ":");
        for (int i = 0; i < cliente.getCuentas().size(); i++) {
            cuenta = cliente.getCuentas().get(i);
            DecimalFormat formatter = new DecimalFormat("#,###,###,###,###,###.##"); // Formato con separadores de miles y dos decimales
            String saldoFormateado = formatter.format(cuenta.getSaldo());
            System.out.println((i + 1) + ". " + cuenta.getNumeroCuenta() + " - Saldo: $" + saldoFormateado);
        }

        // Solicitar el número de la cuenta
        int numeroCuenta;
        do {
            System.out.print("Ingrese el numero de la cuenta (o 0 para cancelar): ");
            numeroCuenta = teclado.nextInt();
            if (numeroCuenta < 0 || numeroCuenta > cliente.getCuentas().size()) {
                System.out.println("Numero de cuenta invalido. Por favor, intente nuevamente.");
            }
        } while (numeroCuenta < 0 || numeroCuenta > cliente.getCuentas().size());

        if (numeroCuenta != 0) {
            CuentaBancaria cuentaSeleccionada = cliente.getCuentas().get(numeroCuenta - 1);

            // Realizar el giro
            System.out.print("Ingrese el monto a retirar: $");
            double monto = teclado.nextDouble();
            
            // Verificar si el monto a retirar es menor o igual al saldo disponible
            if (monto <= cuentaSeleccionada.getSaldo()) {
                // Restar el monto al saldo y actualizar el objeto
                cuentaSeleccionada.setSaldo(cuentaSeleccionada.getSaldo() - monto);
                System.out.println("Retiro realizado con exito. Su saldo actual es: $" + cuentaSeleccionada.getSaldo());
            } else {
                System.out.println("Fondos insuficientes.");
            }
        }
    }

    //metodo para generar un numero de cuenta aleatorio de 9 digitos
    private static String generarNumeroCuenta() {
        Random random = new Random();
        String numeroCuenta;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 9; i++) {
                sb.append(random.nextInt(10));
            }
            numeroCuenta = sb.toString();
        } while (existeNumeroCuenta(numeroCuenta)); // Verificamos si el número ya existe
        return numeroCuenta;
    }

    //metodo para saber si ya se ha creado un numero de cuenta o no
    private static boolean existeNumeroCuenta(String numeroCuenta) {
        for (CuentaBancaria cuenta : listaCuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                return true;
            }
        }
        return false;
    }

    private static Cliente buscarClientePorRun(List<Cliente> listaClientes, String run) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getRun().equals(run)) {
                return cliente;
            }
        }
        return null;
    }
    
}
