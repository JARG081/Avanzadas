package controller;

import com.mycompany.actividad1.model.Programa;
import service.ProgramaService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProgramaController {
    private final ProgramaService service;

    public ProgramaController(ProgramaService service) { this.service = service; }

    private static Double parseId(String s, String campo){
        if (s==null || s.isBlank()) throw new IllegalArgumentException(campo+" es obligatorio");
        try {
            Double v = Double.valueOf(s.trim());
            if (v < 0) throw new IllegalArgumentException(campo+" debe ser positivo");
            return v;
        } catch (NumberFormatException e){ throw new IllegalArgumentException(campo+" inválido"); }
    }
    private static Double parseIdNullable(String s){
        if (s==null || s.isBlank()) return null;
        return parseId(s, "ID Facultad");
    }

    public void insertar(String id, String nombre, String duracion, String registro, String idFacultad) {
        service.registrar(parseId(id,"ID Programa"), nombre, duracion, registro, parseIdNullable(idFacultad));
    }
    public boolean actualizar(String id, String nombre, String duracion, String registro, String idFacultad) {
        return service.actualizar(parseId(id,"ID Programa"), nombre, duracion, registro, parseIdNullable(idFacultad));
    }
    public boolean eliminar(String id){ return service.eliminar(parseId(id,"ID Programa")); }
    public Programa buscar(String id){ return service.buscar(parseId(id,"ID Programa")); }
    public List<Programa> listar(){ return service.listar(); }

    public DefaultTableModel modeloTablaTodos() {
        DefaultTableModel m = new DefaultTableModel(
            new Object[]{"ID","Nombre","Duración","Registro","Facultad"}, 0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        for (Programa p : listar()) {
            m.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getDuracion(), p.getRegistro(),
                p.getFacultad()==null? "" : p.getFacultad().getNombre()
            });
        }
        return m;
    }
}
