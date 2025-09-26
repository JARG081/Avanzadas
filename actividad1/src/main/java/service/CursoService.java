package service;

import com.mycompany.actividad1.model.Curso;
import com.mycompany.actividad1.model.Programa;
import repository.CursoRepository;

import java.util.List;
import java.util.Objects;

public class CursoService {

    private final CursoRepository repo;

    public CursoService(CursoRepository repo) {
        this.repo = Objects.requireNonNull(repo);
    }

    private static void validarIdPos(Double id) {
        if (id == null || id < 0) throw new IllegalArgumentException("ID de curso inválido");
    }
    private static void validarNombre(String s) {
        if (s == null || s.isBlank()) throw new IllegalArgumentException("Nombre es obligatorio");
    }

    public void registrar(Double id, String nombre, Double idPrograma, boolean activo) {
        validarIdPos(id);
        validarNombre(nombre);
        Programa p = null;
        if (idPrograma != null) {
            if (idPrograma < 0) throw new IllegalArgumentException("ID Programa inválido");
            p = new Programa();
            p.setId(idPrograma);
        }
        Curso c = new Curso();
        c.setID(id);
        c.setNombre(nombre.trim());
        c.setPrograma(p);
        c.setActivo(activo);
        repo.insertar(c);
    }

    public boolean actualizar(Double id, String nombre, Double idPrograma, boolean activo) {
        validarIdPos(id);
        validarNombre(nombre);
        Programa p = null;
        if (idPrograma != null) {
            if (idPrograma < 0) throw new IllegalArgumentException("ID Programa inválido");
            p = new Programa();
            p.setId(idPrograma);
        }
        Curso c = new Curso();
        c.setID(id);
        c.setNombre(nombre.trim());
        c.setPrograma(p);
        c.setActivo(activo);
        return repo.actualizar(c);
    }

    public boolean eliminar(Double id) {
        validarIdPos(id);
        return repo.eliminar(id);
    }

    public Curso buscar(Double id) {
        validarIdPos(id);
        return repo.buscarPorId(id);
    }

    public List<Curso> listar() {
        return repo.listar();
    }
}
