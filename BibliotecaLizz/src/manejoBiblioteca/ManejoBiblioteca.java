
package manejoBiblioteca;


import informacionGeneral.Libro;
import informacionGeneral.Usuario;


import exceptions.IdNoEncontradoExcpetion;
import exceptions.InformacionDuplicadaException;
import exceptions.InformacionNoEncontradaException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

public class ManejoBiblioteca {
    //lista que almacena los libros
    private ArrayList<Libro> libros = new ArrayList<>(); 
    
    //mapa de localizacion de los clientes de la biblioteca
    private HashMap<String, Usuario> usuarios = new HashMap<>(); 
    
    //titulos de lobros ordenados
    private HashSet<String> titulosUnicos = new HashSet<>(); 
    
    //clientes ordenados
    private TreeSet<String> usuariosOrdenados = new TreeSet<>(); 
    
    // Agregar libro a la biblioteca
    public void agregarLibro(Libro libro) throws InformacionDuplicadaException {
        // Validar que el titulo no este repetido
        if (titulosUnicos.contains(libro.getTitulo())) {
            throw new InformacionDuplicadaException("El título del libro ya ha sido ingresado");
        }
        
        // Validar unicidad del ID
        for (Libro l : libros) {
            if (l.getId().equals(libro.getId())) {
                throw new InformacionDuplicadaException("El ID del libro ya existe.");
            }
        }

        // Agregar libro a la lista para orden y ver si son unicos  o no
        libros.add(libro);
        titulosUnicos.add(libro.getTitulo());
        System.out.println("Libro agregado exitosamente. ID asignado: " + libro.getId());
    }

    private boolean idExiste(int id) {
        // Método para verificar si un ID ya existe en la colección de libros
        for (Libro libro : libros) {
            if (Integer.parseInt(libro.getId()) == id) {
                return true;
            }
        }
        return false;
    }
    
    // Agregar usuario a la biblioteca
    public void agregarUsuario(Usuario usuario) throws InformacionDuplicadaException {
        // Validar que no hay nombres repeditos
        if (usuariosOrdenados.contains(usuario.getNombreUsuario())) {
            throw new InformacionDuplicadaException("El nombre del usuario ya esta registrado");
        }
        
        // Generar un ID aleatorio de 9 dígitos
        Random random = new Random();
        int idAleatorio;
        do {
            idAleatorio = random.nextInt(900000000) + 100000000; // Rango de 100000000 a 999999999
        } while (idExiste(idAleatorio)); // Verificar si el ID ya existe
        
        // Validar que no est repetido el id asignado
        if (usuarios.containsKey(usuario.getId())) {
            throw new InformacionDuplicadaException("El ID del usuario ya existe.");
        }
        // Agregar usuario a las estructuras
        usuarios.put(usuario.getId(), usuario);
        usuariosOrdenados.add(usuario.getNombreUsuario());
        System.out.println("Usuario agregado exitosamente.");
    }
    
    // Mostrar todos los libros
    public void mostrarLibros() {
        System.out.println("\nLista de libros:");
        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }

    // Mostrar todos los usuarios
    public void mostrarUsuarios() {
        System.out.println("\nLista de usuarios:");
        for (Usuario usuario : usuarios.values()) {
            System.out.println(usuario);
        }
    }

    // Buscar libro por ID
    public Libro buscarLibroPorId(String id) throws InformacionNoEncontradaException {
        for (Libro libro : libros) {
            if (libro.getId().equals(id)) {
                return libro;
            }
        }
        throw new InformacionNoEncontradaException("El libro con ID " + id + " no fue encontrado.");
    }


    // Mostrar usuarios ordenados alfabeticamente
    public void mostrarUsuariosOrdenados() {
        System.out.println("\nUsuarios ordenados:");
        for (String nombre : usuariosOrdenados) {
            System.out.println("- " + nombre);
        }
    }

    // Eliminar libro por id
    public void eliminarLibro(String id) throws InformacionNoEncontradaException {
        boolean encontrado = libros.removeIf(libro -> libro.getId().equals(id));
        if (!encontrado) {
            throw new InformacionNoEncontradaException("No se encontro el  libro con el ID ingresado");
        }
        System.out.println("Se ha eliminado el libro exitosamente");
    }

    // Eliminar usuario por id
    public void eliminarUsuario(String id) throws InformacionNoEncontradaException {
        Usuario usuario = usuarios.remove(id);
        if (usuario == null) {
            throw new InformacionNoEncontradaException("No se encontró un usuario con el ID especificado.");
        }
        usuariosOrdenados.remove(usuario.getNombreUsuario());
        System.out.println("Se ha eliminado exitosamente el Usuario");
    }

    public Usuario buscarUsuarioPorId(String id) throws InformacionNoEncontradaException {
        // Utilizamos el HashMap para buscar directamente por el ID
        Usuario usuario = usuarios.get(id);

        // Si el usuario no se encuentra, lanzamos la excepción
        if (usuario == null) {
            throw new InformacionNoEncontradaException("El usuario con ID " + id + " no fue encontrado.");
        }

        return usuario;
    }

    
}
