package controller;

import com.mycompany.actividad1.dao.ProgramaJdbcRepository;
import com.mycompany.actividad1.model.Programa;
import repository.ProgramaRepository;
import service.ProgramaService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProgramaController {

    private final ProgramaService service;

    public ProgramaController() {
        ProgramaRepository repo = new ProgramaJdbcRepository();
        this.service = new ProgramaService(repo);
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
    public void insertar(String id, String nombre, String duracion, String registroYYYYMMDD, String idFacultad) {
        service.registrar(parseDoubleOrNull(id), nombre, duracion, registroYYYYMMDD, parseDoubleOrNull(idFacultad));
    }

    public boolean actualizar(String id, String nombre, String duracion, String registroYYYYMMDD, String idFacultad) {
        return service.actualizar(parseDoubleOrNull(id), nombre, duracion, registroYYYYMMDD, parseDoubleOrNull(idFacultad));
    }

    public boolean eliminar(String id) {
        return service.eliminar(parseDoubleOrNull(id));
    }

    public Programa buscar(String id) {
        return service.buscar(parseDoubleOrNull(id));
    }

    public List<Programa> listar() {
        return service.listar();
    }

    public DefaultTableModel modeloTablaTodos() {
        String[] cols = {"ID","Nombre","Duración","Registro","Facultad"};
        DefaultTableModel m = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Programa p : listar()) {
            m.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getDuracion(),
                p.getRegistro()==null? "" : p.getRegistro().toString(),
                p.getFacultad()==null? "" : p.getFacultad().getNombre()
            });
        }
        return m;
    }
}
