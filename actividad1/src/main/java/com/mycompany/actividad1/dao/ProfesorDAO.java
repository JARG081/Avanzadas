package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.model.Persona;
import com.mycompany.actividad1.model.Profesor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesorDAO {
    private Connection conn;

    public ProfesorDAO(Connection conn) {
        this.conn = conn;
    }

    // INSERTAR
    public boolean insertar(Profesor profesor) {
        String sql = "INSERT INTO profesor (ID_PERSONA, CONTRATO) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, profesor.getIdPersona()); // ID de persona
            ps.setString(2, profesor.getContrato());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar profesor: " + e.getMessage());
            return false;
        }
    }

    // BUSCAR
    public Profesor buscar(double idPersona) {
        String sql = "SELECT * FROM profesor WHERE ID_PERSONA = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, idPersona);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Profesor profesor = new Profesor();
                profesor.setIdPersona(rs.getDouble("ID_PERSONA"));
                profesor.setContrato(rs.getString("CONTRATO"));
                return profesor;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar profesor: " + e.getMessage());
        }
        return null;
    }

    // ACTUALIZAR
    public boolean actualizar(Profesor profesor) {
        String sql = "UPDATE profesor SET CONTRATO = ? WHERE ID_PERSONA = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, profesor.getContrato());
            ps.setDouble(2, profesor.getIdPersona());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar profesor: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(double idPersona) {
        String sql = "DELETE FROM profesor WHERE ID_PERSONA = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, idPersona);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar profesor: " + e.getMessage());
            return false;
        }
    }

    // LISTAR
    public List<Profesor> listar() {
        List<Profesor> lista = new ArrayList<>();
        String sql = "SELECT p.ID, p.NOMBRES, p.APELLIDOS, p.EMAIL, pr.ID_PERSONA, pr.CONTRATO " +
                     "FROM profesor pr " +
                     "JOIN persona p ON pr.ID_PERSONA = p.ID";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Persona persona = new Persona(
                    rs.getDouble("ID"),          
                    rs.getString("NOMBRES"),
                    rs.getString("APELLIDOS"),
                    rs.getString("EMAIL")
                );
                Profesor profesor = new Profesor(
                    rs.getDouble("ID_PERSONA"),  
                    rs.getString("CONTRATO"),
                    persona
                );

                lista.add(profesor);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar profesores: " + e.getMessage());
        }
        return lista;
    }
}
