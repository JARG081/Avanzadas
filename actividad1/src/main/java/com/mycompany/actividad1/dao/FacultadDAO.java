/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.model.Facultad;
import com.mycompany.actividad1.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultadDAO {

    public void insertar(Facultad facultad) throws SQLException {
        String sql = "INSERT INTO facultad(nombre) VALUES (?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, facultad.getNombre());
            stmt.executeUpdate();
        }
    }

    public List<Facultad> listar() throws SQLException {
        List<Facultad> lista = new ArrayList<>();
        String sql = "SELECT * FROM facultad";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Facultad(
                    rs.getInt("id"),
                    rs.getString("nombre")
                ));
            }
        }
        return lista;
    }
}
