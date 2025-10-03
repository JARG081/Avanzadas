package service;

import Observer.CursoObserver;
import com.mycompany.actividad1.model.Curso;
import com.mycompany.actividad1.model.Programa;
import repository.CursoRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class CursoService {

    private final CursoRepository repo;

    public CursoService(CursoRepository repo) {
        this.repo = Objects.requireNonNull(repo);
    }
    private final List<CursoObserver> observers = new CopyOnWriteArrayList<>();
    public void addObserver(CursoObserver o){ if(o!=null) observers.add(o); }
    public void removeObserver(CursoObserver o){ observers.remove(o); }

    private void notifyCursoCreado(Curso c){
        for (CursoObserver o : observers) o.onCursoCreado(c);
    }
    
    private static void validarIdPos(Double id) {
        if (id == null || id < 0) throw new IllegalArgumentException("ID de curso inválido");
    }
    private static void validarNombre(String s) {
        if (s == null || s.isBlank()) throw new IllegalArgumentException("Nombre es obligatorio");
    }

    public Curso registrar(Double id, String nombre, Double idPrograma, boolean activo) {
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
        notifyCursoCreado(c);
        return c;
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
