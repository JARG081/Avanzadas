/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.model.Programa;
import com.mycompany.actividad1.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramaDAO {
    
    public void insertar(Programa programa) throws SQLException {
        String sql = "INSERT INTO programa(nombre, facultad_id) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, programa.getNombre());
            stmt.setInt(2, programa.getFacultadId());
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
                lista.add(new Programa(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("facultad_id")
                ));
            }
        }
        return lista;
    }
}
