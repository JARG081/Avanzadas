package controller;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.dao.FacultadDAO;
import com.mycompany.actividad1.dao.ProgramaDAO;
import com.mycompany.actividad1.model.Facultad;
import com.mycompany.actividad1.model.Programa;

import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ProgramaController {

    // ====== Casos de uso ======

    public boolean insertar(String idTxt, String nombre, String duracionTxt, String registroTxt, String idFacTxt) throws Exception {
        validarInsertUpdate(idTxt, nombre, duracionTxt, registroTxt, idFacTxt);

        Double id = parseDouble(idTxt);
        int duracion = parseInt(duracionTxt);
        Date registro = Date.valueOf(registroTxt.trim()); // YYYY-MM-DD
        Double idFac = parseDouble(idFacTxt);

        FacultadDAO fdao = new FacultadDAO();
        Facultad fac = fdao.buscarPorId(idFac);
        if (fac == null) {
            throw new IllegalArgumentException("No existe facultad con ID " + idFacTxt);
        }

        Programa prog = new Programa(id, nombre.trim(), duracion, registro, fac);
        ProgramaDAO dao = new ProgramaDAO(Database.getConnection());
        return dao.insertarPrograma(prog);
    }

    public boolean actualizar(String idTxt, String nombre, String duracionTxt, String registroTxt, String idFacTxt) throws Exception {
        validarInsertUpdate(idTxt, nombre, duracionTxt, registroTxt, idFacTxt);

        Double id = parseDouble(idTxt);
        int duracion = parseInt(duracionTxt);
        Date registro = Date.valueOf(registroTxt.trim());
        Double idFac = parseDouble(idFacTxt);

        FacultadDAO fdao = new FacultadDAO();
        Facultad fac = fdao.buscarPorId(idFac);
        if (fac == null) {
            throw new IllegalArgumentException("No existe facultad con ID " + idFacTxt);
        }

        Programa prog = new Programa(id, nombre.trim(), duracion, registro, fac);
        ProgramaDAO dao = new ProgramaDAO(Database.getConnection());
        return dao.actualizar(prog);
    }

    public Programa buscar(String idTxt) throws Exception {
        if (isEmpty(idTxt)) throw new IllegalArgumentException("Ingrese ID de programa.");
        int id = parseInt(idTxt);
        ProgramaDAO dao = new ProgramaDAO(Database.getConnection());
        return dao.buscar(id);
    }

    public boolean eliminar(String idTxt) throws Exception {
        if (isEmpty(idTxt)) throw new IllegalArgumentException("Ingrese ID de programa.");
        int id = parseInt(idTxt);
        ProgramaDAO dao = new ProgramaDAO(Database.getConnection());
        return dao.eliminar(id);
    }

    public List<Programa> listar() throws SQLException {
        ProgramaDAO dao = new ProgramaDAO(Database.getConnection());
        return dao.listar();
    }

    // ====== Helpers para la vista ======

    public DefaultTableModel modeloTablaTodos() throws SQLException {
        List<Programa> lista = listar();
        DefaultTableModel m = new DefaultTableModel(new Object[]{"ID","Nombre","Duración","Registro","Facultad"}, 0);
        for (Programa p : lista) {
            m.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getDuracion(),
                p.getRegistro(),
                p.getFacultad() == null ? "" : p.getFacultad().getNombre()
            });
        }
        return m;
    }

    public DefaultTableModel modeloTablaDe(Programa p) {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"ID","Nombre","Duración","Registro","Facultad"}, 0);
        if (p != null) {
            m.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getDuracion(),
                p.getRegistro(),
                p.getFacultad() == null ? "" : p.getFacultad().getNombre()
            });
        }
        return m;
    }

    // ====== Validación/util ======

    private void validarInsertUpdate(String idTxt, String nombre, String duracionTxt, String registroTxt, String idFacTxt) {
        if (isEmpty(idTxt) || isEmpty(nombre) || isEmpty(duracionTxt) || isEmpty(registroTxt) || isEmpty(idFacTxt)) {
            throw new IllegalArgumentException("Todos los campos son obligatorios (ID, Nombre, Duración, Registro, ID Facultad).");
        }
        parseDouble(idTxt);
        parseInt(duracionTxt);
        // Validación de fecha: valueOf lanza IllegalArgumentException si el formato no es YYYY-MM-DD
        try { Date.valueOf(registroTxt.trim()); } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La fecha debe tener formato YYYY-MM-DD.");
        }
        parseDouble(idFacTxt);
    }

    private boolean isEmpty(String s) { return s == null || s.trim().isEmpty(); }
    private Double parseDouble(String s) {
        try { return Double.valueOf(s.trim()); }
        catch (NumberFormatException nfe) { throw new IllegalArgumentException("Número inválido: " + s); }
    }
    private int parseInt(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (NumberFormatException nfe) { throw new IllegalArgumentException("Entero inválido: " + s); }
    }
}
