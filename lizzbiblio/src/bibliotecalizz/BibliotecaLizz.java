
package bibliotecalizz;

import java.io.*;
import java.util.*;

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

    
    public static void main(String[] args) throws IOException{
        //se inicia la biblioteca con la carga del archivo
        ManejoBiblioteca.inicializarBiblioteca();
        
        // Instancia del gestor de biblioteca
        ManejoBiblioteca manager = new ManejoBiblioteca(); 
        Scanner teclado = new Scanner(System.in);
        manager.cargarLibros("libro.csv");
        manager.cargarUsuarios("misUsuarios.csv");
        
        
        int opcion = -1;
        
        System.out.println("BIENVENIDOS A BIBLIOTECA LIZZ");
        while (opcion != 0){
            try{
                System.out.println(""); //deja espacio en blanco
                // Menú interactivo para el usuario
                System.out.println("            MENU DE BIBLIOTECA");
                System.out.println(" 1.-  Ingresar usuario");
                System.out.println(" 2.-  Agregar libro");
                System.out.println(" 3.-  Mostrar todos los libros");
                System.out.println(" 4.-  Mostrar todos los usuarios agregados");
                System.out.println(" 5.-  Buscar libro");
                System.out.println(" 6.-  Pedir libro");
                System.out.println(" 7.-  Devolver libro");
                System.out.println(" 8.-  Ver lista de usuarios");
                System.out.println(" 9.-  Eliminar un libro");
                System.out.println("10.-  Eliminar un usuario");
                System.out.println("0.-  Salir");
                System.out.print("Seleccione una opcion: ");

                opcion = Integer.parseInt(teclado.nextLine()); 
                
                switch (opcion){
                    case 1:
                        try{
                            //registrar usuaario
                            System.out.print("Ingrese el nombre de usuario sin espacio (solo letras): ");
                            String nombreUsuario = "";
                            nombreUsuario = teclado.nextLine();
                            
                            // Validación: solo letras
                            if (!nombreUsuario.matches("[a-zA-Z]+")) {
                                throw new IllegalArgumentException("El nombre de usuario solo debe contener letras.");
                            }
                            
                            // Verificar si el usuario ya existe
                            try {
                                if (ManejoBiblioteca.listaUsuarios.containsKey(nombreUsuario)) {
                                    throw new InformacionDuplicadaException("El nombre de usuario ya existe, por favor elegir otro");
                                }
                            //manejo de excepcion
                            } catch (Exception e) {
                                System.err.println("No se puede verificar la existencia del usuario: " + e.getMessage());
                            }
                            
                            // Generar ID aleatorio de 9 dígitos
                            String id = generarIdAleatorio();

                            //se agrega el usuario
                            Usuario nuevoUsuario = new Usuario(nombreUsuario, id);
                            manager.agregarUsuario(nuevoUsuario);
                            
                            //muestra el id que se le genero
                            System.out.println("ID asignado: " + id);
                            
                        //manejo de excepciones
                        } catch (InformacionDuplicadaException e) {
                            System.err.println("Error: El nombre de usuario ya existe.");
                        } catch (IllegalArgumentException e) {
                            System.err.println(e.getMessage());
                        }
                        break;
                        
                    case 2:
                        try {
                            //ingreso de datos del libro
                            System.out.print("Ingrese el titulo del libro: ");
                            String titulo = teclado.nextLine();
                            
                            System.out.print("Ingrese el autor del libro: ");
                            String autor = teclado.nextLine();
                            
                            System.out.print("Ingrese el año de publicacion: ");
                            String anio = teclado.nextLine();

                            // Generar un ID aleatorio de 9 dígitos
                            String id = generarIdAleatorio();

                            //agregar el libro a la lista
                            Libro nuevoLibro = new Libro(titulo, autor, anio, id);
                            manager.agregarLibro(nuevoLibro);
                            
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
                        // Pedir libro
                        try {
                            System.out.print("Ingrese el ID del libro a pedir: ");
                            String id = teclado.nextLine();
                            
                            // Buscar el libro por ID
                            Libro libro = manager.buscarLibroPorId(id);
                            
                            System.out.print("Ingrese su ID de usuario: ");
                            id = teclado.nextLine();

                            // Buscar al usuario por ID
                            Usuario usuario = manager.buscarUsuarioPorId(id);

                            // Verificar si el libro está disponible
                            if (libro.estaDisponible()) {
                                // Prestar el libro al usuario, pasando la instancia de ManejoBiblioteca
                                manager.prestarLibro(libro, usuario, manager);
                                System.out.println("El libro ha sido prestado exitosamente.");
                            } else {
                                // Si el libro no está disponible, mostramos un mensaje al usuario
                                System.out.println("Lo sentimos, el libro \"" + libro.getTitulo() + "\" no está disponible en este momento.");
                                System.out.println("Por favor, intente más tarde o busque otro libro.");
                            }
                        } catch (InformacionNoEncontradaException e) {
                            System.err.println(e.getMessage());
                        }
                        break;
                    case 7: 
                        //devolver libro
                        try {
                            System.out.print("Ingrese el ID del libro a devolver: ");
                            String id = teclado.nextLine();

                            manager.devolverLibro(id);
                        } catch (InformacionNoEncontradaException e) {
                            System.err.println(e.getMessage());
                        }
                        break;
                    case 8:
                        // Mostrar usuarios ordenados
                        manager.mostrarUsuariosOrdenados();
                        break;
                    case 9:
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
                    case 10:
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
                        try {
                            manager.guardarUsuariosEnArchivo();
                            System.out.println("Usuarios guardados exitosamente.");
                            System.out.println("¿Desea guardar los cambios? (s/n)");
                            String respuesta = teclado.nextLine();
                            if (respuesta.equalsIgnoreCase("s")) {
                                System.out.println("Cambios guardados correctamente.");
                            }
                        } catch (IOException e) {
                            System.err.println("Error al guardar los usuarios: " + e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                        System.out.println("Intente nuevamente");
                }
            // manejo de excepciones    
            }catch (NumberFormatException e) {
                System.err.println("Error: Solo se pueden ingresar numero del 0 al 10");
            }
            
        }
        //guardar todos los libtos nuevos
        manager.guardarTodosLosLibrosNuevos();
    }

    //metodo para generar un ID aleatorio
    private static String generarIdAleatorio() {
        Random random = new Random();
        return String.format("%09d", random.nextInt(900000000) + 100000000);
    }
}
