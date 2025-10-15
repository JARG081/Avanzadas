package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.model.Curso;
import com.mycompany.actividad1.model.Estudiante;
import com.mycompany.actividad1.model.Inscripcion;
import com.mycompany.actividad1.model.Programa;
import repository.CursosInscritosRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursosInscritosJdbc implements CursosInscritosRepository {

    @Override
    public boolean insertar(Inscripcion insc) {
        final String sql = "INSERT INTO INSCRIPCION (curso_id, estudiante_id, anio, semestre) VALUES (?,?,?,?)";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, insc.getCurso().getID());
            ps.setDouble(2, insc.getEstudiante().getId());
            ps.setInt(3, insc.getAnio());
            ps.setInt(4, insc.getSemestre());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new RuntimeException("Error insertando inscripción", e); }
    }

    @Override
    public Inscripcion buscar(Double idCurso, Double idEstudiante, Integer anio, Integer semestre) {
        final String sql =
            "SELECT c.id AS c_id, c.nombre AS c_nombre, c.id_programa AS c_prog, c.activo AS c_activo, " +
            "       p.nombre AS prog_nombre, " +
            "       pe.id AS e_id, pe.nombres AS e_nom, pe.apellidos AS e_ape, pe.email AS e_mail, e.codigo AS e_cod " +
            "FROM INSCRIPCION i " +
            "JOIN CURSO c ON c.id = i.curso_id " +
            "LEFT JOIN PROGRAMA p ON p.id = c.id_programa " +
            "LEFT JOIN PERSONA pe ON pe.id = i.estudiante_id " +
            "LEFT JOIN ESTUDIANTE e ON e.id_persona = pe.id " +
            "WHERE i.curso_id=? AND i.estudiante_id=? AND i.anio=? AND i.semestre=?";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, idCurso);
            ps.setDouble(2, idEstudiante);
            ps.setInt(3, anio);
            ps.setInt(4, semestre);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Curso cu = new Curso();
                cu.setID(rs.getDouble("c_id"));
                cu.setNombre(rs.getString("c_nombre"));
                cu.setActivo(rs.getBoolean("c_activo"));
                double pid = rs.getDouble("c_prog");
                if (!rs.wasNull()) {
                    Programa pr = new Programa();
                    pr.setId(pid);
                    pr.setNombre(rs.getString("prog_nombre"));
                    cu.setPrograma(pr);
                }

                Estudiante es = new Estudiante(
                    rs.getDouble("e_id"),
                    rs.getString("e_nom"),
                    rs.getString("e_ape"),
                    rs.getString("e_mail"),
                    rs.getString("e_cod"),
                    null
                );

                return new Inscripcion(cu, anio, semestre, es);
            }
        } catch (SQLException e) { throw new RuntimeException("Error buscando inscripción", e); }
    }

    @Override
    public boolean actualizar(Inscripcion nueva, Double idCursoOld, Double idEstOld, Integer anioOld, Integer semOld) {
        final String sql =
            "UPDATE INSCRIPCION SET curso_id=?, estudiante_id=?, anio=?, semestre=? " +
            "WHERE curso_id=? AND estudiante_id=? AND anio=? AND semestre=?";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, nueva.getCurso().getID());
            ps.setDouble(2, nueva.getEstudiante().getId());
            ps.setInt(3, nueva.getAnio());
            ps.setInt(4, nueva.getSemestre());
            ps.setDouble(5, idCursoOld);
            ps.setDouble(6, idEstOld);
            ps.setInt(7, anioOld);
            ps.setInt(8, semOld);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new RuntimeException("Error actualizando inscripción", e); }
    }

    @Override
    public boolean eliminar(Double idCurso, Double idEstudiante, Integer anio, Integer semestre) {
        final String sql = "DELETE FROM INSCRIPCION WHERE curso_id=? AND estudiante_id=? AND anio=? AND semestre=?";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, idCurso);
            ps.setDouble(2, idEstudiante);
            ps.setInt(3, anio);
            ps.setInt(4, semestre);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new RuntimeException("Error eliminando inscripción", e); }
    }

    @Override
    public boolean existe(Double idCurso, Double idEstudiante, Integer anio, Integer semestre) {
        final String sql = "SELECT 1 FROM INSCRIPCION WHERE curso_id=? AND estudiante_id=? AND anio=? AND semestre=? LIMIT 1";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, idCurso);
            ps.setDouble(2, idEstudiante);
            ps.setInt(3, anio);
            ps.setInt(4, semestre);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("Error verificando existencia de inscripción", e); }
    }

    @Override
    public List<Inscripcion> listar() {
        final String sql =
            "SELECT i.anio, i.semestre, " +
            "       c.id AS c_id, c.nombre AS c_nombre, c.id_programa AS c_prog, c.activo AS c_activo, p.nombre AS prog_nombre, " +
            "       pe.id AS e_id, pe.nombres AS e_nom, pe.apellidos AS e_ape, pe.email AS e_mail, e.codigo AS e_cod " +
            "FROM INSCRIPCION i " +
            "JOIN CURSO c ON c.id = i.curso_id " +
            "LEFT JOIN PROGRAMA p ON p.id = c.id_programa " +
            "LEFT JOIN PERSONA pe ON pe.id = i.estudiante_id " +
            "LEFT JOIN ESTUDIANTE e ON e.id_persona = pe.id " +
            "ORDER BY i.anio DESC, i.semestre DESC, c.id";
        List<Inscripcion> out = new ArrayList<>();
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Curso cu = new Curso();
                cu.setID(rs.getDouble("c_id"));
                cu.setNombre(rs.getString("c_nombre"));
                cu.setActivo(rs.getBoolean("c_activo"));
                double pid = rs.getDouble("c_prog");
                if (!rs.wasNull()) {
                    Programa pr = new Programa();
                    pr.setId(pid);
                    pr.setNombre(rs.getString("prog_nombre"));
                    cu.setPrograma(pr);
                }
                Estudiante es = new Estudiante(
                    rs.getDouble("e_id"),
                    rs.getString("e_nom"),
                    rs.getString("e_ape"),
                    rs.getString("e_mail"),
                    rs.getString("e_cod"),
                    null
                );
                out.add(new Inscripcion(cu, rs.getInt("anio"), rs.getInt("semestre"), es));
            }
        } catch (SQLException e) { throw new RuntimeException("Error listando inscripciones", e); }
        return out;
    }
}
