package repository;

import com.mycompany.actividad1.model.Inscripcion;
import java.util.List;

public interface CursosInscritosRepository {
    boolean insertar(Inscripcion i);
    Inscripcion buscar(Double cursoId, Double estId, Integer anio, Integer semestre);
    boolean actualizar(Inscripcion nueva, Double cursoIdOld, Double estIdOld, Integer anioOld, Integer semOld);
    boolean eliminar(Double cursoId, Double estId, Integer anio, Integer semestre);
    boolean existe(Double cursoId, Double estId, Integer anio, Integer semestre);
    List<Inscripcion> listar();
}
