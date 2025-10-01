package mapper;

import com.mycompany.actividad1.model.Facultad;
import dto.FacultadDTO;

import java.util.ArrayList;
import java.util.List;

public class FacultadMapper {
    public static FacultadDTO toDTO(Facultad m){
        if (m==null) return null;
        FacultadDTO dto = new FacultadDTO();
        dto.setId(m.getID());
        dto.setNombre(m.getNombre());
        if (m.getDecano()!=null){
            dto.setDecanoId(m.getDecano().getId());
            String nom = (m.getDecano().getNombres()==null? "" : m.getDecano().getNombres());
            String ape = (m.getDecano().getApellidos()==null? "" : m.getDecano().getApellidos());
            String full = (nom + " " + ape).trim();
            dto.setDecanoNombre(full.isEmpty()? null : full);
        }
        return dto;
    }

    public static List<FacultadDTO> toDTOs(List<Facultad> list){
        List<FacultadDTO> out = new ArrayList<>();
        if (list!=null) for (Facultad f : list) out.add(toDTO(f));
        return out;
    }
}
