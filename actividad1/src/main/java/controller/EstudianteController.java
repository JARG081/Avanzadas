package controller;

import com.mycompany.actividad1.dao.EstudianteJdbcRepository;
import com.mycompany.actividad1.model.Estudiante;
import repository.EstudianteRepository;
import service.EstudianteService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class EstudianteController {

    private final EstudianteService service;

    public EstudianteController() {
        EstudianteRepository repo = new EstudianteJdbcRepository();
        this.service = new EstudianteService(repo);
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
    private static boolean empty(String s) { return s == null || s.isBlank(); }

    // Insertar: nombres/apellidos/email no vienen del form de estudiante → los completamos con lo que ya haya en la BD al leer
    public boolean insertar(String idPersona, String codigo, String idPrograma) {
        if (empty(codigo)) throw new IllegalArgumentException("Código es obligatorio");
        // Para registrar sin tocar la tabla persona, puedes traer primero al estudiante (no existirá) o a la persona desde otro controller.
        // Si prefieres no depender, pasa cadenas vacías (service no las valida) o busca primero con un PersonaController.
        // Aquí los dejamos vacíos; el SELECT de listar/buscar siempre trae los reales por JOIN.
        service.registrar(parseDouble(idPersona), codigo, idPrograma, "", "", "");
        return true;
    }

    public Estudiante buscar(String idPersona) {
        return service.buscar(parseDouble(idPersona));
    }

    public boolean actualizar(String idPersona, String codigo, String idPrograma) {
        if (empty(codigo)) throw new IllegalArgumentException("Código es obligatorio");

        // Para no romper, obtenemos nombres/apellidos/email actuales desde DB y se los pasamos al service:
        Estudiante actual = service.buscar(parseDouble(idPersona));
        String nom = actual == null ? "" : actual.getNombres();
        String ape = actual == null ? "" : actual.getApellidos();
        String email = actual == null ? "" : actual.getEmail();

        return service.actualizar(parseDouble(idPersona), codigo, idPrograma, nom, ape, email);
    }

    public boolean eliminar(String idPersona) {
        return service.eliminar(parseDouble(idPersona));
    }

    public List<Estudiante> listar() { return service.listar(); }

    public DefaultTableModel modeloTablaTodos() {
        DefaultTableModel m = new DefaultTableModel(
            new Object[]{"ID Persona","Nombres","Apellidos","Email","Código","Programa"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Estudiante e : listar()) {
            m.addRow(new Object[]{
                e.getId(),
                e.getNombres(),
                e.getApellidos(),
                e.getEmail(),
                e.getCodigo(),
                e.getPrograma()==null ? "" : e.getPrograma().getNombre()
            });
        }
        return m;
    }
}
