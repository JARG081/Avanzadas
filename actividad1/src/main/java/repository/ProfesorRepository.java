package repository;

import com.mycompany.actividad1.model.Profesor;
import java.util.List;

public interface ProfesorRepository {
    void insertar(Profesor profesor);
    boolean actualizar(Profesor profesor);
    boolean eliminar(Double idPersona);
    Profesor buscarPorIdPersona(Double idPersona);
    List<Profesor> listar();
}
