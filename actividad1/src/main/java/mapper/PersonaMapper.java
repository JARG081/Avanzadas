package mapper;

import com.mycompany.actividad1.model.Persona;
import dto.PersonaDTO;

import java.util.List;
import java.util.stream.Collectors;

public final class PersonaMapper {
    private PersonaMapper(){}

    public static PersonaDTO toDTO(Persona p){
        if (p == null) return null;
        return new PersonaDTO(p.getId(), p.getNombres(), p.getApellidos(), p.getEmail());
    }

    public static Persona fromDTO(PersonaDTO dto){
        if (dto == null) return null;
        Persona p = new Persona();
        p.setId(dto.getId());
        p.setNombres(dto.getNombres());
        p.setApellidos(dto.getApellidos());
        p.setEmail(dto.getEmail());
        return p;
    }

    public static List<PersonaDTO> toDTOs(List<Persona> list){
        return list==null ? List.of() : list.stream().map(PersonaMapper::toDTO).collect(Collectors.toList());
    }
}
