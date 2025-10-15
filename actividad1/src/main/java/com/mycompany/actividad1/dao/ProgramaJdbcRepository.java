package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.model.Facultad;
import com.mycompany.actividad1.model.Programa;
import repository.ProgramaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramaJdbcRepository implements ProgramaRepository {

    @Override
    public void insertar(Programa p) {
        final String sql = "INSERT INTO PROGRAMA (id, nombre, duracion, registro, id_facultad) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, p.getId());
            ps.setString(2, p.getNombre());
            ps.setInt(3, p.getDuracion());
            java.sql.Date reg = p.getRegistro();
            if (reg != null) ps.setDate(4, reg);
            else ps.setNull(4, java.sql.Types.DATE);
            if (p.getFacultad() != null && p.getFacultad().getID() != null)
                ps.setDouble(5, p.getFacultad().getID());
            else
                ps.setNull(5, Types.DOUBLE);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar programa", e);
        }
    }

    @Override
    public boolean actualizar(Programa p) {
        final String sql = "UPDATE PROGRAMA SET nombre=?, duracion=?, registro=?, id_facultad=? WHERE id=?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getDuracion());
            java.sql.Date reg = p.getRegistro();
            if (reg != null) ps.setDate(3, reg);
            else ps.setNull(3, java.sql.Types.DATE);
            if (p.getFacultad() != null && p.getFacultad().getID() != null)
                ps.setDouble(4, p.getFacultad().getID());
            else
                ps.setNull(4, Types.DOUBLE);
            ps.setDouble(5, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar programa", e);
        }
    }

    @Override
    public boolean eliminar(Double id) {
        final String sql = "DELETE FROM PROGRAMA WHERE id = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar programa", e);
        }
    }

    @Override
    public Programa buscarPorId(Double id) {
        final String sql =
            "SELECT pr.id, pr.nombre, pr.duracion, pr.registro, pr.id_facultad, " +
            "       f.nombre AS facultad_nombre " +
            "FROM PROGRAMA pr " +
            "LEFT JOIN FACULTAD f ON f.id = pr.id_facultad " +
            "WHERE pr.id = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Programa p = new Programa();
                    p.setId(rs.getDouble("id"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDuracion(rs.getInt("duracion"));
                    java.sql.Date d = rs.getDate("registro");
                    p.setRegistro(d); // SIN toLocalDate()
                    Double idFac = rs.getDouble("id_facultad");
                    if (!rs.wasNull()) {
                        Facultad f = new Facultad();
                        f.setID(idFac);
                        f.setNombre(rs.getString("facultad_nombre"));
                        p.setFacultad(f);
                    }
                    return p;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar programa", e);
        }
        return null;
    }

    @Override
    public List<Programa> listar() {
        final String sql =
            "SELECT pr.id, pr.nombre, pr.duracion, pr.registro, pr.id_facultad, " +
            "       f.nombre AS facultad_nombre " +
            "FROM PROGRAMA pr " +
            "LEFT JOIN FACULTAD f ON f.id = pr.id_facultad " +
            "ORDER BY pr.id";
        List<Programa> out = new ArrayList<>();
        try (Connection c = Database.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Programa p = new Programa();
                p.setId(rs.getDouble("id"));
                p.setNombre(rs.getString("nombre"));
                p.setDuracion(rs.getInt("duracion"));
                java.sql.Date d = rs.getDate("registro");
                p.setRegistro(d);
                Double idFac = rs.getDouble("id_facultad");
                if (!rs.wasNull()) {
                    Facultad f = new Facultad();
                    f.setID(idFac);
                    f.setNombre(rs.getString("facultad_nombre"));
                    p.setFacultad(f);
                }
                out.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar programas", e);
        }
        return out;
    }
}
