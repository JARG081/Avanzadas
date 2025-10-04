package com.mycompany.actividad1.adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabaseAdapter implements DatabaseAdapter {
    private final String url;
    private final String user;
    private final String password;

    private static MySQLDatabaseAdapter instance;

    private MySQLDatabaseAdapter(String url, String user, String password) {
        System.out.println("[MySQLDatabaseAdapter] Instancia creada");
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static synchronized MySQLDatabaseAdapter getInstance(String url, String user, String password) {
        if (instance == null) {
            instance = new MySQLDatabaseAdapter(url, user, password);
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void initDatabase() {
        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement()) {
          
            // Crear tablas específicas para MySQL
            stmt.execute("CREATE TABLE IF NOT EXISTS PERSONA (" +
                        "id DOUBLE AUTO_INCREMENT PRIMARY KEY, " +
                        "nombres VARCHAR(100), " +
                        "apellidos VARCHAR(100), " +
                        "email VARCHAR(120))");
                        
            stmt.execute("CREATE TABLE IF NOT EXISTS FACULTAD (" +
                        "id DOUBLE AUTO_INCREMENT PRIMARY KEY, " +
                        "nombre VARCHAR(120), " +
                        "id_decano DOUBLE, " +
                        "FOREIGN KEY (id_decano) REFERENCES PERSONA(id))");
                        
            stmt.execute("CREATE TABLE IF NOT EXISTS PROGRAMA (" +
                        "id DOUBLE AUTO_INCREMENT PRIMARY KEY, " +
                        "nombre VARCHAR(120), " +
                        "duracion DOUBLE, " +
                        "registro DATE, " +
                        "id_facultad DOUBLE, " +
                        "FOREIGN KEY (id_facultad) REFERENCES FACULTAD(id))");
                        
            stmt.execute("CREATE TABLE IF NOT EXISTS PROFESOR (" +
                        "id DOUBLE AUTO_INCREMENT PRIMARY KEY, " +
                        "id_persona DOUBLE, " +
                        "contrato ENUM('Trabajo', 'Servicios'), " +
                        "FOREIGN KEY (id_persona) REFERENCES PERSONA(id))");
                        
            stmt.execute("CREATE TABLE IF NOT EXISTS ESTUDIANTE (" +
                        "id_persona DOUBLE PRIMARY KEY, " +
                        "codigo DOUBLE, " +
                        "id_programa DOUBLE, " +
                        "activo BOOLEAN, " +
                        "promedio DOUBLE, " +
                        "FOREIGN KEY (id_persona) REFERENCES PERSONA(id), " +
                        "FOREIGN KEY (id_programa) REFERENCES PROGRAMA(id))");
                        
            stmt.execute("CREATE TABLE IF NOT EXISTS CURSO (" +
                        "id DOUBLE AUTO_INCREMENT PRIMARY KEY, " +
                        "nombre VARCHAR(255), " +
                        "id_programa DOUBLE, " +
                        "activo BOOLEAN, " +
                        "FOREIGN KEY (id_programa) REFERENCES PROGRAMA(id))");
                        
            stmt.execute("CREATE TABLE IF NOT EXISTS INSCRIPCION (" +
                        "curso_id DOUBLE AUTO_INCREMENT PRIMARY KEY, " +
                        "estudiante_id DOUBLE, " +
                        "anio INT, " +
                        "semestre INT, " +
                        "FOREIGN KEY (estudiante_id) REFERENCES ESTUDIANTE(id_persona), " +
                        "FOREIGN KEY (curso_id) REFERENCES CURSO(id))");
                        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDatabaseType() {
        return "MySQL";
    }

    @Override
    public String getServerTime() {
        try (Connection conn = getConnection();
             java.sql.Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery("SELECT NOW()")) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "(error obteniendo hora)";
    }
}
