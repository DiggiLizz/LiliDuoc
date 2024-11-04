
package bank_houston.banco;

import bank_houston.banco.Cliente;


//clase padre de la que se heredan atributos
public abstract class CuentaBancaria {
    //atributos de la clase
    public double saldo;
    public String numeroCuenta;
    public String tipoCuenta;
    public Cliente cliente;
    
    //constructor de la clase padre
    public CuentaBancaria(double saldo, String numeroCuenta, String tipoCuenta, Cliente cliente){
        this.saldo = saldo;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.cliente = cliente;
    }
    
    //geter y setter de los atributos
    //saldo
    public double getSaldo(){
        return saldo;
    }
    public void setSaldo(double saldo){
        if (saldo >= 0){
            this.saldo = saldo;
        }else{
            System.out.println("No tiene saldo en su cuenta actualmente.");
        }
    }
    
    //numero de cuenta
    public String getNumeroCuenta(){
        return numeroCuenta;
    }
    public void setNumeroCuenta(String numeroCuenta){
        this.numeroCuenta = numeroCuenta;
    }
    
    //tipo de cuenta
    public String getTipoCuenta(){
        return numeroCuenta;
    }
    public void setTipoCuenta(String tipoCuenta){
        this.tipoCuenta = tipoCuenta;
    }
    
    //cliente
    public Cliente getCliente(){
        return cliente;
    }
    
    //metodo para girar dinero
    public void girar(double monto){
        if (monto <= saldo){
            saldo -= monto; // se actualiza el monto al girar dinero
        }else{
            System.out.println("No posee fondos suficientes para ginerar dinero");
        }
    }
    
    //metodos abstractos de la clase
    public abstract void calcularInteres();
    public abstract void mostrarInformacionCliente();

    

}
    
    

