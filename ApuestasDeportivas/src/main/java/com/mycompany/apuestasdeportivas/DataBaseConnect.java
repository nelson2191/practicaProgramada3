/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apuestasdeportivas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nelsonrivas
 */
class DataBaseConnect {
     private static final String URL = "jdbc:mysql://localhost:3306/gestion_apuesta"; 
    
    // Nombre de usuario para autenticarse en la base de datos.
    private static final String USER = "root";
    
    // Contraseña correspondiente al usuario para acceder a la base de datos.
    private static final String PASSWORD = "mi-contraseña";

    /**
     * Método estático que devuelve una conexión a la base de datos.
     * 
     * @return Connection Objeto de conexión a la base de datos MySQL.
     * @throws SQLException Si ocurre algún error durante la conexión.
     */
    public static Connection getConnection() throws SQLException {
        // Establece y devuelve una conexión a la base de datos usando DriverManager.
        // DriverManager gestiona el driver JDBC y crea una conexión a la base de datos.
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
}
