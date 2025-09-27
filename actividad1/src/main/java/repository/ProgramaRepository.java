package repository;

import com.mycompany.actividad1.model.Programa;
import java.util.List;

public interface ProgramaRepository {
    void insertar(Programa p);
    boolean actualizar(Programa p);
    boolean eliminar(Double id);
    Programa buscarPorId(Double id);
    List<Programa> listar();
}
