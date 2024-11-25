
package informacionGeneral;

import exceptions.InformacionNoEncontradaException;
import java.util.ArrayList;
import java.util.List;
import manejoBiblioteca.ManejoBiblioteca;



public class Usuario {
    
    //lista nueva para almacenar los id de los libros prestados
    private List<String> libroPrestado;
    //atributos
    private String nombreUsuario;
    private String id;
    
    //contructor
    public Usuario(String nombreUsuario, String id){
        this.nombreUsuario = nombreUsuario;
        this.id = id;
        this.libroPrestado = new ArrayList<>();
    }
    
    //getter y setters de nombre e id
    public String getNombreUsuario(){
        return nombreUsuario;
    }
    public String getId(){
        return id;
    }
    
    
    //sobre escritura de metodo con la infromacion
    @Override
    public String toString() {
        return "Usuario [ID: " + id + ", Nombre: " + nombreUsuario + "]";
    }

    
    public List<String> getLibroPrestado() {
        return libroPrestado;
    }

    //metodo para saber si existe o no la informacion ingresada
    public boolean existeEnBiblioteca(ManejoBiblioteca biblioteca) {
        try {
            biblioteca.buscarUsuarioPorId(this.id);
            return true; // Si no se lanza una excepción, el usuario existe
        } catch (InformacionNoEncontradaException e) {
            return false;
        }
    }

    public void setLibroPrestado(List<String> libroPrestado) {
        this.libroPrestado = libroPrestado;
    }

    public void setId(String idAleatorioStr) {
        this.id = idAleatorioStr;
    }
}
