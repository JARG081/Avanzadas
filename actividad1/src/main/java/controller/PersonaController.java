package controller;

import com.mycompany.actividad1.model.Persona;
import service.PersonaService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PersonaController {

    private final PersonaService service;

    public PersonaController(PersonaService service) {
        this.service = service;
    }

    // Helper local de parseo (para mantener el controller recibiendo String del UI/console)
    private static Double parseDouble(String s) {
        if (s == null || s.isBlank()) throw new IllegalArgumentException("ID es obligatorio");
        try {
            Double v = Double.valueOf(s.trim());
            if (v < 0) throw new IllegalArgumentException("ID debe ser positivo");
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID no es numérico");
        }
    }

    // ==== Operaciones expuestas al UI/Consola ====

    public void insertar(String id, String nombres, String apellidos, String email) {
        service.registrar(parseDouble(id), nombres, apellidos, email); // service.registrar acepta Double
    }

    public boolean actualizar(String id, String nombres, String apellidos, String email) {
        return service.actualizar(parseDouble(id), nombres, apellidos, email); // Double → OK
    }

    public boolean eliminar(String id) {
        return service.eliminar(parseDouble(id)); // Double → OK
    }

    public Persona buscar(String id) {
        return service.buscar(parseDouble(id)); // Double → OK
    }

    public List<Persona> listar() {
        return service.listar();
    }

    // Modelo de tabla (si lo usas en la UI)
    public DefaultTableModel modeloTablaTodas() {
        String[] cols = {"ID", "Nombres", "Apellidos", "Email"};
        DefaultTableModel m = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Persona p : listar()) {
            m.addRow(new Object[]{
                p.getId(),
                p.getNombres(),
                p.getApellidos(),
                p.getEmail()
            });
        }
        return m;
    }
}
