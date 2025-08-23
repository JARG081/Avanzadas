package com.mycompany.actividad1;

import com.mycompany.actividad1.dao.PersonaDAO;
import com.mycompany.actividad1.model.*;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PersonaDAO personaDAO = new PersonaDAO();

        try {
            // 1. Insertar y listar Persona (guardarDatos / cargarDatos)
            Persona persona = new Persona(1.0, "Juan", "Gómez", "juan.gomez@uni.edu");
            personaDAO.insertar(persona);

            List<Persona> personas = personaDAO.listar();
            System.out.println("Personas en BD:");
            for (Persona p : personas) {
                System.out.println(p);
            }

            // 2. Ejemplo de herencia
            Estudiante estudiante = new Estudiante(2.0, "Laura", "Martínez", "laura.m@uni.edu", "2025001", null);
            System.out.println("\nEjemplo de Estudiante (herencia):");
            System.out.println(estudiante);

            // 3. Ejemplo con interfaz Servicios
            Servicios cursos = new CursosInscritos();
            System.out.println("\nProbando interfaz Servicios:");
            System.out.println("Cantidad cursos inscritos: " + cursos.cantidadActual());

        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
    }
}
