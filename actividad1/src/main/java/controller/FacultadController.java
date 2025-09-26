package controller;

import com.mycompany.actividad1.model.Facultad;
import service.FacultadService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FacultadController {
    private final FacultadService service;

    public FacultadController(FacultadService service) { this.service = service; }

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
        return parseId(s, "ID Decano");
    }

    public void insertar(String id, String nombre, String idDecano) {
        service.registrar(parseId(id,"ID Facultad"), nombre, parseIdNullable(idDecano));
    }
    public boolean actualizar(String id, String nombre, String idDecano) {
        return service.actualizar(parseId(id,"ID Facultad"), nombre, parseIdNullable(idDecano));
    }
    public boolean eliminar(String id){ return service.eliminar(parseId(id,"ID Facultad")); }
    public Facultad buscar(String id){ return service.buscar(parseId(id,"ID Facultad")); }
    public List<Facultad> listar(){ return service.listar(); }

    public DefaultTableModel modeloTablaTodas() {
        DefaultTableModel m = new DefaultTableModel(
            new Object[]{"ID","Nombre","Decano (ID)","Decano"}, 0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        for (Facultad f : listar()) {
            String decNom = f.getDecano()==null ? "" : (f.getDecano().getNombres()+" "+f.getDecano().getApellidos());
            m.addRow(new Object[]{ f.getID(), f.getNombre(), f.getDecano()==null? null : f.getDecano().getId(), decNom });
        }
        return m;
    }
}
