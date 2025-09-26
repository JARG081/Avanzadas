package com.mycompany.actividad1.adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2DatabaseAdapter implements DatabaseAdapter {
    private final String url;
    private final String user;
    private final String password;

    public H2DatabaseAdapter(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void initDatabase() {
        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement()) {
            
            // Crear tablas específicas para H2
            stmt.execute("CREATE TABLE IF NOT EXISTS PERSONA (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "nombre VARCHAR(255), " +
                        "edad INT)");
                        
            stmt.execute("CREATE TABLE IF NOT EXISTS FACULTAD (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "nombre VARCHAR(255), " +
                        "ubicacion VARCHAR(255))");
                        
            stmt.execute("CREATE TABLE IF NOT EXISTS PROGRAMA (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "nombre VARCHAR(255), " +
                        "facultad_id INT, " +
                        "FOREIGN KEY (facultad_id) REFERENCES FACULTAD(id))");
                        
            stmt.execute("CREATE TABLE IF NOT EXISTS PROFESOR (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "persona_id INT, " +
                        "especialidad VARCHAR(255), " +
                        "FOREIGN KEY (persona_id) REFERENCES PERSONA(id))");
                        
            stmt.execute("CREATE TABLE IF NOT EXISTS ESTUDIANTE (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "persona_id INT, " +
                        "programa_id INT, " +
                        "semestre INT, " +
                        "FOREIGN KEY (persona_id) REFERENCES PERSONA(id), " +
                        "FOREIGN KEY (programa_id) REFERENCES PROGRAMA(id))");
                        
            stmt.execute("CREATE TABLE IF NOT EXISTS CURSO (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "nombre VARCHAR(255), " +
                        "creditos INT, " +
                        "programa_id INT, " +
                        "FOREIGN KEY (programa_id) REFERENCES PROGRAMA(id))");
                        
            stmt.execute("CREATE TABLE IF NOT EXISTS INSCRIPCION (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "estudiante_id INT, " +
                        "curso_id INT, " +
                        "fecha_inscripcion DATE, " +
                        "FOREIGN KEY (estudiante_id) REFERENCES ESTUDIANTE(id), " +
                        "FOREIGN KEY (curso_id) REFERENCES CURSO(id))");
                        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDatabaseType() {
        return "H2";
    }
}
