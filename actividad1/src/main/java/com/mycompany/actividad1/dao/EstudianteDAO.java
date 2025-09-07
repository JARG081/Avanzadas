package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.model.Estudiante;
import com.mycompany.actividad1.model.Persona;
import com.mycompany.actividad1.model.Programa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    // INSERTAR (valida unicidad por id_persona)
    public boolean insertar(Estudiante est) throws SQLException {
        String sql = "INSERT INTO estudiante (id_persona, codigo, id_programa) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, est.getId());                 // Persona ID (de la superclase)
            ps.setString(2, est.getCodigo());
            if (est.getPrograma() != null && est.getPrograma().getId() != null) {
                ps.setDouble(3, est.getPrograma().getId());
            } else {
                ps.setNull(3, Types.DOUBLE);
            }
            return ps.executeUpdate() > 0;
        }
    }

    // BUSCAR por id_persona
    public Estudiante buscarPorIdPersona(double idPersona) throws SQLException {
        String sql =
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
                    Persona persona = new Persona(
                        rs.getDouble("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email")
                    );
                    Programa prog = null;
                    double progId = rs.getDouble("id_programa");
                    if (!rs.wasNull()) {
                        prog = new Programa();
                        prog.setId(progId);
                        prog.setNombre(rs.getString("programa_nombre"));
                    }
                    return new Estudiante(
                        rs.getDouble("id"),
                        persona.getNombres(),
                        persona.getApellidos(),
                        persona.getEmail(),
                        rs.getString("codigo"),
                        prog
                    );
                }
            }
        }
        return null;
    }

    // LISTAR todos
    public List<Estudiante> listar() throws SQLException {
        List<Estudiante> lista = new ArrayList<>();
        String sql =
            "SELECT p.id, p.nombres, p.apellidos, p.email, " +
            "       e.codigo, e.id_programa, pr.nombre AS programa_nombre " +
            "FROM estudiante e " +
            "JOIN persona p ON p.id = e.id_persona " +
            "LEFT JOIN programa pr ON pr.id = e.id_programa " +
            "ORDER BY p.id";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Persona persona = new Persona(
                    rs.getDouble("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email")
                );
                Programa prog = null;
                double progId = rs.getDouble("id_programa");
                if (!rs.wasNull()) {
                    prog = new Programa();
                    prog.setId(progId);
                    prog.setNombre(rs.getString("programa_nombre"));
                }
                Estudiante est = new Estudiante(
                    persona.getId().doubleValue(),
                    persona.getNombres(),
                    persona.getApellidos(),
                    persona.getEmail(),
                    rs.getString("codigo"),
                    prog
                );
                lista.add(est);
            }
        }
        return lista;
    }

    // ACTUALIZAR (por id_persona)
    public boolean actualizar(Estudiante est) throws SQLException {
        String sql = "UPDATE estudiante SET codigo = ?, id_programa = ? WHERE id_persona = ?";
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
        }
    }

    // ELIMINAR (por id_persona)
    public boolean eliminar(double idPersona) throws SQLException {
        String sql = "DELETE FROM estudiante WHERE id_persona = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, idPersona);
            return ps.executeUpdate() > 0;
        }
    }

    // Aux: ¿ya existe registro para este id_persona?
    public boolean existeByIdPersona(double idPersona) throws SQLException {
        String sql = "SELECT 1 FROM estudiante WHERE id_persona = ? LIMIT 1";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, idPersona);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
