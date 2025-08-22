package model;

import java.util.ArrayList;
import java.util.List;

public class InscripcionesPersonas {
    private List<Persona> personas;

    public InscripcionesPersonas() {
        personas = new ArrayList<>();
    }

    public void inscribir(Persona persona) {
        personas.add(persona);
        System.out.println("Persona inscrita: " + persona.getNombres() + " " + persona.getApellidos());
    }

    public void eliminar(Persona persona) {
        personas.remove(persona);
        System.out.println("Persona eliminada: " + persona.getNombres() + " " + persona.getApellidos());
    }

    public void actualizar(Persona persona) {
        personas.set(personas.indexOf(persona), persona);
        System.out.println("Persona actualizada: " + persona.getNombres() + " " + persona.getApellidos());
    }

    public void guardarInformacion() {
        //Guardar en la BD   
    }

    public void cargarDatos(){
        //no se que hace esta función
    }
}






