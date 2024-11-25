
package informacionGeneral;

import java.io.*;
import java.util.*;


public class Libro {
    //atributos
    private String titulo;
    private String autor;
    private String anio;
    private String id;
    private boolean prestado;
    private Usuario usuarioActual;
    
    //contructor
    public Libro(String titulo, String autor, String anio, String id){
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.id = id;
        this.prestado = prestado;
    }
    
    //getter y setters de nombre e id
    public String getTitulo(){
        return titulo;
    }
    public String getAutor(){
        return autor;
    }
    public String getAnio(){
        return anio;
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }
    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }
    
    @Override
    public String toString() {
        return "Libro [ID: " + id + ", Título: " + titulo + ", Autor: " + autor + ", Año publicacion: " + "]";
    }

    //metodo para saber si esta disponible o no 
    public boolean estaDisponible() {
        return !prestado;
    }
}
