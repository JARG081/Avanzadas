package repository;

import com.mycompany.actividad1.model.Inscripcion;
import java.util.List;

public interface InscripcionRepository {
    boolean insertar(Inscripcion insc);
    Inscripcion buscar(Double idCurso, Double idEstudiante, Integer anio, Integer semestre);
    boolean actualizar(Inscripcion nueva,
                       Double idCursoOld, Double idEstOld, Integer anioOld, Integer semOld);
    boolean eliminar(Double idCurso, Double idEstudiante, Integer anio, Integer semestre);
    boolean existe(Double idCurso, Double idEstudiante, Integer anio, Integer semestre);
    List<Inscripcion> listar();
}
