package controller;

import com.mycompany.actividad1.dao.PersonaDAO;
import com.mycompany.actividad1.model.Persona;

import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador para las operaciones de Persona.
 * - Valida entradas de la Vista.
 * - Orquesta llamadas al DAO.
 * - Formatea datos para la Vista (por ejemplo, DefaultTableModel).
 */
public class PersonaController {

    private final PersonaDAO dao;

    public PersonaController() {
        this.dao = new PersonaDAO();
    }

    // ---------- Acciones de caso de uso ----------

    public void insertar(String id, String nombres, String apellidos, String email)
            throws SQLException, IllegalArgumentException {

        validarCamposObligatorios(id, nombres, apellidos, email);
        Persona nueva = new Persona(Double.valueOf(id), nombres, apellidos, email);
        dao.insertar(nueva);
    }

    public boolean actualizar(String id, String nombres, String apellidos, String email)
            throws SQLException, IllegalArgumentException {

        validarCamposObligatorios(id, nombres, apellidos, email);
        Persona actualizada = new Persona(Double.valueOf(id), nombres, apellidos, email);
        return dao.actualizar(actualizada);
    }

    public boolean eliminar(String id) throws SQLException, IllegalArgumentException {
        if (vacio(id)) throw new IllegalArgumentException("Ingrese un ID para eliminar");
        long idLong = Long.parseLong(id.trim());
        return dao.eliminar(idLong);
    }

    public Persona buscar(String id) throws SQLException, IllegalArgumentException {
        if (vacio(id)) throw new IllegalArgumentException("Ingrese un ID para buscar");
        double idDouble = Double.parseDouble(id.trim());
        return dao.buscarPorId(idDouble);
    }

    public List<Persona> listar() throws SQLException {
        return dao.listar();
    }

    // ---------- Helpers para la vista ----------

    public DefaultTableModel modeloTablaTodas() throws SQLException {
        List<Persona> lista = listar();
        return construirModeloTabla(lista);
    }

    public DefaultTableModel modeloTablaDe(Persona p) {
        DefaultTableModel m = crearModelo();
        if (p != null) {
            m.addRow(new Object[]{ p.getId(), p.getNombres(), p.getApellidos(), p.getEmail() });
        }
        return m;
    }

    // ---------- Validaciones y util ----------

    private void validarCamposObligatorios(String id, String nombres, String apellidos, String email) {
        if (vacio(id) || vacio(nombres) || vacio(apellidos) || vacio(email)) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }
        // Validaciones adicionales (email, rangos, etc.) si lo deseas
    }

    private boolean vacio(String s) {
        return s == null || s.trim().isEmpty();
    }

    private DefaultTableModel crearModelo() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Email");
        return modelo;
    }

    private DefaultTableModel construirModeloTabla(List<Persona> lista) {
        DefaultTableModel modelo = crearModelo();
        if (lista != null) {
            for (Persona p : lista) {
                modelo.addRow(new Object[]{ p.getId(), p.getNombres(), p.getApellidos(), p.getEmail() });
            }
        }
        return modelo;
    }
}
