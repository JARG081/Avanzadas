package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.model.Persona;
import com.mycompany.actividad1.model.Profesor;
import repository.ProfesorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesorJdbcRepository implements ProfesorRepository {

    @Override
    public void insertar(Profesor profesor) {
        final String sql = "INSERT INTO profesor (id_persona, contrato) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, profesor.getIdPersona());
            ps.setString(2, profesor.getContrato());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar profesor", e);
        }
    }

    @Override
    public boolean actualizar(Profesor profesor) {
        final String sql = "UPDATE profesor SET contrato = ? WHERE id_persona = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, profesor.getContrato());
            ps.setDouble(2, profesor.getIdPersona());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar profesor", e);
        }
    }

    @Override
    public boolean eliminar(Double idPersona) {
        final String sql = "DELETE FROM profesor WHERE id_persona = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, idPersona);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar profesor", e);
        }
    }

    @Override
    public Profesor buscarPorIdPersona(Double idPersona) {
        final String sql =
            "SELECT p.id, p.nombres, p.apellidos, p.email, pr.contrato " +
            "FROM profesor pr JOIN persona p ON p.id = pr.id_persona " +
            "WHERE pr.id_persona = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, idPersona);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Persona per = new Persona(
                        rs.getDouble("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email")
                    );
                    Profesor pr = new Profesor(idPersona, rs.getString("contrato"));
                    pr.setPersona(per);
                    return pr;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar profesor", e);
        }
        return null;
    }

    @Override
    public List<Profesor> listar() {
        final String sql =
            "SELECT p.id, p.nombres, p.apellidos, p.email, pr.contrato " +
            "FROM profesor pr JOIN persona p ON p.id = pr.id_persona " +
            "ORDER BY p.id";
        List<Profesor> out = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Persona per = new Persona(
                    rs.getDouble("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email")
                );
                Profesor pr = new Profesor(per.getId(), rs.getString("contrato"));
                pr.setPersona(per);
                out.add(pr);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar profesores", e);
        }
        return out;
    }
}
