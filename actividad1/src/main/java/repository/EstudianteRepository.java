package repository;

import com.mycompany.actividad1.model.Estudiante;
import java.util.List;

public interface EstudianteRepository {
    void insertar(Estudiante est);
    boolean actualizar(Estudiante est);
    boolean eliminar(Double idPersona);
    Estudiante buscarPorIdPersona(Double idPersona);
    List<Estudiante> listar();
}
