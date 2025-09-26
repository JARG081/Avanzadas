package com.mycompany.actividad1.factory;

import controller.*;
import repository.*;
import service.*;

import com.mycompany.actividad1.dao.*; 
import com.mycompany.actividad1.adapter.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class InfraFactory {
    
    private static Properties properties;
    
    static {
        loadProperties();
    }
    
    private static void loadProperties() {
        properties = new Properties();
        try (InputStream input = InfraFactory.class.getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new RuntimeException("No se pudo encontrar database.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error cargando database.properties", e);
        }
    }
    
    // === Database Adapter ===
    public DatabaseAdapter databaseAdapter() {
        String activeDb = properties.getProperty("database.active", "h2");
        
        switch (activeDb.toLowerCase()) {
            case "h2":
                return new H2DatabaseAdapter(
                    properties.getProperty("h2.url"),
                    properties.getProperty("h2.user"),
                    properties.getProperty("h2.password")
                );
                
            case "mysql":
                return new MySQLDatabaseAdapter(
                    properties.getProperty("mysql.url"),
                    properties.getProperty("mysql.user"),
                    properties.getProperty("mysql.password")
                );
                
            case "oracle":
                return new OracleDatabaseAdapter(
                    properties.getProperty("oracle.url"),
                    properties.getProperty("oracle.user"),
                    properties.getProperty("oracle.password")
                );
                
            default:
                throw new IllegalArgumentException("Base de datos no soportada: " + activeDb);
        }
    }
    
    public String getActiveDatabase() {
        return properties.getProperty("database.active", "h2");
    }

    // === Repositories ===
    public PersonaRepository personaRepository()         { return new PersonaJdbcRepository(); }
    public ProfesorRepository profesorRepository()       { return new ProfesorJdbcRepository(); }
    public EstudianteRepository estudianteRepository()   { return new EstudianteJdbcRepository(); }
    public FacultadRepository facultadRepository()       { return new FacultadJdbcRepository(); }
    public ProgramaRepository programaRepository()       { return new ProgramaJdbcRepository(); }
    public CursoRepository cursoRepository()             { return new CursoJdbcRepository(); }
    public InscripcionRepository inscripcionRepository() { return new InscripcionJdbcRepository(); }

    // === Services (un solo argumento, como tus clases actuales) ===
    public PersonaService personaService()         { return new PersonaService(personaRepository()); }
    public ProfesorService profesorService()       { return new ProfesorService(profesorRepository()); }
    public EstudianteService estudianteService()   { return new EstudianteService(estudianteRepository()); }
    public FacultadService facultadService()       { return new FacultadService(facultadRepository()); }
    public ProgramaService programaService()       { return new ProgramaService(programaRepository()); }
    public CursoService cursoService()             { return new CursoService(cursoRepository()); }
    public InscripcionService inscripcionService() { return new InscripcionService(inscripcionRepository()); }

    // === Controllers (inyectados) ===
    public PersonaController personaController()         { return new PersonaController(personaService()); }
    public ProfesorController profesorController()       { return new ProfesorController(profesorService()); }
    public EstudianteController estudianteController()   { return new EstudianteController(estudianteService()); }
    public FacultadController facultadController()       { return new FacultadController(facultadService()); }
    public ProgramaController programaController()       { return new ProgramaController(programaService()); }
    public CursoController cursoController()             { return new CursoController(cursoService()); }
    public InscripcionController inscripcionController() { return new InscripcionController(inscripcionService()); }
}
