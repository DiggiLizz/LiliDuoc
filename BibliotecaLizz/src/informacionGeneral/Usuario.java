
package informacionGeneral;


public class Usuario {
    //atributos
    private String nombreUsuario;
    private String id;
    
    //contructor
    public Usuario(String nombreUsuario, String id){
        this.nombreUsuario = nombreUsuario;
        this.id = id;
    }
    
    //getter y setters de nombre e id
    public String getNombreUsuario(){
        return nombreUsuario;
    }
    public String getId(){
        return id;
    }
    
    @Override
    public String toString() {
        return "Usuario [ID: " + id + ", Nombre: " + nombreUsuario + "]";
    }
}
