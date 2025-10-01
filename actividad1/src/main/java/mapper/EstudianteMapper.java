package mapper;

import com.mycompany.actividad1.model.Estudiante;
import dto.EstudianteDTO;

import java.util.ArrayList;
import java.util.List;

public class EstudianteMapper {
    public static EstudianteDTO toDTO(Estudiante m){
        if (m==null) return null;
        EstudianteDTO dto = new EstudianteDTO();
        dto.setIdPersona(m.getId());
        dto.setCodigo(m.getCodigo());
        if (m.getPrograma()!=null){
            dto.setIdPrograma(m.getPrograma().getId());
            dto.setProgramaNombre(m.getPrograma().getNombre());
        }
        dto.setNombres(m.getNombres());
        dto.setApellidos(m.getApellidos());
        dto.setEmail(m.getEmail());
        return dto;
    }
    public static List<EstudianteDTO> toDTOs(List<Estudiante> list){
        List<EstudianteDTO> out = new ArrayList<>();
        if (list!=null) for (Estudiante e : list) out.add(toDTO(e));
        return out;
    }
}
