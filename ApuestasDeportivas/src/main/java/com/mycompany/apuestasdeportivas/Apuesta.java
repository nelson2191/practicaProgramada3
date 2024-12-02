/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.apuestasdeportivas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nelsonrivas
 */
public class Apuesta {
private static final int PORT = 12340; 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
  
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor escuchando en el puerto " + PORT);
            ApuestaDao apuestaDao = new ApuestaDao(); // Instancia de la clase DAO para interactuar con la base de datos
            apuestaDao.crearTablaApuestas();
            while (true) {
                // Aceptar una nueva conexión de cliente
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                // Manejar cliente en un hilo separado
                new Thread(new ClientHandler(clientSocket, apuestaDao)).start();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Imprimir el error en caso de que falle la creación del servidor
        }
    }
    
}
    
    class ClientHandler implements Runnable {
        private final Socket clientSocket; // Socket para la conexión con el cliente
        private final ApuestaDao apuestaDao; // Instancia de la clase DAO para realizar operaciones en la base de datos

    // Constructor que recibe el socket y el DAO para interactuar con la base de datos
    public ClientHandler(Socket clientSocket, ApuestaDao apuestaDao) {
        this.clientSocket = clientSocket;
        this.apuestaDao = apuestaDao;
    }
    
     @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

            List<ClaseApuesta> apuestas; // Lista de estudiantes para manejar la respuesta al cliente

            while (true) {
                // Leer la operación solicitada por el cliente
                String operation = (String) input.readObject();
                System.out.println(operation); // Imprimir la operación recibida

                // Dependiendo de la operación recibida, se realizan diferentes acciones
                switch (operation) {
                    case "INSERT": // Si la operación es de inserción de un estudiante
                        ClaseApuesta ClaseApuestaInsert = (ClaseApuesta) input.readObject(); // Leer el objeto Estudiante del cliente
                        try {
                            // Insertar el estudiante en la base de datos
                            apuestaDao.insertar(ClaseApuestaInsert);

                            // Obtener la lista actualizada de estudiantes
                            try {
                                apuestas = apuestaDao.listar();
                            } catch (SQLException ex) {
                                apuestas = new ArrayList(); // Si ocurre un error, inicializamos una lista vacía
                                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            System.out.println(apuestas); // Imprimir la lista de estudiantes
                            output.writeObject(apuestas); // Enviar la lista de estudiantes al cliente
                            output.flush(); // Asegurarse de que los datos se envíen

                            System.out.println("guardado"); // Mensaje de confirmación en el servidor

                        } catch (SQLException ex) {
                            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;

                    case "LIST": // Si la operación es para listar todos los estudiantes
                        try {
                            // Obtener la lista completa de estudiantes desde la base de datos
                            apuestas = apuestaDao.listar();
                        } catch (SQLException ex) {
                            apuestas = new ArrayList(); // Si ocurre un error, inicializamos una lista vacía
                            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        output.writeObject(apuestas); // Enviar la lista de estudiantes al cliente
                        break;

                    default:
                        output.writeObject("Operación no válida."); // Si la operación no es válida, enviar un mensaje de error
                        break;
                }
                output.flush(); // Asegurarse de que los datos se envíen al cliente
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Apostador desconectado."); // Mensaje cuando el cliente se desconecta
        }
    }
}
    

