package controller;

import com.mycompany.actividad1.model.Curso;
import service.CursoService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CursoController {
    private final CursoService service;

    public CursoController(CursoService service) { this.service = service; }

    private static Double parseId(String s, String campo){
        if (s==null || s.isBlank()) throw new IllegalArgumentException(campo+" es obligatorio");
        try{
            Double v = Double.valueOf(s.trim());
            if (v < 0) throw new IllegalArgumentException(campo+" debe ser positivo");
            return v;
        }catch(NumberFormatException e){ throw new IllegalArgumentException(campo+" inválido"); }
    }
    private static Double parseIdNullable(String s){
        if (s==null || s.isBlank()) return null;
        return parseId(s, "ID Programa");
    }

    public void insertar(String id, String nombre, String idPrograma, boolean activo) {
        service.registrar(parseId(id,"ID Curso"), nombre, parseIdNullable(idPrograma), activo);
    }
    public boolean actualizar(String id, String nombre, String idPrograma, boolean activo) {
        return service.actualizar(parseId(id,"ID Curso"), nombre, parseIdNullable(idPrograma), activo);
    }
    public boolean eliminar(String id) { return service.eliminar(parseId(id,"ID Curso")); }
    public Curso buscar(String id)     { return service.buscar(parseId(id,"ID Curso")); }
    public List<Curso> listar()        { return service.listar(); }

    public DefaultTableModel modeloTablaTodos() {
        DefaultTableModel m = new DefaultTableModel(
            new Object[]{"ID","Nombre","Programa","Activo"}, 0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        for (Curso c : listar()) {
            m.addRow(new Object[]{
                c.getID(), c.getNombre(),
                c.getPrograma()==null? "" : c.getPrograma().getNombre(),
                c.getActivo()
            });
        }
        return m;
    }
    public DefaultTableModel modeloTablaDe(Curso c) {
    DefaultTableModel m = new DefaultTableModel(
        new Object[]{"ID","Nombre","Programa","Activo"}, 0) {
        @Override public boolean isCellEditable(int r,int col){ return false; }
    };
    if (c != null) {
        m.addRow(new Object[]{
            c.getID(),
            c.getNombre(),
            c.getPrograma()==null ? "" : c.getPrograma().getNombre(),
            c.getActivo()
        });
    }
    return m;
}

}
