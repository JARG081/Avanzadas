package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.model.Persona;
import com.mycompany.actividad1.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PersonaDAO {

    public void insertar(Persona persona) throws SQLException {
        String sql = "INSERT INTO persona (id, nombres, apellidos, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, persona.getId());
            stmt.setString(2, persona.getNombres());
            stmt.setString(3, persona.getApellidos());
            stmt.setString(4, persona.getEmail());
            stmt.executeUpdate();
        }
    }

    public List<Persona> listar() throws SQLException {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT id, nombres, apellidos, email FROM persona ORDER BY id ASC";

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
    public Persona buscarPorId(Double id) throws SQLException {
        String sql = "SELECT * FROM persona WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Persona(
                        rs.getDouble("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email")
                    );
                }
            }
        }
        return null;
    }

    
    public boolean actualizar(Persona persona) throws SQLException {
    String sql = "UPDATE persona SET nombres = ?, apellidos = ?, email = ? WHERE id = ?";
    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, persona.getNombres());
        stmt.setString(2, persona.getApellidos());
        stmt.setString(3, persona.getEmail());
        stmt.setDouble(4, persona.getId());

        int filas = stmt.executeUpdate();
        return filas > 0; // true si se actualizó, false si no
    }
}


    public boolean eliminar(long id) throws SQLException {
        String sql = "DELETE FROM persona WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int filas = stmt.executeUpdate();
            return filas > 0; // true si se eliminó, false si no había registro
        }
    }

}
