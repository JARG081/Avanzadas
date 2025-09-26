package com.mycompany.actividad1.factory;

import controller.*;
import repository.*;
import service.*;

import com.mycompany.actividad1.dao.*; 

public class InfraFactory {

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
