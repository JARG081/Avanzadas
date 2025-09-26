package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.model.Estudiante;
import com.mycompany.actividad1.model.Persona;
import com.mycompany.actividad1.model.Programa;
import repository.EstudianteRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteJdbcRepository implements EstudianteRepository {

    @Override
    public void insertar(Estudiante est) {
        final String sql = "INSERT INTO estudiante (id_persona, codigo, id_programa) VALUES (?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, est.getId());
            ps.setString(2, est.getCodigo());
            if (est.getPrograma() != null && est.getPrograma().getId() != null) {
                ps.setDouble(3, est.getPrograma().getId());
            } else {
                ps.setNull(3, Types.DOUBLE);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar estudiante", e);
        }
    }

    @Override
    public Estudiante buscarPorIdPersona(Double idPersona) {
        final String sql =
            "SELECT p.id, p.nombres, p.apellidos, p.email, " +
            "       e.codigo, e.id_programa, pr.nombre AS programa_nombre " +
            "FROM estudiante e " +
            "JOIN persona p ON p.id = e.id_persona " +
            "LEFT JOIN programa pr ON pr.id = e.id_programa " +
            "WHERE e.id_persona = ?";
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
                    Programa prog = null;
                    double pid = rs.getDouble("id_programa");
                    if (!rs.wasNull()) {
                        prog = new Programa();
                        prog.setId(pid);
                        prog.setNombre(rs.getString("programa_nombre"));
                    }
                    return new Estudiante(
                        per.getId(), per.getNombres(), per.getApellidos(), per.getEmail(),
                        rs.getString("codigo"), prog
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar estudiante", e);
        }
        return null;
    }

    @Override
    public List<Estudiante> listar() {
        final String sql =
            "SELECT p.id, p.nombres, p.apellidos, p.email, " +
            "       e.codigo, e.id_programa, pr.nombre AS programa_nombre " +
            "FROM estudiante e " +
            "JOIN persona p ON p.id = e.id_persona " +
            "LEFT JOIN programa pr ON pr.id = e.id_programa " +
            "ORDER BY p.id";
        List<Estudiante> out = new ArrayList<>();
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
                Programa prog = null;
                double pid = rs.getDouble("id_programa");
                if (!rs.wasNull()) {
                    prog = new Programa();
                    prog.setId(pid);
                    prog.setNombre(rs.getString("programa_nombre"));
                }
                out.add(new Estudiante(
                    per.getId(), per.getNombres(), per.getApellidos(), per.getEmail(),
                    rs.getString("codigo"), prog
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar estudiantes", e);
        }
        return out;
    }

    @Override
    public boolean actualizar(Estudiante est) {
        final String sql = "UPDATE estudiante SET codigo = ?, id_programa = ? WHERE id_persona = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, est.getCodigo());
            if (est.getPrograma() != null && est.getPrograma().getId() != null) {
                ps.setDouble(2, est.getPrograma().getId());
            } else {
                ps.setNull(2, Types.DOUBLE);
            }
            ps.setDouble(3, est.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estudiante", e);
        }
    }

    @Override
    public boolean eliminar(Double idPersona) {
        final String sql = "DELETE FROM estudiante WHERE id_persona = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, idPersona);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar estudiante", e);
        }
    }
}
