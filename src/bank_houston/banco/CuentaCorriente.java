
package bank_houston.banco;

import bank_houston.banco.InfoCliente;
import bank_houston.banco.Cliente;
import bank_houston.banco.CuentaBancaria.*;

//clase hija que hereda de Cuenta bancaria
public class CuentaCorriente extends CuentaBancaria implements InfoCliente {
    //atributos
    public Cliente cliente;
    
    //constructor
    public CuentaCorriente(double saldo, String numeroCuenta, String tipoCuenta, Cliente cliente){
        //se llama al constructor padre
        super(saldo, numeroCuenta, tipoCuenta, cliente);
        this.cliente = cliente;
    }
    
    //getter y setter de los atributos
    //cliente
    public Cliente getCliente(){
        return cliente;
    }
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    
    //metodo sobrescrito de la clasa padre
    @Override
    public void calcularInteres(){
        //se calcula el interes para la cuenta corriente
        double interes = saldo * 0.09;
        
        //se actualiza el saldo de la cuenta
        saldo += interes;
        System.out.println("El interes de su cuenta es: " + interes);
    }
    
    //metodo sobreescrito
    @Override
    public  void mostrarInformacionCliente(){
        System.out.println("INFORMACION DE LA CUENTA CORRIENTE");
        System.out.println("Nombre cliente  : " + cliente.getNombreUsuario());
        System.out.println("Numero de cuenta: " + numeroCuenta);
        System.out.println("Saldo           : " + saldo);
    }
}
