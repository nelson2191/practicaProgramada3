/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apuestasdeportivas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelsonrivas
 */
public class ApuestaDao {
    private Connection connection;
    
    public void insertar(ClaseApuesta claseApuesta) throws SQLException {
        String sql = "INSERT INTO apuesta ( id, fecha, apostador, evento, ganador, monto) VALUES (?,?,?,?,?,?)";
        // Uso de PreparedStatement para evitar inyecciones SQL y manejar valores dinámicos.
        try (Connection conn = DataBaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, claseApuesta.getId());
            stmt.setString(2, claseApuesta.getFecha());
            stmt.setString(3, claseApuesta.getApostador());
            stmt.setString(4, claseApuesta.getEvento());
            stmt.setString(5, claseApuesta.getGanador());
            stmt.setDouble(6, claseApuesta.getMonto());
    
            stmt.executeUpdate(); // Ejecuta la instrucción SQL.
        }
    }
    
    public void crearTablaApuestas() throws SQLException {
        // Conexión específica a la base de datos (puede configurarse en otra clase si se prefiere).
        String url = "jdbc:mysql://localhost:3306/gestion_apuesta"; // Cambiar según la configuración.
        String user = "root";
        String password = "mi-contraseña";
        this.connection = DriverManager.getConnection(url, user, password);

        // SQL para crear la tabla si no existe.
        String sql = """
                CREATE TABLE IF NOT EXISTS apuesta (
                    id int NOT NULL AUTO_INCREMENT,
                    fecha VARCHAR(50),
                    apostador VARCHAR(50),
                    evento VARCHAR(50),
                    ganador VARCHAR(50),
                    monto float,
                    PRIMARY KEY(id) 
                );
                """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql); // Ejecuta la creación/verificación de la tabla.
            System.out.println("Tabla 'apuesta' verificada o creada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'apuesta': " + e.getMessage());
        }
    }
    
    public List<ClaseApuesta> listar() throws SQLException {
        String sql = "SELECT * FROM apuesta"; // Consulta para obtener todos los registros.
        List<ClaseApuesta> apuesta = new ArrayList<>(); // Lista donde se almacenarán los resultados.

        try (Connection conn = DataBaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) { // Ejecuta la consulta.
            while (rs.next()) { // Itera sobre los resultados.
                apuesta.add(new ClaseApuesta(
                        rs.getInt("id"),
                        rs.getString("fecha"),
                        rs.getString("apostador"),
                        rs.getString("evento"),
                        rs.getString("ganador"),
                        rs.getFloat("monto")
                ));
            }
        }
        return apuesta; // Retorna la lista de estudiantes.
    }
    
    public void actualizar(ClaseApuesta claseApuesta) throws SQLException {
        String sql = "UPDATE apuesta SET id=?, fecha= ?, apostador=?, evento=?, ganador=?, monto=? WHERE id=?";
        try (Connection conn = DataBaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, claseApuesta.getId());
            stmt.setString(2, claseApuesta.getFecha());
            stmt.setString(3, claseApuesta.getApostador());
            stmt.setString(4, claseApuesta.getEvento());
            stmt.setString(5, claseApuesta.getGanador());
            stmt.setFloat(6, claseApuesta.getMonto());
        
            stmt.executeUpdate(); // Ejecuta la actualización.
        }
    }
    
    

     
}
