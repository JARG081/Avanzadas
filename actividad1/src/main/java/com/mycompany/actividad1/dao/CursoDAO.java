package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.model.Curso;
import com.mycompany.actividad1.model.Programa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    // Insertar curso
public boolean insertar(Curso curso) {
    String sql = "INSERT INTO curso (id, nombre, id_programa, activo) VALUES (?, ?, ?, ?)";
    try (Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setDouble(1, curso.getID());
        stmt.setString(2, curso.getNombre());
        if (curso.getPrograma() != null && curso.getPrograma().getId() != null) {
            stmt.setDouble(3, curso.getPrograma().getId());
        } else {
            stmt.setNull(3, java.sql.Types.DOUBLE);
        }
        stmt.setBoolean(4, curso.getActivo());

        stmt.executeUpdate();
        return true;

    } catch (SQLException e) {
        System.err.println("Error: no se pudo insertar el curso.");
        return false;
    }
}


// Listar cursos
public List<Curso> listar() throws SQLException {
    List<Curso> cursos = new ArrayList<>();
    String sql = "SELECT * FROM curso";
    try (Connection conn = Database.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nombre = rs.getString("nombre");

            Programa p = null;
            double progId = rs.getDouble("id_programa"); // ✅ corregido
            if (!rs.wasNull()) {
                p = new Programa();
                p.setId(progId); // ✅ double
            }

            Boolean activo = rs.getBoolean("activo");
            Curso curso = new Curso(id, nombre, p, activo);

            cursos.add(curso);
        }
    }
    return cursos;
}

// Buscar curso por ID
public Curso buscarPorId(int id) throws SQLException {
    String sql = "SELECT * FROM curso WHERE id = ?";
    try (Connection conn = Database.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Curso curso = new Curso();
                curso.setID(rs.getInt("id"));
                curso.setNombre(rs.getString("nombre"));

                double progId = rs.getDouble("id_programa"); // ✅ corregido
                if (!rs.wasNull()) {
                    Programa p = new Programa();
                    p.setId(progId); // ✅ double
                    curso.setPrograma(p);
                }

                curso.setActivo(rs.getBoolean("activo"));
                return curso;
            }
        }
    }
    return null;
}


    // Actualizar curso
    public boolean actualizar(Curso curso) throws SQLException {
        String sql = "UPDATE curso SET nombre = ?, id_programa = ?, activo = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, curso.getNombre());

            if (curso.getPrograma() != null && curso.getPrograma().getId() != null) {
                ps.setDouble(2, curso.getPrograma().getId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            ps.setBoolean(3, curso.getActivo() != null ? curso.getActivo() : false);
            ps.setInt(4, curso.getID());

            return ps.executeUpdate() > 0;
        }
    }

    // Eliminar curso
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM curso WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
