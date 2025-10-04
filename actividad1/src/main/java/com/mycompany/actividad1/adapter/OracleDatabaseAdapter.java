package com.mycompany.actividad1.adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleDatabaseAdapter implements DatabaseAdapter {
    private final String url;
    private final String user;
    private final String password;

    private static OracleDatabaseAdapter instance;

    private OracleDatabaseAdapter(String url, String user, String password) {
        System.out.println("[OracleDatabaseAdapter] Instancia creada");
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static synchronized OracleDatabaseAdapter getInstance(String url, String user, String password) {
        if (instance == null) {
            instance = new OracleDatabaseAdapter(url, user, password);
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
            
            // Crear tablas específicas para Oracle
            stmt.execute("CREATE TABLE PERSONA (" +
                        "id NUMBER PRIMARY KEY, " +
                        "nombres VARCHAR2(100), " +
                        "apellidos VARCHAR2(100), " +
                        "email VARCHAR2(120))");

            stmt.execute("CREATE TABLE FACULTAD (" +
                        "id NUMBER PRIMARY KEY, " +
                        "nombre VARCHAR2(120), " +
                        "id_decano NUMBER, " +
                        "CONSTRAINT fk_facultad_decano FOREIGN KEY (id_decano) REFERENCES PERSONA(id))");

            stmt.execute("CREATE TABLE PROGRAMA (" +
                        "id NUMBER PRIMARY KEY, " +
                        "nombre VARCHAR2(120), " +
                        "duracion NUMBER, " +
                        "registro DATE, " +
                        "id_facultad NUMBER, " +
                        "CONSTRAINT fk_programa_facultad FOREIGN KEY (id_facultad) REFERENCES FACULTAD(id))");

            stmt.execute("CREATE TABLE PROFESOR (" +
                        "id NUMBER PRIMARY KEY, " +
                        "id_persona NUMBER, " +
                        "contrato VARCHAR2(20), " +
                        "CONSTRAINT fk_profesor_persona FOREIGN KEY (id_persona) REFERENCES PERSONA(id))");

            stmt.execute("CREATE TABLE ESTUDIANTE (" +
                        "id_persona NUMBER PRIMARY KEY, " +
                        "codigo NUMBER, " +
                        "id_programa NUMBER, " +
                        "activo NUMBER(1), " +
                        "promedio NUMBER, " +
                        "CONSTRAINT fk_estudiante_persona FOREIGN KEY (id_persona) REFERENCES PERSONA(id), " +
                        "CONSTRAINT fk_estudiante_programa FOREIGN KEY (id_programa) REFERENCES PROGRAMA(id))");

            stmt.execute("CREATE TABLE CURSO (" +
                        "id NUMBER PRIMARY KEY, " +
                        "nombre VARCHAR2(255), " +
                        "id_programa NUMBER, " +
                        "activo NUMBER(1), " +
                        "CONSTRAINT fk_curso_programa FOREIGN KEY (id_programa) REFERENCES PROGRAMA(id))");

            stmt.execute("CREATE TABLE INSCRIPCION (" +
                        "curso_id NUMBER PRIMARY KEY, " +
                        "estudiante_id NUMBER, " +
                        "anio NUMBER, " +
                        "semestre NUMBER, " +
                        "CONSTRAINT fk_inscripcion_estudiante FOREIGN KEY (estudiante_id) REFERENCES ESTUDIANTE(id_persona), " +
                        "CONSTRAINT fk_inscripcion_curso FOREIGN KEY (curso_id) REFERENCES CURSO(id))");
                        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDatabaseType() {
        return "Oracle";
    }

    @Override
    public String getServerTime() {
        try (Connection conn = getConnection();
             java.sql.Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery("SELECT CURRENT_TIMESTAMP FROM dual")) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "(error obteniendo hora)";
    }
}
