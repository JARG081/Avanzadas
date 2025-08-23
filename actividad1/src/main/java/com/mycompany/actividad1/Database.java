package com.mycompany.actividad1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:h2:C:\\Users\\josem\\OneDrive\\Escritorio\\Avanzadas\\Avanzadas\\actividad1\\src\\main\\java\\com\\mycompany\\actividad1/bd"; 
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initDatabase() {
        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS PERSONA (" +
                         "id INT AUTO_INCREMENT PRIMARY KEY, " +
                         "nombre VARCHAR(255), " +
                         "edad INT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
