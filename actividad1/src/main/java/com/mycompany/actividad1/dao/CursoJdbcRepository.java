package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.model.Curso;
import com.mycompany.actividad1.model.Programa;
import repository.CursoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoJdbcRepository implements CursoRepository {

    @Override
    public void insertar(Curso c) {
        final String sql = "INSERT INTO curso (id, nombre, id_programa, activo) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, c.getID());
            ps.setString(2, c.getNombre());
            if (c.getPrograma() != null && c.getPrograma().getId() != null)
                ps.setDouble(3, c.getPrograma().getId());
            else
                ps.setNull(3, Types.DOUBLE);
            ps.setBoolean(4, c.getActivo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar curso", e);
        }
    }

    @Override
    public boolean actualizar(Curso c) {
        final String sql = "UPDATE curso SET nombre=?, id_programa=?, activo=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            if (c.getPrograma() != null && c.getPrograma().getId() != null)
                ps.setDouble(2, c.getPrograma().getId());
            else
                ps.setNull(2, Types.DOUBLE);
            ps.setBoolean(3, c.getActivo());
            ps.setDouble(4, c.getID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar curso", e);
        }
    }

    @Override
    public boolean eliminar(Double id) {
        final String sql = "DELETE FROM curso WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar curso", e);
        }
    }

    @Override
    public Curso buscarPorId(Double id) {
        final String sql =
            "SELECT c.id, c.nombre, c.id_programa, c.activo, pr.nombre AS programa_nombre " +
            "FROM curso c " +
            "LEFT JOIN programa pr ON pr.id = c.id_programa " +
            "WHERE c.id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Curso c = new Curso();
                    c.setID(rs.getDouble("id"));
                    c.setNombre(rs.getString("nombre"));
                    c.setActivo(rs.getBoolean("activo"));
                    Double idProg = rs.getDouble("id_programa");
                    if (!rs.wasNull()) {
                        Programa p = new Programa();
                        p.setId(idProg);
                        p.setNombre(rs.getString("programa_nombre"));
                        c.setPrograma(p);
                    }
                    return c;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar curso", e);
        }
        return null;
    }

    @Override
    public List<Curso> listar() {
        final String sql =
            "SELECT c.id, c.nombre, c.id_programa, c.activo, pr.nombre AS programa_nombre " +
            "FROM curso c " +
            "LEFT JOIN programa pr ON pr.id = c.id_programa " +
            "ORDER BY c.id";
        List<Curso> out = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Curso c = new Curso();
                c.setID(rs.getDouble("id"));
                c.setNombre(rs.getString("nombre"));
                c.setActivo(rs.getBoolean("activo"));
                Double idProg = rs.getDouble("id_programa");
                if (!rs.wasNull()) {
                    Programa p = new Programa();
                    p.setId(idProg);
                    p.setNombre(rs.getString("programa_nombre"));
                    c.setPrograma(p);
                }
                out.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar cursos", e);
        }
        return out;
    }
}
