package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.model.Persona;
import com.mycompany.actividad1.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PersonaDAO {

    public void insertar(Persona persona) throws SQLException {
        String sql = "INSERT INTO persona (nombres, apellidos, email) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, persona.getNombres());
            stmt.setString(2, persona.getApellidos());
            stmt.setString(3, persona.getEmail());
            stmt.executeUpdate();
        }
    }

    public List<Persona> listar() throws SQLException {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT * FROM persona";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Persona persona = new Persona(
                        rs.getDouble("id"),          
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email")
                );
                lista.add(persona);
            }
        }
        return lista;
    }

    public void eliminar(long id) throws SQLException {
        String sql = "DELETE FROM persona WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
