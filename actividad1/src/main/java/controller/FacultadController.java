package controller;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.dao.FacultadDAO;
import com.mycompany.actividad1.dao.PersonaDAO;
import com.mycompany.actividad1.model.Facultad;
import com.mycompany.actividad1.model.Persona;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FacultadController {

    // ====== Casos de uso ======

    public boolean insertar(String idTxt, String nombre, String decanoIdTxt) throws Exception {
        validarCampos(idTxt, nombre, decanoIdTxt);

        Double id = parseDouble(idTxt);
        Double decanoId = parseDouble(decanoIdTxt);

        PersonaDAO personaDAO = new PersonaDAO();
        Persona decano = personaDAO.buscarPorId(decanoId);
        if (decano == null) {
            throw new IllegalArgumentException("No existe una Persona con ID (decano) = " + decanoIdTxt);
        }

        FacultadDAO facDAO = new FacultadDAO();
        if (facDAO.existeDecano(decanoId)) {
            throw new IllegalArgumentException("El decano con ID " + decanoIdTxt + " ya está asignado a otra facultad.");
        }

        Facultad fac = new Facultad(id, nombre, decano);
        return facDAO.insertar(fac); // <- ahora SÍ existe insertar(Facultad)
    }

    // actualizar: igual, pero excluyendo la misma facultad en la verificación
    public boolean actualizar(String idTxt, String nombre, String decanoIdTxt) throws Exception {
        validarCampos(idTxt, nombre, decanoIdTxt);

        Double id = parseDouble(idTxt);
        Double decanoId = parseDouble(decanoIdTxt);

        PersonaDAO personaDAO = new PersonaDAO();
        Persona decano = personaDAO.buscarPorId(decanoId);
        if (decano == null) {
            throw new IllegalArgumentException("No existe una Persona con ID (decano) = " + decanoIdTxt);
        }

        FacultadDAO facDAO = new FacultadDAO();
        if (facDAO.existeDecanoEnOtraFacultad(decanoId, id)) {
            throw new IllegalArgumentException("El decano con ID " + decanoIdTxt + " ya está asignado a otra facultad.");
        }

        Facultad fac = new Facultad(id, nombre, decano);
        return facDAO.actualizar(fac);
    }

    public boolean eliminar(String idTxt) throws Exception {
        if (isEmpty(idTxt)) throw new IllegalArgumentException("Ingrese ID de facultad.");
        Double id = parseDouble(idTxt);
        FacultadDAO facDAO = new FacultadDAO();
        return facDAO.eliminar(id);
    }

    public Facultad buscar(String idTxt) throws Exception {
        if (isEmpty(idTxt)) throw new IllegalArgumentException("Ingrese ID de facultad.");
        Double id = parseDouble(idTxt);
        FacultadDAO facDAO = new FacultadDAO();
        return facDAO.buscarPorId(id);
    }

    public List<Facultad> listar() throws SQLException {
        FacultadDAO facDAO = new FacultadDAO();
        return facDAO.listar();
    }

    // ====== Helpers para la vista ======

    public DefaultTableModel modeloTablaTodas() throws SQLException {
        List<Facultad> lista = listar();
        DefaultTableModel m = new DefaultTableModel(new Object[]{"ID","Nombre","Decano (ID)","Decano (Nombre)"}, 0);
        for (Facultad f : lista) {
            Long decanoId = (f.getDecano() == null) ? null : f.getDecano().getId();
            String decanoNom = (f.getDecano() == null) ? "" :
                    (f.getDecano().getNombres() + " " + f.getDecano().getApellidos());
            m.addRow(new Object[]{ f.getID(), f.getNombre(), decanoId, decanoNom });
        }
        return m;
    }

    public DefaultTableModel modeloTablaDe(Facultad f) {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"ID","Nombre","Decano (ID)","Decano (Nombre)"}, 0);
        if (f != null) {
            Long decanoId = (f.getDecano() == null) ? null : f.getDecano().getId();
            String decanoNom = (f.getDecano() == null) ? "" :
                    (f.getDecano().getNombres() + " " + f.getDecano().getApellidos());
            m.addRow(new Object[]{ f.getID(), f.getNombre(), decanoId, decanoNom });
        }
        return m;
    }

    private void validarCampos(String idTxt, String nombre, String decanoIdTxt) {
        if (isEmpty(idTxt) || isEmpty(nombre) || isEmpty(decanoIdTxt)) {
            throw new IllegalArgumentException("ID, Nombre e ID de Decano son obligatorios.");
        }
        parseDouble(idTxt);      // valida numérico o lanza IllegalArgumentException
        parseDouble(decanoIdTxt);
    }
    private boolean isEmpty(String s) { return s == null || s.trim().isEmpty(); }
    private Double parseDouble(String s) {
        try { return Double.valueOf(s.trim()); }
        catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Formato numérico inválido: " + s);
        }
    }
}
