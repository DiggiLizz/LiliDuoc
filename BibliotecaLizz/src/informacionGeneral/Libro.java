
package informacionGeneral;


public class Libro {
    //atributos
    private String titulo;
    private String autor;
    private String id;
    
    //contructor
    public Libro(String titulo, String autor, String id){
        this.titulo = titulo;
        this.autor = autor;
        this.id = id;
    }
    
    //getter y setters de nombre e id
    public String getTitulo(){
        return titulo;
    }
    public String getAutor(){
        return autor;
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    
    @Override
    public String toString() {
        return "Libro [ID: " + id + ", Título: " + titulo + ", Autor: " + autor + "]";
    }
    
}
