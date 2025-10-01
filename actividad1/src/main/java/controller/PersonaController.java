package controller;

import dto.PersonaDTO;
import mapper.PersonaMapper;
import service.PersonaService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PersonaController {
    private final PersonaService service;
    public PersonaController(PersonaService service) { this.service = service; }

    private static Double parseId(String s){
        if (s == null || s.isBlank()) throw new IllegalArgumentException("ID es obligatorio");
        try {
            Double v = Double.valueOf(s.trim());
            if (v < 0) throw new IllegalArgumentException("ID debe ser positivo");
            return v;
        } catch (NumberFormatException e) { throw new IllegalArgumentException("ID no es numérico"); }
    }

    // ===== CRUD basado en DTO =====
    public void insertar(PersonaDTO dto) {
        service.registrar(dto.getId(), dto.getNombres(), dto.getApellidos(), dto.getEmail());
    }

    public boolean actualizar(PersonaDTO dto) {
        return service.actualizar(dto.getId(), dto.getNombres(), dto.getApellidos(), dto.getEmail());
    }

    public boolean eliminar(String idTxt) {
        return service.eliminar(parseId(idTxt));
    }

    public PersonaDTO buscar(String idTxt) {
        var p = service.buscar(parseId(idTxt));
        return PersonaMapper.toDTO(p);
    }

    public List<PersonaDTO> listar() {
        return PersonaMapper.toDTOs(service.listar());
    }

    public DefaultTableModel modeloTablaTodas() {
        String[] cols = {"ID", "Nombres", "Apellidos", "Email"};
        DefaultTableModel m = new DefaultTableModel(cols, 0){ @Override public boolean isCellEditable(int r,int c){return false;} };
        for (PersonaDTO p : listar()) {
            m.addRow(new Object[]{ p.getId(), p.getNombres(), p.getApellidos(), p.getEmail() });
        }
        return m;
    }
}
