package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.model.Programa;
import com.mycompany.actividad1.model.Facultad;
import com.mycompany.actividad1.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramaDAO {
    
    public void insertar(Programa programa) throws SQLException {
        String sql = "INSERT INTO programa(nombre, duracion, registro, facultad_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, programa.getNombre());
            stmt.setString(2, programa.getDuracion());
            stmt.setDate(3, new java.sql.Date(programa.getRegistro().getTime()));
            stmt.setDouble(4, programa.getFacultad().getID());
            stmt.executeUpdate();
        }
    }

    public List<Programa> listar() throws SQLException {
        List<Programa> lista = new ArrayList<>();
        String sql = "SELECT * FROM programa";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                
                Facultad facultad = new Facultad(
                    rs.getDouble("facultad_id"),
                    null,
                    null
                );

                lista.add(new Programa(
                    rs.getDouble("id"),
                    rs.getString("nombre"),
                    rs.getString("duracion"),
                    rs.getDate("registro"),
                    facultad
                ));
            }
        }
        return lista;
    }
}
