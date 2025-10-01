package mapper;

import com.mycompany.actividad1.model.Programa;
import dto.ProgramaDTO;

import java.util.ArrayList;
import java.util.List;

public class ProgramaMapper {
    public static ProgramaDTO toDTO(Programa m){
        if (m==null) return null;
        ProgramaDTO dto = new ProgramaDTO();
        dto.setId(m.getId());
        dto.setNombre(m.getNombre());
        dto.setDuracion(m.getDuracion());
        dto.setRegistro(m.getRegistro()==null? null : m.getRegistro().toString());
        if (m.getFacultad()!=null){
            dto.setIdFacultad(m.getFacultad().getID());
            dto.setFacultadNombre(m.getFacultad().getNombre());
        }
        return dto;
    }
    public static List<ProgramaDTO> toDTOs(List<Programa> list){
        List<ProgramaDTO> out = new ArrayList<>();
        if (list!=null) for (Programa p : list) out.add(toDTO(p));
        return out;
    }
}

