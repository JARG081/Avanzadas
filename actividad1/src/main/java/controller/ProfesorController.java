// src/main/java/controller/ProfesorController.java
package controller;

import com.mycompany.actividad1.model.Profesor;
import dto.ProfesorDTO;
import mapper.ProfesorMapper;
import service.ProfesorService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProfesorController {
    private final ProfesorService service;
    public ProfesorController(ProfesorService service) { this.service = service; }

    private static Double parseId(String s) {
        if (s == null || s.isBlank()) throw new IllegalArgumentException("ID Persona es obligatorio");
        try {
            Double v = Double.valueOf(s.trim());
            if (v < 0) throw new IllegalArgumentException("ID Persona debe ser positivo");
            return v;
        } catch (NumberFormatException e) { throw new IllegalArgumentException("ID Persona inválido"); }
    }
    private static String norm(Object o){ return o==null? "" : o.toString().trim(); }

    // ===== CRUD DTO =====
    public void insertar(ProfesorDTO dto) {
        String contrato = norm(dto.getContrato());
        if (contrato.isBlank()) throw new IllegalArgumentException("Debe seleccionar un contrato.");
        service.registrar(dto.getIdPersona(), contrato);
    }
    public boolean actualizar(ProfesorDTO dto) {
        String contrato = norm(dto.getContrato());
        if (contrato.isBlank()) throw new IllegalArgumentException("Debe seleccionar un contrato.");
        return service.actualizar(dto.getIdPersona(), contrato);
    }
    public boolean eliminar(String idPersonaTxt) { return service.eliminar(parseId(idPersonaTxt)); }
    public ProfesorDTO buscar(String idPersonaTxt) {
        Profesor m = service.buscar(parseId(idPersonaTxt));
        return ProfesorMapper.toDTO(m);
    }
    public List<ProfesorDTO> listar() { return ProfesorMapper.toDTOs(service.listar()); }

    public DefaultTableModel modeloTablaTodos() {
        DefaultTableModel m = new DefaultTableModel(new Object[]{"ID Persona","Contrato"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        for (ProfesorDTO p : listar()) m.addRow(new Object[]{ p.getIdPersona(), p.getContrato() });
        return m;
    }
}
