package controller;

import com.mycompany.actividad1.Database;
import com.mycompany.actividad1.dao.ProfesorDAO;
import com.mycompany.actividad1.model.Profesor;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProfesorController {

    // ====== CASOS DE USO ======

    // Firma EXACTA que llamas desde la vista: (String, Object) y retorna void
    public void insertar(String idPersonaTxt, Object contratoComboValue) throws SQLException {
        String contrato = normalize(contratoComboValue);
        validar(idPersonaTxt, contrato);

        int idPersona = parseEntero(idPersonaTxt);
        Profesor profesor = new Profesor(idPersona, contrato);

        try (Connection conn = Database.getConnection()) {
            ProfesorDAO dao = new ProfesorDAO(conn);
            dao.insertar(profesor); // si tu DAO devuelve boolean, puedes ignorarlo aquí
        }
    }

    public boolean actualizar(String idPersonaTxt, Object contratoComboValue) throws SQLException {
        String contrato = normalize(contratoComboValue);
        validar(idPersonaTxt, contrato);

        int idPersona = parseEntero(idPersonaTxt);
        Profesor profesor = new Profesor(idPersona, contrato);

        try (Connection conn = Database.getConnection()) {
            ProfesorDAO dao = new ProfesorDAO(conn);
            return dao.actualizar(profesor); // ← devuelve true/false del DAO
        }
    }


    public Profesor buscar(String idPersonaTxt) throws SQLException {
        if (isEmpty(idPersonaTxt)) throw new IllegalArgumentException("Ingrese un ID de persona.");
        int idPersona = parseEntero(idPersonaTxt);
        try (Connection conn = Database.getConnection()) {
            ProfesorDAO dao = new ProfesorDAO(conn);
            return dao.buscar(idPersona);
        }
    }
    public boolean eliminar(String idPersonaTxt) throws SQLException {
        if (isEmpty(idPersonaTxt)) {
            throw new IllegalArgumentException("Ingrese un ID de persona.");
        }
        int idPersona = parseEntero(idPersonaTxt);

        try (Connection conn = Database.getConnection()) {
            ProfesorDAO dao = new ProfesorDAO(conn);
            return dao.eliminar(idPersona); // ← devuelve true/false del DAO
        }
    }


    // Opcional, si tu DAO tiene listar()
    public DefaultTableModel modeloTablaTodos() throws SQLException {
        try (Connection conn = Database.getConnection()) {
            ProfesorDAO dao = new ProfesorDAO(conn);
            List<Profesor> lista = dao.listar();
            DefaultTableModel m = new DefaultTableModel(new Object[]{"ID Persona","Contrato"}, 0);
            for (Profesor p : lista) {
                // Usa getId() si añadiste el alias en Profesor; si no, usa getIdPersona()
                m.addRow(new Object[]{ p.getId(), p.getContrato() });
            }
            return m;
        }
    }

    // ====== UTIL ======

    private void validar(String idPersonaTxt, String contrato) {
        if (isEmpty(idPersonaTxt)) {
            throw new IllegalArgumentException("Debe ingresar un ID de persona.");
        }
        parseEntero(idPersonaTxt); // valida formato
        if (isEmpty(contrato)) {
            throw new IllegalArgumentException("Debe seleccionar un contrato.");
        }
    }

    private int parseEntero(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("El ID de persona debe ser un número válido.");
        }
    }

    private boolean isEmpty(String s) { return s == null || s.trim().isEmpty(); }
    private String normalize(Object o) { return o == null ? "" : o.toString().trim(); }
}
