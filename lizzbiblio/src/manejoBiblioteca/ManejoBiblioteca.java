
package manejoBiblioteca;


import informacionGeneral.Libro;
import informacionGeneral.Usuario;


import exceptions.IdNoEncontradoExcpetion;
import exceptions.InformacionDuplicadaException;
import exceptions.InformacionNoEncontradaException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ManejoBiblioteca {

    //lista que almacena los libros
    private ArrayList<Libro> libros = new ArrayList<>(); 
    
    //lista de nuevos libros
    private List<Libro> librosNuevos = new ArrayList<>();
    
    //mapa de localizacion de los clientes de la biblioteca
    public static HashMap<String, Usuario> listaUsuarios = new HashMap<>(); 
    
    //titulos de lobros ordenados
    private HashSet<String> titulosUnicos = new HashSet<>(); 
    
    //clientes ordenados
    private TreeSet<String> usuariosOrdenados = new TreeSet<>(); 
    
    //
    private static Map<Integer, String> prestamos = new HashMap<>();
    
    // Método para guardar los nuevos libros en el archivo
    public void guardarLibrosNuevos(Libro nuevoLibro) throws IOException {
        String archivoLibros = "librosLizz.csv"; // Nombre del archivo donde se guardan los libros
        System.out.println("Guardando nuevos libros en: " + archivoLibros);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoLibros, true))) {
            // Crear una nueva línea con los datos del libro
            String nuevaLinea = nuevoLibro.getId() + "," + nuevoLibro.getTitulo() + "," + nuevoLibro.getAutor() + "\n";
            bw.write(nuevaLinea);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }finally {
            librosNuevos.clear(); // Limpiar la lista en cualquier caso
        }
        // Agregar el libro a la lista de libros nuevos
        librosNuevos.add(nuevoLibro);
    }
    
    public void guardarUsuariosEnArchivo() throws IOException {
        // Nombre del archivo donde se guardarán los usuarios
        String archivoUsuarios = "misUsuarios.csv";

        // Crear un FileWriter para escribir en el archivo
        FileWriter writer = new FileWriter(archivoUsuarios);
        BufferedWriter br = new BufferedWriter(writer);

        // Escribir el encabezado (opcional)
        br.write("ID,Nombre,LibrosPrestados");
        
        
        // Iterar sobre el HashMap de usuarios y escribir cada uno en una línea
        for (Usuario usuario : listaUsuarios.values()) {
            List<String> libroPrestadoIds = usuario.getLibroPrestado();
            StringBuilder linea = new StringBuilder();
            br.write(linea.toString() + "\n"); //salto de linea
            linea.append(usuario.getId()).append(",").append(usuario.getNombreUsuario()).append(",").append(String.join(",", libroPrestadoIds));
            br.write(linea.toString() + "\n"); //salto de linea
        }

        // Cerrar el BufferedWriter
        br.close();

        System.out.println("Usuarios guardados en el archivo " + archivoUsuarios);
    }
    
    
    // Agregar libro a la biblioteca
    public void agregarLibro(Libro libro) throws InformacionDuplicadaException {
        // Validar que el titulo no este repetido
        if (titulosUnicos.contains(libro.getTitulo())) {
            throw new InformacionDuplicadaException("El titulo del libro ya ha sido ingresado");
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
        
        // Agregar el libro a la lista de libros nuevos
        librosNuevos.add(libro);
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
        // Validar que el nombre de usuario no sea nulo o vacío
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío.");
        }

        // Validar que no hay nombres repetidos
        if (usuariosOrdenados.contains(usuario.getNombreUsuario())) {
            throw new InformacionDuplicadaException("El nombre del usuario ya esta registrado");
        }

        // Generar un ID aleatorio de 9 dígitos y convertirlo a cadena
        Random random = new Random();
        int idAleatorio;
        do {
            idAleatorio = random.nextInt(900000000) + 100000000;
        } while (idExiste(idAleatorio)); // Verificar si el ID ya existe
        String idAleatorioStr = Integer.toString(idAleatorio);

        // Asignar el ID al usuario
        usuario.setId(idAleatorioStr);

        // Validar que no esté repetido el id asignado
        if (listaUsuarios.containsKey(usuario.getId())) {
            throw new InformacionDuplicadaException("El ID del usuario ya existe.");
        }

        // Agregar usuario a las estructuras
        listaUsuarios.put(usuario.getId(), usuario);
        usuariosOrdenados.add(usuario.getNombreUsuario());
        System.out.println("Usuario agregado exitosamente.");
    }
    
    // Mostrar todos los libros
    public void mostrarLibros() {
        System.out.println("\nLista de libros:");
        for (Libro libro : libros) {
            System.out.println("Titulo: " + libro.getTitulo() + ", Autor: " + libro.getAutor() + ", Año: " + libro.getAnio() + ", ID: " +libro.getId());
            
            //separa cada libro
            System.out.println("-------------------------------------------");
        }
    }

    // Mostrar todos los usuarios
    public void mostrarUsuarios() {
        System.out.println("\nLista de usuarios:");
        for (Usuario usuario : listaUsuarios.values()) {
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
        throw new InformacionNoEncontradaException("El libro con ID " + id + ", no fue encontrado.");
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
        Usuario usuario = listaUsuarios.remove(id);
        if (usuario == null) {
            throw new InformacionNoEncontradaException("No se encontro el usuario con el ID ingresado");
        }
        usuariosOrdenados.remove(usuario.getNombreUsuario());
        System.out.println("Se ha eliminado exitosamente el Usuario");
    }

    //metodo para encontrar usuario por id
    public Usuario buscarUsuarioPorId(String id) throws InformacionNoEncontradaException {
        // Verificar si el ID está vacío o nulo
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede estar vacio.");
        }

        // Buscar el usuario en el HashMap por su ID
        Usuario usuarioEncontrado = listaUsuarios.get(id);

        // Si el usuario no se encuentra, lanzar una excepción
        if (usuarioEncontrado == null) {
            throw new InformacionNoEncontradaException("El usuario con ID " + id + " no fue encontrado.");
        }

        return usuarioEncontrado;
    }

    //metodo para pedir un libro
    public void prestarLibro(Libro libro, Usuario usuario, ManejoBiblioteca biblioteca) throws InformacionNoEncontradaException{
        if (!usuario.existeEnBiblioteca(biblioteca)) {
            throw new InformacionNoEncontradaException("Usuario no encontrado");
        }

        // Verificar si el libro está disponible
        if (libro.estaDisponible()) {
            // Prestar el libro al usuario
            libro.setPrestado(true);
            libro.setUsuarioActual(usuario);

            // Agregar el ID del libro a la lista de libros prestados del usuario
            usuario.getLibroPrestado().add(libro.getId());

            System.out.println("El libro ha sido prestado exitosamente.");
        } else {
            System.out.println("Lo sentimos, el libro no esta disponible en este momento.");
        }
    }

    //metodo para devolver el libro
    public void devolverLibro(String id) throws InformacionNoEncontradaException{
        try {
            // Busca el libro por ID
            Libro libro = buscarLibroPorId(id);

            // Verifica si el libro está prestado
            if (!libro.estaDisponible()) {
                // Marca el libro como disponible
                libro.setPrestado(false);
                libro.setUsuarioActual(null);

                System.out.println("El libro ha sido devuelto correctamente.");
            } else {
                System.out.println("El libro se encuentra en biblioteca");
            }
        } catch (InformacionNoEncontradaException e) {
            System.err.println("No se encontro el libro con el ID ingresado.");
        }
    }
    
    public static void inicializarBiblioteca() {
        ManejoBiblioteca manager = new ManejoBiblioteca();
        try {
            manager.cargarLibros("librosLizz.csv");
        } catch (IOException e) {
            System.err.println("Error al cargar los libros: " + e.getMessage());
        }
    }
    
    //metodo para cargar los archivos
    public void cargarLibros(String Libro) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("librosLizz.csv"))) {
            String linea;
            // Saltar la primera línea de titulo
            br.readLine();

            while ((linea = br.readLine()) != null) {
                
                // Eliminar espacios en blanco al principio y al final de la línea
                linea = linea.trim();
                
                //cambia las ',' del archivo csv por ';'
                String[] datosLibro = linea.split(";");
                
                // Verificar si hay al menos 3 elementos en el arreglo
                if (datosLibro.length >= 3){
                    String titulo = datosLibro[0];
                    String autor = datosLibro[1];
                    String anio = datosLibro[2];
                    String id = generarIdAleatorio(); // Generar un ID único para cada libro

                    // Crear un objeto Libro con los datos y agregarlo a una lista
                    Libro nuevoLibro = new Libro(titulo, autor, anio, id);
                    libros.add(nuevoLibro);
                    
                } else {
                    System.err.println("Linea con formato incorrecto: " + linea);
                }
            }
        //manejo de excepciones
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + "librosLizz.csv");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
    
    //metodo para generar un ID aleatorio
    private static String generarIdAleatorio() {
        Random random = new Random();
        return String.format("%09d", random.nextInt(900000000) + 100000000);
    }

    public void cargarUsuarios(String archivoUsuarios) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoUsuarios))) {
            String linea;
            // Saltar la primera línea si tiene un encabezado
            br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datosUsuario = linea.split(","); // Ajustar el separador si es necesario

                // Validación del número de elementos en el arreglo
                if (datosUsuario.length >= 3) {
                    String id = datosUsuario[0];
                    String nombre = datosUsuario[1];
                    String librosPrestadosStr = datosUsuario[2];

                    // Convertir la cadena de libros prestados a una lista de Strings
                    List<String> libroPrestado = Arrays.asList(librosPrestadosStr.split(","));

                    // Crear un objeto Usuario
                    Usuario nuevoUsuario = new Usuario(id, nombre);
                    nuevoUsuario.setLibroPrestado(libroPrestado);

                    // Agregar el usuario a la estructura de datos
                    listaUsuarios.put(id, nuevoUsuario);
                } else {
                    System.err.println("Línea con formato incorrecto: " + linea);
                    // Manejar la línea con formato incorrecto (por ejemplo, omitirla)
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo de usuarios no encontrado: " + archivoUsuarios);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de usuarios: " + e.getMessage());
        }
    }
    
    // Método para guardar todos los libros nuevos en el archivo
    public void guardarTodosLosLibrosNuevos() throws IOException {
        String archivoLibros = "librosLizz.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoLibros, true))) {
            for (Libro libro : librosNuevos) {
                String nuevaLinea = libro.getId() + "," + libro.getTitulo() + "," + libro.getAutor() + "\n";
                bw.write(nuevaLinea);
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        } finally {
            librosNuevos.clear(); // Limpiar la lista después de guardar
        }
    }
    
}
