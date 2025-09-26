package controller;

import com.mycompany.actividad1.dao.FacultadJdbcRepository;
import com.mycompany.actividad1.model.Facultad;
import com.mycompany.actividad1.model.Persona;
import repository.FacultadRepository;
import service.FacultadService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FacultadController {

    private final FacultadService service;

    public FacultadController() {
        FacultadRepository repo = new FacultadJdbcRepository();
        this.service = new FacultadService(repo);
    }

    private static Double parseDoubleOrNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        if (t.isEmpty()) return null;
        try {
            Double v = Double.valueOf(t);
            if (v < 0) throw new IllegalArgumentException("El ID debe ser positivo");
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido: " + s);
        }
    }

    // ==== API para UI ====
    public void insertar(String id, String nombre, String idDecano) {
        service.registrar(parseDoubleOrNull(id), nombre, parseDoubleOrNull(idDecano));
    }

    public boolean actualizar(String id, String nombre, String idDecano) {
        return service.actualizar(parseDoubleOrNull(id), nombre, parseDoubleOrNull(idDecano));
    }

    public boolean eliminar(String id) {
        return service.eliminar(parseDoubleOrNull(id));
    }

    public Facultad buscar(String id) {
        return service.buscar(parseDoubleOrNull(id));
    }

    public List<Facultad> listar() {
        return service.listar();
    }

    public DefaultTableModel modeloTablaTodas() {
        String[] cols = {"ID","Nombre","Decano ID","Decano"};
        DefaultTableModel m = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Facultad f : listar()) {
            Persona d = f.getDecano();
            m.addRow(new Object[]{
                f.getID(),
                f.getNombre(),
                d == null ? "" : d.getId(),
                d == null ? "" : ((d.getNombres()==null?"":d.getNombres()) + " " + (d.getApellidos()==null?"":d.getApellidos())).trim()
            });
        }
        return m;
    }
}
