package com.mycompany.actividad1.model;

import java.util.ArrayList;
import java.util.List;

import repository.PersonaRepository;
import com.mycompany.actividad1.dao.PersonaJdbcRepository;

public class InscripcionesPersonas {
    private final List<Persona> listado = new ArrayList<>();
    private final PersonaRepository personaRepo = new PersonaJdbcRepository();

    public void inscribir(Persona persona) {
        if (persona == null) return;
        listado.add(persona);
    }

    public void eliminar(Persona persona) {
        if (persona == null) return;
        listado.remove(persona);
        try {
            Double id = persona.getId();
            if (id != null) {
                personaRepo.eliminar(id); // ahora usa repository
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar(Persona persona) {
        if (persona == null || persona.getId() == null) return;
        int idx = indexOfById(persona.getId());
        if (idx >= 0) {
            listado.set(idx, persona);
            try {
                personaRepo.actualizar(persona); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void guardarInformacion(Persona persona) {
        try {
            personaRepo.insertar(persona); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarDatos() {
        listado.clear();
        try {
            listado.addAll(personaRepo.listar());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Persona> getListado() {
        return listado;
    }

    private int indexOfById(Double id) {
        if (id == null) return -1;
        for (int i = 0; i < listado.size(); i++) {
            Double cur = listado.get(i).getId();
            if (cur != null && cur.doubleValue() == id.doubleValue()) {
                return i;
            }
        }
        return -1;
    }
}
