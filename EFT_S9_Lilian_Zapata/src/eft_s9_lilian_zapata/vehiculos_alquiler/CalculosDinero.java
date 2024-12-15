
package eft_s9_lilian_zapata.vehiculos_alquiler;


public interface CalculosDinero {
    
    public String mostrarBoleta(int duracion, double distancia);
    
    public double calcularIva(double precioBase);
    
    public double descuento(double precioBase);
}
