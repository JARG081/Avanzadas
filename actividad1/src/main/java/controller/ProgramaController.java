package controller;

import com.mycompany.actividad1.model.Programa;
import dto.ProgramaDTO;
import mapper.ProgramaMapper;
import service.ProgramaService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProgramaController {
    private final ProgramaService service;
    public ProgramaController(ProgramaService service) { this.service = service; }

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
        return parseIdReq(s,"ID Facultad");
    }
    private static Integer parseIntReq(String s, String campo){
        if (s==null || s.isBlank()) throw new IllegalArgumentException(campo+" es obligatorio");
        try { return Integer.valueOf(s.trim()); }
        catch(NumberFormatException e){ throw new IllegalArgumentException(campo+" inválido"); }
    }

    
    public void insertar(ProgramaDTO dto){
        service.registrar(dto.getId(), dto.getNombre(),
                String.valueOf(dto.getDuracion()),
                dto.getRegistro(),
                dto.getIdFacultad());
    }
    public boolean actualizar(ProgramaDTO dto){
        return service.actualizar(dto.getId(), dto.getNombre(),
                String.valueOf(dto.getDuracion()),
                dto.getRegistro(),
                dto.getIdFacultad());
    }
    public boolean eliminar(String idTxt){ return service.eliminar(parseIdReq(idTxt,"ID Programa")); }

    public ProgramaDTO buscar(String idTxt){
        Programa p = service.buscar(parseIdReq(idTxt,"ID Programa"));
        return ProgramaMapper.toDTO(p);
    }
    public List<ProgramaDTO> listar(){ return ProgramaMapper.toDTOs(service.listar()); }

    public DefaultTableModel modeloTablaTodos() {
        DefaultTableModel m = new DefaultTableModel(
            new Object[]{"ID","Nombre","Duración","Registro","Facultad"}, 0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        for (ProgramaDTO p : listar()) {
            m.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getDuracion(), p.getRegistro(),
                p.getFacultadNombre()==null? "" : p.getFacultadNombre()
            });
        }
        return m;
    }

    public DefaultTableModel modeloTablaDe(ProgramaDTO p) {
        DefaultTableModel m = new DefaultTableModel(
            new Object[]{"ID","Nombre","Duración","Registro","Facultad"}, 0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        if (p!=null) m.addRow(new Object[]{
            p.getId(), p.getNombre(), p.getDuracion(), p.getRegistro(),
            p.getFacultadNombre()==null? "" : p.getFacultadNombre()
        });
        return m;
    }

   
    public ProgramaDTO buildFromStrings(String id, String nombre, String duracion, String registro, String idFacultad){
        ProgramaDTO dto = new ProgramaDTO();
        dto.setId(parseIdReq(id,"ID Programa"));
        dto.setNombre(nombre);
        dto.setDuracion(parseIntReq(duracion,"Duración"));
        dto.setRegistro(registro);
        dto.setIdFacultad(parseIdOpt(idFacultad));
        return dto;
    }
}
