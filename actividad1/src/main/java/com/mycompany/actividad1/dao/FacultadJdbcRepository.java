package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.model.Facultad;
import com.mycompany.actividad1.model.Persona;
import repository.FacultadRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultadJdbcRepository implements FacultadRepository {

    @Override
    public void insertar(Facultad f) {
        final String sql = "INSERT INTO facultad (id, nombre, id_decano) VALUES (?, ?, ?)";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, f.getID());
            ps.setString(2, f.getNombre());
            if (f.getDecano() != null && f.getDecano().getId() != null) {
                ps.setDouble(3, f.getDecano().getId());
            } else {
                ps.setNull(3, Types.DOUBLE);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar facultad", e);
        }
    }

    @Override
    public boolean actualizar(Facultad f) {
        final String sql = "UPDATE facultad SET nombre=?, id_decano=? WHERE id=?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, f.getNombre());
            if (f.getDecano() != null && f.getDecano().getId() != null) {
                ps.setDouble(2, f.getDecano().getId());
            } else {
                ps.setNull(2, Types.DOUBLE);
            }
            ps.setDouble(3, f.getID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar facultad", e);
        }
    }

    @Override
    public boolean eliminar(Double id) {
        final String sql = "DELETE FROM facultad WHERE id = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar facultad", e);
        }
    }

    @Override
    public Facultad buscarPorId(Double id) {
        final String sql =
            "SELECT f.id, f.nombre, f.id_decano, " +
            "       p.nombres, p.apellidos, p.email " +
            "FROM facultad f " +
            "LEFT JOIN persona p ON p.id = f.id_decano " +
            "WHERE f.id = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Persona dec = null;
                    Double idDec = rs.getDouble("id_decano");
                    if (!rs.wasNull()) {
                        dec = new Persona(idDec, rs.getString("nombres"),
                                rs.getString("apellidos"), rs.getString("email"));
                    }
                    com.mycompany.actividad1.model.Facultad f = new com.mycompany.actividad1.model.Facultad();//coonstructor no se puede arrancar con los tipos dados
                    f.setID(rs.getDouble("id"));
                    f.setNombre(rs.getString("nombre"));
                    f.setDecano(dec);
                    return f;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar facultad", e);
        }
        return null;
    }

    @Override
    public List<Facultad> listar() {
        final String sql =
            "SELECT f.id, f.nombre, f.id_decano, " +
            "       p.nombres, p.apellidos, p.email " +
            "FROM facultad f " +
            "LEFT JOIN persona p ON p.id = f.id_decano " +
            "ORDER BY f.id";
        List<Facultad> out = new ArrayList<>();
        try (Connection c = Database.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Persona dec = null;
                Double idDec = rs.getDouble("id_decano");
                if (!rs.wasNull()) {
                    dec = new Persona(idDec, rs.getString("nombres"),
                            rs.getString("apellidos"), rs.getString("email"));
                }
                com.mycompany.actividad1.model.Facultad f = new com.mycompany.actividad1.model.Facultad();//coonstructor no se puede arrancar con los tipos dados
                f.setID(rs.getDouble("id"));
                f.setNombre(rs.getString("nombre"));
                f.setDecano(dec);
                out.add(f);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar facultades", e);
        }
        return out;
    }
}
