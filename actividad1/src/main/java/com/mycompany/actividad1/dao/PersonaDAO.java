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

    public Persona buscarPorId(double id) throws SQLException {
        String sql = "SELECT id, nombres, apellidos, email FROM persona WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, id);
            try (ResultSet rs = ps.executeQuery()) {
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

    public List<Persona> listar() throws SQLException {
        String sql = "SELECT id, nombres, apellidos, email FROM persona ORDER BY id";
        List<Persona> out = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                out.add(new Persona(
                    rs.getDouble("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email")
                ));
            }
        }
        return out;
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


    public boolean eliminar(double id) throws SQLException {
        String sql = "DELETE FROM persona WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, id); // <-- antes era setLong
            int filas = stmt.executeUpdate();
            return filas > 0;
        }
    }


}
