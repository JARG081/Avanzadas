package repository;

import com.mycompany.actividad1.model.Curso;
import java.util.List;

public interface CursoRepository {
    void insertar(Curso c);
    boolean actualizar(Curso c);
    boolean eliminar(Double id);
    Curso buscarPorId(Double id);
    List<Curso> listar(); // join con programa para mostrar su nombre
}
