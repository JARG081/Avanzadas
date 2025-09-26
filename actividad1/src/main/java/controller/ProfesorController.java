package controller;

import service.ProfesorService;
import com.mycompany.actividad1.model.Profesor;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProfesorController {
    private final ProfesorService service;

    public ProfesorController(ProfesorService service) { this.service = service; }

    private static Double parseId(String s) {
        if (s == null || s.isBlank()) throw new IllegalArgumentException("ID Persona es obligatorio");
        try {
            Double v = Double.valueOf(s.trim());
            if (v < 0) throw new IllegalArgumentException("ID Persona debe ser positivo");
            return v;
        } catch (NumberFormatException e) { throw new IllegalArgumentException("ID Persona inválido"); }
    }
    private static String normalize(Object o){ return o==null? "" : o.toString().trim(); }

    public void insertar(String idPersonaTxt, Object contratoComboValue) {
        String contrato = normalize(contratoComboValue);
        if (contrato.isBlank()) throw new IllegalArgumentException("Debe seleccionar un contrato.");
        service.registrar(parseId(idPersonaTxt), contrato);
    }
    public boolean actualizar(String idPersonaTxt, Object contratoComboValue) {
        String contrato = normalize(contratoComboValue);
        if (contrato.isBlank()) throw new IllegalArgumentException("Debe seleccionar un contrato.");
        return service.actualizar(parseId(idPersonaTxt), contrato);
    }
    public boolean eliminar(String idPersonaTxt) { return service.eliminar(parseId(idPersonaTxt)); }
    public Profesor buscar(String idPersonaTxt)   { return service.buscar(parseId(idPersonaTxt)); }
    public List<Profesor> listar()                { return service.listar(); }

    public DefaultTableModel modeloTablaTodos() {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"ID Persona","Contrato"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        for (Profesor p : listar()) m.addRow(new Object[]{ p.getId(), p.getContrato() });
        return m;
    }
}
