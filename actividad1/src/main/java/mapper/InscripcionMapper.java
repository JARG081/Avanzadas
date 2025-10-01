package mapper;

import com.mycompany.actividad1.model.Inscripcion;
import dto.InscripcionDTO;

import java.util.ArrayList;
import java.util.List;

public class InscripcionMapper {
    public static InscripcionDTO toDTO(Inscripcion m){
        if (m==null) return null;
        InscripcionDTO dto = new InscripcionDTO();
        if (m.getCurso()!=null){
            dto.setCursoId(m.getCurso().getID());
            dto.setCursoNombre(m.getCurso().getNombre());
        }
        if (m.getEstudiante()!=null){
            dto.setEstudianteId(m.getEstudiante().getId());
            String nom = m.getEstudiante().getNombres()==null? "" : m.getEstudiante().getNombres();
            String ape = m.getEstudiante().getApellidos()==null? "" : m.getEstudiante().getApellidos();
            String full = (nom+" "+ape).trim();
            dto.setEstudianteNombre(full.isEmpty()? null : full);
        }
        dto.setAnio(m.getAnio());
        dto.setSemestre(m.getSemestre());
        return dto;
    }

    public static List<InscripcionDTO> toDTOs(List<Inscripcion> list){
        List<InscripcionDTO> out = new ArrayList<>();
        if (list!=null) for (Inscripcion i : list) out.add(toDTO(i));
        return out;
    }
}
