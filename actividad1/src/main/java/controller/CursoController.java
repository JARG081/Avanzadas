package controller;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.dao.CursoDAO;
import com.mycompany.actividad1.dao.ProgramaDAO;
import com.mycompany.actividad1.model.Curso;
import com.mycompany.actividad1.model.Programa;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class CursoController {

    // ====== Casos de uso ======

    public boolean insertar(String idTxt, String nombre, String idProgramaTxt, Object activoVal) throws Exception {
        validarIdNombre(idTxt, nombre);
        Integer id = parseInt(idTxt);
        Programa programa = buscarProgramaSiAplica(idProgramaTxt); // puede ser null
        Boolean activo = parseBooleanFlexible(activoVal);

        Curso curso = new Curso(id, nombre.trim(), programa, activo);
        CursoDAO dao = new CursoDAO();
        return dao.insertar(curso);
    }

    public boolean actualizar(String idTxt, String nombre, String idProgramaTxt, Object activoVal) throws Exception {
        validarIdNombre(idTxt, nombre);
        Integer id = parseInt(idTxt);
        Programa programa = buscarProgramaSiAplica(idProgramaTxt);
        Boolean activo = parseBooleanFlexible(activoVal);

        Curso curso = new Curso(id, nombre.trim(), programa, activo);
        CursoDAO dao = new CursoDAO();
        return dao.actualizar(curso);
    }

    public Curso buscar(String idTxt) throws Exception {
        if (isEmpty(idTxt)) throw new IllegalArgumentException("Ingrese ID de curso.");
        int id = parseInt(idTxt);
        CursoDAO dao = new CursoDAO();
        return dao.buscarPorId(id);
    }

    public boolean eliminar(String idTxt) throws Exception {
        if (isEmpty(idTxt)) throw new IllegalArgumentException("Ingrese ID de curso.");
        int id = parseInt(idTxt);
        CursoDAO dao = new CursoDAO();
        return dao.eliminar(id);
    }

    public List<Curso> listar() throws SQLException {
        CursoDAO dao = new CursoDAO();
        return dao.listar();
    }

    // ====== Helpers para la vista ======

    public DefaultTableModel modeloTablaTodos() throws SQLException {
        List<Curso> lista = listar();
        DefaultTableModel m = new DefaultTableModel(new Object[]{"ID", "Nombre", "Programa (ID)", "Activo"}, 0);
        for (Curso c : lista) {
            m.addRow(new Object[]{
                c.getID(),
                c.getNombre(),
                (c.getPrograma() == null || c.getPrograma().getId() == null) ? null : c.getPrograma().getId(),
                c.getActivo()
            });
        }
        return m;
    }

    public DefaultTableModel modeloTablaDe(Curso c) {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"ID", "Nombre", "Programa (ID)", "Activo"}, 0);
        if (c != null) {
            m.addRow(new Object[]{
                c.getID(),
                c.getNombre(),
                (c.getPrograma() == null || c.getPrograma().getId() == null) ? null : c.getPrograma().getId(),
                c.getActivo()
            });
        }
        return m;
    }

    // ====== Util / validación ======

    private void validarIdNombre(String idTxt, String nombre) {
        if (isEmpty(idTxt) || isEmpty(nombre)) {
            throw new IllegalArgumentException("ID y Nombre son obligatorios.");
        }
        parseInt(idTxt); // valida formato
    }

    private Programa buscarProgramaSiAplica(String idProgramaTxt) throws SQLException {
        if (isEmpty(idProgramaTxt)) return null; // curso sin programa
        Double idProg = parseDouble(idProgramaTxt);
        ProgramaDAO pdao = new ProgramaDAO(Database.getConnection()); // tu ProgramaDAO requiere Connection
        Programa p = pdao.buscar(idProg.intValue()); // tu buscar(int) recibe entero
        if (p == null) {
            throw new IllegalArgumentException("No existe programa con ID " + idProgramaTxt);
        }
        // opcional: dejar solo id para no arrastrar nombre/demases
        Programa soloId = new Programa();
        soloId.setId(idProg);
        return soloId;
    }

    private Boolean parseBooleanFlexible(Object val) {
        if (val == null) return Boolean.FALSE;
        if (val instanceof Boolean) return (Boolean) val;
        String s = val.toString().trim().toLowerCase();
        return s.equals("true") || s.equals("1") || s.equals("sí") || s.equals("si") || s.equals("activo");
    }

    private boolean isEmpty(String s) { return s == null || s.trim().isEmpty(); }
    private int parseInt(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (NumberFormatException nfe) { throw new IllegalArgumentException("ID inválido: " + s); }
    }
    private Double parseDouble(String s) {
        try { return Double.valueOf(s.trim()); }
        catch (NumberFormatException nfe) { throw new IllegalArgumentException("Número inválido: " + s); }
    }
}
