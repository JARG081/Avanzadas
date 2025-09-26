package repository;

import com.mycompany.actividad1.model.Persona;
import java.util.List;

public interface PersonaRepository {
    void insertar(Persona persona);
    boolean actualizar(Persona persona);
    boolean eliminar(Double id);
    Persona buscarPorId(Double id);
    List<Persona> listar();
}
