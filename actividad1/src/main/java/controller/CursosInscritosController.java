package controller;

import com.mycompany.actividad1.model.Inscripcion;
import dto.InscripcionDTO;
import mapper.InscripcionMapper;
import service.CursosInscritosService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CursosInscritosController {
    private final CursosInscritosService service;
    public CursosInscritosController(CursosInscritosService service){ this.service = service; }

    private static Double pD(String s, String campo){
        if (s==null || s.isBlank()) throw new IllegalArgumentException(campo+" es obligatorio");
        try{
            Double v = Double.valueOf(s.trim());
            if (v<0) throw new IllegalArgumentException(campo+" debe ser positivo");
            return v;
        }catch(NumberFormatException e){ throw new IllegalArgumentException(campo+" inválido"); }
    }
    private static Integer pI(String s, String campo){
        if (s==null || s.isBlank()) throw new IllegalArgumentException(campo+" es obligatorio");
        try{ return Integer.valueOf(s.trim()); }
        catch(NumberFormatException e){ throw new IllegalArgumentException(campo+" inválido"); }
    }

    // ---------- Builders para UI/Consola ----------
    public InscripcionDTO build(String cursoId, String estId, String anio, String semestre){
        InscripcionDTO dto = new InscripcionDTO();
        dto.setCursoId(pD(cursoId,"ID Curso"));
        dto.setEstudianteId(pD(estId,"ID Estudiante"));
        dto.setAnio(pI(anio,"Año"));
        dto.setSemestre(pI(semestre,"Semestre"));
        return dto;
    }

    // ---------- CRUD basado en DTO ----------
    public boolean insertar(InscripcionDTO dto){
        service.registrar(dto.getCursoId(), dto.getEstudianteId(), dto.getAnio(), dto.getSemestre());
        return true;
    }

    public boolean actualizar(InscripcionDTO oldDto, InscripcionDTO newDto){
        return service.actualizar(
            oldDto.getCursoId(), oldDto.getEstudianteId(), oldDto.getAnio(), oldDto.getSemestre(),
            newDto.getCursoId(), newDto.getEstudianteId(), newDto.getAnio(), newDto.getSemestre()
        );
    }

    public boolean eliminar(InscripcionDTO dto){
        return service.eliminar(dto.getCursoId(), dto.getEstudianteId(), dto.getAnio(), dto.getSemestre());
    }

    public InscripcionDTO buscar(InscripcionDTO key){
        Inscripcion i = service.buscar(key.getCursoId(), key.getEstudianteId(), key.getAnio(), key.getSemestre());
        return InscripcionMapper.toDTO(i);
    }

    public List<InscripcionDTO> listar(){
        return InscripcionMapper.toDTOs(service.listar());
    }

    public DefaultTableModel modeloTablaTodos(){
        DefaultTableModel m = new DefaultTableModel(
            new Object[]{"ID Curso","Curso","ID Est","Estudiante","Año","Semestre"}, 0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        for (InscripcionDTO i : listar()){
            m.addRow(new Object[]{
                i.getCursoId(),
                i.getCursoNombre()==null? "" : i.getCursoNombre(),
                i.getEstudianteId(),
                i.getEstudianteNombre()==null? "" : i.getEstudianteNombre(),
                i.getAnio(),
                i.getSemestre()
            });
        }
        return m;
    }
}
