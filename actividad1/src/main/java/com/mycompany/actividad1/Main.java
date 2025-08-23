package com.mycompany.actividad1;

import com.mycompany.actividad1.model.InscripcionesPersonas;
import com.mycompany.actividad1.model.Persona;

public class Main {
    public static void main(String[] args) {
        InscripcionesPersonas inscripciones = new InscripcionesPersonas();

        // Cargar lo que ya existe en BD a la lista en memoria
        inscripciones.cargarDatos();
        System.out.println("Personas en BD al iniciar:");
        for (Persona p : inscripciones.getListado()) {
            System.out.println(p);
        }

        // Trabajar con la lista en memoria
        Persona nueva = new Persona(0.0, "Juan", "Gómez", "juan.gomez@uni.edu");
        inscripciones.inscribir(nueva);

        // Guardar SOLO esa persona en la BD (cumple 'guardarInformación(Persona)')
        inscripciones.guardarInformacion(nueva);

        // Verificar recargando desde la BD
        inscripciones.cargarDatos();
        System.out.println("\nPersonas en BD después de guardar:");
        for (Persona p : inscripciones.getListado()) {
            System.out.println(p);
        }
    }
}
