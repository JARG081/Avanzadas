package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.model.Facultad;
import com.mycompany.actividad1.model.Programa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramaDAO {
    private Connection conexion;

    public ProgramaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // INSERTAR
    public boolean insertarPrograma(Programa programa) {
        String sql = "INSERT INTO programa (id, nombre, duracion, registro, id_facultad) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, programa.getId());
            stmt.setString(2, programa.getNombre());
            stmt.setInt(3, programa.getDuracion());
            stmt.setDate(4, programa.getRegistro());
            stmt.setDouble(5, programa.getFacultad().getID());  
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar programa: " + e.getMessage());
            return false;
        }
    }

    // LISTAR
    public List<Programa> listar() {
        List<Programa> programas = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, p.duracion, p.registro, " +
             "f.id as facultad_id, f.nombre as facultad_nombre " +
             "FROM programa p JOIN facultad f ON p.id_facultad = f.id " +
             "ORDER BY p.id ASC";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Facultad facultad = new Facultad(
                        rs.getDouble("facultad_id"),
                        rs.getString("facultad_nombre"),
                        null
                );

                Programa programa = new Programa(//cannot aply to given types
                        rs.getDouble("id"),
                        rs.getString("nombre"),
                        rs.getInt("duracion"),
                        rs.getDate("registro"),
                        facultad
                );
                programas.add(programa);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar programas: " + e.getMessage());
        }
        return programas;
    }

    // BUSCAR
    public Programa buscar(int id) {
        String sql = "SELECT p.id, p.nombre, p.duracion, p.registro, " +
                     "f.id as facultad_id, f.nombre as facultad_nombre " +
                     "FROM programa p JOIN facultad f ON p.id_facultad = f.id WHERE p.id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setDouble(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Facultad facultad = new Facultad(
                        rs.getDouble("facultad_id"),
                        rs.getString("facultad_nombre"),
                        null
                );

                return new Programa(//cannot aply to given types
                        rs.getDouble("id"),
                        rs.getString("nombre"),
                        rs.getInt("duracion"),
                        rs.getDate("registro"),
                        facultad
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar programa: " + e.getMessage());
        }
        return null;
    }

    // ACTUALIZAR
    public boolean actualizar(Programa programa) {
        String sql = "UPDATE programa SET nombre = ?, duracion = ?, registro = ?, id_facultad = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, programa.getNombre());
            stmt.setInt(2, programa.getDuracion());
            stmt.setDate(3, programa.getRegistro());
            stmt.setDouble(4, programa.getFacultad().getID());
            stmt.setDouble(5, programa.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar programa: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {
        String sql = "DELETE FROM programa WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar programa: " + e.getMessage());
            return false;
        }
    }
}
