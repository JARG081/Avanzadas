package com.mycompany.actividad1;

import com.mycompany.actividad1.model.InscripcionesPersonas;
import com.mycompany.actividad1.model.Persona;

public class Main {
    public static void main(String[] args) {
        InscripcionesPersonas inscripciones = new InscripcionesPersonas();

        inscripciones.cargarDatos();
        System.out.println("Personas en BD al iniciar:");
        for (Persona p : inscripciones.getListado()) {
            System.out.println(p);
        }


        Persona nueva = new Persona(0.0, "Juan", "Gómez", "juan.gomez@uni.edu");
        inscripciones.inscribir(nueva);

        inscripciones.guardarInformacion(nueva);
        inscripciones.cargarDatos();
        System.out.println("\nPersonas en BD después de guardar:");
        for (Persona p : inscripciones.getListado()) {
            System.out.println(p);
        }
    }
}
