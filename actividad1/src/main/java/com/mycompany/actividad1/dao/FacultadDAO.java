package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.model.Facultad;
import com.mycompany.actividad1.model.Persona;
import com.mycompany.actividad1.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultadDAO {

    // ================= INSERTAR =================
    public void insertar(Facultad facultad) throws Exception {
        String sql = "INSERT INTO facultad (id, nombre, id_decano) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, facultad.getID());
            stmt.setString(2, facultad.getNombre());
            stmt.setDouble(3, facultad.getDecano().getId()); // usamos el id de Persona
            stmt.executeUpdate();
        }
    }

    // ================= BUSCAR =================
    public Facultad buscarPorId(Double id) throws Exception {
        String sql = "SELECT * FROM facultad WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    Double decanoId = rs.getDouble("id_decano");

                    // Traemos al decano como objeto Persona
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

                Facultad facultad = new Facultad(id, nombre, decano);
                lista.add(facultad);
            }
        }
        return lista;
    }

    // ================= ACTUALIZAR =================
    public void actualizar(Facultad facultad) throws Exception {
        String sql = "UPDATE facultad SET nombre = ?, id_decano = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, facultad.getNombre());
            stmt.setDouble(2, facultad.getDecano().getId()); // ID del decano
            stmt.setDouble(3, facultad.getID());
            stmt.executeUpdate();
        }
    }

    // ================= ELIMINAR =================
    public void eliminar(Double id) throws Exception {
        String sql = "DELETE FROM facultad WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, id);
            stmt.executeUpdate();
        }
    }
}

