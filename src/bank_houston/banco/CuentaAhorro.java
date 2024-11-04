
package bank_houston.banco;

import bank_houston.banco.InfoCliente;
import bank_houston.banco.*;
import bank_houston.banco.Cliente;

//clase hija que hereda de Cuenta bancaria
public class CuentaAhorro extends CuentaBancaria implements InfoCliente {
    //atributos
    private Cliente cliente;
    private double tasaInteres;
    
    //contructor
    public CuentaAhorro(double saldo, String numeroCuenta, String tipoCuenta, Cliente cliente){
        //llamamos al contructor de la clase padre
        super(saldo, numeroCuenta, tipoCuenta, cliente);
        this.tasaInteres = tasaInteres;
        this.cliente = cliente;
    }
    
    //geter y setter de los atributos
    //cliente
    public Cliente getCliente(){
        return cliente;
    }
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    
    //tasa de interes
    public double getTasaInteres(){
        return tasaInteres;
    }
    public void setTasaInteres(double tasaInteres){
        if (tasaInteres >= 0){
            this.tasaInteres = tasaInteres;
        }else{
            System.out.println("La tasa de interes deber ser un numero positivo");
            System.out.println("Ejemplo: 2");
        }
    }
    
    //sobreescritura de los metodos
    @Override
    public void calcularInteres(){
        double interes = saldo * tasaInteres;  // se obtiene el interes al multiplicar por el interes ingresado
        saldo += interes; //se actualiza el saldo con el interes
        System.out.println("El interes actual es: " + interes);
    }
    
    //sobreescritura de la informacion del cliente
    @Override
    public void mostrarInformacionCliente(){
        System.out.println("");
        System.out.println("Informacion de Cliente Bank Houston");
        System.out.println("");
        System.out.println("Nombre cliente   : " + cliente.getNombreUsuario());
        System.out.println("Numero de cuenta : " + numeroCuenta);
        System.out.println("Saldo disponible : " + saldo);
        System.out.println("Tasa de interes  : " + tasaInteres + "%.");
        System.out.println("");
    }
}
