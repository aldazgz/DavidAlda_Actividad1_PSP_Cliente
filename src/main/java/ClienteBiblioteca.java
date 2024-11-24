import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClienteBiblioteca {
    public static void main(String[] args) {
        // IP servidor
        final String HOST = "192.168.1.189";
        // Puerto del servidor
        final int PORT = 8080;

        // Conexión con el servidor
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())) {

            Scanner scanner = new Scanner(System.in);
            boolean continuar = true;

            // Menú
            while (continuar) {
                System.out.println("\n=== Menú Biblioteca ===");
                System.out.println("1. Consultar libro por ISBN");
                System.out.println("2. Consultar libro por título");
                System.out.println("3. Consultar libros por autor");
                System.out.println("4. Añadir libro");
                System.out.println("5. Salir");
                System.out.print("Selecciona una opción: ");
                String opcion = scanner.nextLine();
                salida.writeObject(opcion);

                switch (opcion) {
                    case "1":
                        System.out.print("Ingrese el ISBN del libro: ");
                        String isbn = scanner.nextLine();
                        salida.writeObject(isbn);
                        String respuestaISBN = (String) entrada.readObject();
                        System.out.println("\nResultado: " + respuestaISBN);
                        break;

                    case "2":
                        System.out.print("Ingrese el título del libro: ");
                        String titulo = scanner.nextLine();
                        salida.writeObject(titulo);
                        String respuestaTitulo = (String) entrada.readObject();
                        System.out.println("\nResultado: " + respuestaTitulo);
                        break;

                    case "3":
                        System.out.print("Ingrese el autor del libro: ");
                        String autor = scanner.nextLine();
                        salida.writeObject(autor);
                        List<String> respuestaAutor = (List<String>) entrada.readObject();

                        if (respuestaAutor == null || respuestaAutor.isEmpty()) {
                            System.out.println("No se encontraron libros de " + autor);
                        } else {
                            System.out.println("Libros de " + autor + ":");
                            for (String libroDetalles : respuestaAutor) {
                                System.out.println(libroDetalles);
                            }
                        }
                        break;
                    case "4":
                        System.out.print("Ingrese ISBN: ");
                        String isbnAdd = scanner.nextLine();
                        System.out.print("Ingrese Título: ");
                        String tituloAdd = scanner.nextLine();
                        System.out.print("Ingrese Autor: ");
                        String autorAdd = scanner.nextLine();
                        System.out.print("Ingrese Precio: ");
                        double precioAdd = scanner.nextDouble();
                        scanner.nextLine();
                        String libroData = isbnAdd + "," + tituloAdd + "," + autorAdd + "," + precioAdd;
                        salida.writeObject(libroData);
                        String mensaje = (String) entrada.readObject();
                        System.out.println(mensaje);
                        break;
                    case "5":
                        System.out.println("Saliendo de la aplicación...");
                        continuar = false;
                        break;

                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                        break;
                }
            }
            // Capturar errores
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en la conexión con el servidor: " + e.getMessage());
        }
    }
}