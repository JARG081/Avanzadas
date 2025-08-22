package com.mycompany.actividad1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:h2:file:C:\\Users\\josem\\OneDrive\\Documentos\\NetBeansProjects\\actividad1\\src\\main\\java\\com\\mycompany\\actividad1\\bd";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
