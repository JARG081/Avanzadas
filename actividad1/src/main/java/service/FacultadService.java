package service;

import com.mycompany.actividad1.model.Facultad;
import com.mycompany.actividad1.model.Persona;
import repository.FacultadRepository;

import java.util.List;
import java.util.Objects;

public class FacultadService {

    private final FacultadRepository repo;

    public FacultadService(FacultadRepository repo) {
        this.repo = Objects.requireNonNull(repo);
    }

    private static void validarIdPos(Double id) {
        if (id == null || id < 0) throw new IllegalArgumentException("ID de facultad inválido");
    }
    private static void validarNombre(String s) {
        if (s == null || s.isBlank()) throw new IllegalArgumentException("Nombre es obligatorio");
    }

    public void registrar(Double id, String nombre, Double idDecano) {
        validarIdPos(id);
        validarNombre(nombre);
        Persona dec = null;
        if (idDecano != null) {
            if (idDecano < 0) throw new IllegalArgumentException("ID Decano inválido");
            dec = new Persona(idDecano, null, null, null); // ligero
        }
        Facultad f = new Facultad();//coonstructor no se puede arrancar con los tipos dados
        f.setID(id);
        f.setNombre(nombre.trim());
        f.setDecano(dec);
        repo.insertar(f);
    }

    public boolean actualizar(Double id, String nombre, Double idDecano) {
        validarIdPos(id);
        validarNombre(nombre);
        Persona dec = null;
        if (idDecano != null) {
            if (idDecano < 0) throw new IllegalArgumentException("ID Decano inválido");
            dec = new Persona(idDecano, null, null, null);
        }
        Facultad f = new Facultad();//coonstructor no se puede arrancar con los tipos dados
        f.setID(id);
        f.setNombre(nombre.trim());
        f.setDecano(dec);
        return repo.actualizar(f);
    }

    public boolean eliminar(Double id) {
        validarIdPos(id);
        return repo.eliminar(id);
    }

    public Facultad buscar(Double id) {
        validarIdPos(id);
        return repo.buscarPorId(id);
    }

    public List<Facultad> listar() {
        return repo.listar();
    }
}
