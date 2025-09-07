package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.model.Facultad;
import com.mycompany.actividad1.model.Persona;
import com.mycompany.actividad1.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultadDAO {

    // ================= INSERTAR =================
    public boolean insertar(Facultad facultad) throws SQLException {
        String sql = "INSERT INTO facultad (id, nombre, id_decano) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, facultad.getID());
            stmt.setString(2, facultad.getNombre());
            // Persona.getId() es Long -> lo convertimos explícitamente a double
            Double decanoId = facultad.getDecano() == null ? null : 
                              (facultad.getDecano().getId() == null ? null : facultad.getDecano().getId().doubleValue());
            if (decanoId == null) stmt.setNull(3, Types.DOUBLE);
            else stmt.setDouble(3, decanoId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ================= BUSCAR =================
    public Facultad buscarPorId(Double id) throws SQLException {
        String sql = "SELECT * FROM facultad WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    Double decanoId = rs.getDouble("id_decano");

                    PersonaDAO personaDAO = new PersonaDAO();
                    Persona decano = personaDAO.buscarPorId(decanoId);

                    return new Facultad(id, nombre, decano);
                }
            }
        }
        return null;
    }

    public List<Facultad> listar() throws SQLException {
        List<Facultad> lista = new ArrayList<>();
        String sql = "SELECT * FROM facultad ORDER BY id";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            PersonaDAO personaDAO = new PersonaDAO();
            while (rs.next()) {
                Double id = rs.getDouble("id");
                String nombre = rs.getString("nombre");
                Double decanoId = rs.getDouble("id_decano");

                Persona decano = personaDAO.buscarPorId(decanoId);
                lista.add(new Facultad(id, nombre, decano));
            }
        }
        return lista;
    }

    // ================= ACTUALIZAR =================
    public boolean actualizar(Facultad facultad) throws SQLException {
        String sql = "UPDATE facultad SET nombre = ?, id_decano = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, facultad.getNombre());
            Double decanoId = facultad.getDecano() == null ? null :
                              (facultad.getDecano().getId() == null ? null : facultad.getDecano().getId().doubleValue());
            if (decanoId == null) stmt.setNull(2, Types.DOUBLE);
            else stmt.setDouble(2, decanoId);
            stmt.setDouble(3, facultad.getID());
            return stmt.executeUpdate() > 0;
        }
    }

    // ================= ELIMINAR =================
    public boolean eliminar(Double id) throws SQLException {
        String sql = "DELETE FROM facultad WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // ======= REGLAS de unicidad para el decano =======

    // ¿El decano ya está asignado en alguna facultad?
    public boolean existeDecano(Double decanoId) throws SQLException {
        String sql = "SELECT 1 FROM facultad WHERE id_decano = ? LIMIT 1";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, decanoId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // ¿El decano está asignado en otra facultad distinta a 'facultadId'?
    public boolean existeDecanoEnOtraFacultad(Double decanoId, Double facultadId) throws SQLException {
        String sql = "SELECT 1 FROM facultad WHERE id_decano = ? AND id <> ? LIMIT 1";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, decanoId);
            ps.setDouble(2, facultadId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
