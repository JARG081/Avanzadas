package repository;

import com.mycompany.actividad1.model.Curso;
import java.util.List;

public interface CursoRepository {
    void insertar(Curso curso);
    boolean actualizar(Curso curso);
    boolean eliminar(Double id);
    Curso buscarPorId(Double id);
    List<Curso> listar();
}
