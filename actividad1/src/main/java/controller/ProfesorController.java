package controller;

import com.mycompany.actividad1.model.Profesor;
import repository.ProfesorRepository;
import com.mycompany.actividad1.dao.ProfesorJdbcRepository;
import service.ProfesorService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProfesorController {

    private final ProfesorService service;

    public ProfesorController() {
        ProfesorRepository repo = new ProfesorJdbcRepository();
        this.service = new ProfesorService(repo);
    }

    private static Double parseDouble(String s) {
        if (s == null || s.isBlank()) throw new IllegalArgumentException("ID Persona es obligatorio");
        try {
            Double v = Double.valueOf(s.trim());
            if (v < 0) throw new IllegalArgumentException("ID Persona debe ser positivo");
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID Persona inválido");
        }
    }
    private static String norm(Object o) { return o == null ? "" : o.toString().trim(); }

    public void insertar(String idPersona, Object contratoComboValue) {
        service.registrar(parseDouble(idPersona), norm(contratoComboValue));
    }

    public boolean actualizar(String idPersona, Object contratoComboValue) {
        return service.actualizar(parseDouble(idPersona), norm(contratoComboValue));
    }

    public boolean eliminar(String idPersona) {
        return service.eliminar(parseDouble(idPersona));
    }

    public Profesor buscar(String idPersona) {
        return service.buscar(parseDouble(idPersona));
    }

    public List<Profesor> listar() { return service.listar(); }

    public DefaultTableModel modeloTablaTodos() {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"ID Persona","Contrato"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Profesor p : listar()) {
            m.addRow(new Object[]{ p.getId(), p.getContrato() });
        }
        return m;
    }
}
