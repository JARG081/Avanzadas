package mapper;

import com.mycompany.actividad1.model.Curso;
import com.mycompany.actividad1.model.Programa;
import dto.CursoDTO;

public final class CursoMapper {
    private CursoMapper(){}

    public static CursoDTO toDTO(Curso c) {
        if (c == null) return null;
        Double progId = null;
        String progNombre = null;
        Programa p = c.getPrograma();
        if (p != null) {
            progId = p.getId();
            progNombre = p.getNombre();
        }
        return new CursoDTO(c.getID(), c.getNombre(), progId, progNombre, c.getActivo());
    }

    public static Curso toModel(CursoDTO d) {
        if (d == null) return null;
        Curso c = new Curso();
        c.setID(d.getId());
        c.setNombre(d.getNombre());
        c.setActivo(d.getActivo());
        if (d.getProgramaId() != null || d.getProgramaNombre() != null) {
            Programa p = new Programa();
            p.setId(d.getProgramaId());
            p.setNombre(d.getProgramaNombre());
            c.setPrograma(p);
        }
        return c;
    }
}
