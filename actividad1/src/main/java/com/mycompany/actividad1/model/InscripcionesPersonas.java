package com.mycompany.actividad1.model;
import com.mycompany.actividad1.dao.PersonaDAO;
import java.util.ArrayList;
import java.util.List;

public class InscripcionesPersonas {
    private final List<Persona> listado = new ArrayList<>();
    private final PersonaDAO personaDAO = new PersonaDAO();

    public void inscribir(Persona persona) {
        if (persona == null) return;
        listado.add(persona);
    }

    public void eliminar(Persona persona) {
        if (persona == null) return;
        listado.remove(persona);
        try {

            if (persona.getID() != null) {
                personaDAO.eliminar(persona.getID().longValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar(Persona persona) {
        if (persona == null || persona.getID() == null) return;
        int idx = indexOfById(persona.getID());
        if (idx >= 0) {
            listado.set(idx, persona);
        }
    }

    public void guardarInformacion(Persona persona) {
        try {
            personaDAO.insertar(persona); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarDatos() {
        listado.clear();
        try {
            listado.addAll(personaDAO.listar());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Persona> getListado() {
        return listado;
    }

    private int indexOfById(Double id) {
        for (int i = 0; i < listado.size(); i++) {
            Double cur = listado.get(i).getID();
            if (cur != null && cur.equals(id)) return i;
        }
        return -1;
    }
}
