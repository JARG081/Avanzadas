package repository;

import com.mycompany.actividad1.model.Facultad;
import java.util.List;

public interface FacultadRepository {
    void insertar(Facultad f);
    boolean actualizar(Facultad f);
    boolean eliminar(Double id);
    Facultad buscarPorId(Double id);
    List<Facultad> listar();
}
