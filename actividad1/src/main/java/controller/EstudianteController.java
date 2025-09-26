package controller;

import com.mycompany.actividad1.model.Estudiante;
import service.EstudianteService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class EstudianteController {
    private final EstudianteService service;

    public EstudianteController(EstudianteService service) { this.service = service; }

    private static Double parseId(String s) {
        if (s == null || s.isBlank()) throw new IllegalArgumentException("ID Persona es obligatorio");
        try {
            Double v = Double.valueOf(s.trim());
            if (v < 0) throw new IllegalArgumentException("ID Persona debe ser positivo");
            return v;
        } catch (NumberFormatException e) { throw new IllegalArgumentException("ID Persona inválido"); }
    }
    private static boolean empty(String s){ return s==null || s.isBlank(); }

    public void insertar(String idPersona, String codigo, String idPrograma) {
        if (empty(codigo)) throw new IllegalArgumentException("Código es obligatorio");
        service.registrar(parseId(idPersona), codigo, idPrograma, "", "", "");
    }
    public Estudiante buscar(String idPersona) { return service.buscar(parseId(idPersona)); }

    public boolean actualizar(String idPersona, String codigo, String idPrograma) {
        if (empty(codigo)) throw new IllegalArgumentException("Código es obligatorio");
        Estudiante actual = service.buscar(parseId(idPersona));
        String nom = actual == null ? "" : actual.getNombres();
        String ape = actual == null ? "" : actual.getApellidos();
        String email = actual == null ? "" : actual.getEmail();
        return service.actualizar(parseId(idPersona), codigo, idPrograma, nom, ape, email);
    }

    public boolean eliminar(String idPersona) { return service.eliminar(parseId(idPersona)); }
    public List<Estudiante> listar()          { return service.listar(); }

    public DefaultTableModel modeloTablaTodos() {
        DefaultTableModel m = new DefaultTableModel(
            new Object[]{"ID Persona","Nombres","Apellidos","Email","Código","Programa"}, 0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        for (Estudiante e : listar()) {
            m.addRow(new Object[]{
                e.getId(), e.getNombres(), e.getApellidos(), e.getEmail(),
                e.getCodigo(), e.getPrograma()==null? "" : e.getPrograma().getNombre()
            });
        }
        return m;
    }
}
