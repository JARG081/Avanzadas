package controller;

import com.mycompany.actividad1.dao.CursoJdbcRepository;
import com.mycompany.actividad1.model.Curso;
import repository.CursoRepository;
import service.CursoService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CursoController {

    private final CursoService service;

    public CursoController() {
        CursoRepository repo = new CursoJdbcRepository();
        this.service = new CursoService(repo);
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
    public void insertar(String id, String nombre, String idPrograma, boolean activo) {
        service.registrar(parseDoubleOrNull(id), nombre, parseDoubleOrNull(idPrograma), activo);
    }

    public boolean actualizar(String id, String nombre, String idPrograma, boolean activo) {
        return service.actualizar(parseDoubleOrNull(id), nombre, parseDoubleOrNull(idPrograma), activo);
    }

    public boolean eliminar(String id) {
        return service.eliminar(parseDoubleOrNull(id));
    }

    public Curso buscar(String id) {
        return service.buscar(parseDoubleOrNull(id));
    }

    public List<Curso> listar() {
        return service.listar();
    }

    public DefaultTableModel modeloTablaTodos() {
        String[] cols = {"ID","Nombre","Programa","Activo"};
        DefaultTableModel m = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Curso c : listar()) {
            m.addRow(new Object[]{
                c.getID(),
                c.getNombre(),
                c.getPrograma()==null? "" : c.getPrograma().getNombre(),
                c.getActivo()
            });
        }
        return m;
    }
}
