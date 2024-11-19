
package bibliotecalizz;

import informacionGeneral.Libro;
import informacionGeneral.Usuario;
import java.util.Scanner;

import manejoBiblioteca.ManejoBiblioteca;
import exceptions.BibliotecaLizzException;
import exceptions.InformacionDuplicadaException;
import exceptions.InformacionNoEncontradaException;
import java.util.Random;
import informacionGeneral.Libro;
import informacionGeneral.Usuario;


public class BibliotecaLizz {

    
    public static void main(String[] args) {
        ManejoBiblioteca manager = new ManejoBiblioteca(); // Instancia del gestor de biblioteca
        Scanner teclado = new Scanner(System.in);
        int opcion = -1;
        
        System.out.println("BIENVENIDOS A BIBLIOTECA LIZZ");
        while (opcion != 0){
            try{
                System.out.println(""); //deja espacio en blanco
                // Menú interactivo para el usuario
                System.out.println("      MENU DE BIBLIOTECA ");
                System.out.println("1.-  Ingresar usuario");
                System.out.println("2.-  Agregar libro");
                System.out.println("3.-  Mostrar todos los libros");
                System.out.println("4.-  Mostrar todos los usuarios agregados");
                System.out.println("5.-  Buscar libro");
                System.out.println("6.-  Ver lista de usuarios");
                System.out.println("7.-  Eliminar un libro");
                System.out.println("8.-  Eliminar un usuario");
                System.out.println("0.-  Salir");
                System.out.print("Seleccione una opcion: ");

                opcion = Integer.parseInt(teclado.nextLine()); 
                
                switch (opcion){
                    case 1:
                        try{
                            System.out.print("Ingrese el nombre de usuario sin espacio (solo letras): ");
                            String nombreUsuario = teclado.nextLine();

                            // Validación: solo letras
                            if (!nombreUsuario.matches("[a-zA-Z]+")) {
                                throw new IllegalArgumentException("El nombre de usuario solo debe contener letras.");
                            }
                            
                            // Generar ID aleatorio de 9 dígitos
                            String id = generarIdAleatorio();

                            Usuario nuevoUsuario = new Usuario(nombreUsuario, id);
                            manager.agregarUsuario(nuevoUsuario);
                            System.out.println("Usuario agregado exitosamente. ID asignado: " + id);
                            
                        } catch (InformacionDuplicadaException e) {
                            System.err.println("Error: El nombre de usuario ya existe.");
                        } catch (IllegalArgumentException e) {
                            System.err.println(e.getMessage());
                        }
                        break;
                        
                    case 2:
                        try {
                            System.out.print("Ingrese el titulo del libro: ");
                            String titulo = teclado.nextLine();
                            System.out.print("Ingrese el autor del libro: ");
                            String autor = teclado.nextLine();

                            // Generar un ID aleatorio de 9 dígitos
                            String id = generarIdAleatorio();

                            Libro nuevoLibro = new Libro(titulo, autor, id);
                            manager.agregarLibro(nuevoLibro);
                            System.out.println("Libro agregado exitosamente. ID asignado: " + id);
                        } catch (InformacionDuplicadaException e) {
                            System.err.println("Error: El titulo del libro ya existe.");
                        } catch (Exception e) {
                            System.err.println("Error al agregar el libro: " + e.getMessage());
                        }
                        break;
                        
                    case 3:
                        // Mostrar todos los libros
                        manager.mostrarLibros();
                        break;
                    case 4:
                        // Mostrar todos los usuarios
                        manager.mostrarUsuarios();
                        break;
                    case 5:
                        // Buscar libro por ID
                        System.out.print("Ingrese el ID del libro a buscar: ");
                        String idLibro = teclado.nextLine();
                        try {
                            Libro libroEncontrado = manager.buscarLibroPorId(idLibro);
                            System.out.println(libroEncontrado);
                        } catch (InformacionNoEncontradaException e) {
                            System.err.println(e.getMessage());
                        }
                        break;
                    case 6:
                        // Mostrar usuarios ordenados
                        manager.mostrarUsuariosOrdenados();
                        break;
                    case 7:
                        // Eliminar libro por id con validacion
                        System.out.print("Ingrese el ID del libro a eliminar: ");
                        String id = teclado.nextLine();
                        try {
                            Libro libroEncontrado = manager.buscarLibroPorId(id);
                            System.out.println("Seguro que desea eliminar el libro " + libroEncontrado.getTitulo() + "? (S/N)");
                            String confirmacion = teclado.nextLine().toUpperCase();
                            if (confirmacion.equals("S")) {
                                manager.eliminarLibro(id);
                                System.out.println("Se ha eliminado exitosamente");
                            } else {
                                System.out.println("Se ha cancelado la Eliminacion");
                            }
                        } catch (InformacionNoEncontradaException e) {
                            System.err.println(e.getMessage());
                        }
                        break;
                    case 8:
                        // Eliminar usuario por id y validacion
                        System.out.print("Ingrese el ID del usuario a eliminar: ");
                        id = teclado.nextLine();
                        try {
                            Usuario usuarioEncontrado = manager.buscarUsuarioPorId(id);
                            System.out.println("Seguro que desea eliminar el usuario " + usuarioEncontrado.getNombreUsuario() + "? (S/N)");
                            String confirmacion = teclado.nextLine().toUpperCase();
                            if (confirmacion.equals("S")) {
                                manager.eliminarUsuario(id);
                                System.out.println("Usuario eliminado exitosamente");
                            } else {
                                System.out.println("Se ha cancelado la Eliminacion");
                            }
                        } catch (InformacionNoEncontradaException e) {
                            System.err.println(e.getMessage());
                        }
                        break;
                    case 0:
                        System.out.println("Gracias por visitar Biblioteca Lizz");
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                        System.out.println("Intente nuevamente");
                        break;
                }
                
            }catch (NumberFormatException e) {
                System.err.println("Error: Solo se pueden ingresar numero del 0 al 8");
            }
        }
    }

    private static String generarIdAleatorio() {
        Random random = new Random();
        return String.format("%09d", random.nextInt(900000000) + 100000000);
    }
}
