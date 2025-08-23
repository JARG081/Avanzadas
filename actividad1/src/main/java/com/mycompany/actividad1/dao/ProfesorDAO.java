package com.mycompany.actividad1.dao;

import com.mycompany.actividad1.model.Profesor;
import com.mycompany.actividad1.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesorDAO {
    
    public void insertar(Profesor profesor) throws SQLException {
        String sql = "INSERT INTO profesor(nombre, apellido, correo, tipo_contrato) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, profesor.getNombres());
            stmt.setString(2, profesor.getApellidos());
            stmt.setString(3, profesor.getEmail());
            stmt.setString(4, profesor.getTipoContrato());
            stmt.executeUpdate();
        }
    }
    
    public List<Profesor> listar() throws SQLException {
        List<Profesor> lista = new ArrayList<>();
        String sql = "SELECT * FROM profesor";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Profesor(
                    rs.getDouble("id"),             
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo"),
                    rs.getString("tipo_contrato") 
                ));
            }
        }
        return lista;
    }
}
