package controller;

import com.mycompany.actividad1.model.Facultad;
import dto.FacultadDTO;
import mapper.FacultadMapper;
import service.FacultadService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FacultadController {
    private final FacultadService service;
    public FacultadController(FacultadService service) { this.service = service; }

    private static Double parseIdReq(String s, String campo){
        if (s==null || s.isBlank()) throw new IllegalArgumentException(campo+" es obligatorio");
        try {
            Double v = Double.valueOf(s.trim());
            if (v < 0) throw new IllegalArgumentException(campo+" debe ser positivo");
            return v;
        } catch (NumberFormatException e){ throw new IllegalArgumentException(campo+" inválido"); }
    }
    private static Double parseIdOpt(String s){
        if (s==null || s.isBlank()) return null;
        return parseIdReq(s,"ID Decano");
    }

    // ---- CRUD DTO
    public void insertar(FacultadDTO dto){
        service.registrar(dto.getId(), dto.getNombre(), dto.getDecanoId());
    }
    public boolean actualizar(FacultadDTO dto){
        return service.actualizar(dto.getId(), dto.getNombre(), dto.getDecanoId());
    }
    public boolean eliminar(String idTxt){ return service.eliminar(parseIdReq(idTxt,"ID Facultad")); }

    public FacultadDTO buscar(String idTxt){
        Facultad f = service.buscar(parseIdReq(idTxt,"ID Facultad"));
        return FacultadMapper.toDTO(f);
    }
    public List<FacultadDTO> listar(){ return FacultadMapper.toDTOs(service.listar()); }

    public DefaultTableModel modeloTablaTodas(){
        DefaultTableModel m = new DefaultTableModel(
            new Object[]{"ID","Nombre","Decano (ID)","Decano"}, 0){
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        for (FacultadDTO f : listar()){
            m.addRow(new Object[]{
                f.getId(),
                f.getNombre(),
                f.getDecanoId(),
                f.getDecanoNombre()==null? "" : f.getDecanoNombre()
            });
        }
        return m;
    }

    public DefaultTableModel modeloTablaDe(FacultadDTO f){
        DefaultTableModel m = new DefaultTableModel(
            new Object[]{"ID","Nombre","Decano (ID)","Decano"}, 0){
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        if (f!=null) m.addRow(new Object[]{
            f.getId(),
            f.getNombre(),
            f.getDecanoId(),
            f.getDecanoNombre()==null? "" : f.getDecanoNombre()
        });
        return m;
    }

    // Helper para UI/Consola
    public FacultadDTO buildFromStrings(String id, String nombre, String decanoId){
        FacultadDTO dto = new FacultadDTO();
        dto.setId(parseIdReq(id,"ID Facultad"));
        dto.setNombre(nombre);
        dto.setDecanoId(parseIdOpt(decanoId));
        return dto;
    }
}
