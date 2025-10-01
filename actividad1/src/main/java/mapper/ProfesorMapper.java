// src/main/java/mapper/ProfesorMapper.java
package mapper;

import com.mycompany.actividad1.model.Profesor;
import dto.ProfesorDTO;

import java.util.ArrayList;
import java.util.List;

public class ProfesorMapper {
    public static ProfesorDTO toDTO(Profesor m) {
        if (m == null) return null;
        ProfesorDTO dto = new ProfesorDTO(m.getIdPersona(), m.getContrato());
        if (m.getPersona()!=null) {
            dto.setNombres(m.getPersona().getNombres());
            dto.setApellidos(m.getPersona().getApellidos());
        }
        return dto;
    }
    public static List<ProfesorDTO> toDTOs(List<Profesor> list) {
        List<ProfesorDTO> out = new ArrayList<>();
        if (list!=null) for (Profesor p : list) out.add(toDTO(p));
        return out;
    }
}
