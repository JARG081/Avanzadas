package com.mycompany.actividad1;

import com.mycompany.actividad1.dao.PersonaDAO;
import com.mycompany.actividad1.model.Persona;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PersonaDAO personaDAO = new PersonaDAO();

        try {
            // Insertar una persona de ejemplo
            Persona persona = new Persona(0, "Juan", "Gómez", "juan.gomez@uni.edu");
            personaDAO.insertar(persona);
            System.out.println("Persona insertada correctamente.");

            // Listar personas
            List<Persona> personas = personaDAO.listar();
            System.out.println("Lista de Personas:");
            for (Persona p : personas) {
                System.out.println(p.getId() + " - " + p.getNombres() + " " + p.getApellidos() + " - " + p.getEmail());
            }

        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
    }
}
