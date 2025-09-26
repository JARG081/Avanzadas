package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.model.Persona;
import repository.PersonaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaJdbcRepository implements PersonaRepository {

    @Override
    public void insertar(Persona persona) {
        final String sql = "INSERT INTO persona (id, nombres, apellidos, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, persona.getId());     // IDs como DOUBLE
            ps.setString(2, persona.getNombres());
            ps.setString(3, persona.getApellidos());
            ps.setString(4, persona.getEmail());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar persona", e);
        }
    }

    @Override
    public boolean actualizar(Persona persona) {
        final String sql = "UPDATE persona SET nombres = ?, apellidos = ?, email = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, persona.getNombres());
            ps.setString(2, persona.getApellidos());
            ps.setString(3, persona.getEmail());
            ps.setDouble(4, persona.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar persona", e);
        }
    }

    @Override
    public boolean eliminar(Double id) {
        final String sql = "DELETE FROM persona WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar persona", e);
        }
    }

    @Override
    public Persona buscarPorId(Double id) {
        final String sql = "SELECT id, nombres, apellidos, email FROM persona WHERE id = ?";
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

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar persona", e);
        }
        return null;
    }

    @Override
    public List<Persona> listar() {
        final String sql = "SELECT id, nombres, apellidos, email FROM persona ORDER BY id";
        final List<Persona> lista = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Persona(
                        rs.getDouble("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar personas", e);
        }
        return lista;
    }
}
