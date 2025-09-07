package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscripcionDAO {

    // INSERTAR
    public boolean insertar(Inscripcion i) throws SQLException {
        String sql = "INSERT INTO inscripcion (curso_id, estudiante_id, anio, semestre) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, i.getCurso().getID());
            ps.setDouble(2, i.getEstudiante().getId());
            ps.setInt(3, i.getAnio());
            ps.setInt(4, i.getSemestre());
            return ps.executeUpdate() > 0;
        }
    }

    // BUSCAR por PK
    public Inscripcion buscar(int cursoId, double estudianteId, int anio, int semestre) throws SQLException {
        String sql =
            "SELECT c.id AS c_id, c.nombre AS c_nombre, " +
            "       p.id AS e_id, p.nombres, p.apellidos, p.email, e.codigo, " +
            "       i.anio, i.semestre " +
            "FROM inscripcion i " +
            "JOIN curso c ON c.id = i.curso_id " +
            "JOIN estudiante e ON e.id_persona = i.estudiante_id " +
            "JOIN persona p ON p.id = e.id_persona " +
            "WHERE i.curso_id = ? AND i.estudiante_id = ? AND i.anio = ? AND i.semestre = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cursoId);
            ps.setDouble(2, estudianteId);
            ps.setInt(3, anio);
            ps.setInt(4, semestre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Curso curso = new Curso(rs.getInt("c_id"), rs.getString("c_nombre"), null, true);
                    Estudiante est = new Estudiante(
                        rs.getDouble("e_id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email"),
                        rs.getString("codigo"),
                        null
                    );
                    return new Inscripcion(curso, rs.getInt("anio"), rs.getInt("semestre"), est);
                }
            }
        }
        return null;
    }

    // LISTAR todas
    public List<Inscripcion> listar() throws SQLException {
        List<Inscripcion> lista = new ArrayList<>();
        String sql =
            "SELECT c.id AS c_id, c.nombre AS c_nombre, " +
            "       p.id AS e_id, p.nombres, p.apellidos, p.email, e.codigo, " +
            "       i.anio, i.semestre " +
            "FROM inscripcion i " +
            "JOIN curso c ON c.id = i.curso_id " +
            "JOIN estudiante e ON e.id_persona = i.estudiante_id " +
            "JOIN persona p ON p.id = e.id_persona " +
            "ORDER BY i.anio DESC, i.semestre DESC, c.id, p.id";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Curso curso = new Curso(rs.getInt("c_id"), rs.getString("c_nombre"), null, true);
                Estudiante est = new Estudiante(
                    rs.getDouble("e_id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getString("codigo"),
                    null
                );
                lista.add(new Inscripcion(curso, rs.getInt("anio"), rs.getInt("semestre"), est));
            }
        }
        return lista;
    }

    // ACTUALIZAR (cambia curso o semestre/año para la misma persona)
    public boolean actualizar(Inscripcion nueva, int cursoIdOld, double estudianteIdOld,
                              int anioOld, int semestreOld) throws SQLException {
        String sql = "UPDATE inscripcion SET curso_id = ?, estudiante_id = ?, anio = ?, semestre = ? " +
                     "WHERE curso_id = ? AND estudiante_id = ? AND anio = ? AND semestre = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nueva.getCurso().getID());
            ps.setDouble(2, nueva.getEstudiante().getId());
            ps.setInt(3, nueva.getAnio());
            ps.setInt(4, nueva.getSemestre());
            ps.setInt(5,  cursoIdOld);
            ps.setDouble(6, estudianteIdOld);
            ps.setInt(7,  anioOld);
            ps.setInt(8,  semestreOld);
            return ps.executeUpdate() > 0;
        }
    }

    // ELIMINAR
    public boolean eliminar(int cursoId, double estudianteId, int anio, int semestre) throws SQLException {
        String sql = "DELETE FROM inscripcion WHERE curso_id = ? AND estudiante_id = ? AND anio = ? AND semestre = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cursoId);
            ps.setDouble(2, estudianteId);
            ps.setInt(3, anio);
            ps.setInt(4, semestre);
            return ps.executeUpdate() > 0;
        }
    }

    // Regla útil: ¿ya existe esa inscripción?
    public boolean existe(int cursoId, double estudianteId, int anio, int semestre) throws SQLException {
        String sql = "SELECT 1 FROM inscripcion WHERE curso_id = ? AND estudiante_id = ? AND anio = ? AND semestre = ? LIMIT 1";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cursoId);
            ps.setDouble(2, estudianteId);
            ps.setInt(3, anio);
            ps.setInt(4, semestre);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
