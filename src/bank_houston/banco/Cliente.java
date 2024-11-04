
package bank_houston.banco;

import bank_houston.banco.CuentaBancaria.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

//clase que contiene la informacion de los clientes
public class Cliente {
    //atributos
    public String nombreUsuario;
    public String run;
    public String telefono;
    public String domicilio;
    public List<CuentaBancaria> cuentas = new ArrayList<>();
    
    
    
    //constructor
    public Cliente(String nombreUsuario, String run, String telefono, String domicilio){
        this.nombreUsuario = nombreUsuario;
        this.run = run; 
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.cuentas = cuentas;
    }
    
    //geter y setters de los atributos
    public String getNombreUsuario(){
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }
    public String getRun(){
        return run;
    }
    public void setRun(String run){
        this.run = run;
    }
    public String getTelefono(){
        return telefono;
    }
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    public List<CuentaBancaria> getCuentas(){
        return cuentas;
    }
    
    //metodo que muestra la informacion del cliente
    public void mostrarInformacionCliente() {
        System.out.println("INFORMACION DEL CLIENTE BANK HOUSTON"); 
        System.out.println(""); //en blanco para mejor lectura en consola
        System.out.println("Nombre cliente   : " + nombreUsuario);
        System.out.println("RUN cliente      : " + run);
        System.out.println("Telefono cliente : " + telefono);
        System.out.println("Domiclio cliente : " + domicilio);
        System.out.println("Cuentas Asociadas: ");
        if (cuentas.isEmpty()){
            System.out.println("Cliente no tiene actualmente cuentas asociadas");
        }else{
            for (CuentaBancaria cuenta : cuentas){ // las revisare al completar los datos de las otras clases
                DecimalFormat formatter = new DecimalFormat("#,###,###,###,###,###.##");
                String saldoFormateado = formatter.format(cuenta.getSaldo());
                System.out.println("----------------------------------------");
                System.out.println("Tipo de cuenta  : " + cuenta.getTipoCuenta());
                System.out.println("Numero de cuenta: " + cuenta.getNumeroCuenta());
                System.out.println("Saldo           : $ " + saldoFormateado);
                if (cuenta instanceof CuentaAhorro cuentaAhorro){
                    System.out.println("Tasa de Interes: " + cuentaAhorro.getTasaInteres() + "%.");
                }else if (cuenta instanceof CuentaInversion cuentaInversion){
                    System.out.println("Tasa de Interes anual: " + cuentaInversion.getTasaInteres + "%.");
                }
                System.out.println("----------------------------------------");
            }
        }
    }
}
