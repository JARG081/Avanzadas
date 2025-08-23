package com.mycompany.actividad1.model;
import com.mycompany.actividad1.dao.PersonaDAO;
import java.util.ArrayList;
import java.util.List;

public class InscripcionesPersonas {
    private final List<Persona> listado = new ArrayList<>();
    private final PersonaDAO personaDAO = new PersonaDAO();

    // NOTA: no llamamos métodos sobreescribibles desde el constructor.
    // Llama a cargarDatos() desde Main.

    public void inscribir(Persona persona) {
        if (persona == null) return;
        listado.add(persona);
    }

    public void eliminar(Persona persona) {
        if (persona == null) return;
        listado.remove(persona);
        try {
            // Tu PersonaDAO expone eliminar(long id). Convertimos Double -> long.
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
            // No hay update en PersonaDAO. Si hiciera falta, podríamos hacer:
            // personaDAO.eliminar(persona.getID().longValue());
            // personaDAO.insertar(persona);
        }
    }

    // === Persistencia requerida por el diagrama ===
    public void guardarInformacion(Persona persona) {
        try {
            personaDAO.insertar(persona); // guarda en H2
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarDatos() {
        listado.clear();
        try {
            listado.addAll(personaDAO.listar()); // trae de H2 a la lista
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
