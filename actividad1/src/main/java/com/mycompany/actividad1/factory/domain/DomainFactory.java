package com.mycompany.actividad1.factory.domain;

import com.mycompany.actividad1.adapter.*;
import com.mycompany.actividad1.dao.*;
import repository.*;
import service.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DomainFactory {

    private static final Properties properties = loadProps();

    private static Properties loadProps() {
        Properties p = new Properties();
        
        // Cargar configuraciones de base de datos
        try (InputStream dbIn = DomainFactory.class.getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (dbIn == null) throw new RuntimeException("No se pudo encontrar database.properties");
            p.load(dbIn);
        } catch (IOException e) {
            throw new RuntimeException("Error cargando database.properties", e);
        }
        
        // Cargar configuración de BD activa
        try (InputStream configIn = DomainFactory.class.getClassLoader()
                .getResourceAsStream("configurationDB.properties")) {
            if (configIn == null) throw new RuntimeException("No se pudo encontrar configurationDB.properties");
            p.load(configIn);
        } catch (IOException e) {
            throw new RuntimeException("Error cargando configurationDB.properties", e);
        }
        
        return p;
    }

    // === Database Adapter ===
    public DatabaseAdapter databaseAdapter() {
        String activeDb = properties.getProperty("database.active", "h2").toLowerCase();
        return switch (activeDb) {
            case "h2" -> H2DatabaseAdapter.getInstance(
                    properties.getProperty("h2.url"),
                    properties.getProperty("h2.user"),
                    properties.getProperty("h2.password"));
            case "mysql" -> MySQLDatabaseAdapter.getInstance(
                    properties.getProperty("mysql.url"),
                    properties.getProperty("mysql.user"),
                    properties.getProperty("mysql.password"));
            case "oracle" -> OracleDatabaseAdapter.getInstance(
                    properties.getProperty("oracle.url"),
                    properties.getProperty("oracle.user"),
                    properties.getProperty("oracle.password"));
            default -> throw new IllegalArgumentException("DB no soportada: " + activeDb);
        };
    }

    public String getActiveDatabase() {
        return properties.getProperty("database.active", "h2");
    }

    // === Repositories ===
    public PersonaRepository personaRepository() { return new PersonaJdbcRepository(); }
    public ProfesorRepository profesorRepository() { return new ProfesorJdbcRepository(); }
    public EstudianteRepository estudianteRepository() { return new EstudianteJdbcRepository(); }
    public FacultadRepository facultadRepository() { return new FacultadJdbcRepository(); }
    public ProgramaRepository programaRepository() { return new ProgramaJdbcRepository(); }

    // Curso repositorio SINGLETON
    private final CursoRepository cursoRepo = new CursoJdbcRepository();
    public CursoRepository cursoRepository() { return cursoRepo; }

    public CursosInscritosRepository cursosInscritosRepository() { return new CursosInscritosJdbc(); }

    // === Services ===
    public PersonaService personaService() { return new PersonaService(personaRepository()); }
    public ProfesorService profesorService() { return new ProfesorService(profesorRepository()); }
    public EstudianteService estudianteService() { return new EstudianteService(estudianteRepository()); }
    public FacultadService facultadService() { return new FacultadService(facultadRepository()); }
    public ProgramaService programaService() { return new ProgramaService(programaRepository()); }

    // Curso service SINGLETON (usa repo compartido)
    private final CursoService cursoSrv = new CursoService(cursoRepo);
    public CursoService cursoService() { return cursoSrv; }

    public CursosInscritosService cursosInscritosService() {
        return new CursosInscritosService(cursosInscritosRepository());
    }
}
