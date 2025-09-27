package com.mycompany.actividad1.factory.app;


import com.mycompany.actividad1.factory.domain.DomainFactory;
import controller.*;       
       

public class AppFactory {

    private final DomainFactory domain;

    public AppFactory() { this.domain = new DomainFactory(); }
    public AppFactory(DomainFactory domain) { this.domain = domain; }

    public PersonaController personaController()         { return new PersonaController(domain.personaService()); }
    public ProfesorController profesorController()       { return new ProfesorController(domain.profesorService()); }
    public EstudianteController estudianteController()   { return new EstudianteController(domain.estudianteService()); }
    public FacultadController facultadController()       { return new FacultadController(domain.facultadService()); }
    public ProgramaController programaController()       { return new ProgramaController(domain.programaService()); }
    public CursoController cursoController()             { return new CursoController(domain.cursoService()); }
    public CursosInscritosController cursosInscritosController(){ return new CursosInscritosController(domain.cursosInscritosService()); }

    public String activeDb() { return domain.getActiveDatabase(); }
    public DomainFactory domain() { return domain; } 

    public CursosInscritosController inscripcionController() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
