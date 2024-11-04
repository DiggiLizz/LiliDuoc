
package bank_houston.banco;

import bank_houston.banco.InfoCliente;
import bank_houston.banco.Cliente;
import bank_houston.banco.CuentaBancaria.*;

//clase hija que hereda de Cuenta bancaria
public class CuentaInversion extends CuentaBancaria implements InfoCliente {
    //atribitos
    public Cliente cliente;
    double tasaInteres;
    String getTasaInteres;
    
    //constructor
    public CuentaInversion(double saldo, String numeroCuenta, String tipoCuenta, double tasaInteres, Cliente cliente){
        //se llama al contructor de la clase padre
        super(saldo, numeroCuenta, tipoCuenta, cliente);
        this.cliente = cliente;
        this.tasaInteres = tasaInteres;
    }
    
    //getter y setter de los atributos
    //cliente
    public Cliente getCliente(){
        return cliente;
    }
    public void setCLiente(Cliente cliente){
        this.cliente = cliente;
    }
    
    //tasa de interes
    public double getTasaInteres(){
        return tasaInteres;
    }
    public void setTasaInteres(double tasaInteres){
        if (tasaInteres >=0){
            this.tasaInteres = tasaInteres;
        }else{
            System.out.println("La tasa de interes debe ser un  numero positivo");
            System.out.println("Ejemplo 2");
        }
    }
    
    //metodos sobre escrito de tasa interes
    @Override
    public void calcularInteres(){
        //calcular el interes de la cuenta de inversion
        double interes = saldo*Math.pow(1 + tasaInteres, 1);
        
        //se actualiza el saldo
    }
    
    //metodo de sobreescritura
    @Override
    public void mostrarInformacionCliente(){
        System.out.println("INFORMACION DE CLIENTE CUENTA INVERSION");
        System.out.println("Nombre cliente  : " + cliente.getNombreUsuario());
        System.out.println("Numero de cuenta: " +  numeroCuenta);
        System.out.println("Saldo           : $ " +  saldo);
        System.out.println("tasa de interes : " + tasaInteres + "%.");
    }
        
    }
