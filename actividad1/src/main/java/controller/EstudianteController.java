package controller;

import com.mycompany.actividad1.dao.EstudianteDAO;
import com.mycompany.actividad1.dao.PersonaDAO;
import com.mycompany.actividad1.dao.ProgramaDAO;
import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.model.Estudiante;
import com.mycompany.actividad1.model.Persona;
import com.mycompany.actividad1.model.Programa;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class EstudianteController {

    // ====== Casos de uso ======

    public boolean insertar(String idPersonaTxt, String codigo, String idProgramaTxt) throws Exception {
        validarCampos(idPersonaTxt, codigo); // idPersona y codigo obligatorios

        double idPersona = parseDouble(idPersonaTxt);

        // Persona debe existir
        PersonaDAO personaDAO = new PersonaDAO();
        Persona persona = personaDAO.buscarPorId(idPersona);
        if (persona == null) {
            throw new IllegalArgumentException("No existe Persona con ID " + idPersonaTxt);
        }

        // Unicidad: una persona no puede ser estudiante más de una vez
        EstudianteDAO eDao = new EstudianteDAO();
        if (eDao.existeByIdPersona(idPersona)) {
            throw new IllegalArgumentException("La Persona " + idPersonaTxt + " ya está registrada como Estudiante.");
        }

        // Programa (opcional): si llega vacío, lo dejamos null
        Programa prog = null;
        if (!isEmpty(idProgramaTxt)) {
            Double idProg = parseDouble(idProgramaTxt);
            ProgramaDAO pdao = new ProgramaDAO(Database.getConnection());
            Programa encontrado = pdao.buscar(idProg.intValue());
            if (encontrado == null) throw new IllegalArgumentException("No existe Programa con ID " + idProgramaTxt);
            prog = new Programa(); // guardamos solo el id (ligero)
            prog.setId(idProg);
        }

        Estudiante est = new Estudiante(
            idPersona, persona.getNombres(), persona.getApellidos(), persona.getEmail(),
            codigo.trim(), prog
        );
        return eDao.insertar(est);
    }

    public Estudiante buscar(String idPersonaTxt) throws Exception {
        if (isEmpty(idPersonaTxt)) throw new IllegalArgumentException("Ingrese ID de persona.");
        double idPersona = parseDouble(idPersonaTxt);
        EstudianteDAO eDao = new EstudianteDAO();
        return eDao.buscarPorIdPersona(idPersona);
    }

    public boolean actualizar(String idPersonaTxt, String codigo, String idProgramaTxt) throws Exception {
        validarCampos(idPersonaTxt, codigo);

        double idPersona = parseDouble(idPersonaTxt);

        // Debe existir como estudiante
        EstudianteDAO eDao = new EstudianteDAO();
        Estudiante actual = eDao.buscarPorIdPersona(idPersona);
        if (actual == null) throw new IllegalArgumentException("No existe Estudiante con ID persona " + idPersonaTxt);

        Programa prog = null;
        if (!isEmpty(idProgramaTxt)) {
            Double idProg = parseDouble(idProgramaTxt);
            ProgramaDAO pdao = new ProgramaDAO(Database.getConnection());
            Programa encontrado = pdao.buscar(idProg.intValue());
            if (encontrado == null) throw new IllegalArgumentException("No existe Programa con ID " + idProgramaTxt);
            prog = new Programa();
            prog.setId(idProg);
        }

        // Conservamos los datos de persona del registro actual
        Estudiante est = new Estudiante(
            idPersona,
            actual.getNombres(),
            actual.getApellidos(),
            actual.getEmail(),
            codigo.trim(),
            prog
        );
        return eDao.actualizar(est);
    }

    public boolean eliminar(String idPersonaTxt) throws Exception {
        if (isEmpty(idPersonaTxt)) throw new IllegalArgumentException("Ingrese ID de persona.");
        double idPersona = parseDouble(idPersonaTxt);
        EstudianteDAO eDao = new EstudianteDAO();
        return eDao.eliminar(idPersona);
    }

    public List<Estudiante> listar() throws SQLException {
        EstudianteDAO eDao = new EstudianteDAO();
        return eDao.listar();
    }

    // ====== Helpers para la vista ======

    public DefaultTableModel modeloTablaTodos() throws SQLException {
        List<Estudiante> lista = listar();
        DefaultTableModel m = new DefaultTableModel(
            new Object[]{"ID Persona","Nombres","Apellidos","Email","Código","Programa"}, 0);
        for (Estudiante e : lista) {
            m.addRow(new Object[]{
                e.getId(),
                e.getNombres(),
                e.getApellidos(),
                e.getEmail(),
                e.getCodigo(),
                e.getPrograma() == null ? "" : e.getPrograma().getNombre()
            });
        }
        return m;
    }

    public DefaultTableModel modeloTablaDe(Estudiante e) {
        DefaultTableModel m = new DefaultTableModel(
            new Object[]{"ID Persona","Nombres","Apellidos","Email","Código","Programa"}, 0);
        if (e != null) {
            m.addRow(new Object[]{
                e.getId(),
                e.getNombres(),
                e.getApellidos(),
                e.getEmail(),
                e.getCodigo(),
                e.getPrograma() == null ? "" : e.getPrograma().getNombre()
            });
        }
        return m;
    }

    // ====== util ======
    private void validarCampos(String idPersonaTxt, String codigo) {
        if (isEmpty(idPersonaTxt) || isEmpty(codigo)) {
            throw new IllegalArgumentException("ID de Persona y Código son obligatorios.");
        }
        parseDouble(idPersonaTxt);
    }
    private boolean isEmpty(String s) { return s == null || s.trim().isEmpty(); }
    private double parseDouble(String s) {
        try { return Double.parseDouble(s.trim()); }
        catch (NumberFormatException nfe) { throw new IllegalArgumentException("Número inválido: " + s); }
    }
}
